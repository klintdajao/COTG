package com.example.gittest;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.function.DoubleUnaryOperator;

public class ProductDesc_Vendor extends AppCompatActivity {

    ImageView imgProd;
    TextView txtProdName, txtProdDesc, txtProdPrice;
    DatabaseHelper db;
    String prodImgURI, prodName, prodDesc,price;
    Double prodPrice;
    ProductInfo p;
    File imgFile;
    int id;
    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_desc_vendor);
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
        price = String.valueOf(prodPrice);
        edit = findViewById(R.id.btnedit_proddesc);
        imgProd = findViewById(R.id.prodIMG);
        txtProdName = findViewById(R.id.txt_prodName_vendor);
        txtProdDesc = findViewById(R.id.txt_prodDesc_vendor);
        txtProdPrice = findViewById(R.id.txt_prodPrice_vendor);
        txtProdName.setText(prodName);
        txtProdPrice.setText(Double.toString(prodPrice));
        txtProdDesc.setText(prodDesc);
        Log.d(TAG, "price1 = "+prodPrice);
        Log.d(TAG, "price = "+price);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductDesc_Vendor_Edit.class);
                intent.putExtra("prodID_key",userid);
                startActivity(intent);
            }
        });

        imgFile = new File(String.valueOf(prodImgURI));
        Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        imgProd.setImageBitmap(bitmap);
    }
}