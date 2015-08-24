package org.jderobot.test.wearclient;


import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import jderobot.*;

/**
 * Created by Shady on 15/08/15.
 */
public class ListenerService extends WearableListenerService{


    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if(messageEvent.getPath().equals(Constants.HeatmapInfoPath)) {
            try {
                HeatmapInfo hmInfo = HeatmapInfo.deserialize(messageEvent.getData());
                Log.i("LISTENER", "HeatmapInfo received");
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("info")
                        .putExtra("data", hmInfo));
                //startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (messageEvent.getPath().equals(Constants.HeatmapDataPath) ) {
            try {
                HeatmapData hmData = HeatmapData.deserialize(messageEvent.getData());
                Log.i("LISTENER", "HeatmapData received");
                //Log.i("LISTENER", Integer.toString(hmData.curcp));
                Intent intent = new Intent("data").putExtra("data", hmData);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
