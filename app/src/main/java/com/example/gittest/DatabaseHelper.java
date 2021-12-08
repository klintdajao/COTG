package com.example.gittest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "cotg.db";
    public static final String ACCOUNT_TABLE_NAME = "account_table";
    public static final String CART_TABLE_NAME = "cart_table";
    public static final String VENDOR_TABLE_NAME = "vendor_table";

    public static final String ACCOUNT_COL_1 = "ID";
    public static final String ACCOUNT_COL_2 = "EMAIL";
    public static final String ACCOUNT_COL_3 = "FIRSTNAME";
    public static final String ACCOUNT_COL_4 = "MIDDLENAME";
    public static final String ACCOUNT_COL_5 = "SURNAME";
    public static final String ACCOUNT_COL_6 = "PASSWORD";

    public static final String CART_COL_1 = "CARTID";
    public static final String CART_COL_2 = "PROD_NAME";
    public static final String CART_COL_3 = "PROD_QUANT";
    public static final String CART_COL_4 = "PROD_PRICE";
    public static final String CART_COL_5 = "ID";

    public static final String VENDOR_COL_1 = "VENDORID";
    public static final String VENDOR_COL_2 = "VENDORNAME";
    public static final String VENDOR_COL_3 = "VENDOREMAIL";
    public static final String VENDOR_COL_4 = "VENDORPASS";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ACCOUNT_TABLE_NAME + "(ID TEXT PRIMARY KEY, EMAIL TEXT, FIRSTNAME TEXT, MIDDLENAME TEXT, SURNAME TEXT, PASSWORD TEXT)");
        db.execSQL("create table  " + CART_TABLE_NAME +  "(CARTID INTEGER PRIMARY KEY AUTOINCREMENT, PROD_NAME TEXT, PROD_QUANT INTEGER, PROD_PRICE DOUBLE, ID TEXT, FOREIGN KEY (ID) REFERENCES cart_table (ID))");
        db.execSQL("create table " + VENDOR_TABLE_NAME + "(VENDORID TEXT PRIMARY KEY, VENDORNAME TEXT, VENDOREMAIL TEXT, MIDDLENAME TEXT, SURNAME TEXT, VENDORPASS TEXT)");

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
    public AccountInfo readUser (String idnum){
        SQLiteDatabase db = this.getReadableDatabase();
        AccountInfo a = null;
        String where = "ID = '"+idnum+"'";
        Cursor cursor = db.query(ACCOUNT_TABLE_NAME,null,where,null,null,null,null);

        if(cursor.moveToNext()){
            a = new AccountInfo();
            a.setId(cursor.getString(0));
            a.setEmail(cursor.getString(1));
            a.setFn(cursor.getString(2));
            a.setMn(cursor.getString(3));
            a.setLn(cursor.getString(4));
            a.setP(cursor.getString(5));
        }
        return a;

    }

    public boolean updateUser(String id,String email, String fn, String mn, String ln ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ACCOUNT_COL_2,email);
        cv.put(ACCOUNT_COL_3,fn);
        cv.put(ACCOUNT_COL_4,mn);
        cv.put(ACCOUNT_COL_5,ln);
        Cursor cursor = db.rawQuery("select * from account_table where ID = ?",new String[]{id});
        if(cursor.getCount()>0) {
            long result = db.update("account_table", cv, "ID = ?", new String[]{id});
            if (result==-1)
                return false;
            else
                return true;
        }else
            return false;

    }
    public boolean addToCart(String prodName, int prodQty, double prodP, String user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CART_COL_2, prodName);
        contentValues.put(CART_COL_3, prodQty);
        contentValues.put(CART_COL_4, prodP);
        contentValues.put(CART_COL_5, user);

        long result = db.insert(CART_TABLE_NAME, null, contentValues);
        db.close();
        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public int checkOrderQuantity(String prodName, String userid){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select PROD_QUANT from cart_table where " + CART_COL_2 + "= '"+prodName+"'" + " AND " + CART_COL_5 + "= '"+userid+"'";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int quant = 0;
        do {
            if(c.getCount()!=0) {
                quant = Integer.parseInt(c.getString(0));
                return quant;
            }
            else {
                return 0;
            }
        }while(c.moveToNext());
    }

    public boolean updateOrder(String userid, String ordername, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CART_COL_3, quantity);

        return db.update(CART_TABLE_NAME, contentValues, ACCOUNT_COL_1 + "= '"+userid+"'" + " AND " + CART_COL_2 + "= '"+ordername+"'", null)>0;
    }

    public ArrayList<String> checkCartList(String userid){
        ArrayList<String> data=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select PROD_NAME from cart_table where ID = ?", new String[]{userid});
        String fieldToAdd;
        while(c.moveToNext()){
            fieldToAdd = c.getString(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }

    public ArrayList<Integer> checkCartQuantity(String userid){
        ArrayList<Integer> data = new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select PROD_QUANT from cart_table where ID = ?", new String[]{userid});
        Integer fieldToAdd=null;
        while(c.moveToNext()){
            fieldToAdd = c.getInt(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }

    public ArrayList<Double> checkPrice(String userid){
        ArrayList<Double> data=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT PROD_PRICE from cart_table where ID = ?", new String[]{userid});
        Double fieldToAdd=null;
        while(c.moveToNext()){
            fieldToAdd = c.getDouble(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }

    public boolean deleteCartEntry(String userid, String ordername) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        String query = "Select * from cart_table where ID =" + "'" + userid + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            cursor.getString(0);
            db.delete("cart_table", "PROD_NAME = ?", new String[]{ordername});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    public boolean deleteCart(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        String query = "Select * from cart_table where ID =" + "'" + userid + "'";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            db.delete("cart_table", "ID = ?", new String[]{userid});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean checkId(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * from account_table where ID = ?", new String[]{id});
        if (c.getCount()>0)
            return true;
        return false;
    }

    public boolean updatePassword(String pass , String id){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_COL_6,pass);
        long result = db.update("account_table", contentValues, "ID = ?", new String[]{id});
        if(result == -1) return false;
        else
            return true;
    }
}
