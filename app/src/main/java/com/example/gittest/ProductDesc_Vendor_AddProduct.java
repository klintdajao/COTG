package com.example.gittest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class ProductDesc_Vendor_AddProduct extends AppCompatActivity {
    ImageView imgProd, imgLayer;
    EditText txtAddProdName, txtAddProdDesc, txtAddProdPrice;
    TextView  txtUploadImg;
    DatabaseHelper db;
    Button btnAddProd;
    String prodImgURI, prodName, prodDesc;
    Double prodPrice;
    ProductInfo p;
    File imgFile;

    public void setProdImgURI(String prodImgURI) {
        this.prodImgURI = prodImgURI;
    }

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_desc_vendor_add_product);
        Intent intent = getIntent();
        db = new DatabaseHelper(this);
        String vendor = intent.getStringExtra("vendorId_key");
        Log.d("vendorId", vendor);

        imgProd = findViewById(R.id.addProdIMG);
        imgLayer = findViewById(R.id.imageLayer);
        txtUploadImg = findViewById(R.id.txtUploadImg);
        txtAddProdName = findViewById(R.id.txtAddProdName_vendor);
        txtAddProdDesc = findViewById(R.id.txtAddProdDesc_vendor);
        txtAddProdPrice = findViewById(R.id.txtAddProdPrice_vendor);
        btnAddProd = findViewById(R.id.btnConfirmAdd);

        btnAddProd.setEnabled(false);

        imgProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
            }
        });

        txtUploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivity(i);
                onActivityResult(1,RESULT_OK, i);
                Log.d("picturePath: ", prodImgURI+"");
            }
        });

//        Bitmap thumbnail = (BitmapFactory.decodeFile(prodImgURI));
        txtAddProdName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(txtAddProdName.length()==0) {
                    txtAddProdName.setError("This field cannot be blank");
                    btnAddProd.setEnabled(false);
                }
                else {
                    if(txtAddProdName.length()==0 || txtAddProdPrice.length()==0){
                        btnAddProd.setEnabled(false);
                    }
                    else{
                        txtAddProdName.setError(null);
                        btnAddProd.setEnabled(true);
                    }
                }
            }
        });
        txtAddProdDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(txtAddProdDesc.length()==0) {
                    txtAddProdDesc.setError("This field cannot be blank");
                    btnAddProd.setEnabled(false);
                }

                else {
                    if(txtAddProdName.length()==0 || txtAddProdPrice.length()==0){
                        btnAddProd.setEnabled(false);
                    }
                    else {
                        txtAddProdDesc.setError(null);
                        btnAddProd.setEnabled(true);
                    }
                }
            }
        });
        txtAddProdPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(txtAddProdPrice.length()==0) {
                    txtAddProdPrice.setError("This field cannot be blank");
                    btnAddProd.setEnabled(false);
                }
                else {
                    if(txtAddProdDesc.length()==0 || txtAddProdName.length()==0){
                        btnAddProd.setEnabled(false);
                    }
                    else {
                        txtAddProdPrice.setError(null);
                        btnAddProd.setEnabled(true);
                    }
                }
            }
        });
        if(txtAddProdName.length()!= 0 && txtAddProdDesc.length()!=0 &&
                txtAddProdPrice.length()!=0 && prodImgURI.length()!=0){
            btnAddProd.setEnabled(true);
        }
        else{
            btnAddProd.setEnabled(false);
        }

        btnAddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prodName = txtAddProdName.getText().toString();
                prodDesc = txtAddProdDesc.getText().toString();
                prodPrice = Double.parseDouble(txtAddProdPrice.getText().toString());
                Log.d("data", "prodName: " + prodName + ", prodDesc: " + prodDesc +", prodPrice: " + prodPrice + ", ProdImgURI: " + prodImgURI + ", VendorID: "+vendor);

                if(db.addProd(prodName,prodDesc,prodPrice,prodImgURI,vendor)){
                    Toast.makeText(ProductDesc_Vendor_AddProduct.this, "Added Product!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), VendorHome.class);
                    intent.putExtra("vendorId_key", vendor);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ProductDesc_Vendor_AddProduct.this, "Product was not added.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.d("onActivityResultpath: ", picturePath+"");
                this.setProdImgURI(picturePath);
                imgProd.setImageBitmap(thumbnail);
                txtUploadImg.setText("Update Image");
                imgLayer.setVisibility(View.VISIBLE);
            }
        }
    }
}