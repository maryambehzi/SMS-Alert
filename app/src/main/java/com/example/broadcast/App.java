package com.example.broadcast;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";

    @Override
    public void onCreate() {
        super.onCreate();
        creatNotificationChannels();
    }

    private void creatNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_1_ID,
                    "channel1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("This is channel 1");

            NotificationChannel channe2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "channel2",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription("This is channel 2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            manager.createNotificationChannel(channe2);
        }
    }
}
