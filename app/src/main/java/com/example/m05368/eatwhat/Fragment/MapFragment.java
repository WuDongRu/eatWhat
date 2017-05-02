package com.example.m05368.eatwhat.Fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.m05368.eatwhat.DBHelper;
import com.example.m05368.eatwhat.R;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


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
            str1[i]= c.getString(3);
            str2[i]= c.getString(4);

            LatLng test = new LatLng(Double.parseDouble(str2[i]), Double.parseDouble(str1[i]));
            mMap.addMarker(new MarkerOptions().position(test).title(str[i]));
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }

        LatLng test = new LatLng(25.047739, 121.578002);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(test, 16));

    }
}
