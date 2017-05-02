package com.example.m05368.eatwhat;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by M05368 on 2017/4/19.
 */

public class DBHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "eatWhat_database";
    private final static int DATABASE_VERSION = 1;

    private final static String RESTAURANT_TABLE = "restaurantGet";
    private final static String _ID = "_id";
    private final static String S_NAME = " S_name";
    private final static String S_ADDRESS = " S_address";
    private final static String S_LONGITUDE = " S_longitude";
    private final static String S_LATITUDE = " S_latitude";


    private String sqlstore =
            "CREATE TABLE IF NOT EXISTS "+RESTAURANT_TABLE+"("+
                    _ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    S_NAME+" CHAR,"+
                    S_ADDRESS+" CHAR,"+
                    S_LONGITUDE+" CHAR,"+
                    S_LATITUDE+" CHAR"+
                    ")";


    private SQLiteDatabase database;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlstore);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS"+RESTAURANT_TABLE);
        onCreate(db);
    }

    public void deleteAll(){
        database.delete("restaurantGet",null, null);
    }

    public void addrestaurant(int _id , String S_name , String S_address , String S_longitude, String S_latitude){
        ContentValues values = new ContentValues();
        values.put("_id", _id);
        values.put("S_name", S_name);
        values.put("S_address", S_address);
        values.put("S_longitude", S_longitude);
        values.put("S_latitude", S_latitude);
        database.insert("restaurantGet", null, values);
    }

    public void close(){
        database.close();
    }
}
