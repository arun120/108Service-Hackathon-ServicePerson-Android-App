package com.b2b.home.a108serviceperson;


    import android.app.Activity;
    import android.app.ProgressDialog;
    import android.content.Context;
    import android.content.Intent;
    import android.content.pm.PackageManager;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.graphics.Matrix;
    import android.location.Location;
    import android.net.Uri;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.os.Handler;
    import android.support.v4.app.ActivityCompat;
    import android.support.v4.app.Fragment;
    import android.support.v4.app.FragmentActivity;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.view.animation.Animation;
    import android.view.animation.AnimationUtils;
    import android.view.animation.LinearInterpolator;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.RelativeLayout;
    import android.widget.Toast;

    import com.daimajia.androidanimations.library.Techniques;
    import com.daimajia.androidanimations.library.YoYo;
    import com.google.android.gms.maps.CameraUpdateFactory;
    import com.google.android.gms.maps.GoogleMap;
    import com.google.android.gms.maps.OnMapReadyCallback;
    import com.google.android.gms.maps.SupportMapFragment;
    import com.google.android.gms.maps.model.BitmapDescriptorFactory;
    import com.google.android.gms.maps.model.LatLng;
    import com.google.android.gms.maps.model.MarkerOptions;

    import java.util.Calendar;


public class DriverMap extends android.support.v4.app.Fragment implements OnMapReadyCallback {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";
        ProgressDialog mProgressDialog;
        private FragmentActivity myContext;
        GoogleMap mMap;
        LatLng loc;


        // TODO: Rename and change types of parameters
        // private String mParam1;
        //private String mParam2;


        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Notes_fragment.
         */
        // TODO: Rename and change types and number of parameters
        public static DriverMap newInstance(String param1, String param2) {
            DriverMap fragment = new DriverMap();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        }

        public DriverMap() {
            // Required empty public constructor


        }


        @Override
        public void onAttach(Activity activity) {
            myContext = (FragmentActivity) activity;
            super.onAttach(activity);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);


        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            return inflater.inflate(R.layout.fragment_driver_map, container, false);


        }
    static Button takerequest;
    Button cancelrequest;
        @Override
        public void onStart() {
            super.onStart();
            mProgressDialog = new ProgressDialog(getActivity());
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map2);
            mapFragment.getMapAsync(this);
        }
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            GPSTracker gps=new GPSTracker(getContext());
            loc=new LatLng(gps.getLatitude(), gps.getLongitude());
            if (ActivityCompat.checkSelfPermission(myContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(myContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(ShowCase.c.getLatitude()),Double.valueOf(ShowCase.c.getLongitude()))));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {

                    LatLng marker_pos=marker.getPosition();

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr="+"My+Location"+"&daddr="+marker_pos.latitude+","+marker_pos.longitude));
                    startActivity(intent);
                    return true;
                }
            });
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    loc = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));


                }
            });


        }






    }

