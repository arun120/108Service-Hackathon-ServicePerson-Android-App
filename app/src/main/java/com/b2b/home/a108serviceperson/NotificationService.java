package com.b2b.home.a108serviceperson;

import android.app.ListFragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import java.util.List;

public class NotificationService extends Service {
    public NotificationService() {
    }
    Handler handler=new Handler();
    Runnable runnable =null;
    SharedPreferences sharedPreferences=null;
    SharedPreferences.Editor edit=null;
    private PowerManager.WakeLock mWakeLock;







    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"TAG" );
        mWakeLock.acquire();




        sharedPreferences= getSharedPreferences("User", Context.MODE_PRIVATE);

        Log.i("Notification","Checking");
        runnable=new Runnable() {
            @Override
            public void run() {

                DbNotification dbn = new DbNotification();

                    dbn.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,sharedPreferences.getString("user", ""));


            }
        };

        handler.postDelayed(runnable,1000);




        return Service.START_STICKY;
    }

    private class DbNotification extends AsyncTask<String,Void,Void>
    {
        // public String ret=new String();


        @Override
        protected Void doInBackground(String... params) {

            String id=params[0];

            List<String> case_id=Database.getNotification(id);
            //Log.i("NotificationDB",case_id.get(0));

            for(String val:case_id){
                sendNotification(val);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


        }

        void sendNotification(String case_id){

                Intent intent=new Intent(NotificationService.this,ShowCase.class);
                intent.putExtra("case_id",case_id);

                PendingIntent pIntent = PendingIntent.getActivity(NotificationService.this, (int) System.currentTimeMillis(), intent, 0);





                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    Uri sounduri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);



                    Notification.Builder mbuilder = new Notification.Builder(NotificationService.this)
                            .setContentTitle("New Request has Come ")
                            .setContentText("Click this to view the Location")
                           // .setSound(sounduri)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentIntent(pIntent)
                            .setAutoCancel(true);


                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    notificationManager.notify(Integer.valueOf(case_id),  mbuilder.build());




                }





        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        super.onDestroy();
        mWakeLock.release();
    }
}
