package org.jderobot.wearteleoperator;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;


import java.io.ByteArrayOutputStream;
import java.util.Timer;
import java.util.TimerTask;

import Ice.Communicator;
import Ice.LocalException;
import jderobot.ArDroneExtraPrx;
import jderobot.CMDVelData;
import jderobot.CMDVelPrx;
import jderobot.CameraPrx;
import jderobot.ImageData;


public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    public static boolean connected = false;
    private Communicator ic;
    public static CameraPrx cameraPrx;
    public static CMDVelPrx cmdVelPrx;
    public static ArDroneExtraPrx arDroneExtraPrx;
    private GoogleApiClient mGoogleApiClient=null;
    private Bitmap mBitmap;
    private int[] pixels;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView.setImageDrawable(getResources().getDrawable(R.mipmap.icon));
        new initProxy().execute();
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mGoogleApiClient.isConnected()) mGoogleApiClient.connect();
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (connected) {
                    try {
                        int width = cameraPrx.getImageData("RGB8").description.width;
                        int height = cameraPrx.getImageData("RGB8").description.height;
                        pixels = new int[height * width];
                        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                        ImageData image = cameraPrx.getImageData("RGB8");
                        int c;
                        for (int i = 0; i < width * height; i++) {
                            c = Color.rgb(0x000000ff & image.pixelData[i * 3 + 0],
                                    0x000000ff & image.pixelData[i * 3 + 1],
                                    0x000000ff & image.pixelData[i * 3 + 2]);
                            pixels[i] = c;
                        }
                        mBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
                        //mImageView.setImageBitmap(mBitmap);
                        sendImg(mBitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 200);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("GoogleApiClient", "connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("GoogleApiClient", "connection failed");
    }

    public class initProxy extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            ic  = null;
            boolean flag = true;
            try {
                ic = Ice.Util.initialize();
                Ice.ObjectPrx cambase = ic.stringToProxy(Constants.PROXY_CAMERA);
                cameraPrx = jderobot.CameraPrxHelper.checkedCast(cambase);
                if (cameraPrx==null) {
                    flag = false;
                    throw new Error("Camera: Invalid proxy!");
                }
                Ice.ObjectPrx cmdbase = ic.stringToProxy(Constants.PROXY_CMDVEL);
                cmdVelPrx = jderobot.CMDVelPrxHelper.checkedCast(cmdbase);
                if (cmdVelPrx==null) {
                    flag = false;
                    throw new Error("CMDVel: Invalid proxy!");
                }
                Ice.ObjectPrx arbase = ic.stringToProxy(Constants.PROXY_EXTRA);
                arDroneExtraPrx = jderobot.ArDroneExtraPrxHelper.checkedCast(arbase);
                if (arDroneExtraPrx==null) {
                    flag = false;
                    throw new Error("ArExtra: Invalid proxy!");
                }
                if (flag) connected = true;
            } catch (LocalException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void params) {
            Log.d("onPostExecute", Boolean.toString(connected));
            if (!connected) {
                Toast.makeText(getApplicationContext(), "ICE Server could not be reached", Toast.LENGTH_SHORT).show();
                MainActivity.this.finish();
            }
        }
    }

    public static void sendVel(float vx, float vy, float vz, float roll, float pitch, float yaw) {
        CMDVelData vel = new CMDVelData();
        vel.linearX = vx;
        vel.linearY = vy;
        vel.linearZ = vz;
        vel.angularX = roll;
        vel.angularY = pitch;
        vel.angularZ = yaw;
        cmdVelPrx.setCMDVelData(vel);
    }

    public void sendImg(Bitmap bp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] arr = stream.toByteArray();
        try {
            NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
            MessageApi.SendMessageResult result = null;
            for (Node node : nodes.getNodes()) {

                result = Wearable.MessageApi.sendMessage(mGoogleApiClient, node.getId(),
                        Constants.IMAGE_PATH, arr).await();

                if (!result.getStatus().isSuccess()) {
                    Log.e("MessageAPI", "Could not send Camera Image");
                } else {
                    Log.i("MessageAPI", "success!! Camera Image sent to: " + node.getDisplayName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
