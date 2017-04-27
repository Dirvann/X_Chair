package team_310.x_chair;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
////////////WEBSOCKET//////////////
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
/////////////////////////////////////


public class mainactivity extends AppCompatActivity {
    //checks if the app is sending motion messages (button pressed)
    String sendingMotion = "0";
    private WebSocketClient mWebSocketClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
///////////////////////////////////////////////////////////////////////
///////////////packet sending configuration////////////////////////////
//////////////////////////////////////////////////////////////////////
        final UDPSender sender = new UDPSender();
        sender.host = "3.1.0.1";
        sender.port = 9310;
        sender.sendingInterval = 90; //interval at which moving commands will be sent repeatedly
        final int controlRepeat = 4; //extra sending to be sure the message arrives

        //final UDPReceiver receiver = new UDPReceiver();
        //receiver.run(9310);          //Opening UDP listening port

        //Initialize WebSocket
        final WebSocket webSocket = new WebSocket();   //Creating a new webSocket
        webSocket.host = "3.1.0.1";    //Host to connect to
        webSocket.port = 9310;
        webSocket.sendingInterval = 90; //Interval at which the socket is going to resend
        webSocket.connectWebSocket();   //connecting the websocket to arduino

        //TODO: add webSocket.sendMessage(message) to all commands
/////////////////////////////EXTRA WEBSOCKET////////////////////////////////////

        connectWebSocket();

/////////////////////////////////////////////////////////////////////
////////////////////Color configuration//////////////////////////////
/////////////////////////////////////////////////////////////////////
        final String pressColor = "#F7E0FA"; //color of background of buttons
        final String releaseColor = "#ECBE06";

/////////////////////////////////////////////////////////////////////
/////////////////Controller configuration////////////////////////////
//////////////////////////////////////////////////////////////////////
        /*//0 for TV, 1 for DVD
        commands p = new commands();
        p.controller = 1;*/
/////////////////////////////////////////////////////////////////////
///////////////////MOTION BUTTONS////////////////////////////////////
////////////////////////////////////////////////////////////////////
/////------------------------FORWARD-------------------------------//////
        final Button UpButton = (Button) findViewById(R.id.Up);

        UpButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN && sendingMotion.equals("0")) {
                    sendingMotion = "forward";
                    /*//System.out.println("Button down");
                    sender.running = true;
                    UpButton.setTextColor(Color.BLACK);
                    UpButton.setBackgroundColor(Color.parseColor(pressColor));
                    sender.send(commands.FORWARD, true);
*/                  webSocket.connectWebSocket();
                    //sendMessage("Forward");
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("forward")) {
                    //System.out.println("button up");
                    sendingMotion = "0";
                    sender.running = false;
                    UpButton.setTextColor(Color.BLACK);
                    UpButton.setBackgroundColor(Color.parseColor(releaseColor));

                    webSocket.running = false;
                    return true;
                }

                return false;
            }
        });
//////------------------------DOWN-----------------------------//////////

        final Button DownButton = (Button) findViewById(R.id.Down);

        DownButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN && sendingMotion.equals("0")) {
                    sendingMotion = "down";
                    sender.running = true;
                    sender.send(commands.BACKWARD, true);
                    DownButton.setTextColor(Color.BLACK);
                    DownButton.setBackgroundColor(Color.parseColor(pressColor));
                    DownButton.setShadowLayer(20, 20, 20, Color.BLACK);

                    webSocket.running = true;
                    webSocket.sendMessage(String.valueOf(commands.BACKWARD), true);
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("down")) {
                    sendingMotion = "0";
                    sender.running = false;
                    DownButton.setTextColor(Color.BLACK);
                    DownButton.setBackgroundColor(Color.parseColor(releaseColor));
                    DownButton.setShadowLayer(0, 0, 0, Color.BLACK);
                    return true;
                }

                return false;
            }
        });
