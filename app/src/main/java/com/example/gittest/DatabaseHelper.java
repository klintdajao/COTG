package com.example.gittest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "cotg.db";
    public static final String ACCOUNT_TABLE_NAME = "account_table";
    public static final String ACCOUNT_COL_1 = "ID";
    public static final String ACCOUNT_COL_2 = "EMAIL";
    public static final String ACCOUNT_COL_3 = "FIRSTNAME";
    public static final String ACCOUNT_COL_4 = "MIDDLENAME";
    public static final String ACCOUNT_COL_5 = "SURNAME";
    public static final String ACCOUNT_COL_6 = "PASSWORD";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ACCOUNT_TABLE_NAME + "(ID TEXT PRIMARY KEY, EMAIL TEXT, FIRSTNAME TEXT, MIDDLENAME TEXT, SURNAME TEXT, PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS "+ACCOUNT_TABLE_NAME);
        onCreate(db);
    }

    public boolean addUser(String id, String email, String fn, String mn, String ln, String p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ACCOUNT_COL_1,id);
        cv.put(ACCOUNT_COL_2,email);
        cv.put(ACCOUNT_COL_3,fn);
        cv.put(ACCOUNT_COL_4,mn);
        cv.put(ACCOUNT_COL_5,ln);
        cv.put(ACCOUNT_COL_6,p);
        long result = db.insert(ACCOUNT_TABLE_NAME,null,cv);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean checkUser(String id,String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * from account_table where ID = ? and PASSWORD = ?", new String[]{id,pass});
        if (c.getCount()>0)
            return true;
        return false;
    }
}
