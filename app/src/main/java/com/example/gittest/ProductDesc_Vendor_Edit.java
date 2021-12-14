package com.example.gittest;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

public class ProductDesc_Vendor_Edit extends AppCompatActivity {

    ImageView imgProd;
    EditText txtProdName, txtProdDesc, txtProdPrice;
    DatabaseHelper db;
    String prodImgURI, prodName, prodDesc;
    Double prodPrice;
    ProductInfo p;
    File imgFile;

    int id;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_desc_vendor_edit);
        Intent intent = getIntent();
        p = new ProductInfo();
        int userid = intent.getIntExtra("prodID_key", 0);
        Log.d(TAG, "onCreate: hello"+userid);
        db = new DatabaseHelper(this);
        p =  db.readProduct(userid);
        prodDesc = p.getProdDesc();
        prodName = p.getProdName();
        prodPrice = p.getProdPrice();
        prodImgURI = p.getProdImg();


        imgProd = findViewById(R.id.prodIMG);
        txtProdName = findViewById(R.id.editTxt_prodName);
        txtProdDesc = findViewById(R.id.editTxt_prodDesc);
        txtProdPrice = findViewById(R.id.editTxt_prodPrice);
        txtProdName.setText(prodName);
        txtProdPrice.setText(prodPrice.toString());
        txtProdDesc.setText(prodDesc);


        imgFile = new File(String.valueOf(prodImgURI));
        Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        imgProd.setImageBitmap(bitmap);
    }
}