////////----------------------LEFT-------------------------------//////////
        final Button LeftButton = (Button) findViewById(R.id.Left);

        LeftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN && sendingMotion.equals("0")) {
                    sendingMotion = "left";
                    sender.running = true;
                    sender.send(commands.LEFT, true);
                    LeftButton.setTextColor(Color.BLACK);
                    LeftButton.setBackgroundColor(Color.parseColor(pressColor));

                    /*webSocket.running = true;
                    webSocket.sendMessage(String.valueOf(commands.LEFT), true);*/
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("left")) {
                    sendingMotion = "0";
                    sender.running = false;
                    LeftButton.setTextColor(Color.BLACK);
                    LeftButton.setBackgroundColor(Color.parseColor(releaseColor));
                    return true;
                }

                return false;
            }
        });
////////------------------------RIGHT---------------------------///////////
        final Button RightButton = (Button) findViewById(R.id.Right);

        RightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN && sendingMotion.equals("0")) {
                    sendingMotion = "right";
                    sender.running = true;
                    sender.send(commands.RIGHT, true);
                    RightButton.setTextColor(Color.BLACK);
                    RightButton.setBackgroundColor(Color.parseColor(pressColor));

                    //webSocket.running = true;
                    //webSocket.sendMessage(String.valueOf(commands.RIGHT), true);
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("right")) {
                    sendingMotion = "0";
                    sender.running = false;
                    RightButton.setTextColor(Color.BLACK);
                    RightButton.setBackgroundColor(Color.parseColor(releaseColor));
                    return true;
                }
                return false;
            }
        });
////////////-----------SPEED UP------------------------/////////////////////////
        final Button SuButton = (Button) findViewById(R.id.speedup);

        SuButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN && sendingMotion.equals("0")) {
                    sendingMotion = "su";
                    sender.running = true;
                    sender.send(commands.SPEEDUP, false);
                    SuButton.setTextColor(Color.BLACK);
                    SuButton.setBackgroundColor(Color.parseColor(pressColor));

                    /*webSocket.running = true;
                    webSocket.sendMessage(String.valueOf(commands.BACKWARD), true);*/
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("su")) {
                    sendingMotion = "0";
                    sender.running = false;
                    SuButton.setTextColor(Color.BLACK);
                    SuButton.setBackgroundColor(Color.parseColor(releaseColor));
                    return true;
                }
                return false;
            }
        });
////////////////--------------SPEED DOWN--------------------////////////////////////////
        final Button SoButton = (Button) findViewById(R.id.speeddown);

        SoButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN && sendingMotion.equals("0")) {
                    sendingMotion = "so";
                    sender.running = true;
                    sender.send(commands.SPEEDDOWN, false);
                    SoButton.setTextColor(Color.BLACK);
                    SoButton.setBackgroundColor(Color.parseColor(pressColor));
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("so")) {
                    sendingMotion = "0";
                    sender.running = false;
                    SoButton.setTextColor(Color.BLACK);
                    SoButton.setBackgroundColor(Color.parseColor(releaseColor));
                    return true;
                }
                return false;
            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////COLOR BUTTON SECTION//////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////
/////////////-----------------RED--------------------------------------------////////////////////
        final Button redButton = (Button) findViewById(R.id.red);

        redButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN && sendingMotion.equals("0")) {
                    sendingMotion = "red";
                    sender.running = true;
                    sender.send(commands.LIGHTS_RED, false); // sending LIGHTS_RED data to address defined at beginning
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("red")) {
                    sendingMotion = "0";
                    sender.running = false;
                    return true;
                }
                return false;
            }
        });
//////////////-------------------GREEN------------------------------//////////////////////////
        final Button greenButton = (Button) findViewById(R.id.green);

        greenButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN && sendingMotion.equals("0")) {
                    sendingMotion = "green";
                    sender.running = true;
                    sender.send(commands.LIGHTS_GREEN, false); // sending green data to address defined at beginning
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("green")) {
                    sendingMotion = "0";
                    sender.running = false;
                    return true;
                }
                return false;
            }
        });
//////////////////---------------------YELLOW----------------------------///////////////////
        final Button yellowButton = (Button) findViewById(R.id.yellow);

        yellowButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN && sendingMotion.equals("0")) {
                    sendingMotion = "yellow";
                    sender.running = true;
                    sender.send(commands.LIGHTS_YELLOW, false); // sending yellow to address defined at beginning
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("yellow")) {
                    sendingMotion = "0";
                    sender.running = false;
                    return true;
                }
                return false;
            }
        });
