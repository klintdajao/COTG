package com.example.gittest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class VendorAccountSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_account_settings);
        getSupportActionBar().hide();
    }
}