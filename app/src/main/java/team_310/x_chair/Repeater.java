package team_310.x_chair;

import android.app.Activity;
import android.os.StrictMode;

/**
 * Created by Dirk Vanbeveren on 4/05/2017.
 */

class Repeater {
    boolean running;
    private Activity act;
    int sendingInterval;
    void repeat(final WebSocket webSocket, final String message){
        if (webSocket.connected) {
            final Thread sendingThread = new Thread() {
                public void run() {
                    try {
                        while (running) {
                            //Strictmode to delete Network error
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);

                            webSocket.sendMessage(message);

                            //System.out.println(new Date());
                            Thread.sleep(sendingInterval);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            };
            sendingThread.start();
        }
    }
}
