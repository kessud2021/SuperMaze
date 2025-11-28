// --- SuperMaze Server ---
// Handles HTTP, leaderboard, and WebSocket for real-time tracking/dashboard

const express = require("express");
const fs = require("fs");
const path = require("path");
const WebSocket = require("ws");

const app = express();
const PORT = 3000;

// Serve static frontend (HTML/CSS/JS)
app.use(express.json());
app.use(express.static(path.join(__dirname, "public")));

// Leaderboard file
const leaderboardFile = path.join(__dirname, "leaderboard.json");
if (!fs.existsSync(leaderboardFile)) fs.writeFileSync(leaderboardFile, JSON.stringify([]));

// --- Leaderboard endpoints ---
app.get("/leaderboard", (req, res) => {
  try {
    const data = JSON.parse(fs.readFileSync(leaderboardFile, "utf8"));
    data.sort((a, b) => a.time - b.time);
    res.json(data);
  } catch (err) {
    res.status(500).json({ error: "Failed to read leaderboard" });
  }
});

app.post("/leaderboard", (req, res) => {
  const { name, time } = req.body;
  if (!name || time == null) return res.status(400).json({ error: "Missing name or time" });

  try {
    const data = JSON.parse(fs.readFileSync(leaderboardFile, "utf8"));
    data.push({ name, time, date: new Date().toISOString() });
    fs.writeFileSync(leaderboardFile, JSON.stringify(data, null, 2));
    res.json({ success: true });
  } catch (err) {
    res.status(500).json({ error: "Failed to write leaderboard" });
  }
});

// Start HTTP server
const server = app.listen(PORT, () => {
  console.log(`Server running at http://localhost:${PORT}`);
});

// --- WebSocket server for live tracking & dashboard ---
const wss = new WebSocket.Server({ server });

// Structure: server -> players
let servers = {
  "server1": { name: "Server 1", online: true, players: {} }
};

// Broadcast data to all connected clients
function broadcast() {
  const payloadPlayers = JSON.stringify({
    type: "players",
    players: servers["server1"].players
  });
  const payloadServers = JSON.stringify({
    type: "servers",
    servers: Object.fromEntries(Object.entries(servers).map(([id, s]) => [
      id, { name: s.name, online: s.online, players: Object.values(s.players) }
    ]))
  });

  wss.clients.forEach(client => {
    if (client.readyState === WebSocket.OPEN) {
      client.send(payloadPlayers);
      client.send(payloadServers);
    }
  });
}

// Handle WebSocket connections
wss.on("connection", (ws) => {
  const id = Math.random().toString(36).slice(2);
  servers["server1"].players[id] = { username: "Unknown", x: 0, y: 0, lastSeen: Date.now() };

  // Send initial data
  ws.send(JSON.stringify({ type: "players", players: servers["server1"].players }));
  ws.send(JSON.stringify({ type: "servers", servers: {
    server1: { name: "Server 1", online: true, players: Object.values(servers["server1"].players) }
  }}));

  ws.on("message", (msg) => {
    let data;
    try { data = JSON.parse(msg); } catch (e) { return; }

    if (data.type === "join") {
      servers["server1"].players[id].username = data.username || servers["server1"].players[id].username;
      servers["server1"].players[id].lastSeen = Date.now();
    }

    if (data.type === "move") {
      if (typeof data.x === "number" && typeof data.y === "number") {
        servers["server1"].players[id].x = data.x;
        servers["server1"].players[id].y = data.y;
        servers["server1"].players[id].lastSeen = Date.now();
      }
    }

    broadcast();
  });

  ws.on("close", () => {
    delete servers["server1"].players[id];
    broadcast();
  });

  ws.on("error", () => {
    delete servers["server1"].players[id];
    broadcast();
  });
});

// Remove stale players every minute
setInterval(() => {
  const now = Date.now();
  let changed = false;
  for (const id in servers["server1"].players) {
    if (now - servers["server1"].players[id].lastSeen > 1000 * 60 * 5) {
      delete servers["server1"].players[id];
      changed = true;
    }
  }
  if (changed) broadcast();
}, 60 * 1000);
