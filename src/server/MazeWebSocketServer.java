import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import java.net.InetSocketAddress;
import event.*;

public class MazeWebSocketServer extends WebSocketServer {
    private final EventBus eventBus;

    public MazeWebSocketServer(int port, EventBus eventBus) {
        super(new InetSocketAddress(port));
        this.eventBus = eventBus;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        String ip = conn.getRemoteSocketAddress().getAddress().getHostAddress();
        eventBus.post(new PlayerJoinEvent(ip));
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        String player = conn.getRemoteSocketAddress().getAddress().getHostAddress();
        eventBus.post(new PlayerChatEvent(player, message));
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        String ip = conn.getRemoteSocketAddress().getAddress().getHostAddress();
        eventBus.post(new PlayerLeaveEvent(ip));
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println("⚠️ WebSocket Error: " + ex.getMessage());
    }

    @Override
    public void onStart() {
        System.out.println("✅ Maze WebSocket Server is running on port " + getPort());
    }
}
