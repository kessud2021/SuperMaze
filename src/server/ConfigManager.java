package server;

import java.io.*;
import java.util.*;

public class ConfigManager {

    public static Properties loadOrCreate(String fileName, Map<String, String> defaults) {
        Properties props = new Properties();
        File file = new File(fileName);

        if (!file.exists()) {
            try (FileWriter fw = new FileWriter(file)) {
                for (var e : defaults.entrySet()) {
                    fw.write(e.getKey() + ": " + e.getValue() + "\n");
                }
                System.out.println("🆕 Created default " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                br.lines().forEach(line -> {
                    String[] parts = line.split(":");
                    if (parts.length == 2) props.put(parts[0].trim(), parts[1].trim());
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return props;
    }
}
