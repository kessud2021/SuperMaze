package utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatManager {
    public static void logChatToFile(String maze, String player, String message) {
        try {
            String timestamp = new SimpleDateFormat("dd-MM-yyyy (HH:mm)").format(new Date());
            File folder = new File("chatlogs");
            if (!folder.exists()) folder.mkdirs();

            File file = new File(folder, maze + ".txt");
            try (FileWriter writer = new FileWriter(file, true)) {
                writer.write("[" + timestamp + "] {" + maze + "} " + player + ": " + message + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
