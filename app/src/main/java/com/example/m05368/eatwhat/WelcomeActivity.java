package com.example.m05368.eatwhat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by M05368 on 2017/4/19.
 */

public class WelcomeActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        TextView text = (TextView) findViewById(R.id.text);
        db = openOrCreateDatabase("eatWhat_database", android.content.Context.MODE_PRIVATE, null);
        helper = new DBHelper(getApplicationContext());
        helper.deleteAll();

        getAsynHttp();
        postAsynHttp();

        mHandler.sendEmptyMessageDelayed(GOTO_MAIN_ACTIVITY, 5000); //2秒跳轉
    }


    private static final int GOTO_MAIN_ACTIVITY = 0;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case GOTO_MAIN_ACTIVITY:
                    Intent intent = new Intent();
                    //將原本Activity的換成MainActivity
                    intent.setClass(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;

                default:
                    break;
            }
        }
    };

    private void getAsynHttp() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://10.11.24.95/eatwhat/api/selectall")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String resStr = response.body().string();
                final List<JsonData> jsonData = new Gson().fromJson(resStr, new TypeToken<List<JsonData>>() {
                }.getType());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StringBuffer sb = new StringBuffer();

                        for (JsonData json : jsonData) {
                            sb.append("id:");
                            sb.append(json.getS_id());
                            sb.append("\n");
                            sb.append("name:");
                            sb.append(json.getS_name());
                            sb.append("\n");


                            helper.addrestaurant(json.getS_id(), json.getS_name(), json.getS_address(), json.getS_price(), json.getS__longitude(), json.getS_latitude(),
                                                String.valueOf(json.getT_name()).replace("[", "").replace("]",""), json.getS_phone(),json.getS_opentime(),json.getS_closetime(),
                                                String.valueOf(json.getP_photes()).replace("[", "").replace("]",""));
                        }
                        //text.setText(sb.toString());
                    }
                });
            }
        });

    }

    private void postAsynHttp() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("S_latitude", String.valueOf(latitude))
                .add("S__longitude", String.valueOf(longitude))
                .build();
        Request request1 = new Request.Builder()
                .url("http://10.11.24.95/eatwhat/api/getgps_name")
                .post(formBody)
                .build();
        client.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String str = response.body().string();
                final List<JsonData> jsonData = new Gson().fromJson(str, new TypeToken<List<JsonData>>() {
                }.getType());

                for (JsonData json : jsonData) {
                    helper.addtoslot(json.getS_name());
                }
            }
        });
    }


}
