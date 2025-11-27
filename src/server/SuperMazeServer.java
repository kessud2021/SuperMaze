import event.*;
import utils.ChatManager;

public class SuperMazeServer {
    private final EventBus eventBus;
    private final SuperMazeServerGUI gui;
    private MazeWebSocketServer wsServer;

    public SuperMazeServer(SuperMazeServerGUI gui) {
        this.gui = gui;
        this.eventBus = new EventBus();

        // Register event listeners
        eventBus.register(new Listener() {
            @EventHandler
            public void onPlayerJoin(PlayerJoinEvent e) {
                gui.appendLog("🟢 Player joined: " + e.getIp());
            }

            @EventHandler
            public void onPlayerChat(PlayerChatEvent e) {
                gui.appendLog("💬 " + e.getPlayer() + ": " + e.getMessage());
                ChatManager.logChatToFile("default", e.getPlayer(), e.getMessage());
            }

            @EventHandler
            public void onPlayerLeave(PlayerLeaveEvent e) {
                gui.appendLog("🔴 Player left: " + e.getIp());
            }
        });
    }

    public void startServer(int port) {
        if (wsServer != null) {
            gui.appendLog("⚠️ Server already running!");
            return;
        }
        wsServer = new MazeWebSocketServer(port, eventBus);
        wsServer.start();
        gui.appendLog("✅ Server started on port " + port);
    }
}
