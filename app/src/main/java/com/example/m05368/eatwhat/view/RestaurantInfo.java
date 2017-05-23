package com.example.m05368.eatwhat.view;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.m05368.eatwhat.R;

import java.util.ArrayList;


public class RestaurantInfo extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView list_restaurant;
    private TextView restaurant;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        Intent intent = this.getIntent();
        final String name = intent.getStringExtra("name");

        list_restaurant = (ListView) findViewById(R.id.list_restaurant);
        restaurant = (TextView) findViewById(R.id.restaurant);
        restaurant.setText(name);

        final ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.openOrCreateDatabase("eatWhat_database", android.content.Context.MODE_PRIVATE, null);
        Cursor c=db.rawQuery("SELECT * FROM restaurantGet WHERE S_name = ?",new String[]{name});
        c.moveToFirst();
        arrayList.add(0,"類別 : "+c.getString(6));
        arrayList.add(1,"價格 : "+c.getString(3)+"元");
        arrayList.add(2,"時間 : "+(c.getString(8)).substring(0,5)+"-"+(c.getString(9)).substring(0,5));
        arrayList.add(3,"電話 : "+c.getString(7));
        arrayList.add(4,"地址 : "+c.getString(2));
        if(c.getString(11)!=null) {
            arrayList.add(5, "評論 :\n " + (c.getString(11)).replace(", ", "\n "));
        }


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        listAdapter = new ArrayAdapter<String>(this,R.layout.restaurant_item,arrayList);
        list_restaurant.setAdapter(listAdapter);

    }
}