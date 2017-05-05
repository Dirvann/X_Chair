package team_310.x_chair;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class mainactivity extends AppCompatActivity {
    //checks if the app is sending motion messages (button pressed)
    String sendingMotion = "0";
    ////////SECRET SETTINGS////////////
    private int egg = 0;
    private int imState = 1;
    RelativeLayout rl;
    static TextView speedDisplay;
    List sequence;
    static MediaPlayer mp;

    //context of application
    static Activity act = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        act = this;

        rl = (RelativeLayout) findViewById(R.id.relativelayout);
///////////////////////////////////////////////////////////////////////
///////////////packet sending configuration////////////////////////////
//////////////////////////////////////////////////////////////////////
        //Initialize WebSocket
        final WebSocket webSocket = new WebSocket();   //Creating a new webSocket
        webSocket.host = "3.1.0.1";    //Host to connect to
        webSocket.port = 81;
        webSocket.connected = false; //variable that checks if app is connected to websocket
        webSocket.connectWebSocket();   //connecting the websocket to arduino

        System.out.println("check");

        /////SECRET SETTINGS//////////
        //mainactivity.context = getApplicationContext();
        speedDisplay = (TextView) findViewById(R.id.ViewSpeed);
        mp = MediaPlayer.create(this, R.raw.jinglebells);
        sequence = new ArrayList();
        sequence.add("r");
        sequence.add("b");
        sequence.add("g");
        sequence.add("y");
        sequence.add("rain");
        sequence.add("y");
        sequence.add("r");
        /////////////////////////////


///////////////////TEMP REPEATER/////////////
        final Repeater repeater = new Repeater();
        repeater.sendingInterval = 90;



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
        //TODO: add reconnection function
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
                    System.out.println("Button FORWARD down");
                    /*UpButton.setBackgroundColor(Color.parseColor(pressColor));
                    UpButton.setShadowLayer(20, 20, 20, Color.BLACK);*/
                    //webSocket.sendMessage(commands.FORWARD);    //uncomment for normal

                    repeater.running = true;
                    repeater.repeat(webSocket, commands.FORWARD);
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("forward")) {
                    System.out.println("Button FORWARD up");
                    webSocket.sendMessage(commands.BRAKE);
                    sendingMotion = "0";
                    /*UpButton.setTextColor(Color.BLACK);
                    UpButton.setBackgroundColor(Color.parseColor(releaseColor));*/

                    repeater.running = false;

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
                    System.out.println("Button BACKWARD down");
                    //webSocket.sendMessage(commands.BACKWARD);   //uncomment for normal
                    DownButton.setTextColor(Color.BLACK);
                    /*DownButton.setBackgroundColor(Color.parseColor(pressColor));
                    DownButton.setShadowLayer(20, 20, 20, Color.BLACK);*/

                    repeater.running = true;
                    repeater.repeat(webSocket, commands.BACKWARD);
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("down")) {
                    sendingMotion = "0";
                    System.out.println("Button FORWARD up");
                    webSocket.sendMessage(commands.BRAKE);
                    DownButton.setTextColor(Color.BLACK);
                    /*DownButton.setBackgroundColor(Color.parseColor(releaseColor));
                    DownButton.setShadowLayer(0, 0, 0, Color.BLACK);*/

                    repeater.running = false;
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
                    System.out.println("Button LEFT down");
                    webSocket.sendMessage(commands.LEFT);
                    /*LeftButton.setTextColor(Color.BLACK);
                    LeftButton.setBackgroundColor(Color.parseColor(pressColor));*/

                    repeater.running = true;
                    repeater.repeat(webSocket, commands.LEFT);
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("left")) {
                    sendingMotion = "0";
                    System.out.println("Button LEFT up");
                    webSocket.sendMessage(commands.BRAKE);
                    /*LeftButton.setTextColor(Color.BLACK);
                    LeftButton.setBackgroundColor(Color.parseColor(releaseColor));*/

                    repeater.running = false;
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
                    System.out.println("Button RIGHT down");
                    //webSocket.sendMessage(commands.RIGHT); //uncomment for normal
                    /*RightButton.setTextColor(Color.BLACK);
                    RightButton.setBackgroundColor(Color.parseColor(pressColor));*/

                    repeater.running = true;
                    repeater.repeat(webSocket, commands.RIGHT);

                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("right")) {
                    sendingMotion = "0";
                    System.out.println("Button RIGHT up");
                    webSocket.sendMessage(commands.BRAKE);
                    /*RightButton.setTextColor(Color.BLACK);
                    RightButton.setBackgroundColor(Color.parseColor(releaseColor));*/

                    repeater.running = false;
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
                    webSocket.sendMessage(commands.SPEEDUP);
                    /*SuButton.setTextColor(Color.BLACK);
                    SuButton.setBackgroundColor(Color.parseColor(pressColor));*/
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("su")) {
                    sendingMotion = "0";
                    /*SuButton.setTextColor(Color.BLACK);
                    SuButton.setBackgroundColor(Color.parseColor(releaseColor));*/
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
                    webSocket.sendMessage(commands.SPEEDDOWN);
                    /*SoButton.setTextColor(Color.BLACK);
                    SoButton.setBackgroundColor(Color.parseColor(pressColor));*/
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("so")) {
                    sendingMotion = "0";
                    /*SoButton.setTextColor(Color.BLACK);
                    SoButton.setBackgroundColor(Color.parseColor(releaseColor));*/
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
                    webSocket.sendMessage(commands.LIGHTS_RED);

                    backgroundChanger("r");
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("red")) {
                    sendingMotion = "0";
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
                    webSocket.sendMessage(commands.LIGHTS_GREEN); // sending green data to address defined at beginning
                    backgroundChanger("g");
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("green")) {
                    sendingMotion = "0";
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
                    webSocket.sendMessage(commands.LIGHTS_YELLOW); // sending yellow to address defined at beginning
                    backgroundChanger("y");
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("yellow")) {
                    sendingMotion = "0";
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
                    webSocket.sendMessage(commands.LIGHTS_BLUE); // sending blue data to address defined at beginning
                    backgroundChanger("b");
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("blue")) {
                    sendingMotion = "0";
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
                    webSocket.sendMessage(commands.LIGHTS_RAINBOW); // sending rainbow data to address defined at beginning
                    backgroundChanger("rain");
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("rainbow")) {
                    sendingMotion = "0";
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
                    webSocket.sendMessage(commands.LIGHTS_ON); // sending lights on data to address defined at beginning

                    //sendMessage("0x61");
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("on")) {
                    sendingMotion = "0";
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
                    webSocket.sendMessage(commands.LIGHTS_OFF); // sending lights off to address defined at beginning
                    return true;

                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("off")) {
                    sendingMotion = "0";
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
                    webSocket.sendMessage(commands.LIGHTS_AUTO); // sending lights auto to address defined at beginning*/
                    return true;


                } else if (action == MotionEvent.ACTION_UP && sendingMotion.equals("lights_auto")) {
                    sendingMotion = "0";
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
                    webSocket.sendMessage(commands.CHG_STATION);
                } else {
                    webSocket.sendMessage(commands.LIGHTS_RED);
                }
            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////VALUE DATA INPUTS(ex:speed)/////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////

