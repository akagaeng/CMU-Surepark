package com.spark_web.service;

public class PaymentService extends PushNotificationService {

	public void payment(String authenticationnum, int parking_price) {
		
		PushNotification(authenticationnum, "payment:$"+parking_price);
	}

}
