package server;

import java.io.*;
import java.net.*;
import java.util.jar.*;
import java.lang.reflect.*;

public class PluginLoader {

    public void loadPlugins() {
        File folder = new File("plugins");
        File[] files = folder.listFiles((d, n) -> n.endsWith(".jar"));
        if (files == null) return;

        for (File jar : files) {
            try {
                URLClassLoader loader = new URLClassLoader(new URL[]{jar.toURI().toURL()});
                JarFile jf = new JarFile(jar);
                String mainClass = jf.getManifest().getMainAttributes().getValue("Main-Class");

                if (mainClass != null) {
                    Class<?> cls = loader.loadClass(mainClass);
                    Object instance = cls.getDeclaredConstructor().newInstance();

                    System.out.println("🧠 Loaded plugin: " + jar.getName());
                    Method enable = cls.getMethod("onEnable");
                    enable.invoke(instance);
                }

                jf.close();
            } catch (Exception e) {
                System.err.println("❌ Failed to load plugin " + jar.getName() + ": " + e.getMessage());
            }
        }
    }
}
