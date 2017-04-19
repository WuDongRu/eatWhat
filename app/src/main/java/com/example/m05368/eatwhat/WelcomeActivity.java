package com.example.m05368.eatwhat;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
        mHandler.sendEmptyMessageDelayed(GOTO_MAIN_ACTIVITY, 2000); //2秒跳轉

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://10.11.36.142/eatwhat/api/selectall")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String resStr = response.body().string();
                final List<JsonData> jsonData = new Gson().fromJson(resStr , new TypeToken<List<JsonData>>(){}.getType());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StringBuffer sb = new StringBuffer();
                        db = openOrCreateDatabase("eatWhat_database", android.content.Context.MODE_PRIVATE, null);
                        helper = new DBHelper(getApplicationContext());
                        for(JsonData json : jsonData){
                            sb.append("id:");
                            sb.append(json.getS_id());
                            sb.append("\n");
                            sb.append("name:");
                            sb.append(json.getS_name());
                            sb.append("\n");
                            sb.append("address:");
                            sb.append(json.getS_address());
                            sb.append("\n");
                            sb.append("\n");
                            helper.addrestaurant(json.getS_id(),json.getS_name(),json.getS_address());
                        }
                        db.close();
                        helper.close();
                    }
                });
            }
        });
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
}
