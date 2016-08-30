package com.example.hdy.mqttexample;

/**
 * Created by 동재 on 2016-07-15.
 */

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.ArrayList;

public class MQTTUtils {

    private static MqttClient client;
    private static MQTTUtils mqttUtilsInstance = null;
    private ArrayList<IReceivedMessageListener> receivedMessageListeners = new ArrayList<IReceivedMessageListener>();
    private static String clientHandle = "";
    private static Context curContext;
    private static final String activityClass = "com.example.hdy.mqttexample.MainActivity";

    public static MqttClient getClient() {
        return client;
    }

    public synchronized static MQTTUtils getInstance(){
        if(mqttUtilsInstance ==  null){
            mqttUtilsInstance = new MQTTUtils();
        }
        return mqttUtilsInstance;
    }

    public static boolean connect(String url, Context context) {
        try {
            MemoryPersistence persistance = new MemoryPersistence();
            client = new MqttClient("tcp://" + url + ":1883", "client1", persistance);
            clientHandle = "client1";
            curContext = context;
            client.setCallback(new MqttCallbackHandler(curContext, clientHandle));
            client.connect();
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean pub(String topic, String payload) {
        MqttMessage message = new MqttMessage(payload.getBytes());
        try {
            client.publish(topic, message);
            return true;
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sub(String topic) {
        try {
            client.subscribe(topic);
            Log.d("abc", "subscribe");
            return true;
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addReceivedMessageListner(IReceivedMessageListener listener) {
        receivedMessageListeners.add(listener);
    }

    public void messageArrived(String topic, MqttMessage message) {
        ReceivedMessage msg = new ReceivedMessage(topic, message);
        Log.d("abc", "Get message: " + message);
        //notify the user
        //Notify.notifcation(context, context.getString(R.string.notification, notifyArgs), intent, R.string.notifyTitle);

        Log.d("abc", "number of listener: " + receivedMessageListeners.size());
        for (IReceivedMessageListener listener : receivedMessageListeners) {
            Log.d("abc", "Xxxxxxxxxxxxxxxxxxx");
            listener.onMessageReceived(msg);
        }

        Intent intent = new Intent();
        intent.setClassName(curContext, activityClass);

        Object[] notifyArgs = new String[3];
        notifyArgs[0] = this.getId();
        notifyArgs[1] = new String(message.getPayload());
        notifyArgs[2] = topic;

        Notify.notifcation(curContext, curContext.getString(R.string.notification, notifyArgs), intent, R.string.notifyTitle);
        Log.d("abc", "mqttutils ends");
    }

    public String getId() {
        return clientHandle;
    }
}