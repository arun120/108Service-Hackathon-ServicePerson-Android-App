package com.b2b.home.a108serviceperson;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class ShowCase extends AppCompatActivity {
    public static Customer_Case c;
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog=new ProgressDialog(this);
        setContentView(R.layout.activity_show_case);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String id=getIntent().getExtras().getString("case_id");

        new AsyncTask<String,Void,String>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();


                mProgressDialog.setMessage("Fetching Details .....");
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.show();
            }

            @Override
            protected void onPostExecute(String id) {
                super.onPostExecute(id);
                c.setCase_id(id);
                mProgressDialog.dismiss();




            }

            @Override
            protected String doInBackground(String... params) {
                c=Database.getcaseDetails(params[0]);
                return params[0];

            }
        }.execute(id);


        ViewPager mViewPager;
        mViewPager=(ViewPager) findViewById(R.id.pager);


        MyPagerAdapter adapterViewPager;
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(),null,ShowCase.this);
        mViewPager.setAdapter(adapterViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }







    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private final int NUM_ITEMS = 2;
        Bundle args;
        private Context context;

        public MyPagerAdapter(FragmentManager fragmentManager, Bundle a, Context context) {

            super(fragmentManager);
            this.context=context;
            args=a;
        }

        // Returns total number of pages
        @Override public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override   public android.support.v4.app.Fragment getItem(int position) {

            android.support.v4.app.Fragment fragment;

            switch (position) {


                case 0:
                    fragment=new DriverMap();
                    break;
                case 1:
                    fragment=new viewDetails();
                    break;
                default:
                    return null;
            }
            fragment.setArguments(args);

            return fragment;
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {


                case 0:
                    return "View Point";

                case 1:
                    return "view Details";

                default:
                    return null;
            }
        }

    }
}
