package server;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.sun.net.httpserver.*;

public class ChatEndpoint {
    private static final File CHAT_LOG = new File("logs/chat/chatlog.txt");

    public static void start(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/chatlog", new ChatLogHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("💬 Chat log server started on http://localhost:" + port + "/chatlog");
    }

    static class ChatLogHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = getChatLogHtml();
                exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    private static String getChatLogHtml() throws IOException {
        if (!CHAT_LOG.exists()) {
            return "<html><body><h1>No chat logs yet!</h1></body></html>";
        }
        StringBuilder html = new StringBuilder("<html><head><title>SuperMaze Chat Log</title>");
        html.append("<style>body{font-family:Arial;background:#111;color:#0f0;padding:20px;}h1{color:#ff0;}pre{background:#000;padding:10px;border-radius:8px;}</style></head><body>");
        html.append("<h1>SuperMaze Chat Log</h1><pre>");
        try (BufferedReader reader = new BufferedReader(new FileReader(CHAT_LOG))) {
            String line;
            while ((line = reader.readLine()) != null) html.append(line).append("\n");
        }
        html.append("</pre></body></html>");
        return html.toString();
    }

    public static void logChat(String mazeName, String player, String message) {
        try {
            CHAT_LOG.getParentFile().mkdirs();
            try (FileWriter fw = new FileWriter(CHAT_LOG, true)) {
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("[dd-MM-yyyy] (HH:mm)"));
                fw.write(timestamp + " {" + mazeName + "} " + player + ": " + message + "\n");
            }
        } catch (IOException e) {
            System.out.println("❌ Chat logging error: " + e.getMessage());
        }
    }
}
