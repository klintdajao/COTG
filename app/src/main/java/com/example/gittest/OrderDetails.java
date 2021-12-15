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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class OrderDetails extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper db;
    private ArrayList<Integer> mOrderCountIDList = new ArrayList<>();
    private ArrayList<Integer> mOrderQty  = new ArrayList<>();
    private ArrayList<String> mOrderName  = new ArrayList<>();
    private ArrayList<Double> subTotal  = new ArrayList<>() ;
    private ArrayList<Double> mOrderPrice  = new ArrayList<>();
    private ArrayList<Bitmap> mOrderImageURI  = new ArrayList<>();
    TextView txtOrderID, txtOrderName, txtOrderEmail, txtOrderDate, txtOrderTime, txtOrderSubtotal, txtTFee,txtTotal;
    AccountInfo a = new AccountInfo();
    Button btnReady, btnCancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details2);
        db = new DatabaseHelper(this);
        Intent intent = getIntent();
        int countId = intent.getIntExtra("countId_key", 0);
        String userid = db.checkOrderCountIdUserID(countId);
        a = db.readUser(userid);
        btnReady = findViewById(R.id.btnReady);
        btnCancel = findViewById(R.id.btnOrderCancel);

        btnReady.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

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
        txtOrderTime = findViewById(R.id.txtTime);
        txtOrderSubtotal = findViewById(R.id.txtOrderSubtotal);
        txtTFee = findViewById(R.id.txtFee);
        txtTotal = findViewById(R.id.txtTotal);
        //------------------------//

        String name = a.getLn() +", " + a.getFn() + " " + a.getMn();
        txtOrderID.setText(a.getId());
        txtOrderName.setText(name);
        txtOrderEmail.setText(a.getEmail());
        txtOrderDate.setText(db.checkOrderCountIdDate(countId));
        txtOrderTime.setText(db.checkOrderCountIdTime(countId));


        Double subT = 0.0;
        Double subTot = 0.0;
        for(int i=0;i<mOrderName.size();i++){
            subT = mOrderQty.get(i)*mOrderPrice.get(i);
            subTot+=subT;
            subTotal.add(subT);
        }
        Double tFee = subTot*.05;
        String strTFee = "₱" + Double.toString(tFee);
        String strTotal = "₱"+ Double.toString(subTot+tFee);
        txtTFee.setText(strTFee);
        txtTotal.setText(strTotal);
        txtOrderSubtotal.setText("₱"+ subTot);
        File[] imgFile = new File[mOrderName.size()];
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

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnReady:
                Toast.makeText(OrderDetails.this, "ready is clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnOrderCancel:
                Toast.makeText(OrderDetails.this, "Cancel is clicked", Toast.LENGTH_SHORT).show();
                break;

                default:
                throw new IllegalStateException("Unexpected value: " + v.getId());

        }

    }
}
