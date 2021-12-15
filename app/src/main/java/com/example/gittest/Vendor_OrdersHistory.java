package com.example.gittest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class Vendor_OrdersHistory extends AppCompatActivity {
    DatabaseHelper db;
    ArrayList<String> productList = new ArrayList<>();
    ArrayList<Double> amountList= new ArrayList<>();
    ArrayList<String> dateList= new ArrayList<>();
    ArrayList<String> idList= new ArrayList<>();
    Intent intent;
    String show = "";
    String show2 = "";
    String show3 = "";
    String show4 = "";
    String name = "";
    Double amount = 0.0;
    String date = "";
    String id = "";
    TextView txtProduct, txtAmount, txtDate, txtUser, txtID;
    int ctr=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_orders_history);

        txtProduct = (TextView) findViewById(R.id.txtProduct);
        txtAmount = (TextView) findViewById(R.id.txtAmount);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtUser= (TextView) findViewById(R.id.txtUser);
        txtID= (TextView) findViewById(R.id.txtID);

        db = new DatabaseHelper(this);
        intent = getIntent();

        String id = intent.getStringExtra("vendorId_key");
        productList = db.checkVendorList(id);
        amountList = db.checkVendorAmount(id);
        dateList = db.checkVendorDate(id);
        idList = db.checkIDVendorList(id);

//        Date currentTime = new Date();
//
//        DateFormat formatTime = new SimpleDateFormat("hh:mm:ss aa");
//        DateFormat formatDate = new SimpleDateFormat("MMMMM, dd, yyyy");
//        String formattedTimeStr = formatTime.format(currentTime);
//        String formattedDateStr = formatDate.format(currentTime);
        AccountInfo b = new AccountInfo();


        for (ctr=0;ctr<productList.size();ctr++) {
            name = productList.get(ctr);
            amount = amountList.get(ctr);
            date = dateList.get(ctr);
            b = db.readUser(idList.get(ctr));
            show += b.getFn() + " ordered " + name + "\n";
            show3 +=  amount + "\n";
            show4 +=  date + "\n";
        }

        txtProduct.setText(show);
        txtAmount.setText(show3);

        txtDate.setText(show4);

        VendorInfo a = new VendorInfo();


        a = db.readVendor(id);

        txtUser.setText(a.getName());
        txtID.setText(a.getId());
    }
}