/*******************************************************************************
 * Copyright (c) 1999, 2016 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 *
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 *
 */
package com.example.hdy.mqttexample;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
    TextView resultText;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MQTTUtils mqttUtils = MQTTUtils.getInstance();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        Button connect = (Button)findViewById(R.id.connectButton);
        connect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                EditText serverEditText = (EditText) findViewById(R.id.serverText);
                Button connectButton = (Button) findViewById(R.id.connectButton);
                Button publishButton = (Button) findViewById(R.id.publishButton);
                Button subscribeButton = (Button) findViewById(R.id.subscribeButton);
                resultText = (TextView) findViewById(R.id.resultTextView);
                String server = serverEditText.getText().toString();
                if (MQTTUtils.connect(server, getApplicationContext())) {
                    connectButton.setEnabled(false);
                    serverEditText.setEnabled(false);
                    publishButton.setEnabled(true);
                    subscribeButton.setEnabled(true);
                    resultText.setText("Connected to the server.");
                } else {
                    resultText.setText("Error connecting the server.");
                }
            }

        });

        Button publish = (Button)findViewById(R.id.publishButton);
        publish.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                resultText = (TextView)findViewById(R.id.resultTextView);
                EditText publishEditText = (EditText)findViewById(R.id.publishText);
                String topic = "topic";
                String payload = publishEditText.getText().toString();
                MQTTUtils.pub(topic, payload);
                Log.d("abc", "publish");
            }
        });

        Button subscribe = (Button)findViewById(R.id.subscribeButton);
        subscribe.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                resultText = (TextView)findViewById(R.id.resultTextView);
                EditText subscribeEditText = (EditText)findViewById(R.id.subscribeText);
                String topic = subscribeEditText.getText().toString();
                MQTTUtils.sub(topic);
                resultText.setText(String.format("Topic: %s", topic));
            }
        });

        mqttUtils.addReceivedMessageListner(new IReceivedMessageListener() {
            @Override
            public void onMessageReceived(ReceivedMessage message) {
                String messageContext = message.getMessage().toString();
                Log.d("abc", "topic:" + message.getTopic() + " message: " + messageContext + " timestamp: " + message.getTimestamp());
                //resultText.setText(messageContext);
                Log.d("abc", "message listener ends");
                vibrator.vibrate(700);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}