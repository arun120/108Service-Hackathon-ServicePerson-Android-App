package com.b2b.home.a108serviceperson;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.skyfishjy.library.RippleBackground;

public class MainActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    SharedPreferences sharedPreferences=null;
    SharedPreferences.Editor edit=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login=(Button) findViewById(R.id.login);

        final EditText user=(EditText) findViewById(R.id.username);
        final EditText pass=(EditText) findViewById(R.id.pass);
        String type="fontb.otf";
        final Typeface typeface=Typeface.createFromAsset(getAssets(),type);
        YoYo.with(Techniques.Landing).duration(1500).playOn(findViewById(R.id.logo));
        final RippleBackground rippleBackground= (RippleBackground) findViewById(R.id.content);
        rippleBackground.startRippleAnimation();
        final Boolean bol=rippleBackground.isRippleAnimationRunning();
        if(bol==true){
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rippleBackground.stopRippleAnimation();
                }
            },2500);
        }
        final TextView v1,v2;
        v1= (TextView) findViewById(R.id.textView5);
        v2= (TextView) findViewById(R.id.usrs);
        final RelativeLayout rl= (RelativeLayout) findViewById(R.id.rel);

        sharedPreferences= getSharedPreferences("User", Context.MODE_PRIVATE);
        edit=sharedPreferences.edit();
        mProgressDialog=new ProgressDialog(this);

        if( sharedPreferences.getString("user",null)!=null){
            //  Toast.makeText(getApplicationContext(),"Proceed to home",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(this,HomePage.class);
            i.putExtra("user",sharedPreferences.getString("user",""));
            startActivity(i);
            finish();

        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userid=user.getText().toString();
                String password=pass.getText().toString();
                rippleBackground.startRippleAnimation();
                Boolean bols=rippleBackground.isRippleAnimationRunning();
                if(bols==true){
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rippleBackground.stopRippleAnimation();
                        }
                    },1500);
                }

                new AsyncTask<String,Void,String>(){

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();


                        mProgressDialog.setMessage("Registering .....");
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        mProgressDialog.show();
                    }

                    @Override
                    protected void onPostExecute(String id) {
                        super.onPostExecute(id);
                        mProgressDialog.dismiss();
                        if(id==null) {
                            Toast.makeText(getApplicationContext(), "Invalid UserName/Password", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getApplicationContext(),HomePage.class);
                            i.putExtra("user",id);
                            startActivity(i);
                            return;
                        }

                        YoYo.with(Techniques.SlideOutUp).duration(1000).playOn(findViewById(R.id.rel));
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                rl.setVisibility(View.INVISIBLE);
                                v1.setTypeface(typeface);
                                v2.setTypeface(typeface);
                                v1.setVisibility(View.VISIBLE);
                                v2.setVisibility(View.VISIBLE);
                                v2.setText(userid);
                                YoYo.with(Techniques.BounceIn).duration(1500).playOn(findViewById(R.id.textView5));
                                YoYo.with(Techniques.BounceIn).duration(1500).playOn(findViewById(R.id.usrs));
                            }
                        },900);



                        edit.putString("user",id);
                        edit.commit();
                        // Toast.makeText(getApplicationContext(),String.valueOf(c.getCust_id()),Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),HomePage.class);
                        i.putExtra("user",id);
                        startActivity(i);
                    }

                    @Override
                    protected String doInBackground(String... params) {
                        return Database.verifyDriver(params[0],params[1]);

                    }
                }.execute(userid,password);


            }
        });
    }
}
