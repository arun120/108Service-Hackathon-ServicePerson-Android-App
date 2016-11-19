package com.b2b.home.a108serviceperson;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                String userid=user.getText().toString();
                String password=pass.getText().toString();

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
                            return;
                        }



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
