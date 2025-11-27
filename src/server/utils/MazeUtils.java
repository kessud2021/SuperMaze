package utils;

import java.io.*;
import java.util.*;

public class MazeUtils {
    public static String generateMazeJSON(int width, int height) {
        Random r = new Random();
        int[][] maze = new int[height][width];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                maze[y][x] = r.nextBoolean() ? 1 : 0;

        StringBuilder json = new StringBuilder("{\"maze\":[");
        for (int y = 0; y < height; y++) {
            json.append("[");
            for (int x = 0; x < width; x++)
                json.append(maze[y][x]).append(x < width - 1 ? "," : "");
            json.append("]").append(y < height - 1 ? "," : "");
        }
        json.append("]}");
        return json.toString();
    }
}
    