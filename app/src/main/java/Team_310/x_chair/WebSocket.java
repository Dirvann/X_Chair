package Team_310.x_chair;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Dirk Vanbeveren on 12/04/2017.
 */

class WebSocket {
    private Activity act;
    String host;
    int port;
    private WebSocketClient webSocketClient;
    void connectWebSocket() {
        URI uri;
        try {
           uri = new URI("ws://"+ host+ ":"+ String.valueOf(port));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        webSocketClient =new WebSocketClient(uri) {
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
    void sendMessage(String message) {
        webSocketClient.send(message);
    }

}
