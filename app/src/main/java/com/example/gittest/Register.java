package com.example.gittest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Register extends AppCompatActivity {
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        db = new DatabaseHelper(this);
    }
}