package org.jderobot.wearteleoperator;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by Shady on 18/08/15.
 */
public class MyButton extends ImageButton {
    private String myButtonString;

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setup(String s) {
        myButtonString = s;
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        sendMessage(myButtonString);
                        return true;
                    case MotionEvent.ACTION_UP:
                        sendMessage(Constants.MESSAGE_STOP);
                        return true;
                }
                return false;
            }
        });
    }

    public static void sendMessage(String s) {
        final String message = s;
        new Thread(new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(MainActivity.mGoogleApiClient).await();
                MessageApi.SendMessageResult result = null;
                for (Node node : nodes.getNodes()) {
                    try {
                        result = Wearable.MessageApi.sendMessage(MainActivity.mGoogleApiClient, node.getId(),
                                Constants.MESSAGE_PATH, message.getBytes()).await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!result.getStatus().isSuccess()) {
                        Log.e("MessageAPI", "Could not send "+message);
                    } else {
                        Log.i("MessageAPI", "success!! "+message+" sent to: "+node.getDisplayName());
                    }
                }
            }
        }).start();
    }
}
