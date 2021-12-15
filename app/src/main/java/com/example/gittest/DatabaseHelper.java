package com.example.gittest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "cotg.db";
    public static final String ACCOUNT_TABLE_NAME = "account_table";
    public static final String PRODUCT_TABLE_NAME = "products_table";
    public static final String CART_TABLE_NAME = "cart_table";
    public static final String VENDOR_TABLE_NAME = "vendor_table";
    public static final String ORDER_TABLE_NAME = "order_table";
    public static final String CATEGORY_TABLE_NAME = "category_table";

    public static final String ACCOUNT_COL_1 = "ID";
    public static final String ACCOUNT_COL_2 = "EMAIL";
    public static final String ACCOUNT_COL_3 = "FIRSTNAME";
    public static final String ACCOUNT_COL_4 = "MIDDLENAME";
    public static final String ACCOUNT_COL_5 = "SURNAME";
    public static final String ACCOUNT_COL_6 = "PASSWORD";

    public static final String PRODUCT_COL_1 = "PROD_ID";
    public static final String PRODUCT_COL_2 = "PROD_NAME";
    public static final String PRODUCT_COL_3 = "PROD_DESC";
    public static final String PRODUCT_COL_4 = "PROD_PRICE";
    public static final String PRODUCT_COL_5 = "PROD_STOCK";
    public static final String PRODUCT_COL_6 = "PROD_IMG";
    public static final String PRODUCT_COL_7 = "VENDOR_ID";
    public static final String PRODUCT_COL_8 = "CATEG_ID";

    public static final String CART_COL_1 = "CARTID";
    public static final String CART_COL_2 = "PROD_NAME";
    public static final String CART_COL_3 = "PROD_QUANT";
    public static final String CART_COL_4 = "PROD_PRICE";
    public static final String CART_COL_5 = "ID";
    public static final String CART_COL_6 = "PROD_ID";
    public static final String CART_COL_7 = "VENDORID";

    public static final String VENDOR_COL_1 = "VENDORID";
    public static final String VENDOR_COL_2 = "VENDORNAME";
    public static final String VENDOR_COL_3 = "VENDOREMAIL";
    public static final String VENDOR_COL_4 = "VENDORPASS";

    public static final String ORDER_COL_1 = "ORDERID";
    public static final String ORDER_COL_2 = "ID";
    public static final String ORDER_COL_3 = "ORDER_NAME";
    public static final String ORDER_COL_4 = "ORDER_QUANT";
    public static final String ORDER_COL_5 = "ORDER_AMOUNT";
    public static final String ORDER_COL_6 = "ORDER_DATE";
    public static final String ORDER_COL_7 = "ACTIVE";
    public static final String ORDER_COL_8 = "COUNT";
    public static final String ORDER_COL_9 = "PROD_ID";
    public static final String ORDER_COL_10 = "VENDORID";


    public static final String CATEGORY_COL_1 = "CATEG_ID";
    public static final String CATEGORY_COL_2 = "CATEG_NAME";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, 2);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ACCOUNT_TABLE_NAME + "(ID TEXT PRIMARY KEY, EMAIL TEXT, FIRSTNAME TEXT, MIDDLENAME TEXT, SURNAME TEXT, PASSWORD TEXT)");
        db.execSQL("create table " + PRODUCT_TABLE_NAME + "(PROD_ID INTEGER PRIMARY KEY AUTOINCREMENT, PROD_NAME TEXT, PROD_DESC TEXT, PROD_PRICE DOUBLE, PROD_STOCK INTEGER, PROD_IMG TEXT, VENDOR_ID INTEGER, CATEG_ID INTEGER, FOREIGN KEY (VENDOR_ID) REFERENCES product_table (VENDOR_ID), FOREIGN KEY (CATEG_ID) REFERENCES product_table (CATEG_ID))");
        db.execSQL("create table  " + CART_TABLE_NAME +  "(CARTID INTEGER PRIMARY KEY AUTOINCREMENT, PROD_NAME TEXT, PROD_QUANT INTEGER, PROD_PRICE DOUBLE, ID TEXT, PROD_ID TEXT, VENDORID TEXT, FOREIGN KEY (ID) REFERENCES cart_table (ID), FOREIGN KEY (PROD_ID) REFERENCES cart_table (PROD_ID), FOREIGN KEY (VENDORID) REFERENCES cart_table (VENDORID))");
        db.execSQL("create table " + VENDOR_TABLE_NAME + "(VENDORID TEXT PRIMARY KEY, VENDORNAME TEXT, VENDOREMAIL TEXT, VENDORPASS TEXT)");
        db.execSQL("create table " + ORDER_TABLE_NAME + "(ORDERID INTEGER PRIMARY KEY AUTOINCREMENT, ORDER_NAME TEXT, ORDER_QUANT INTEGER, ORDER_AMOUNT DOUBLE, ORDER_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, ACTIVE BOOLEAN, COUNT INTEGER, ID TEXT, PROD_ID INTEGER, VENDORID TEXT, FOREIGN KEY (ID) REFERENCES order_table (ID), FOREIGN KEY (PROD_ID) REFERENCES order_table (PROD_ID), FOREIGN KEY (VENDORID) REFERENCES order_table (VENDORID))");
        db.execSQL("create table " + CATEGORY_TABLE_NAME + "(CATEG_ID INTEGER PRIMARY KEY AUTOINCREMENT, CATEG_NAME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS "+ACCOUNT_TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS "+CART_TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS "+VENDOR_TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS "+ORDER_TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS "+PRODUCT_TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS "+CATEGORY_TABLE_NAME);
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
    public boolean checkVendor(String id, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * from vendor_table where VENDORID = ? and VENDORPASS = ?", new String[]{id,pass});
        if (c.getCount()>0)
            return true;
        return false;
    }

    public VendorInfo readVendor (String id){
        SQLiteDatabase db = this.getReadableDatabase();
        VendorInfo v = null;
        String where = "VENDORID = '"+id+"'";
        Cursor cursor = db.query(VENDOR_TABLE_NAME,null,where,null,null,null,null);

        if(cursor.moveToNext()){
            v = new VendorInfo();
            v.setId(cursor.getString(0));
            v.setName(cursor.getString(1));
            v.setEmail(cursor.getString(2));
            v.setP(cursor.getString(3));
        }
        return v;
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

    //--------Vendor Update-----------//

    public boolean updateVendor(String id,String email){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(VENDOR_COL_3,email);
        Cursor cursor = db.rawQuery("select * from vendor_table where VENDORID = ?",new String[]{id});
        if(cursor.getCount()>0) {
            long result = db.update("vendor_table", cv, "VENDORID = ?", new String[]{id});
            if (result==-1)
                return false;
            else
                return true;
        }else
            return false;

    }

    //--------cart_fragment------------//
    public boolean addToCart(int prodId, int prodQty,String user){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db2 = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        String query = "SELECT PROD_NAME, PROD_PRICE, VENDOR_ID from products_table where PROD_ID =" + prodId;
        Cursor c = db.rawQuery(query, null);

        while (c.moveToNext()){
            contentValues.put(CART_COL_2, c.getString(0));
            contentValues.put(CART_COL_4, c.getString(1));
            contentValues.put(CART_COL_7, c.getString(2));
        }

        contentValues.put(CART_COL_3, prodQty);
        contentValues.put(CART_COL_5, user);
        contentValues.put(CART_COL_6, prodId);

        long result = db.insert(CART_TABLE_NAME, null, contentValues);
        db.close();
        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public int checkOrderQuantity(int prodId, String userid){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select PROD_QUANT from cart_table where " + CART_COL_6 +" = " + prodId + " AND " + CART_COL_5 + "= '"+userid+"'";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int quant = 0;
        do {
            if(c.getCount()!=0) {
                quant = c.getInt(0);
                return quant;
            }
            else {
                return 0;
            }
        }while(c.moveToNext());
    }
    public boolean updateOrder(String userid, int prodid, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CART_COL_3, quantity);

        return db.update(CART_TABLE_NAME, contentValues, ACCOUNT_COL_1 + "= '"+userid+"'" + " AND " + CART_COL_6 + "=" + prodid, null)>0;
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
    public boolean deleteOrder(String userid){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        String query = "Select * from order_table where ID =" + "'" + userid + "'";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            db.delete("order_table", "ID = ?", new String[]{userid});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    //vendor order details ready
    public boolean readyOrder(String userid){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = 0;
        ContentValues cv = new ContentValues();
        cv.put(ORDER_COL_7,false);
        String query = "Select * from order_table where ID =" + "'" + userid + "'";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            result = db.update("order_table", cv, "ID = ?", new String[]{userid});
        }
        db.close();
        if (result==-1)
            return false;
        else
            return true;
    }
    //----------------------------------//

    public boolean checkId(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * from account_table where ID = ?", new String[]{id});
        if (c.getCount()>0)
            return true;
        return false;
    }
    public boolean checkOrderId(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * from order_table where ID = ?", new String[]{id});
        if (c.getCount()>0)
            return true;
        return false;
    }
    public boolean updatePW(String pass, String id ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ACCOUNT_COL_6,pass);
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
    public boolean deleteUser(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("account_table", "ID = ?", new String[]{id})>0;
    }

//    int order_id, String id, String order_name, String order_quant, Double order_amount
    public boolean placeOrder(String id){
        String name, vendor;
        Integer quantity, idL;
        Double amount;

        int ctr;
        boolean active = true;
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db2 = this.getReadableDatabase();
        int count = 0;

        Cursor c = db2.rawQuery("SELECT MAX(count) from order_table", null);
        while(c.moveToNext()){
            if(c != null){
                count = c.getInt(0);
            }
            else{
                count=1;
                break;
            }
        }
        count++;

        ArrayList<String> orderList;
        ArrayList<Double> priceList;
        ArrayList<Integer> quantityList;
        ArrayList<Integer> idList;
        ArrayList<String> vendorIdList;

        orderList = checkCartList(id);
        quantityList = checkCartQuantity(id);
        priceList = checkPrice(id);
        idList = checkIDList(id);
        vendorIdList = checkVendorIDList(id);


        ContentValues cv = new ContentValues();

        for (ctr=0;ctr<orderList.size();ctr++) {
            name = orderList.get(ctr);
            quantity = quantityList.get(ctr);
            amount = priceList.get(ctr);
            idL = idList.get(ctr);
            vendor = vendorIdList.get(ctr);

            cv.put(ORDER_COL_2, id);
            cv.put(ORDER_COL_3, name);
            cv.put(ORDER_COL_4, quantity);
            cv.put(ORDER_COL_5, amount);
            cv.put(ORDER_COL_7, active);
            cv.put(ORDER_COL_8, count);
            cv.put(ORDER_COL_9, idL);
            cv.put(ORDER_COL_10, vendor);

            long result = db.insert(ORDER_TABLE_NAME, null, cv);
            if (result == -1)
                return false;
        }

        return true;
    }

    //order_fragment
    public ArrayList<String> checkOrderList(String userid){
        ArrayList<String> data=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select ORDER_NAME from order_table where ID = ? AND active = 1", new String[]{userid});
        String fieldToAdd;
        while(c.moveToNext()){
            fieldToAdd = c.getString(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }
    public ArrayList<Integer> checkOrderQuantity(String userid){
        ArrayList<Integer> data = new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select ORDER_QUANT from order_table where ID = ? AND active = 1", new String[]{userid});
        Integer fieldToAdd=null;
        while(c.moveToNext()){
            fieldToAdd = c.getInt(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }
    public ArrayList<Double> checkOrderAmount(String userid){
        ArrayList<Double> data=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT ORDER_AMOUNT from order_table where ID = ? AND active = 1", new String[]{userid});
        Double fieldToAdd=null;
        while(c.moveToNext()){
            fieldToAdd = c.getDouble(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }
    public ArrayList<String> checkOrderDate(String userid){
        Date date = new Date();
        ArrayList<String> data=new ArrayList();
        DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT ORDER_DATE from order_table where ID = ? AND active = 1", new String[]{userid});
        String fieldToAdd=null;
        while(c.moveToNext()){
            fieldToAdd = c.getString(0);
            Log.d("dateCheck", "checkOrderDate: " + fieldToAdd);
            try {
                date = (Date) formatDate.parse(fieldToAdd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
            String finalDate = newFormat.format(date);
            Log.d("dateCheck", "formattedDateStr: " + finalDate);
            data.add(finalDate);
        }
        c.close();
        return data;
    }



    //order_history
    public ArrayList<String> checkOrderHistoryList(String userid){
        ArrayList<String> data=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select ORDER_NAME from order_table where ID = ?", new String[]{userid});
        String fieldToAdd;
        while(c.moveToNext()){
            fieldToAdd = c.getString(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }
    public ArrayList<Integer> checkOrderHistoryQuantity(String userid){
        ArrayList<Integer> data = new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select ORDER_QUANT from order_table where ID = ?", new String[]{userid});
        Integer fieldToAdd=null;
        while(c.moveToNext()){
            fieldToAdd = c.getInt(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }
    public ArrayList<Double> checkOrderHistoryAmount(String userid){
        ArrayList<Double> data=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT ORDER_AMOUNT from order_table where ID = ?", new String[]{userid});
        Double fieldToAdd=null;
        while(c.moveToNext()){
            fieldToAdd = c.getDouble(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }
    public ArrayList<String> checkOrderHistoryDate(String userid){
        Date date=Calendar.getInstance().getTime();
        ArrayList<String> data=new ArrayList();
        DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT CURRENT_TIMESTAMP 'ORDER_DATE', CAST(CURRENT_TIMESTAMP AS VARCHAR) from order_table where ID = ?", new String[]{userid});
        String fieldToAdd=null;
        while(c.moveToNext()){
            fieldToAdd = c.getString(0);
            try {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(fieldToAdd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String formattedDateStr = formatDate.format(date);
            data.add(formattedDateStr);
        }
        c.close();
        return data;
    }

    //vendor_order_history_fragment
    public ArrayList<String> checkIDVendorList(String id){
        ArrayList<String> data=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select ID from order_table where VENDORID=?", new String[]{id});
        String fieldToAdd;
        while(c.moveToNext()){
            fieldToAdd = c.getString(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }
    public ArrayList<String> checkVendorList(String id){
        ArrayList<String> data=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select ORDER_NAME from order_table where VENDORID=?", new String[]{id});
        String fieldToAdd;
        while(c.moveToNext()){
            fieldToAdd = c.getString(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }
    public ArrayList<Double> checkVendorAmount(String id){
        ArrayList<Double> data=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT ORDER_AMOUNT from order_table where VENDORID=?", new String[]{id});
        Double fieldToAdd=null;
        while(c.moveToNext()){
            fieldToAdd = c.getDouble(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }
    public ArrayList<String> checkVendorDate(String id){
        Date date=Calendar.getInstance().getTime();
        ArrayList<String> data=new ArrayList();
        DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT CURRENT_TIMESTAMP 'ORDER_DATE', CAST(CURRENT_TIMESTAMP AS VARCHAR) from order_table where VENDORID=?", new String[]{id});
        String fieldToAdd=null;
        while(c.moveToNext()){
            fieldToAdd = c.getString(0);
            try {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(fieldToAdd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String formattedDateStr = formatDate.format(date);
            data.add(formattedDateStr);
        }
        c.close();
        return data;
    }

    //products_table
    public ArrayList<Integer> checkProdIDList(){
        ArrayList<Integer> data=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select PROD_ID from products_table", null);
        Integer fieldToAdd;
        while(c.moveToNext()){
            fieldToAdd = c.getInt(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }
    public ArrayList<String> checkProdNameList(){
        ArrayList<String> data=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select PROD_NAME from products_table", null);
        String fieldToAdd;
        while(c.moveToNext()){
            fieldToAdd = c.getString(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }
    public ArrayList<Double> checkProdPriceList(){
        ArrayList<Double> data=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select PROD_PRICE from products_table", null);
        Double fieldToAdd=null;
        while(c.moveToNext()){
            fieldToAdd = c.getDouble(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }
    public ArrayList<String> checkProdImgURIList(){
        ArrayList<String> data=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select PROD_IMG from products_table", null);
        String fieldToAdd;
        while(c.moveToNext()){
            fieldToAdd = c.getString(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }
    public ArrayList<String> checkProdVendorId(){
        ArrayList<String> data=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select VENDOR_ID from products_table", null);
        String fieldToAdd;
        while(c.moveToNext()){
            fieldToAdd = c.getString(0);
            if(!data.contains(fieldToAdd))
                data.add(fieldToAdd);
        }
        c.close();
        return data;
    }

    //cart_table
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
    public ArrayList<Integer> checkIDList(String userid){
        ArrayList<Integer> data = new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT PROD_ID from cart_table where ID = ?", new String[]{userid});
        Integer fieldToAdd=null;
        while(c.moveToNext()){
            fieldToAdd = c.getInt(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }

    public ArrayList<String> checkVendorIDList(String userid){
        ArrayList<String> data = new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT VENDORID from cart_table where ID = ?", new String[]{userid});
        String fieldToAdd=null;
        while(c.moveToNext()){
            fieldToAdd = c.getString(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }

    //vendor_OrdersFragment
    public ArrayList<String> checkActiveOrders(String vendorId){
        ArrayList<String> data=new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select DISTINCT count, id from order_table where active = 1 and VENDORID = ?", new String[]{vendorId});
        String fieldToAdd;
        while(c.moveToNext()){
            fieldToAdd = c.getString(1);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }
    public ArrayList<Integer> checkOrderCountId(String vendorId){
        ArrayList<Integer> data = new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select DISTINCT count from order_table where active = 1 and VENDORID = ?",  new String[]{vendorId});
        int fieldToAdd;
        while(c.moveToNext()){
            fieldToAdd = c.getInt(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }
    public ArrayList<Integer> checkOrderCountIdList(int orderID){
        ArrayList<Integer> data = new ArrayList<Integer>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select prod_id from order_table where count = " + orderID;
        Cursor c = db.rawQuery(query, null);
        int fieldToAdd;
        while(c.moveToNext()){
            fieldToAdd = c.getInt(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }

    //vendor order details - user details
    public String checkOrderCountIdUserID(int orderID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select Distinct id from order_table where count = " + orderID;
        Cursor c = db.rawQuery(query, null);
        String data = "";
        while (c.moveToNext()) {
            data = c.getString(0);
        }
        return data;
    }
    public String checkOrderCountIdVendorID(int orderID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select Distinct vendorId from order_table where count = " + orderID;
        Cursor c = db.rawQuery(query, null);
        String data = "";
        while (c.moveToNext()) {
            data = c.getString(0);
        }
        return data;
    }

    //vendor products table
    public ArrayList<Integer> checkVendorProdIDList(String vendorID){
        ArrayList<Integer> data=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select PROD_ID from products_table where vendor_id = ?", new String[]{vendorID});
        Integer fieldToAdd;
        while(c.moveToNext()){
            fieldToAdd = c.getInt(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }
    public ArrayList<String> checkVendorProdNameList(String vendorID){
        ArrayList<String> data=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select PROD_NAME from products_table where vendor_id = ?", new String[]{vendorID});
        String fieldToAdd;
        while(c.moveToNext()){
            fieldToAdd = c.getString(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }
    public ArrayList<Double> checkVendorProdPriceList(String vendorID){
        ArrayList<Double> data=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select PROD_PRICE from products_table where vendor_id = ?", new String[]{vendorID});
        Double fieldToAdd=null;
        while(c.moveToNext()){
            fieldToAdd = c.getDouble(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }
    public ArrayList<String> checkVendorProdImgURIList(String vendorID){
        ArrayList<String> data=new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select PROD_IMG from products_table where vendor_id = ?", new String[]{vendorID});
        String fieldToAdd;
        while(c.moveToNext()){
            fieldToAdd = c.getString(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }

    //vendor order details
    public ArrayList<String> checkOrderCountIdProdName(int orderID){
        ArrayList<String> data = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select order_name from order_table where count = " + orderID;
        Cursor c = db.rawQuery(query, null);
        String fieldToAdd;
        while(c.moveToNext()){
            fieldToAdd = c.getString(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }
    public ArrayList<Double> checkOrderCountIdProdPrice(int orderID){
        ArrayList<Double> data = new ArrayList<Double>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select order_amount from order_table where count = " + orderID;
        Cursor c = db.rawQuery(query, null);
        Double fieldToAdd;
        while(c.moveToNext()){
            fieldToAdd = c.getDouble(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }
    public ArrayList<Integer> checkOrderCountIdOrderQty(int orderID){
        ArrayList<Integer> data = new ArrayList<Integer>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select order_quant from order_table where count = " + orderID;
        Cursor c = db.rawQuery(query, null);
        int fieldToAdd;
        while(c.moveToNext()){
            fieldToAdd = c.getInt(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }
    public String checkOrderCountIdDate(int orderID){
        Date date = new Date();
        ArrayList<String> data=new ArrayList();
        String finalDate = "";
        DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT MAX(ORDER_DATE) from order_table where count = " + orderID;
        Cursor c = db.rawQuery(query, null);
        String fieldToAdd=null;
        while(c.moveToNext()){
            fieldToAdd = c.getString(0);
            Log.d("dateCheck", "checkOrderDate: " + fieldToAdd);
            try {
                date = (Date) formatDate.parse(fieldToAdd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
            finalDate = newFormat.format(date);
            Log.d("dateCheck", "formattedDateStr: " + finalDate);
        }
        c.close();
        return finalDate;
    }
    public String checkOrderCountIdTime(int orderID){
        Date date = new Date();
        ArrayList<String> data=new ArrayList();
        String finalTime = "";
        DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT MAX(ORDER_DATE) from order_table where count = " + orderID;
        Cursor c = db.rawQuery(query, null);
        String fieldToAdd=null;
        while(c.moveToNext()){
            fieldToAdd = c.getString(0);
            Log.d("dateCheck", "checkOrderDate: " + fieldToAdd);
            try {
                date = (Date) formatDate.parse(fieldToAdd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat newFormat = new SimpleDateFormat("HH:mm:ss aa");
            finalTime = newFormat.format(date);
            Log.d("dateCheck", "formattedDateStr: " + finalTime);
        }
        c.close();
        return finalTime;
    }

    //vendor add product
    public boolean addProd(String prodName, String prodDesc, Double prodPrice, String prodImgURI, String vendorid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PRODUCT_COL_2,prodName);
        cv.put(PRODUCT_COL_3,prodDesc);
        cv.put(PRODUCT_COL_4,prodPrice);
        cv.put(PRODUCT_COL_5,60);
        cv.put(PRODUCT_COL_6,prodImgURI);
        cv.put(PRODUCT_COL_7,vendorid);
        cv.put(PRODUCT_COL_8,1);

        long result = db.insert(PRODUCT_TABLE_NAME,null,cv);
        if(result == -1)
            return false;
        else
            return true;
    }

    public ArrayList<String> checkOrderCountIdOrderIMG(int orderID){
        ArrayList<String> data = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select prod_img from products_table INNER JOIN order_table on products_table.prod_id = order_table.prod_id where count = " + orderID;
        Cursor c = db.rawQuery(query, null);
        String fieldToAdd;
        while(c.moveToNext()){
            fieldToAdd = c.getString(0);
            data.add(fieldToAdd);
        }
        c.close();
        return data;
    }
    public ProductInfo readProduct (int id){
        SQLiteDatabase db = this.getReadableDatabase();
        ProductInfo p = null;
        String where = "PROD_ID = '"+id+"'";
        Cursor cursor = db.query(PRODUCT_TABLE_NAME, null, where, null,null,null,null);
        if(cursor.moveToNext()){
            p = new ProductInfo();
            p.setProdID(cursor.getInt(0));
            p.setProdName(cursor.getString(1));
            p.setProdDesc(cursor.getString(2));
            p.setProdPrice(cursor.getDouble(3));
            p.setProdStock(cursor.getInt(4));
            p.setProdImg(cursor.getString(5));
            p.setVendorID(cursor.getInt(6));
            p.setCategID(cursor.getInt(7));
        }
        return p;
    }

    public boolean updateProd(int id, String name, String desc, double price, int stock){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PRODUCT_COL_2, name);
        cv.put(PRODUCT_COL_3,desc);
        cv.put(PRODUCT_COL_4,price);
        cv.put(PRODUCT_COL_5,stock);
        Cursor c = db.rawQuery("select * from '"+PRODUCT_TABLE_NAME+"' where PROD_ID = '"+id+"'",null);
        if(c.getCount()>0){
            long res = db.update(PRODUCT_TABLE_NAME, cv, "PROD_ID = "+id,null);
            if(res==-1)
                return false;
            else
                return true;
        }return false;
    }


}
