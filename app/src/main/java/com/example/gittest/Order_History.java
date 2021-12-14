package com.example.gittest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Order_History extends AppCompatActivity {
    DatabaseHelper db;
    ArrayList<String> productList = new ArrayList<>();
    ArrayList<Integer> quantityList= new ArrayList<>();
    ArrayList<Double> amountList= new ArrayList<>();
    ArrayList<String> dateList= new ArrayList<>();
    Intent intent;
    String show = "";
    String show2 = "";
    String show3 = "";
    String show4 = "";
    String name = "";
    Integer quantity = 0;
    Double amount = 0.0;
    String date = "";
    TextView txtProduct, txtQuantity, txtAmount, txtDate, txtUser;
    int ctr=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        txtProduct = (TextView) findViewById(R.id.txtProduct);
        txtQuantity = (TextView) findViewById(R.id.txtQuantity);
        txtAmount = (TextView) findViewById(R.id.txtAmount);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtUser= (TextView) findViewById(R.id.txtUser);

        db = new DatabaseHelper(this);
        intent = getIntent();
        AccountInfo a = new AccountInfo();
        a = db.readUser(loginID.id);
        String title = a.getFn() +" " + a.getLn() + "'s Order History";


        productList = db.checkOrderHistoryList(intent.getStringExtra("userid_key"));
        quantityList = db.checkOrderHistoryQuantity(intent.getStringExtra("userid_key"));
        amountList = db.checkOrderHistoryAmount(intent.getStringExtra("userid_key"));
        dateList = db.checkOrderHistoryDate(intent.getStringExtra("userid_key"));

//        Date currentTime = new Date();
//
//        DateFormat formatTime = new SimpleDateFormat("hh:mm:ss aa");
//        DateFormat formatDate = new SimpleDateFormat("MMMMM, dd, yyyy");
//        String formattedTimeStr = formatTime.format(currentTime);
//        String formattedDateStr = formatDate.format(currentTime);

        for (ctr=0;ctr<productList.size();ctr++) {
            name = productList.get(ctr);
            quantity = quantityList.get(ctr);
            amount = amountList.get(ctr);
            date = dateList.get(ctr);
            show += name + "\n";
            show2 +=  quantity + "\n";
            show3 +=  amount + "\n";
            show4 +=  date + "\n";
        }

        txtProduct.setText(show);
        txtQuantity.setText(show2);
        txtAmount.setText(show3);

        txtDate.setText(show4);
        txtUser.setText(title);
    }
}