/////////////////-------------BLUE------------------------------------//////////////
        final Button blueButton = (Button) findViewById(R.id.blue);

        blueButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN && sendingMotion.equals("0")) {
                    sendingMotion = "blue";
                    sender.running = true;
                    sender.send(commands.LIGHTS_BLUE, false); // sending blue data to address defined at beginning
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("blue")) {
                    sendingMotion = "0";
                    sender.running = false;
                    return true;
                }
                return false;
            }
        });
//////////////-----------------------RAINBOW--------------------------///////////////////
        final Button rainbowButton = (Button) findViewById(R.id.rainbow);

        rainbowButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN && sendingMotion.equals("0")) {
                    sendingMotion = "rainbow";
                    sender.running = true;
                    sender.send(commands.LIGHTS_RAINBOW, false); // sending rainbow data to address defined at beginning
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("rainbow")) {
                    sendingMotion = "0";
                    sender.running = false;
                    return true;
                }
                return false;
            }
        });
//////////////////------------LIGHTS ON---------------------------------////////////////////
        final Button lightsOnButton = (Button) findViewById(R.id.lights_on);

        lightsOnButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN && sendingMotion.equals("0")) {
                    sendingMotion = "on";
                    sender.running = true;
                    sender.send(commands.LIGHTS_ON, false); // sending lights on data to address defined at beginning

                    //sendMessage("0x61");
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("on")) {
                    sendingMotion = "0";
                    sender.running = false;
                    return true;
                }
                return false;
            }
        });
///////////---------------Lights off-----------------------------//////////////////////
        final Button lightsOffButton = (Button) findViewById(R.id.lights_off);

        lightsOffButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN && sendingMotion.equals("0")) {
                    sendingMotion = "off";
                    sender.running = true;
                    sender.send(commands.LIGHTS_OFF, false); // sending lights off to address defined at beginning
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("off")) {
                    sendingMotion = "0";
                    sender.running = false;
                    return true;
                }
                return false;
            }
        });
///////////////////------------------LIGHTS AUTO----------------------------------////////////////
        final Button lightsAutoButton = (Button) findViewById(R.id.lights_auto);

        lightsAutoButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN && sendingMotion.equals("0")) {
                    sendingMotion = "lights_auto";
                    /*sender.running = true;
                    sender.send(commands.LIGHTS_AUTO, false); // sending lights auto to address defined at beginning*/
                    connectWebSocket();
                    return true;


                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("lights_auto")) {
                    sendingMotion = "0";
                    sender.running = false;
                    return true;
                }
                return false;
            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////AUTO DRIVING SECTION////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
        final Switch autoSwitch = (Switch) findViewById(R.id.switch1);

        autoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println(isChecked);
                if (isChecked) {
                    for(int i=0; i < controlRepeat; i++) {
                        sender.send(commands.CHG_STATION, false);
                    }
                } else {
                    for(int i=0; i < controlRepeat; i++) {
                        sender.send(commands.MANUAL, false);
                    }
                }
            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////VALUE DATA INPUTS(ex:speed)/////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////

///////-------------------------------View Speed-------------------------------------------////////
        //Transcoder trans = new Transcoder();
        //trans.transcode("123456");
        /*final TextView speedDisplay = (TextView) findViewById(R.id.ViewSpeed);
        speedDisplay.setText("123");

        final Thread t = new Thread(){
            public void run(){
                try {
                    for (int i = 0; i < 1000; i++) {
                        speedDisplay.setText(String.valueOf(i));
                        Thread.sleep(100);

                    }
                }catch(InterruptedException e){
                    System.err.println(e);
                }

            }
        };
        t.start();*/



    }
    /*public void displaySpeed(String data) {
        final TextView speedDisplay = (TextView) findViewById(R.id.ViewSpeed);
        speedDisplay.setText("123454");
    }*/
    private void connectWebSocket() {
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
                mWebSocketClient.send("request");
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Received message: "+ message);
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }

    public void sendMessage(String message) {
        System.out.println("Sending message: "+ message);
        mWebSocketClient.send(message);
    }
}