package org.jderobot.test.wearclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Shady on 17/08/15.
 */
public class StartActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button mButton = (Button) findViewById(R.id.button);
        final EditText mEditText1 = (EditText) findViewById(R.id.editText);
        final EditText mEditText2 = (EditText) findViewById(R.id.editText2);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("ip",mEditText1.getText().toString());
                intent.putExtra("port", mEditText2.getText().toString());
                startActivity(intent);
                StartActivity.this.finish();
            }
        });
    }
}
