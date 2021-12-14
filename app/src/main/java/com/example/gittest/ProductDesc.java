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
        imgProd = findViewById(R.id.prodIMG_prodDesc);
        txtProdName = findViewById(R.id.txtProdName_prodDesc);
        txtProdDesc = findViewById(R.id.txtProdDesc_prodDesc2);
        txtProdPrice = findViewById(R.id.txtProdPrice_prodDesc);

        db.checkProdDeets(userid, prodImgURI, prodName, prodPrice, prodDesc);




    }
}