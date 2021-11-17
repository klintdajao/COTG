package com.example.gittest;

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
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY, EMAIL TEXT, FIRSTNAME TEXT, MIDDLENAME TEXT, SURNAME TEXT, PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
