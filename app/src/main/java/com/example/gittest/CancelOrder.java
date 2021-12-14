package com.example.gittest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CancelOrder extends AppCompatActivity {
    AlertDialog.Builder builder;
    Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_order);

        cancel= findViewById(R.id.btnCnfrm);
        builder = new AlertDialog.Builder(this);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setMessage("Are you sure you want to Cancel Order?").setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(CancelOrder.this, "Order Cancelled!", Toast.LENGTH_SHORT).show();
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