package com.example.m05368.eatwhat.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.example.m05368.eatwhat.DBHelper;
import com.example.m05368.eatwhat.Json.JsonComment;
import com.example.m05368.eatwhat.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SlotRestaurant extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_restaurant);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        Intent intent = this.getIntent();
        final String name = intent.getStringExtra("name");


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
        LocationManager locationManager = (LocationManager)
                this.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng myLocation = new LatLng(latitude, longitude);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16));
        SQLiteDatabase db = openOrCreateDatabase("eatWhat_database", android.content.Context.MODE_PRIVATE, null);
        DBHelper helper = new DBHelper(getApplicationContext());

        Cursor c=db.rawQuery("SELECT * FROM restaurantGet WHERE S_name = ?",new String[]{name});
        c.moveToFirst();


        LatLng latlng = new LatLng(Double.parseDouble(c.getString(5)),Double.parseDouble(c.getString(4)));
        mMap.addMarker(new MarkerOptions()
                .position(latlng)
                .title(name)
                .snippet("點擊獲取詳細資訊"));

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                getAsynHttp();
                Intent intent = new Intent(SlotRestaurant.this,RestaurantInfo.class);
                intent.putExtra("name",name);
                startActivity(intent);
            } });
    }

    private void getAsynHttp() {
        SQLiteDatabase db = openOrCreateDatabase("eatWhat_database", android.content.Context.MODE_PRIVATE, null);
        final DBHelper helper = new DBHelper(getApplicationContext());
        OkHttpClient client = new OkHttpClient();
        Request request2 = new Request.Builder()
                .url("http://10.11.24.95/eatwhat/api/selectComment")
                .build();
        client.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String resStr = response.body().string();
                final List<JsonComment> jsonComment = new Gson().fromJson(resStr, new TypeToken<List<JsonComment>>() {
                }.getType());
                StringBuffer sb = new StringBuffer();

                for (JsonComment json : jsonComment) {
                    sb.append(json.getComment());
                    sb.append("\n");

                    helper.addcomment(json.getS_id(),String.valueOf(json.getComment()).replace("[", "").replace("]",""));
                }
            }
        });
    }

}
