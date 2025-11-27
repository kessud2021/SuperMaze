from flask import Flask, jsonify
import random

app = Flask(__name__)

def generate_maze(size):
    maze = [[1 for _ in range(size)] for _ in range(size)]
    stack = [(1, 1)]
    maze[1][1] = 0
    dirs = [(0, 2), (0, -2), (2, 0), (-2, 0)]

    while stack:
        x, y = stack[-1]
        neighbors = []
        for dx, dy in dirs:
            nx, ny = x + dx, y + dy
            if 1 < nx < size - 1 and 1 < ny < size - 1 and maze[ny][nx] == 1:
                neighbors.append((nx, ny))
        if neighbors:
            nx, ny = random.choice(neighbors)
            maze[ny][nx] = 0
            maze[y + (ny - y)//2][x + (nx - x)//2] = 0
            stack.append((nx, ny))
        else:
            stack.pop()
    return maze

@app.route("/maze/<int:size>")
def get_maze(size):
    maze = generate_maze(size)
    return jsonify(maze)

if __name__ == "__main__":
    app.run(port=8081)
