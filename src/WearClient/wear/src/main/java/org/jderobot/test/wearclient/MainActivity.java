package org.jderobot.test.wearclient;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    private HeatmapInfo hmInfo;
    private HeatmapData hmData;
    private ImageView mImageView;
    private Bitmap mBitmap, mBitmapMask;
    private Canvas mCanvas, mCanvasMask;
    private Paint mPaint;
    private RelativeLayout rootLayout;
    private HeatmapInfo.checkpoint[] cpList;
    private Point screenSize;
    private boolean initialized = false;
    private boolean dynamic = false;
    private int r, g, b;
    private float lastX, lastY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_activity_main);

        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView.setImageDrawable(getResources().getDrawable(R.drawable.peloto_s));
        rootLayout = (RelativeLayout) findViewById(R.id.relLayout);
        screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        Log.i("DIMENSIONS",Integer.toString(screenSize.x)+" "+Integer.toString(screenSize.y));
        mBitmap = Bitmap.createBitmap(screenSize.x, screenSize.y, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("onCreate", "registering receivers..");
        LocalBroadcastManager.getInstance(this).registerReceiver(mInfoReceiver, new IntentFilter("info"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mDataReceiver, new IntentFilter("data"));
    }

    private BroadcastReceiver mInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            hmInfo = (HeatmapInfo) intent.getSerializableExtra("data");
            cpList = hmInfo.arr;
            for (int i=0; i< hmInfo.arr.length; i++) {
                drawRect(scaleX(cpList[i].y+50)-15, scaleX(cpList[i].y+50)+15, 0, scaleY(100), Color.rgb(50, 50, 50));
                drawLine(scaleX(cpList[i].y+50), scaleX(cpList[i].y+50), 0, scaleY(100), Color.rgb(100, 100, 100));
            }
             initialized = true;
            rootLayout.setBackgroundColor(Color.BLACK);
            mImageView.setImageBitmap(mBitmap);
        }
    };

    private BroadcastReceiver mDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("ondataReceived", "Inside onReceive");
            if (initialized) {
                Log.d("BroadcastReceiver" , "initialized=true");
                hmData = (HeatmapData) intent.getSerializableExtra("data");
                Log.i("BroadcastReceiver", Float.toString(hmData.poseX) + " " + Float.toString(hmData.poseY));
                if (hmData.state==false) {
                    if (dynamic ) dynamic = false;
                    r = getRed(hmData.curfreq, hmData.curvel);
                    g = getGreen(hmData.curfreq, hmData.curvel);
                    //Log.i("Red Green:", Integer.toString(r)+" "+Integer.toString(g));
                    drawCircle(scaleX(cpList[hmData.curcp].y + 50), scaleY(cpList[hmData.curcp].x + 50), 15, Color.rgb(r, g, 0));
                } else {
                    if (!dynamic ) {
                        dynamic = true;
                        mBitmapMask = Bitmap.createBitmap(mBitmap);
                        mCanvasMask = new Canvas(mBitmapMask);
                        mPaint.setColor(Color.rgb(255, 255, 255));
                        lastX = scaleX(hmData.poseY+50);
                        lastY = scaleY(hmData.poseX+50);
                    }
                    else {
                        mCanvasMask.drawLine(lastX, lastY, scaleX(hmData.poseY+50), scaleY(hmData.poseX+50), mPaint);
                        mImageView.setImageBitmap(mBitmapMask);
                    }
                }
            }
        }
    };

    public void drawCircle(int x, int y, int radius, int color) {
        mPaint.setColor(color);//Color.parseColor(color));
        mCanvas.drawCircle(x, y, radius, mPaint);
        mImageView.setImageBitmap(mBitmap);
    }

    public void drawRect(double x1, double x2, float y1, float y2, int color) {
        mPaint.setColor(color);
        mCanvas.drawRect((float)x1, y1, (float)x2, y2, mPaint);
    }

    public void drawLine(int x1, int x2, int y1, int y2, int color) {
        mPaint.setColor(color);
        mCanvas.drawLine(x1, y1, x2, y2, mPaint);
    }

    public int scaleX(double x) {
        return (int) (x*screenSize.x)/100;
    }

    public int scaleY(double y) {
        return (int) (y*screenSize.y)/100;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("onStop","Unregistering receivers..");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mDataReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mInfoReceiver);
    }

    public int getRed(int freq, float vel) {
        int red = 20*freq;
        if (red>255) red=255;
        red -= 5*(vel-5);
        if (red < 0) red =0;
        return red;
    }

    public int getGreen(int freq, float vel) {
        int green = 20*freq;
        if (green>255) green=255;
        green = 255-green;
        green+=25*(vel-5);
        if (green <0) green=0;
        if (green >255) green=255;
        return green;
    }
}
