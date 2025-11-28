// server.js
const express = require("express");
const fs = require("fs");
const path = require("path");
const WebSocket = require("ws");

const app = express();
const PORT = 3000;

// Serve static frontend from 'public'
app.use(express.json());
app.use(express.static(path.join(__dirname, "public")));

const leaderboardFile = path.join(__dirname, "leaderboard.json");
if (!fs.existsSync(leaderboardFile)) fs.writeFileSync(leaderboardFile, JSON.stringify([]));

// Leaderboard endpoints
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

// WebSocket server for live tracking
const wss = new WebSocket.Server({ server });
let players = {}; // { id: { username, x, y, lastSeen } }

wss.on("connection", (ws) => {
  const id = Math.random().toString(36).slice(2);
  players[id] = { username: "Unknown", x: 0, y: 0, lastSeen: Date.now() };

  // send current players to newly connected client
  ws.send(JSON.stringify({ type: "players", players }));

  ws.on("message", (msg) => {
    let data;
    try { data = JSON.parse(msg); } catch (e) { return; }

    if (data.type === "join") {
      players[id].username = data.username || players[id].username;
      players[id].lastSeen = Date.now();
    }

    if (data.type === "move") {
      // expect {type:"move", x: number, y: number}
      if (typeof data.x === "number" && typeof data.y === "number") {
        players[id].x = data.x;
        players[id].y = data.y;
        players[id].lastSeen = Date.now();
      }
    }

    // broadcast updated players to all clients
    broadcastPlayers();
  });

  ws.on("close", () => {
    delete players[id];
    broadcastPlayers();
  });

  ws.on("error", () => {
    delete players[id];
    broadcastPlayers();
  });
});

// Periodically prune stale players (in case a client disconnects badly)
setInterval(() => {
  const now = Date.now();
  let changed = false;
  for (const id of Object.keys(players)) {
    if (now - players[id].lastSeen > 1000 * 60 * 5) { // 5 minutes stale
      delete players[id];
      changed = true;
    }
  }
  if (changed) broadcastPlayers();
}, 60 * 1000);

function broadcastPlayers() {
  const payload = JSON.stringify({ type: "players", players });
  wss.clients.forEach((client) => {
    if (client.readyState === WebSocket.OPEN) client.send(payload);
  });
}

if (!fs.existsSync('leaderboard.json')) {
    fs.writeFileSync('leaderboard.json', '[]');
}

vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
