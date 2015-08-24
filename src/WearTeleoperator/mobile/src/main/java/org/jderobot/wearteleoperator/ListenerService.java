package org.jderobot.wearteleoperator;

import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by Shady on 18/08/15.
 */
public class ListenerService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.i("test", "onMessageReceived()");
        if (!MainActivity.connected) {
            startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
        if(messageEvent.getPath().equals(Constants.MESSAGE_PATH)) {
            final String message = new String(messageEvent.getData());
            if (MainActivity.connected) {
                switch (message) {
                    case Constants.MESSAGE_TAKEOFF:
                        if (MainActivity.arDroneExtraPrx!=null) {
                            MainActivity.arDroneExtraPrx.takeoff();
                        }
                        break;
                    case Constants.MESSAGE_LAND:
                        if (MainActivity.arDroneExtraPrx!=null) {
                            MainActivity.sendVel(0, 0, 0, 0, 0, 0);
                            MainActivity.arDroneExtraPrx.land();
                        }
                        break;
                    case Constants.MESSAGE_UP:
                        if (MainActivity.cmdVelPrx!=null) {
                            MainActivity.sendVel(0, 0, 0.8f, 0, 0, 0);
                        }
                        break;
                    case Constants.MESSAGE_DOWN:
                        if (MainActivity.cmdVelPrx!=null) {
                            MainActivity.sendVel(0, 0, -0.8f, 0, 0, 0);
                        }
                        break;
                    case Constants.MESSAGE_FORWARD:
                        if (MainActivity.cmdVelPrx!=null) {
                            MainActivity.sendVel(0.8f, 0, 0, 0, 0, 0);
                        }
                        break;
                    case Constants.MESSAGE_LEFTROTATE:
                        if (MainActivity.cmdVelPrx!=null) {
                            MainActivity.sendVel(0, 0, 0, 0, 0, -1.0f);
                        }
                        break;
                    case Constants.MESSAGE_RIGHTROTATE:
                        if (MainActivity.cmdVelPrx!=null) {
                            MainActivity.sendVel(0, 0, 0, 0, 0, 1.0f);
                        }
                        break;
                    case Constants.MESSAGE_STOP:
                        if (MainActivity.cmdVelPrx!=null) {
                            MainActivity.sendVel(0, 0, 0, 0, 0, 0);
                        }
                        break;
                }
            }
        } else {
            super.onMessageReceived(messageEvent);
        }
    }
}
