package org.jderobot.wearteleoperator;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by Shady on 18/08/15.
 */
public class ImageListenerService extends WearableListenerService {
    @Override
    public void onMessageReceived(MessageEvent event) {
        if (event.getPath().equals(Constants.IMAGE_PATH)) {
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("bitmap")
                .putExtra("image-bytes", event.getData()));
        }
    }
}
