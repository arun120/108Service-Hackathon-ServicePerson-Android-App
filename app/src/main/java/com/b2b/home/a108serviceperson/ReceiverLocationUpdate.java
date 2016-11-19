package com.b2b.home.a108serviceperson;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;

public class ReceiverLocationUpdate extends BroadcastReceiver {
    public ReceiverLocationUpdate() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        SharedPreferences sharedPreferences=context.getSharedPreferences("User", Context.MODE_PRIVATE);
        Log.i("Broadcast","Received");
        if( sharedPreferences.getString("user",null)!=null) {
            //  Toast.makeText(getApplicationContext(),"Proceed to home",Toast.LENGTH_SHORT).show();

            Intent i1 = new Intent(context, NotificationService.class);
            i1.putExtra("user", sharedPreferences.getString("user", ""));
            PendingIntent pi1 = PendingIntent.getService(context, 0, i1, 0);

            am.cancel(pi1);


            am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + 5 * 1000,//5 sec
                    5 * 1000, pi1);// 5sec

            Intent i = new Intent(context, LocationUpdate.class);
            i.putExtra("user", sharedPreferences.getString("user", ""));
            PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

            am.cancel(pi);


            am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + 5 * 1000,//5 sec
                    5 * 1000, pi);// 5sec





        }

    }
}
