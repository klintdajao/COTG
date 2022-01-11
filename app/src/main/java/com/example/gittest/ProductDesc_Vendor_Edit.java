package com.example.gittest;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gittest.vendorUI.products.ProductsFragment;

import java.io.File;

public class ProductDesc_Vendor_Edit extends AppCompatActivity {

    ImageView imgProd, imgUpdateImg;
    EditText txtProdName, txtProdDesc, txtProdPrice;
    DatabaseHelper db;
    String prodImgURI, prodName, prodDesc;
    Double prodPrice;
    ProductInfo p,pp;
    File imgFile;
    Button ok,cancel,delete;
    int id;
    int prodID;

    String imgProdURI;

    public void setProdImgURI(String prodImgURI) {
        this.prodImgURI = prodImgURI;
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_desc_vendor_edit);
        Intent intent = getIntent();
        p = new ProductInfo();
        prodID = intent.getIntExtra("prodID_key", 0);
        Log.d(TAG, "onCreate: hello"+prodID);
        db = new DatabaseHelper(this);
        p =  db.readProduct(prodID);

        prodDesc = p.getProdDesc();
        prodName = p.getProdName();
        prodPrice = p.getProdPrice();
        prodImgURI = p.getProdImg();

        delete = findViewById(R.id.btnDeleteProd);
        ok = findViewById(R.id.btnOK_editProdDesc);
        cancel = findViewById(R.id.btnCancel_editProdDesc);
        imgProd = findViewById(R.id.prodIMG);
        imgUpdateImg = findViewById(R.id.imgUpdateImg);
        txtProdName = findViewById(R.id.editTxt_prodName);
        txtProdDesc = findViewById(R.id.editTxt_prodDesc);
        txtProdPrice = findViewById(R.id.editTxt_prodPrice);

        imgProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
            }
        });

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
                                pp =  db.readProduct(prodID);
                                String name = txtProdName.getText().toString();
                                String p = txtProdPrice.getText().toString();
                                Double price = Double.parseDouble(txtProdPrice.getText().toString());
                                Log.d(TAG, "price = "+price);
                                Log.d(TAG, "price1 = "+p);
                                String desc = txtProdDesc.getText().toString();
                                String id = String.valueOf(prodID);
                                int stock = pp.getProdStock();
                                Boolean updateprod = db.updateProd(prodID,name,desc,prodImgURI, price,stock);
                                if (updateprod) {
                                    Toast.makeText(ProductDesc_Vendor_Edit.this, "mana cuh", Toast.LENGTH_SHORT).show();
                                    Intent intent1 = new Intent(getApplicationContext(),ProductDesc_Vendor.class);
                                    intent1.putExtra("prodID_key",prodID);
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
                               String vendorID = db.checkProdVendorId(prodID);
                               Log.d("Debug", "vendorId_key: " + vendorID);
                               boolean delete = db.deleteProd(prodID);
                               if(delete){
                                   Toast.makeText(ProductDesc_Vendor_Edit.this, "mana delete bossing", Toast.LENGTH_SHORT).show();
                                   Intent intent = new Intent(ProductDesc_Vendor_Edit.this, VendorHome.class);
                                   intent.putExtra("vendorId_key", vendorID);
                                   Log.d("Debug", "prodID_key: " + prodID);
                                   startActivity(intent);
//                                   Bundle bundle = new Bundle();
//                                   bundle.putInt("prodID_key", prodID);
//                                   ProductsFragment pf = new ProductsFragment();
//                                   pf.setArguments(bundle);
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
            }
        }
    }
}