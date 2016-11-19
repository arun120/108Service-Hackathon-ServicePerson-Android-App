package com.b2b.home.a108serviceperson;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import java.io.Console;

public class LocationUpdate extends Service {
    public LocationUpdate() {
    }


    SharedPreferences sharedPreferences=null;
    SharedPreferences.Editor edit=null;
    private PowerManager.WakeLock mWakeLock;
    GPSTracker gps;




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"TAG" );
        mWakeLock.acquire();




        sharedPreferences= getSharedPreferences("User", Context.MODE_PRIVATE);
        gps=new GPSTracker(this);
      new AsyncTask<Void,Void,Void>(){

          @Override
          protected void onPreExecute() {
              super.onPreExecute();
          }

          @Override
          protected void onPostExecute(Void aVoid) {
              super.onPostExecute(aVoid);
          }

          @Override
          protected Void doInBackground(Void... params) {
              Driver_Location loc=new Driver_Location();

              loc.setDriver_id(sharedPreferences.getString("user", ""));
              loc.setLongitude(String.valueOf(gps.getLongitude()));
              loc.setLatitude(String.valueOf(gps.getLatitude()));
              if(!loc.getLatitude().equals("0.0"))
              Database.updatedriverLocation(loc);
              Log.i("Updated",loc.getLatitude());
              return null;
          }
      }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        return Service.START_STICKY;
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
