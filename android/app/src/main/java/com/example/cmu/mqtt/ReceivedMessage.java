package com.example.cmu.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Date;

/**
 *  Defines the message which is sent from the push server.
 *  */
public class ReceivedMessage {

    private String topic; // topic of message
    private MqttMessage message; // message from the push server
    private Date timestamp; // time when message arrived

    public ReceivedMessage(String topic, MqttMessage message) {
        this.topic = topic;
        this.message = message;
        this.timestamp = new Date();
    }

    public String getTopic() {
        return topic;
    }
    public MqttMessage getMessage() {
        return message;
    }
    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "ReceivedMessage{" +
                "topic='" + topic + '\'' +
                ", message=" + message +
                ", timestamp=" + timestamp +
                '}';
    }
}
