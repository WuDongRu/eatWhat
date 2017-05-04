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
    private final static String S_PRICE = " S_price";
    private final static String S_LONGITUDE = " S_longitude";
    private final static String S_LATITUDE = " S_latitude";
    private final static String T_NAME = " T_name";


    private final static String SLOT_TABLE = "slot";
    private final static String SL_NAME = " SL_name";

    private String sqlstore =
            "CREATE TABLE IF NOT EXISTS "+RESTAURANT_TABLE+"("+
                    _ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    S_NAME+" CHAR,"+
                    S_ADDRESS+" CHAR,"+
                    S_PRICE+" CHAR,"+
                    S_LONGITUDE+" CHAR,"+
                    S_LATITUDE+" CHAR,"+
                    T_NAME+" CHAR"+
                    ")";

    private String sqlslot =
            "CREATE TABLE IF NOT EXISTS "+SLOT_TABLE+"("+
                    _ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    SL_NAME+" CHAR"+
                    ")";


    private SQLiteDatabase database;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlstore);
        db.execSQL(sqlslot);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS"+RESTAURANT_TABLE);
        onCreate(db);
    }

    public void deleteAll(){
        database.delete("restaurantGet",null, null);
        database.delete("slot",null, null);
    }

    public void addrestaurant(int _id , String S_name , String S_address , String S_price, String S_longitude, String S_latitude, String T_name){
        ContentValues values = new ContentValues();
        values.put("_id", _id);
        values.put("S_name", S_name);
        values.put("S_address", S_address);
        values.put("S_price", S_price);
        values.put("S_longitude", S_longitude);
        values.put("S_latitude", S_latitude);
        values.put("T_name", T_name);
        database.insert("restaurantGet", null, values);
    }

    public void addtoslot(String SL_name){
        ContentValues values = new ContentValues();
        values.put("SL_name", SL_name);
        database.insert("slot", null, values);
    }

    public void close(){
        database.close();
    }
}
