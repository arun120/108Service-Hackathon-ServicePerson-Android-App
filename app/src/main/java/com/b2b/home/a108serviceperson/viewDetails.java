package com.b2b.home.a108serviceperson;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class viewDetails extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentActivity myContext;
    SharedPreferences sharedPreferences;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressDialog mProgressDialog,mProgressDialog1;
    Customer_Case  c;


    public viewDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static viewDetails newInstance(String param1, String param2) {
        viewDetails fragment = new viewDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        sharedPreferences= getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        TextView des=(TextView) getActivity().findViewById(R.id.description);
        TextView num=(TextView) getActivity().findViewById(R.id.numaffected);
        mProgressDialog=new ProgressDialog(myContext);
        des.setText(ShowCase.c.getDescription());
        num.setText(ShowCase.c.getNo_ppl_affected());

        Button req=(Button) getActivity().findViewById(R.id.request);

        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Case_Link link=new Case_Link();
                link.setDriver_id(sharedPreferences.getString("user",""));
                link.setCase_id(ShowCase.c.getCase_id());

                new AsyncTask<Case_Link,Integer,String>(){
                    @Override
                    protected void onPreExecute() {


                        super.onPreExecute();

                        mProgressDialog.setMessage("Submitting Request .....");
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        mProgressDialog.show();


                    }

                    @Override
                    protected void onProgressUpdate(Integer... values) {
                        super.onProgressUpdate(values);

                        Toast.makeText(getActivity().getApplicationContext(),"Time out: "+String.valueOf(values[0]),Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    protected void onPostExecute(String res) {
                        super.onPostExecute(res);
                        Toast.makeText(getActivity().getApplicationContext(),res,Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    protected String doInBackground(Case_Link... params) {
                        String s=Database.addRequest(params[0]);
                        mProgressDialog.dismiss();
                        if(s.equals("true")){

                            for(int i=0;i<10;i++){
                                try {
                                    Thread.sleep(1000);
                                    Log.i("Time out",String.valueOf(i));
                                    publishProgress(i);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                                s=Database.finalizeRequest(params[0]);

                                        if(s.equals("true")){

                                            Log.i("Request","Procced to Spot!!!");
                                            //Toast.makeText(getActivity().getApplicationContext(),"Procced to Spot!!!",Toast.LENGTH_SHORT).show();
                                        return "Procced to Spot!!!";

                                        }else{
                                            Log.i("Request","Nearer Driver has already requested");

                                         //  Toast.makeText(getActivity().getApplicationContext(),"Nearer Driver has already requested",Toast.LENGTH_SHORT).show();
                                        return "Nearer Driver has already requested";
                                        }
                        }
                        else
                        {

                            Log.i("Request","Nearer Driver has already requested");
                            return "Nearer Driver has already requested";
                          //  Toast.makeText(getActivity().getApplicationContext(),"Nearer Driver has already requested",Toast.LENGTH_SHORT).show();
                        }



                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,link);
            }
        });




    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myContext = (FragmentActivity) context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_details, container, false);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */



}
