package com.example.m05368.eatwhat;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

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
    private final static String S_PHONE = " S_phone";
    private final static String S_OPENTIME = " S_opentime";
    private final static String S_CLOSETIME = " S_closetime";
    private final static String P_PHOTES = " P_photes";

    private final static String SLOT_TABLE = "slot";
    private final static String SL_NAME = " SL_name";

    private final static String DIARY_TABLE = "diary";
    private final static String D_YEAR = " D_year";
    private final static String D_MONTH = " D_month";
    private final static String D_DAY = " D_day";
    private final static String D_NAME = " D_name";
    private final static String D_ADDRESS = " D_address";
    private final static String D_MEAL = " D_meal";
    private final static String D_PRICE = " D_price";
    private final static String D_SCORE = " D_score";
    private final static String D_COMMENT = " D_comment";
    private final static String D_PICTURE = " D_picture";

    private String sqlstore =
            "CREATE TABLE IF NOT EXISTS "+RESTAURANT_TABLE+"("+
                    _ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    S_NAME+" CHAR,"+
                    S_ADDRESS+" CHAR,"+
                    S_PRICE+" CHAR,"+
                    S_LONGITUDE+" CHAR,"+
                    S_LATITUDE+" CHAR,"+
                    T_NAME+" CHAR,"+
                    S_PHONE+" CHAR,"+
                    S_OPENTIME+" CHAR,"+
                    S_CLOSETIME+" CHAR,"+
                    P_PHOTES+" CHAR"+
                    ")";

    private String sqlslot =
            "CREATE TABLE IF NOT EXISTS "+SLOT_TABLE+"("+
                    _ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    SL_NAME+" CHAR"+
                    ")";

    private String sqldiary =
            "CREATE TABLE IF NOT EXISTS "+DIARY_TABLE+"("+
                    _ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    D_YEAR+" CHAR,"+
                    D_MONTH+" CHAR,"+
                    D_DAY+" CHAR,"+
                    D_NAME+" CHAR,"+
                    D_ADDRESS+" CHAR,"+
                    D_MEAL+" CHAR,"+
                    D_PRICE+" CHAR,"+
                    D_SCORE+" INTEGER,"+
                    D_COMMENT+" CHAR,"+
                    D_PICTURE+" BLOB"+
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
        db.execSQL(sqldiary);
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

    public void addrestaurant(int _id, String S_name, String S_address , String S_price, String S_longitude, String S_latitude,
                              String T_name, String S_phone, String S_opentime, String S_closetime, String P_photes){
        ContentValues values = new ContentValues();
        values.put("_id", _id);
        values.put("S_name", S_name);
        values.put("S_address", S_address);
        values.put("S_price", S_price);
        values.put("S_longitude", S_longitude);
        values.put("S_latitude", S_latitude);
        values.put("T_name", T_name);
        values.put("S_phone", S_phone);
        values.put("S_opentime", S_opentime);
        values.put("S_closetime", S_closetime);
        values.put("P_photes", P_photes);
        database.insert("restaurantGet", null, values);
    }

    public void addtoslot(String SL_name){
        ContentValues values = new ContentValues();
        values.put("SL_name", SL_name);
        database.insert("slot", null, values);
    }

    public void addtodiary(String D_year, String D_month, String D_day, String D_name, String D_address){
        ContentValues values = new ContentValues();
        values.put("D_year", D_year);
        values.put("D_month", D_month);
        values.put("D_day", D_day);
        values.put("D_name", D_name);
        values.put("D_address", D_address);
        database.insert("diary", null, values);
    }

    public void adddiaryinfo(Integer id,String D_meal, String D_price, Integer D_score, String D_comment){
        ContentValues values=new ContentValues();
        values.put("D_meal",D_meal);
        values.put("D_price",D_price);
        values.put("D_score",D_score);
        values.put("D_comment",D_comment);

        database.update("diary",values,"_id="+ id ,null);
    }

    public void close(){
        database.close();
    }
}
