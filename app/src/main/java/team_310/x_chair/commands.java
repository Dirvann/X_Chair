package team_310.x_chair;

/**
 * Created by Dirk Vanbeveren on 2/04/2017.
 */

class commands {
    /*  ///////if def TV/////////////////
    static final int FORWARD = 0x20;
    static final int BACKWARD = 0x21;
    static final int LEFT = 0x15;
    static final int RIGHT = 0x16;
    static final int BRAKE = 0x0C;
    static final int SPEEDUP = 0x10;
    static final int SPEEDDOWN = 0x11;
    static final int CHG_STATION = 0x30;
    ///////LIGHTS///////////////
    static final int LIGHTS_ON = 0x0E;
    static final int LIGHTS_OFF = 0x0D;
    static final int LIGHTS_AUTO = 0x38;

    static final int LIGHTS_RED = 0x37;
    static final int LIGHTS_GREEN = 0x36;
    static final int LIGHTS_YELLOW = 0x32;
    static final int LIGHTS_BLUE = 0x34;
    static final int LIGHTS_RAINBOW = 0x12;
    */
    //////for DVD///////////////
    static final String FORWARD = "0x61";
    static final String BACKWARD = "0x60";
    static final String LEFT = "0x50";
    static final String RIGHT = "0x51";
    static final String BRAKE = "0x62";
    static final String SPEEDUP = "0x10";
    static final String SPEEDDOWN = "0x11";
    static final String CHG_STATION = "0x4E";
    static final String MANUAL = "0x4D";
    ///////LIGHTS///////////////
    static final String LIGHTS_ON = "0x63";
    static final String LIGHTS_OFF = "0x0D";
    static final String LIGHTS_AUTO = "0x69";

    static final String LIGHTS_RED = "0x76";
    static final String LIGHTS_GREEN = "0x72";
    static final String LIGHTS_YELLOW = "0x78";
    static final String LIGHTS_BLUE = "0x74";
    static final String LIGHTS_RAINBOW = "0x41";

    ////////WEB SOCKET COMMANDS/////////////
    static final String WS_FORWARD = "0x6B";
    static final String WS_BACKWARD = "0x6A";
    static final String WS_LEFT = "0x5A";
    static final String WS_RIGHT = "0x5B";

    ////////2 Byte Commands///////////////
    static final String BB_SPEED_SETTING = "0x03";
    static final String BB_LEFT_WHEEL_POWER = "0x04";
    static final String BB_RIGHT_WHEEL_POWER = "0x05";


    ////////VEHICLE TO CLIENT/////////////
    static final String VtoC_RESET = "0x01";
    static final String VtoC_BATTERY_VOLTAGE = "0x02";
    static final String VtoC_SPEED_SETTING = "0x03";
    static final String VtoC_ACTUAL_SPEED = "0x06";

}
