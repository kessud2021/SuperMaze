# 🌀 SuperMaze

**SuperMaze** is a maze game built with **HTML, CSS, and JavaScript**, featuring both singleplayer and multiplayer modes.  
It uses a **Node.js + WebSocket server** for real-time multiplayer and admin dashboard features.  
Each run generates a maze, and you can track players live in the admin panel.

---

## 🎮 Features

- 🧩 **Dynamic Maze Editor** — create mazes from 5x5 to 50x50
- 🧍 **Singleplayer Mode** — play solo
- 🌐 **Multiplayer Mode** — connect via WebSocket server
- 🟩 **Start (Green)** and 🟥 **End (Red)** markers
- 🏆 **Victory Screen** and leaderboard
- 🎨 **Glass / Aero UI** with gradient backgrounds
- 💾 **Save & Load** mazes locally (JSON)
- 🖥️ **Admin Panel**:
  - Live player tracking
  - Server dashboard with online/offline status
  - Home screen title customization
- ⏱️ **Server Time** display in admin panel

---

## 🚀 Getting Started

### 🔧 Requirements
- Node.js 18+  
- Modern web browser (Chrome, Edge, Firefox, etc.)

---

### 🕹️ Singleplayer Setup

```bash
git clone https://github.com/kessud2021/SuperMaze.git
cd SuperMaze
```
Open `index.html` in your browser.

Steps:

Press **Play Maze**

Load a saved maze or create a new one in the editor

Use **WASD** to move

Reach the **End (Blue)** cell to finish the maze

Time is automatically recorded for the leaderboard

## 🌐 Multiplayer Setup

Install dependencies:
`
npm install express ws
`

Start the server:
`
node server.js
`

The server will:

- Host the leaderboard

- Handle saving maze scores

- Allow real-time dashboard stats

- Serve /public files

## 🔐 Admin Panel

The admin panel is used to:

- Edit front-page title

- Manage mazes

- View the real-time dashboard

- Change server configuration

Default password

`boorger123`

➡️ You can change this inside `public/index.html` — fully customizable.

## 📊 Real-time Dashboard

A separate dashboard page shows:

- Active players

- Leaderboard updates

- Requests per second

- Server uptime

- WebSocket connections

- Updates in real time through WebSockets.

## 📁 Project Structure
```
SuperMaze/
 ├── public/
 │   ├── index.html
 │   ├── editor.html
 │   ├── dashboard.html
 │   ├── style.css
 │   └── main.js
 ├── server.js
 └── README.md
```

## ✔️ Features

- Fully working Maze Editor

- Play Maze (with WASD)

- Transparent glossy UI (Aero style)

Leaderboard JSON storage

- Admin panel with password

- Real-time dashboard

- Multiplayer-compatible server
