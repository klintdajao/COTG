package com.example.gittest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderDetails extends AppCompatActivity {
    DatabaseHelper db;
    Intent intent;
    TextView txtorderName, txtorderEmail, txtorderDate, txtTime, txtFee, txtTotal;
    Button btnOrderCancel, btnReady;
    ArrayList<String> dateList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details2);

        txtorderName = (TextView) findViewById(R.id.txtorderName);
        txtorderEmail = (TextView) findViewById(R.id.txtorderEmail);
        txtorderDate = (TextView) findViewById(R.id.txtorderDate);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtFee= (TextView) findViewById(R.id.txtFee);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        btnOrderCancel = (Button) findViewById(R.id.btnOrderCancel);
        btnReady = (Button) findViewById(R.id.btnReady);

        db = new DatabaseHelper(this);
        intent = getIntent();
        AccountInfo a = new AccountInfo();
        a = db.readUser(loginID.id);
//        String title = a.getFn() +" " + a.getLn() + "'s Order History";


//        productList = db.checkOrderList(intent.getStringExtra("userid_key"));
//        quantityList = db.checkOrderQuantity(intent.getStringExtra("userid_key"));
//        amountList = db.checkOrderAmount(intent.getStringExtra("userid_key"));
        dateList = db.checkOrderDate(intent.getStringExtra("userid_key"));

//        Date currentTime = new Date();
//
//        DateFormat formatTime = new SimpleDateFormat("hh:mm:ss aa");
//        DateFormat formatDate = new SimpleDateFormat("MMMMM, dd, yyyy");
//        String formattedTimeStr = formatTime.format(currentTime);
//        String formattedDateStr = formatDate.format(currentTime);

//        for (ctr=0;ctr<productList.size();ctr++) {
//            name = productList.get(ctr);
//            quantity = quantityList.get(ctr);
//            amount = amountList.get(ctr);
//            date = dateList.get(ctr);
//            show += name + "\n";
//            show2 +=  quantity + "\n";
//            show3 +=  amount + "\n";
//            show4 +=  date + "\n";
//        }

        txtorderName.setText(a.getFn() + " " + a.getMn() + " " + a.getLn());
        txtorderEmail.setText(a.getEmail());
        txtorderDate.setText(dateList.get(0));
//
//        txtDate.setText(show4);
//        txtUser.setText(title);
    }
}