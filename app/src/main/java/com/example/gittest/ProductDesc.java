package com.example.gittest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gittest.ui.browse.BrowseFragmentViewAdapter;

import java.io.File;

public class ProductDesc extends AppCompatActivity {

    ImageView imgProd;
    TextView txtProdName, txtProdDesc;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_desc);
        Intent intent = getIntent();
        int userid = intent.getIntExtra("prodId_key", 0);


        imgProd = findViewById(R.id.imgProd);
        txtProdName = findViewById(R.id.txtProdID);
        txtProdDesc = findViewById(R.id.txtProdDesc);


    }
}