# Game Mechanics

## Maze Generation
- Maze can be procedural or manually defined.
- Grid is represented as a 2D array.
- Walls are obstacles; paths are navigable.

## Player Movement
- Controlled via keyboard events (`keydown`).
- Collision detection prevents walking through walls.
- Smooth movement is implemented using `requestAnimationFrame`.

## Victory Conditions
- Player wins by reaching the maze’s end point.
- Victory triggers a screen display and optional leaderboard update.