///////-------------------------------View Speed-------------------------------------------////////
        //Transcoder trans = new Transcoder();
        //trans.transcode("123456");
       /* final TextView speedDisplay = (TextView) findViewById(R.id.ViewSpeed);
        speedDisplay.setText("123");
        for (int i = 0; i < 1000; i++) {
            speedDisplay.setText(String.valueOf(i));

        }
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
        ///////////////////------------------Connect----------------------------------////////////////
        final Button connectButton = (Button) findViewById(R.id.connect);

        connectButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN && sendingMotion.equals("0")) {
                    webSocket.connectWebSocket();
                    return true;
                }
                return false;
            }
        });

//////////////////////////////////////EASTER EGG///////////////////////////////////




    }
    /*public void displaySpeed(String data) {
        final TextView speedDisplay = (TextView) findViewById(R.id.ViewSpeed);
        speedDisplay.setText("123454");
    }*/
    void backgroundChanger(String code) {
        Log.i("egg", "number " + egg+", code: " + code);
        if (sequence.get(egg) == code) {
            egg += 1;
            //displayText(String.valueOf(egg));
            Log.i("check", "code == egg "+ egg);

            if (egg == 7) {
                if (imState == 1) {
                    rl.setBackgroundResource(R.drawable.background2);
                    imState = 2;
                    egg = 0;
                    //displayText(String.valueOf(egg));
                    mp.start();

                } else{
                    rl.setBackgroundResource(R.drawable.background);
                    imState = 1;
                    egg = 0;
                    //displayText(String.valueOf(egg));
                    mp.pause();
                    mp.seekTo(0);
                }

            }
        }else {
            egg = 0;
            //displayText(String.valueOf(egg));
        }
    }
    public static void toast(String message) {
        Toast.makeText(act, message, Toast.LENGTH_SHORT).show();
    }
    public static void displayText(String message){
        speedDisplay.setText(message);
    }
}