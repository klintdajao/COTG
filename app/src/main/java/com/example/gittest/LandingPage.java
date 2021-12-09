package com.example.gittest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LandingPage extends AppCompatActivity {
    storagePermissions sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = new storagePermissions();
        sp.verifyStoragePermissions(this);
        setContentView(R.layout.activity_landing_page);

    }
}