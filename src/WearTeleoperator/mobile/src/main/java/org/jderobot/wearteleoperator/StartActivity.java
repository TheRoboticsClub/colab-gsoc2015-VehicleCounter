package org.jderobot.wearteleoperator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Shady on 21/08/15.
 */
public class StartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button mButton = (Button) findViewById(R.id.button);
        final EditText mEditText = (EditText) findViewById(R.id.editText);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.IP_ADDRESS = mEditText.getText().toString();
                Constants.PROXY_CAMERA = "Camera:default -h "+Constants.IP_ADDRESS+Constants.PROXY_CAMERA;
                Constants.PROXY_CMDVEL = "CMDVel:default -h "+Constants.IP_ADDRESS+Constants.PROXY_CMDVEL;
                Constants.PROXY_EXTRA = "Extra:default -h "+Constants.IP_ADDRESS+Constants.PROXY_EXTRA;
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                StartActivity.this.finish();
            }
        });
    }
}
