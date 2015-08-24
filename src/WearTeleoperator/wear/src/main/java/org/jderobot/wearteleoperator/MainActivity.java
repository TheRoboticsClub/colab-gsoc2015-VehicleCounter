package org.jderobot.wearteleoperator;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static GoogleApiClient mGoogleApiClient;
    private ImageButton takeOffButton;
    private ImageButton landButton;
    private MyButton forwardButton;
    private MyButton upButton;
    private MyButton downButton;
    private MyButton leftRotate;
    private MyButton rightRotate;
    private ImageView imageView;
    private Bitmap mBitmap;

    private BroadcastReceiver ImageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            byte[] arr = intent.getByteArrayExtra("image-bytes");
            mBitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);
            imageView.setImageBitmap(mBitmap);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_activity_main);
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API).build();
        imageView = (ImageView) findViewById(R.id.imageView);
        takeOffButton = (ImageButton) findViewById(R.id.playButton);
        takeOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyButton.sendMessage(Constants.MESSAGE_TAKEOFF);
            }
        });
        landButton = (ImageButton) findViewById(R.id.stopButton);
        landButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyButton.sendMessage(Constants.MESSAGE_LAND);
            }
        });
        forwardButton = (MyButton) findViewById(R.id.forwardButton);
        forwardButton.setup(Constants.MESSAGE_FORWARD);
        upButton = (MyButton) findViewById(R.id.upButton);
        upButton.setup(Constants.MESSAGE_UP);
        downButton = (MyButton) findViewById(R.id.downButton);
        downButton.setup(Constants.MESSAGE_DOWN);
        leftRotate = (MyButton) findViewById(R.id.lrButton);
        leftRotate.setup(Constants.MESSAGE_LEFTROTATE);
        rightRotate = (MyButton) findViewById(R.id.rrButton);
        rightRotate.setup(Constants.MESSAGE_RIGHTROTATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        LocalBroadcastManager.getInstance(this).registerReceiver(ImageReceiver, new IntentFilter("bitmap"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mGoogleApiClient.isConnected()) mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) mGoogleApiClient.disconnect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) mGoogleApiClient.disconnect();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(ImageReceiver);
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.w("GoogleApiClient", "Connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("GoogleApiClient", "Connection failed");
    }
}
