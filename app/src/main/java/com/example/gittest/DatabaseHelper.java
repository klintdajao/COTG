package com.example.gittest;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "account.db";
    public static final String TABLE_NAME = "account_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "EMAIL";
    public static final String COL_3 = "FIRSTNAME";
    public static final String COL_4 = "MIDDLENAME";
    public static final String COL_5 = "SURNAME";
    public static final String COL_6 = "PASSWORD";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID TEXT PRIMARY KEY, EMAIL TEXT, FIRSTNAME TEXT, MIDDLENAME TEXT, SURNAME TEXT, PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean addUser(String id, String email, String fn, String mn, String ln, String p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1,id);
        cv.put(COL_2,email);
        cv.put(COL_3,fn);
        cv.put(COL_4,mn);
        cv.put(COL_5,ln);
        cv.put(COL_6,p);
        long result = db.insert(TABLE_NAME,null,cv);
        if(result == -1)
            return false;
        else
            return true;
    }
}
