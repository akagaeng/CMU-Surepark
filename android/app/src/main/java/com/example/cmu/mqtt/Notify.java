/*******************************************************************************
 * Copyright (c) 1999, 2014 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution. 
 *
 * The Eclipse Public License is available at 
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 */
package com.example.cmu.mqtt;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.cmu.cmu_client.R;

import java.util.Calendar;

/**
 * Provides static methods for creating and showing notifications to the user.
 */
public class Notify {

    private static int MessageID = 0; // Message ID Counter

    static void notifcation(Context context, String messageString, Intent intent, int notificationTitle) {

        //Get the notification manager which we will be used to display the notification
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);

        Calendar.getInstance().getTime().toString();

        long when = System.currentTimeMillis();

        //get the notification title from the application's strings.xml file
        CharSequence contentTitle = context.getString(notificationTitle);

        //the message that will be displayed as the ticker
        String ticker = contentTitle + " " + messageString;

        //build the pending intent that will start the appropriate activity
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, intent, 0);

        //build the notification
        Notification.Builder mBuilder = new Notification.Builder(context);
        mBuilder.setAutoCancel(true)
                .setContentTitle(contentTitle)
                .setContentIntent(pendingIntent)
                .setContentText(messageString)
                .setTicker(ticker)
                .setWhen(when)
                .setSmallIcon(R.mipmap.ic_launcher);

        Notification notification = mBuilder.build();

        //display the notification
        mNotificationManager.notify(MessageID, notification);
        MessageID++;

    }
}