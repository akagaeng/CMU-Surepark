package com.spark_web.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PushNotificationService {

	public static void PushNotification(String topic, String content) {

		// String topic = "topic22";
		// String content = "test";
		int qos = 2;
		//String broker = "tcp://192.168.1.169:1883";
		String broker = "tcp://128.237.135.101:1883";
		String clientId = "Server";
		MemoryPersistence persistence = new MemoryPersistence();

		try {
			MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			System.out.println("Connecting to broker: " + broker);
			sampleClient.connect(connOpts);
			System.out.println("Connected");
			System.out.println("Publishing message: " + content);
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos);
			sampleClient.publish(topic, message);
			System.out.println("Message published");
			sampleClient.disconnect();
			System.out.println("Disconnected");

		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}
}
