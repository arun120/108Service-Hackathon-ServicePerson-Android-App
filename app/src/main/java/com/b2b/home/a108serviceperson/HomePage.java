package com.b2b.home.a108serviceperson;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button b1,b2,b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView name=(TextView) findViewById(R.id.main_name);

        SharedPreferences  sharedPreferences= getSharedPreferences("User", Context.MODE_PRIVATE);
        name.setText("DRI"+sharedPreferences.getString("user",""));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                widget(R.id.his);
            }
        },5000);

        String type="fontb.otf";
        final Typeface typeface=Typeface.createFromAsset(getAssets(),type);
        String type1="font.otf";
        final Typeface typeface1=Typeface.createFromAsset(getAssets(),type1);
        TextView set1,set11,set2,set22;
        set1= (TextView) findViewById(R.id.set1);
        set2= (TextView) findViewById(R.id.set2);
        set11= (TextView) findViewById(R.id.set11);
        set22= (TextView) findViewById(R.id.set22);
        set1.setTypeface(typeface);
        set2.setTypeface(typeface);
        set11.setTypeface(typeface1);
        set22.setTypeface(typeface1);
        b1= (Button) findViewById(R.id.takerequest);
        b2= (Button) findViewById(R.id.takehistory);
        b3= (Button) findViewById(R.id.takehistory1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomePage.this,ShowCase.class);
                i.putExtra("case_id",37);
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomePage.this,History.class);
                startActivity(i);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomePage.this,History.class);
                startActivity(i);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }


    public void widget(int id){
        if(true)
        return;
        RelativeLayout rl= (RelativeLayout) findViewById(id);
        switch (rl.getVisibility()){
            case View.GONE:{
                rl.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInLeft).duration(800).playOn(rl);
              //  YoYo.with(Techniques.Wave).duration(800).playOn(findViewById(R.id.noti));
               /// YoYo.with(Techniques.Wave).duration(800).playOn(findViewById(R.id.noti));
                break;
            }
            case View.VISIBLE:{
                rl.setVisibility(View.GONE);
                YoYo.with(Techniques.SlideOutRight).duration(800).playOn(rl);
                break;
            }
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {

        } else if (id == R.id.profile) {
            Intent i=new Intent(HomePage.this,Profile.class);
            startActivity(i);
        } else if (id == R.id.history) {

            Intent i=new Intent(HomePage.this,History.class);
            startActivity(i);
        }  else if (id == R.id.share) {

        } else if (id == R.id.settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
