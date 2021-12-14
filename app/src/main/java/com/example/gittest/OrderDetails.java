package com.example.gittest;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.utils.Oscillator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class OrderDetails extends AppCompatActivity {
    DatabaseHelper db;
    private ArrayList<Integer> mOrderCountIDList = new ArrayList<>();
    private ArrayList<Integer> mOrderQty  = new ArrayList<>();
    private ArrayList<String> mOrderName  = new ArrayList<>();
    private ArrayList<Double> subTotal  = new ArrayList<>() ;
    private ArrayList<Double> mOrderPrice  = new ArrayList<>();
    private ArrayList<Bitmap> mOrderImageURI  = new ArrayList<>();
    TextView txtOrderID, txtOrderName, txtOrderEmail, txtOrderDate, txtOrderTime, txtTFee,txtTotal;
    AccountInfo a = new AccountInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details2);
        db = new DatabaseHelper(this);
        Intent intent = getIntent();
        int countId = intent.getIntExtra("countId_key", 0);
        String userid = db.checkOrderCountIdUserID(countId);
        a = db.readUser(userid);

        //-------ArrayLists-------//
        mOrderCountIDList = db.checkOrderCountIdList(countId);
        mOrderQty = db.checkOrderCountIdOrderQty(countId);
        mOrderName = db.checkOrderCountIdProdName(countId);
        mOrderPrice = db.checkOrderCountIdProdPrice(countId);
        //------------------------//

        //-------TextViews--------//
        txtOrderID = findViewById(R.id.txtOrder_ID);
        txtOrderName = findViewById(R.id.txtorderName);
        txtOrderEmail = findViewById(R.id.txtorderEmail);
        txtOrderDate = findViewById(R.id.txtorderDate);
        txtOrderTime = findViewById(R.id.txtOrderTime);
        txtTFee = findViewById(R.id.txtFee);
        txtTotal = findViewById(R.id.txtTotal);
        //------------------------//

        txtOrderID.setText(a.getId());
        txtOrderName.setText(a.getLn() +", " + a.getFn() + " " + a.getMn());
        txtOrderEmail.setText(a.getEmail());


        for(int i=0;i<mOrderName.size();i++){
            subTotal.add(mOrderQty.get(i)*mOrderPrice.get(i));
        }
        File imgFile[] = new File[mOrderName.size()];
        for(int i = 0; i<mOrderName.size();i++){
            imgFile[i] = new File(db.checkOrderCountIdOrderIMG(countId).get(i));
            Log.d(TAG, "onCreate: "+ (db.checkOrderCountIdOrderIMG(countId).get(i)));
            if(imgFile[i].exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile[i].getAbsolutePath());
                mOrderImageURI.add(bitmap);
            }
        }

        Log.d("Debug: ", "mOrderName size: " + mOrderName.size());
        Log.d("Debug: ", "mOrderQt size: " + mOrderQty.size());
        Log.d("Debug: ", "mOrderImageURI size: " + mOrderImageURI.size());
        Log.d("Debug: ", "mOrderPrice toString: " + mOrderPrice.size());
        Log.d("Debug: ", "mOrderPrice toString: " + mOrderCountIDList.size());

        Log.d(Oscillator.TAG, "initRecyclerView: init recyclerview called.");
        RecyclerView recyclerView = findViewById(R.id.orderDetailsRecyclerView);
        OrderDetailsViewAdapter adapter = new OrderDetailsViewAdapter(this,mOrderCountIDList, mOrderQty, mOrderName, subTotal, mOrderPrice,mOrderImageURI);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}