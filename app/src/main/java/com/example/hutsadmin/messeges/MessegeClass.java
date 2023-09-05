package com.example.hutsadmin.messeges;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.hutsadmin.MessegeDetailActivity;
import com.example.hutsadmin.R;
import com.example.hutsadmin.ui.DashboardActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MessegeClass extends FirebaseMessagingService {
    private final String CHANNEL_ID = "channel_id";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        String messageType = message.getData().get("type"); // Assuming you have a "type" field in your notification payload

        if ("order".equals(messageType)) {
            // It's an order notification, open the DashboardActivity
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent contentIntent = PendingIntent.getActivity(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
            );

            // Build the order notification
            Notification orderNotification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle(message.getData().get("title"))
                    .setContentText(message.getData().get("message"))
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setAutoCancel(true)
                    .setContentIntent(contentIntent)
                    .build();

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(new Random().nextInt(), orderNotification);
        } else if ("chat".equals(messageType)) {
            // It's a chat notification, open the MessegeDetailActivity
            Intent intent = new Intent(this, MessegeDetailActivity.class);
            // Pass any relevant data to the chat activity via intent extras
            intent.putExtra("chat_id", message.getData().get("chat_id"));

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent contentIntent = PendingIntent.getActivity(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
            );

            // Build the chat notification
            Notification chatNotification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle(message.getData().get("title"))
                    .setContentText(message.getData().get("message"))
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setAutoCancel(true)
                    .setContentIntent(contentIntent)
                    .build();

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(new Random().nextInt(), chatNotification);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "channelName", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("My description");
        channel.enableLights(true);
        channel.setLightColor(Color.WHITE);

        // Create the notification channel
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }
}
