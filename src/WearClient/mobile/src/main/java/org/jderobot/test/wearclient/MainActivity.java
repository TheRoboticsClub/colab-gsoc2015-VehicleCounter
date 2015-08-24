package org.jderobot.test.wearclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.Timer;
import java.util.TimerTask;

import Ice.Communicator;
import Ice.LocalException;


public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private jderobot.HeatmapPrx heatmap;
    private byte[] mHeatmapInfoArray;
    private byte[] mHeatmapDataArray;
    private Timer timer;
    private boolean ICEInitialized = false;
    private Communicator ic1;
    private String ip;
    private String port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        ip = intent.getStringExtra("ip");
        port = intent.getStringExtra("port");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API).build();
        timer = new Timer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
        if (ic1!=null) {
            try {
                ic1.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
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
        Log.i("GoogleApiClient", "connected");
        new HeatmapRetriever().execute();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.w("GoogleApiClient", "connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("GoogleApiClient", "connection failed!!");
    }

    private class HeatmapRetriever extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            ic1 = null;
            try {
                ic1 = Ice.Util.initialize();
                Ice.ObjectPrx base = ic1.stringToProxy("Heatmap:default -h "+ip+" -p "+port);
                heatmap = jderobot.HeatmapPrxHelper.checkedCast(base);
                if (heatmap==null)
                    throw new Error("Invalid Proxy!");
                jderobot.HeatmapInfo hmInfo = heatmap.getHeatmapInfo();
                mHeatmapInfoArray = HeatmapInfo.serialize(new HeatmapInfo(hmInfo.height, hmInfo.width, hmInfo.cpList));
                ICEInitialized = true;
            } catch (LocalException e) {
                e.printStackTrace();
                Log.e("ICE", e.toString());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ICE", e.toString());
            }

            if (ICEInitialized) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
                        MessageApi.SendMessageResult result = null;
                        for (Node node : nodes.getNodes()) {
                            try {
                                result = Wearable.MessageApi.sendMessage(mGoogleApiClient, node.getId(),
                                        Constants.HeatmapInfoPath, mHeatmapInfoArray).await();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (!result.getStatus().isSuccess()) {
                                Log.e("MessageAPI", "Could not send HeatmapInfo");
                            } else {
                                Log.i("MessageAPI", "success!! HeatmapInfo sent to: " + node.getDisplayName());
                            }
                        }
                    }
                }).start();

                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if (heatmap != null) {
                            jderobot.HeatmapData hmData = heatmap.getHeatmapData();
                            jderobot.Pose3DData poseData = heatmap.getDronePose();
                            try {
                                mHeatmapDataArray = HeatmapData.serialize(new HeatmapData(hmData.state, hmData.curcp, hmData.curfreq, hmData.curvel,
                                                        poseData.x, poseData.y));
                                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
                                MessageApi.SendMessageResult result1 = null;
                                for (Node node : nodes.getNodes()) {
                                    result1 = Wearable.MessageApi.sendMessage(mGoogleApiClient, node.getId(),
                                            Constants.HeatmapDataPath, mHeatmapDataArray).await();
                                    if (!result1.getStatus().isSuccess()) {
                                        Log.e("MessageAPI", "Could not send HeatmapData");
                                    } else {
                                        Log.i("MessageAPI", "success!! HeatmapData sent to: " + node.getDisplayName());
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, 0, 1000);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void params) {
             if (!ICEInitialized) {
                Toast.makeText(getApplicationContext(), "ICE Server could not be reached", Toast.LENGTH_SHORT).show();
                MainActivity.this.finish();
            }
        }
    }
}
