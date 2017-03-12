package com.generonumero.blocodaguarda.push;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.generonumero.blocodaguarda.R;
import com.generonumero.blocodaguarda.login.view.impl.LoginActivity;
import com.generonumero.blocodaguarda.menu.view.impl.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class PushMessagingService extends FirebaseMessagingService {
    private String TAG = "teste";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getData());
    }

    private void sendNotification(String msgBody, Map<String, String> data) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        for (String key: data.keySet()) {
            intent.putExtra(key, data.get(key));
        }


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_ONE_SHOT);

        Bitmap rawBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon_push)
                .setLargeIcon(rawBitmap)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(msgBody)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    public PushMessagingService() {
        super();
        Log.i(TAG, "PushMessagingService");
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
        Log.i(TAG, "onDeletedMessages");
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
        Log.i(TAG, "onMessageSent");
        Log.i(TAG, s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
        Log.i(TAG, "onSendError");
        Log.i(TAG, s);
        Log.i(TAG, "exce " +e.getMessage());
    }

    @Override
    protected Intent zzF(Intent intent) {
        Log.i(TAG, "zzF");
        return super.zzF(intent);
    }

    @Override
    public boolean zzH(Intent intent) {
        Log.i(TAG, "zzH");
        return super.zzH(intent);
    }

    @Override
    public void zzm(Intent intent) {
        super.zzm(intent);
    }
}
