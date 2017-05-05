package team_310.x_chair;

import android.widget.Toast;


/**
 * Created by Dirk Vanbeveren on 4/05/2017.
 */

class Notification {
    static void toast(String message){
        Toast.makeText(mainactivity.act, message, Toast.LENGTH_SHORT).show();
    }
}

