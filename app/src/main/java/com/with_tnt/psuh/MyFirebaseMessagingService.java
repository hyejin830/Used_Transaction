package com.with_tnt.psuh;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;
import com.with_tnt.db_connection.push.ReceiveMessage;
import com.with_tnt.myapplication3.R;
import com.with_tnt.myapplication3.chat.ChattingActivity;
import com.with_tnt.myapplication3.main.MainActivity;
import com.with_tnt.myapplication3.product.ProductlistActivity;

import java.util.List;

import static android.app.PendingIntent.getActivity;
import static com.with_tnt.myapplication3.chat.ChattingActivity.chatCon;
import static com.with_tnt.myapplication3.chat.ChattingActivity.productNumber;
import static com.with_tnt.myapplication3.chat.ChattingActivity.sender;


/**
 * Created by KIM on 2017-04-28.
 */


public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().get("title").toString().trim().equals("message")) {
            Context ctx = this;
            ActivityManager activityManager = (ActivityManager) ctx.getSystemService(ctx.ACTIVITY_SERVICE);

            List<ActivityManager.RunningTaskInfo> info;

            info = activityManager.getRunningTasks(1);

            ActivityManager.RunningTaskInfo runningTaskInfo = info.get(0);

            String curActivityName = runningTaskInfo.topActivity.getClassName();
            String receivedData[] = new String[3]; // arr[0] == productNumber, arr[1] == sender, arr[2] == Message
            receivedData = remoteMessage.getData().get("body").split("%_%");
            Log.e("text", remoteMessage.getData().get("body"));
            Log.e("text", receivedData[2].toString());
            if (curActivityName.equals("com.with_tnt.myapplication3.chat.ChattingActivity")) {
                Log.e("text", sender + " and " + productNumber);
                if (sender.equals(receivedData[1]) & productNumber == Integer.parseInt(receivedData[0])) {
                    receiveMessage(receivedData[2]);
                } else {
                    getPushMessage(receivedData[2], receivedData[0], receivedData[1]);
                }
            } else {
                getPushMessage(receivedData[2], receivedData[0], receivedData[1]);
            }
        } else {
            sendPushNotification(remoteMessage.getData().get("body"), remoteMessage.getData().get("title"));
        }
    }

    private void sendPushNotification(String message, String title) {
        Log.e("text", "여기니ssss?;");
        if (getSharedPreferences("Datas", 0).getBoolean("Getnotice", true)) {
            Intent intent = new Intent(this, ProductlistActivity.class);
            intent.putExtra("word", message);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.app_icon).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_icon))
                    .setContentTitle(title)
                    .setContentText(message + "가 등록되었습니다!")
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri).setLights(000000255, 500, 2000)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
            wakelock.acquire(5000);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        } else // 알람을 껐을때
        {
            Log.e("text", "COME FROM FCM");
        }
    }

    private void getPushMessage(String message, String productNumber, String sender) {
        Log.e("text", "여기니sqqqqqsss?;");
        Intent intent = new Intent(this, ChattingActivity.class);
        intent.putExtra("NUMBER", Integer.parseInt(productNumber));
        intent.putExtra("SENDER", sender);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.app_icon).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_icon))
                .setContentTitle("메세지가 도착했습니다!")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri).setLights(000000255, 500, 2000)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        wakelock.acquire(5000);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void receiveMessage(String message) {
        Log.e("text", "receiveMessage : " + message);
        new ReceiveMessage(chatCon, message).execute();
    }
}

