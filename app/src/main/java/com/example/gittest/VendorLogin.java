package com.example.gittest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VendorLogin extends AppCompatActivity {
    Button login, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_login);

        login = findViewById(R.id.btnlogin);
        back = findViewById(R.id.btnBackLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(VendorLogin.this, VendorHome.class);
                startActivity(in);
            }
        });
    }
}