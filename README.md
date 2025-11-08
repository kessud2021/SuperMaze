# 🌀 SuperMaze

**SuperMaze** is a maze game built with **HTML, CSS, and JavaScript**, featuring both singleplayer and multiplayer modes.  
You can connect it to a **Java-based Maze Server** if you want to play with friends.  
Each run generates a brand-new maze — so no two games are ever the same.

---

## 🎮 Features

- 🧩 **Dynamic Maze Generation** (via API)
- 🧍 **Singleplayer Mode** — explore solo
- 🌐 **Multiplayer Mode** — connect via WebSocket
- 🟩 **Start (Green)** and 🟥 **End (Red)** markers
- 🏆 **Victory Screen** when you reach the goal
- 🎨 **Themes** — Light, Dark, Blue, Green, Pink, Red
- 💾 **Export Options** — save your maze as JSON or XML

---

## 🚀 Getting Started

### 🔧 Requirements
- A modern web browser (Chrome, Edge, Firefox, etc.)
- Optional: **Java 17+** if you want to run the Maze Server

---

### 🕹️ Singleplayer Setup

```bash
git clone https://github.com/kessud2021/SuperMaze.git
cd SuperMaze
Then open index.html in your browser.
```

Select Singleplayer

Choose your theme

Press Start Game

Use your Arrow Keys to move

Reach the red square to win 🏆

## 🌐 Multiplayer Setup (Optional)
Compile and run the Maze Server Java project yourself:
```
javac -d out src/**/*.java
java -cp out VSSCO.supermazeserver.Main
```
Get your local IP address or server IP


In the game, select Multiplayer

Enter the server address and start playing with friends!

## 🧠 API
The maze layout is generated using a Maze Generator API.
You can swap in your own API or host one locally for full control.

## 🎨 Customization
Change maze size in script.js

```
const size = 20;
```
Update API URL inside generateMaze()

Modify wall and cell colors in style.css

Edit the victory screen style at the bottom of script.js

## 🧩 Planned Additions
Player stats & victory tracking (MySQL / PHP / Node)

Local leaderboard

Pathfinding-based maze generation

Sounds and better animations

🧑‍💻 Built With
HTML5

CSS3 (Bootstrap 5)

Vanilla JavaScript

WebSocket / Java (for server)

## 🧠 Notes
Players who want to use the multiplayer mode must compile the Java server themselves.
The client (this repo) just connects through WebSockets.

## 📜 License
Open source.
Feel free to fork, edit, or improve — just give credit somewhere! ❤️
