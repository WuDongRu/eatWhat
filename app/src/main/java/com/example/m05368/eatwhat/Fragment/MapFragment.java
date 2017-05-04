package com.example.m05368.eatwhat.Fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.m05368.eatwhat.DBHelper;
import com.example.m05368.eatwhat.DownloadImageTask;
import com.example.m05368.eatwhat.R;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class MapFragment extends Fragment implements OnMapReadyCallback{

    private GoogleMap mMap;
    private static View view;
    private SQLiteDatabase db;
    private DBHelper helper;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view != null) {
            ViewGroup viewGroupParent = (ViewGroup) view.getParent();
            if (viewGroupParent != null)
                viewGroupParent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_map, container, false);
        } catch (Exception e) {
            // map is already there
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView type = (TextView) view.findViewById(R.id.type);
        TextView price = (TextView) view.findViewById(R.id.price);
        TextView address = (TextView) view.findViewById(R.id.address);

        SQLiteDatabase db = getActivity().openOrCreateDatabase("eatWhat_database", android.content.Context.MODE_PRIVATE, null);
        Cursor c=db.query("restaurantGet",null,null,null,null,null,null);
        c.moveToPosition(1);
        name.setText(c.getString(1));
        type.setText(c.getString(6));
        price.setText(c.getString(3)+"元");
        address.setText(c.getString(2));

        new DownloadImageTask((ImageView) view.findViewById(R.id.photo))
                .execute("http://10.11.24.95/eatwhat/image/爭鮮新LOGO-01.jpg");

        return view;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        SQLiteDatabase db = getActivity().openOrCreateDatabase("eatWhat_database", android.content.Context.MODE_PRIVATE, null);
        DBHelper helper = new DBHelper(getActivity().getApplicationContext());
        Cursor c=db.query("restaurantGet",null,null,null,null,null,null);
        String[] str = new String [c.getCount()];
        String[] str1 = new String [c.getCount()];
        String[] str2 = new String [c.getCount()];

        for(int i = 0 ; i < c.getCount() ; i++) {
            c.moveToPosition(i);
            str[i]= c.getString(1);
            str1[i]= c.getString(4);
            str2[i]= c.getString(5);

            LatLng test = new LatLng(Double.parseDouble(str2[i]), Double.parseDouble(str1[i]));
            mMap.addMarker(new MarkerOptions().position(test).title(str[i]));
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
        LocationManager locationManager = (LocationManager)
                getContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng myLocation = new LatLng(latitude, longitude);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));

    }
}
