package com.example.gittest;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class ProductDesc_Vendor_Edit extends AppCompatActivity {

    ImageView imgProd;
    EditText txtProdName, txtProdDesc, txtProdPrice;
    DatabaseHelper db;
    String prodImgURI, prodName, prodDesc;
    Double prodPrice;
    ProductInfo p,pp;
    File imgFile;
    Button ok,cancel,delete;
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

        delete = findViewById(R.id.btnDeleteProd);
        ok = findViewById(R.id.btnOK_editProdDesc);
        cancel = findViewById(R.id.btnCancel_editProdDesc);
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

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductDesc_Vendor_Edit.this);
                builder.setMessage("Are you sure about this bruh?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                pp = new ProductInfo();
                                pp =  db.readProduct(userid);
                                String name = txtProdName.getText().toString();
                                String p = txtProdPrice.getText().toString();
                                Double price = Double.parseDouble(txtProdPrice.getText().toString());
                                Log.d(TAG, "price = "+price);
                                Log.d(TAG, "price1 = "+p);
                                String desc = txtProdDesc.getText().toString();
                                String id = String.valueOf(userid);
                                int stock = pp.getProdStock();
                                Boolean updateprod = db.updateProd(userid,name,desc,price,stock);
                                if (updateprod) {
                                    Toast.makeText(ProductDesc_Vendor_Edit.this, "mana cuh", Toast.LENGTH_SHORT).show();
                                    Intent intent1 = new Intent(getApplicationContext(),ProductDesc_Vendor.class);
                                    intent1.putExtra("prodID_key",userid);
                                    finish();

                                }else
                                Toast.makeText(ProductDesc_Vendor_Edit.this, "wala  cuh", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("No", null);
                AlertDialog ad = builder.create();
                ad.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductDesc_Vendor_Edit.this);
                builder.setMessage("Are you sure you wanna delete this product bruh?")
                        .setPositiveButton("ofc", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               boolean delete = db.deleteProd(userid);
                               if(delete){
                                   Toast.makeText(ProductDesc_Vendor_Edit.this, "mana delete bossing", Toast.LENGTH_SHORT).show();
                                    finish();
                               }
                               else
                                   Toast.makeText(ProductDesc_Vendor_Edit.this, "hala wala na delete boss ataya", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("ayaw lang",null);
                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }
}