package com.example.gittest;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.utils.Oscillator;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gittest.databinding.ActivityVendorHomeBinding;
import com.example.gittest.vendorUI.orders.OrdersFragment;

import java.io.File;
import java.text.DecimalFormat;
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
    AlertDialog.Builder builder,builder2;
    String vendorId;
    int countId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details2);
        db = new DatabaseHelper(this);
        Intent intent = getIntent();

        countId = intent.getIntExtra("countId_key", 0);
        String userid = db.checkOrderCountIdUserID(countId);
        vendorId = db.checkOrderCountIdVendorID((countId));


        a = db.readUser(userid);
        btnReady = findViewById(R.id.btnReady);
        btnCancel = findViewById(R.id.btnOrderCancel);
        builder = new AlertDialog.Builder(this);
        builder2 = new AlertDialog.Builder(this);

        btnReady.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification","My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

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
        DecimalFormat df = new DecimalFormat("#.##");
        Double tFee = subTot*.05;

        String strTFee = "₱" + Double.toString(Double.parseDouble(df.format(tFee)));
        String strTotal = "₱"+ Double.toString(Double.parseDouble(df.format(subTot+tFee)));
        txtTFee.setText(strTFee);
        txtTotal.setText(strTotal);
        txtOrderSubtotal.setText("₱"+ df.format(subTot));
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
        String orderid =txtOrderID.getText().toString();
        switch (v.getId()){
            case R.id.btnReady:
                builder2.setMessage("Do you want to notify the user the order is ready?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean update = db.readyOrder(countId);
                                if(update){
                                    Intent intent1= new Intent(getApplicationContext(), VendorHome.class);
                                    intent1.putExtra("vendorId_key", vendorId);
                                    startActivity(intent1);
                                    NotificationCompat.Builder not = new NotificationCompat.Builder(OrderDetails.this,"My Notification");
                                    not.setContentTitle("Order is Ready!");
                                    not.setContentText("Your Order is ready, Please go to the canteen to claim.");
                                    not.setSmallIcon(R.drawable.ic_baseline_shopping_cart_24);
                                    not.setAutoCancel(true);

                                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(OrderDetails.this);
                                    managerCompat.notify(1, not.build());
                                    Toast.makeText(OrderDetails.this, "Order Ready!", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(OrderDetails.this, "Error: Failed Operation", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(OrderDetails.this, "No is Pressed", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alert2  = builder2.create();
                alert2.setTitle("READY ORDER");
                alert2.show();
                break;

            case R.id.btnOrderCancel:
                builder.setMessage("Do you want to Cancel this order?").setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                boolean delete = db.deleteVendorOrder(countId);
                                if(delete){
                                    Intent intent1= new Intent(getApplicationContext(), VendorHome.class);
                                    intent1.putExtra("vendorId_key", vendorId);
                                    startActivity(intent1);
                                    NotificationCompat.Builder not = new NotificationCompat.Builder(OrderDetails.this,"My Notification");
                                    not.setContentTitle("Cancelled Order");
                                    not.setContentText("It seems the Seller Cancelled your Order");
                                    not.setSmallIcon(R.drawable.ic_baseline_shopping_cart_24);
                                    not.setAutoCancel(true);

                                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(OrderDetails.this);
                                    managerCompat.notify(1, not.build());
                                    Toast.makeText(OrderDetails.this, "Order Cancelled", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(OrderDetails.this, "Order not Cancelled", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(OrderDetails.this, "No is Pressed", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alert  = builder.create();
                alert.setTitle("CANCEL ORDER");
                alert.show();
                break;

                default:
                throw new IllegalStateException("Unexpected value: " + v.getId());


        }

    }
}

