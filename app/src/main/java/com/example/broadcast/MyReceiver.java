package com.example.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
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


                Intent intent1 = new Intent(context, MyReceiver.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);

                Notification notification = new NotificationCompat.Builder(context, App.CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.ic_one)
                        .setContentTitle(phoneNo)
                        .setContentText(msg)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setOnlyAlertOnce(true)
                        .setColor(Color.BLUE)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE).build();

                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
                mNotificationManager.notify(1, notification);
            }
        }
    }
}
