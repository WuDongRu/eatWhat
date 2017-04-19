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
    private final static String RG_NAME = " rG_name";
    private final static String RG_ADDRESS = " rG_address";


    private String sqlstore =
            "CREATE TABLE IF NOT EXISTS "+RESTAURANT_TABLE+"("+
                    _ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    RG_NAME+" CHAR,"+
                    RG_ADDRESS+" CHAR"+
                    ")";


    private SQLiteDatabase database;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlstore);

        db.execSQL("insert into restaurantGet values (20,'老吳魯肉飯','台北市信義區饒河街')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }


    public void addrestaurant(int _id , String rG_name , String rG_address){
        ContentValues values = new ContentValues();
        values.put("_id", _id);
        values.put("rG_name", rG_name);
        values.put("rG_address", rG_address);
        database.insert("restaurantGet", null, values);
    }

    public void close(){
        database.close();
    }
}
