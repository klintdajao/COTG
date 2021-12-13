package com.example.gittest;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gittest.vendorUI.VendorHome;

public class VendorLogin extends AppCompatActivity {
    Button login, back;
    Intent intent;
    TextView vendorID, vendorPass;
    DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_login);
        getSupportActionBar().hide();

        vendorID = findViewById(R.id.txtVendorLogin);
        vendorPass = findViewById(R.id.txtVendorPassword);
        login = findViewById(R.id.btnVendorlogin);
        back = findViewById(R.id.btnBackLogin);
        myDB = new DatabaseHelper(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = vendorID.getText().toString();
                String pass = vendorPass.getText().toString();

                Log.d(TAG, "id: " + id +", pass: "+ pass);
                if(id.equals("") || pass.equals("")) {
                    Toast.makeText(VendorLogin.this, "VendorID or password is empty.", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean check = myDB.checkVendor(id,pass);
                    if (check) {
                        intent = new Intent(VendorLogin.this, VendorHome.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("vendorId_key", id);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Toast.makeText(VendorLogin.this, "VendorID or password is incorrect.", Toast.LENGTH_SHORT).show();
                        vendorPass.setText("");
                    }
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent (VendorLogin.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}