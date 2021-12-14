package com.example.gittest;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CancelOrder extends AppCompatActivity {
    AlertDialog.Builder builder;
    Button cancel;
    DatabaseHelper db;
    RadioGroup rg;
    Intent intent= getIntent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification","My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_order);

        rg= findViewById(R.id.cancelRg);
        cancel= findViewById(R.id.btnCnfrm);
        builder = new AlertDialog.Builder(this);
        db = new DatabaseHelper(this);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setMessage("Are you sure you want to Cancel Order?").setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean delete = db.deleteOrder(loginID.id);
                                if(delete){
                                    NotificationCompat.Builder not = new NotificationCompat.Builder(CancelOrder.this,"My Notification");
                                    not.setContentTitle("Order Cancellation Confirmed!");
                                    not.setContentText("Thank you! order again");
                                    not.setSmallIcon(R.drawable.ic_baseline_shopping_cart_24);
                                    not.setAutoCancel(true);

                                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(CancelOrder.this);
                                    managerCompat.notify(1, not.build());
                                    Toast.makeText(CancelOrder.this, "Please Order Again!", Toast.LENGTH_SHORT).show();
                                    Intent intent1= new Intent(getApplicationContext(),Home.class);
                                    startActivity(intent1);
                                }
                                else{
                                    Toast.makeText(CancelOrder.this, "Order Not Cancelled!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CancelOrder.this, "Order not Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alert  = builder.create();
                alert.setTitle("CANCEL ORDER");
                alert.show();
            }
        });
    }
}