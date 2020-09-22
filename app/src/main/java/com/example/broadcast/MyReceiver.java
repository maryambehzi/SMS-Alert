package com.example.broadcast;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsBroadcastReceiver";
    String msg, phoneNo;
    NotificationManagerCompat notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "intenr received:"+ intent.getAction());
        if (intent.getAction() == SMS_RECEIVED){
            Bundle dataBundle = intent.getExtras();
            if (dataBundle != null){
                Object[] mypdu = (Object[]) dataBundle.get("pdus");
                final SmsMessage[] message = new SmsMessage[mypdu.length];
                for (int i =0; i<mypdu.length; i++){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        String format = dataBundle.getString("format");
                        message[i] = SmsMessage.createFromPdu((byte[])mypdu[i], format);
                    }else {
                        message[i] = SmsMessage.createFromPdu((byte[]) mypdu[i]);
                    }
                    msg = message[i].getMessageBody();
                    phoneNo = message[i].getOriginatingAddress();
                }
                Toast.makeText(context , "Message: "+ msg+ "\nNumber: "+ phoneNo, Toast.LENGTH_LONG).show();
//
//                Intent activityIntent = new Intent(context, MainActivity.class);
//                PendingIntent contentIntent = PendingIntent.getActivity(context, 0 , activityIntent,0);
//
//                Notification notification = new NotificationCompat.Builder(context, App.CHANNEL_1_ID)
//                        .setSmallIcon(R.drawable.ic_one)
//                        .setContentTitle(phoneNo)
//                        .setContentText(msg)
//                        .setPriority(NotificationCompat.PRIORITY_HIGH)
//                        .setContentIntent(contentIntent)
//                        .setAutoCancel(true)
//                        .setOnlyAlertOnce(true)
//                        .setColor(Color.BLUE)
//                        .setCategory(NotificationCompat.CATEGORY_MESSAGE).build();
//
//                notificationManager.notify(1, notification);
            }
        }
    }
}
