package com.example;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

public class MyActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 0;
    private static final String DEFAULT_CHANNEL_ID = "defaultChannelId - doesn't matter anyway because I haven't created any channels";
    private NotificationManagerCompat notificationManager;
    private Glide glide;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        glide = new GlideBuilder().build(this);
        notificationManager = NotificationManagerCompat.from(this);

        setupChannels();
    }

    private void setupChannels() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel(DEFAULT_CHANNEL_ID, "default channel id", NotificationManager.IMPORTANCE_DEFAULT);
        manager.createNotificationChannel(notificationChannel);
    }

    // don't talk to me or my son's nested callbacks ever again
    public void sendNotification(View view) {
        String largeIconUrl = "https://robohash.org/asfdsaf";
        final String bigPictureUrl = "https://robohash.org/asfsdfsafdsaf";

        glide.with(this).asBitmap().load(largeIconUrl).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(final Bitmap largeIcon, Transition<? super Bitmap> transition) {
                glide.with(MyActivity.this).asBitmap().load(bigPictureUrl).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bigPicture, Transition<? super Bitmap> transition) {
                        Notification notification = new NotificationCompat.Builder(MyActivity.this, DEFAULT_CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_local_car_wash_black_24dp)
                                .setContentTitle("This is the content title")
                                .setContentText("This is the content text")
                                .setLargeIcon(largeIcon) // this is the small avatar style image
                                .setStyle(new NotificationCompat.BigPictureStyle()
                                        .bigLargeIcon(null) // overrides largeIcon in the expanded mode - so null will appear as if the large icon is fading out
                                        .bigPicture(bigPicture)
                                )
                                .build();
                        notificationManager.notify(NOTIFICATION_ID, notification);
                    }
                });
            }
        });
    }

    public void sendBasicNotification(View view) {
        Notification notification = new NotificationCompat.Builder(MyActivity.this, DEFAULT_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_local_car_wash_black_24dp)
                .setContentTitle("This is a later notification")
                .setContentText("It makes older big notifications collapse")
                .build();
        notificationManager.notify(1, notification);
    }
}
