package team_310.x_chair;

import android.app.Activity;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

////////////WEBSOCKET//////////////
/////////////////////////////////////

/**
 * Created by Dirk Vanbeveren on 12/04/2017.
 */

class WebSocket {
    private Activity act;
    int sendingInterval;
    String host;
    boolean running;
    int port;
    private WebSocketClient mWebSocketClient;
    boolean connected;
    void connectWebSocket() {
        Wifi.connect();
        URI uri;
        try {
            uri = new URI("ws://3.1.0.1:81/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("going to connect to: "+uri);

        mWebSocketClient = new WebSocketClient(uri, new Draft_17()) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                //mWebSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
                //mWebSocketClient.send("request");
                connected = true;
                Notification.toast("Connected");
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                /*act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Received message: "+ message);


                });*/
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
                connected = false;
                Notification.toast("connection closed");
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        Notification.toast("connecting to websocket");
        mWebSocketClient.connect();
        if(!connected) {
            Notification.toast("unable to connect");
        }
    }

    void sendMessage(String message) {
        if(connected) {
            System.out.println("Sending message: " + message);
            mWebSocketClient.send(message);
        } else {
            Notification.toast("Websocket not connected");
        }
    }
}
