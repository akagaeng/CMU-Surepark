package com.example.cmu.mqtt;

import android.content.Context;
import android.util.Log;

import com.example.cmu.cmu_client.R;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttCallbackHandler implements MqttCallback {

    private Context context;
    private String clientHandle;

    public MqttCallbackHandler(Context context, String clientHandle)
    {
        this.context = context;
        this.clientHandle = clientHandle;
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

        //Get connection object associated with this object
        Log.d("abc", "mqttcallbackhandler start");

        MQTTUtils mqttUtils = MQTTUtils.getInstance();
        mqttUtils.messageArrived(topic, message);

        //get the string from strings.xml and format
        String messageString = context.getString(R.string.messageRecieved, new String(message.getPayload()), topic+";qos:"+message.getQos()+";retained:"+message.isRetained());

        Log.d("abc", "MqttCallbackHandler receive :"+messageString);

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Do nothing
    }

}
