package team_310.x_chair;

import android.app.Activity;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Dirk Vanbeveren on 12/04/2017.
 */

class WebSocket {
    private Activity act;
    int sendingInterval;
    String host;
    boolean running;
    int port;
    private WebSocketClient webSocketClient;
    void connectWebSocket() {
        System.out.println("connecting websocket..");
        URI uri;
        try {
           //uri = new URI("http://"+ host+ ":"+ String.valueOf(port));
            uri = new URI("ws://3.1.0.1:81/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Uri confirmed..");
        webSocketClient =new WebSocketClient(uri, new Draft_17()) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                System.out.println("Websocket Opened");
                webSocketClient.send("connected"); //or any check packet
            }

            @Override
            public void onMessage(final String s) {
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Transcoder transcoder = new Transcoder();
                        transcoder.transcode(s);
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                System.out.println("Websocket Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                System.out.println("Websocket Error: " + e.getMessage());
            }
        };
        webSocketClient.connect();
    }
    void sendMessage(String message, boolean repeat) {
        this.webSocketClient.send(message);
    }

    public void setWebSocketClient(WebSocketClient webSocketClient) {
    }
}
