package com.b2b.home.a108serviceperson;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Clock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        long time= System.currentTimeMillis();
        Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(time);
        int hr=cal.get(Calendar.HOUR);
        int min=cal.get(Calendar.MINUTE);
        int sec=cal.get(Calendar.SECOND);
        Log.i("Hours Minutes",String.valueOf(hr)+','+String.valueOf(min)+','+String.valueOf(sec));
        ImageView hour= (ImageView) findViewById(R.id.hour);
        ImageView mins= (ImageView) findViewById(R.id.minutes);
        ImageView secs= (ImageView) findViewById(R.id.seconds);
        Bitmap hours=rotate(R.drawable.clock_small,30*hr);
        Bitmap minutes=rotate(R.drawable.clock_min,6*min);
        RelativeLayout rl1,rl2;
        rl1= (RelativeLayout) findViewById(R.id.hour_rotate);
        rl2= (RelativeLayout) findViewById(R.id.min_rotate);
        hour.setImageBitmap(hours);
        mins.setImageBitmap(minutes);
        Animation anima= AnimationUtils.loadAnimation(this, R.anim.rotates);
        anima.setInterpolator(new LinearInterpolator());
        secs.startAnimation(anima);
        Animation animah= AnimationUtils.loadAnimation(this, R.anim.rotateh);
        anima.setInterpolator(new LinearInterpolator());
        rl1.startAnimation(animah);
        Animation animam= AnimationUtils.loadAnimation(this, R.anim.rotatem);
        anima.setInterpolator(new LinearInterpolator());
        rl2.startAnimation(animam);

    }

    Bitmap rotate(int id,float angle){
        Matrix matrix=new Matrix();
        matrix.postRotate(angle);
        Bitmap src= BitmapFactory.decodeResource(getResources(),id);
        return Bitmap.createBitmap(src,0,0,src.getWidth(),src.getHeight(),matrix,true);
    }
}
