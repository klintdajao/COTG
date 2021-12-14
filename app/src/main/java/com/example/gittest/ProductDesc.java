package com.example.gittest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


public class ProductDesc extends AppCompatActivity {

    ImageView imgProd;
    TextView txtProdName, txtProdDesc, txtProdPrice;
    DatabaseHelper db;
    String prodImgURI, prodName, prodDesc;
    Double prodPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_desc);
        Intent intent = getIntent();
        int userid = intent.getIntExtra("prodId_key", 0);
        db = new DatabaseHelper(this);

        imgProd = findViewById(R.id.prodIMG);
        txtProdName = findViewById(R.id.txtProd_Name);
        txtProdDesc = findViewById(R.id.txtProdDesc);
        txtProdPrice = findViewById(R.id.txtProd_Price);

        db.checkProdDeets(userid, prodImgURI, prodName, prodPrice, prodDesc);



    }
}