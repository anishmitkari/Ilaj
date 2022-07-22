package com.userdoctor.ui.common.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.userdoctor.R;
import com.userdoctor.ui.common.Session.Session;
import com.userdoctor.ui.common.activity.Home_Activity.ActivityHome;
import com.userdoctor.ui.common.activity.Login_Section.ActivityLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private Session session;

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e("token_fcm_service", s);
        System.out.println("=====fcmtoken==="+s);
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getData().size() > 0){

            Log.e(TAG, "Data_Payload: " + remoteMessage.getData().toString());
            try {
                handleDataMessage(remoteMessage);

            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
                System.out.println("=====hello===="+e.fillInStackTrace());
            }
        }

        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());

            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            Log.e(TAG, "title_body: " + title+" "+body);

            handleNotification(title,body);
        }

    }


    private void handleNotification(String title, String body) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Intent resultIntent = new Intent(getApplicationContext(), ActivityHome.class);
            SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
            String format = s.format(new Date());

            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                Log.e("rr22","pppp22");
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Constants.CHANNEL_NAME);
                pushNotification.putExtra("message", body);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.showNotificationMessage(title, body, "", resultIntent);
             //   notificationUtils.playNotificationSound();
            }else{
                Log.e("rrrr","pppp");
                sendNoti(title, body);

                // If the app is in background, firebase itself handles the notification
            }
        }

    }

    private void sendNoti(String title, String body) {

        Intent intent = new Intent(this, ActivityHome.class);
        intent.putExtra("NOTIFICATION", "NOTIFICATION");
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder b = new NotificationCompat.Builder(this);
        String channelId = "Default";
        b.setAutoCancel(false);
        b.setDefaults(Notification.DEFAULT_ALL);
        b.setWhen(System.currentTimeMillis());
       b.setSmallIcon(R.drawable.ic_message_black_24dp);
        // b.setTicker("Hearty365");
        b.setContentTitle(title);
        b.setContentText(body);
        b.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);
        b.setContentIntent(contentIntent);
        // b.setContentInfo("Info");

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(1, b.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void handleDataMessage(RemoteMessage remoteMessage) {
        // Log.e(TAG, "push_json: " + json.toString());
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        String format = s.format(new Date());
        try {
            Map<String, String> params = remoteMessage.getData();
            JSONObject object = new JSONObject(params);
            Log.e("JSON OBJECT", object.toString());
            System.out.println("====jobj======"+object.toString());
            String body = object.getString("body");
            String title = object.getString("title");
            //String description = object.getString("description");
            // String image = object.getString("image");

            System.out.println("check-title=====" + title);
            //System.out.println("===========body=====" + body);
            // Bitmap bitmap = getBitmapfromUrl(image);



            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                session = new Session(this);

              //  if (session.isLoggedIn()) {
                    Log.e("type splash = ", "" + session.isLoggedIn());
                    Intent intent = new Intent(this, ActivityHome.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

                    String channelId = "Default";
                    NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)

                            .setSmallIcon(R.drawable.ic_message_black_24dp)
                            .setContentTitle(title)
                            .setContentText(body)
                            .setAutoCancel(true).setContentIntent(pendingIntent);
                    System.out.println("=====bhgggg===="+ title);
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                        manager.createNotificationChannel(channel);
                    }

                    manager.notify(generateRandom(), builder.build());

               // }
            }else
            {
               /* session = new Session(this);
                if (session.isLoggedIn()) {*/
                    Log.e("type splash = ", "" + session.isLoggedIn());
                    Intent intent = new Intent(this, ActivityHome.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                    String channelId = "Default";
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                            //.setLargeIcon(R.drawable.applogo)

                            .setSmallIcon(R.drawable.ic_message_black_24dp)
                            .setContentTitle(title)
                            .setContentText(body)
                            .setAutoCancel(true).setContentIntent(pendingIntent);
                    System.out.println("=====aaja===="+ title);
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                        manager.createNotificationChannel(channel);
                    }
                    manager.notify(generateRandom(), builder.build());








/*
                    if (mStr_LoginType.equals("Doctor")) {
                        Log.e("type splash = ", "" + session.isLoggedIn());
                        Intent intent = new Intent(this, Dr_Home_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);


                        String channelId = "Default";
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                                .setSmallIcon(R.drawable.logo)
                                .setContentTitle(title)
                                .setContentText(body).setAutoCancel(true).setContentIntent(pendingIntent);

                        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                            manager.createNotificationChannel(channel);
                        }
                        manager.notify(generateRandom(), builder.build());




                    }
                    else if (mStr_LoginType.equals("Nursing")) {
                        Log.e("type splash = ", "" + session.isLoggedIn());
                        Intent intent = new Intent(this, Nurse_Home_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                        String channelId = "Default";
                        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
                                .setSmallIcon(R.drawable.logo)
                                .setContentTitle(title)
                                .setContentText(body).setAutoCancel(true).setContentIntent(pendingIntent);
                        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                            manager.createNotificationChannel(channel);
                        }
                        manager.notify(generateRandom(), builder.build());






                    }


           */







               // }







            }

        } catch (JSONException e) {
            Log.e("error", "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e("error", "Exception: " + e.getMessage());
        }
    }

/*    private Bitmap getBitmapfromUrl(String image) {
        try {
            URL url = new URL(NOtificationImagepath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }

    }*/


    public int generateRandom(){
        Random random = new Random();
        return random.nextInt(9999 - 1000) + 2000;
    }



}


////////////////////////////////////raghav sir code///////////////////////

























