package com.example.hdy.mqttexample;

/**
 * Created by james on 12/10/15.
 */
public interface IReceivedMessageListener {

    public void onMessageReceived(ReceivedMessage message);
}