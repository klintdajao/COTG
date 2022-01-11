package com.example.gittest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.service.autofill.DateTransformation;
import android.util.Log;
import android.widget.TextView;

import com.example.gittest.ui.browse.BrowseFragmentViewAdapter;

import java.io.File;
import java.util.ArrayList;

public class StoreInfo extends AppCompatActivity {
    DatabaseHelper db;
    private ArrayList<Integer> mProdId = new ArrayList<>();
    private ArrayList<String> mProdNames  = new ArrayList<>();
    private ArrayList<Double> mProdPrice = new ArrayList<>();
    private ArrayList<Bitmap> mProdImageURI = new ArrayList<>();
    TextView txtSeller;
    String vendorID;
    private int prodID;
    VendorInfo v;
    ProductInfo p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info);
        db = new DatabaseHelper(this);
        prodID = getIntent().getIntExtra("prodID_Key", 0);
        vendorID = db.checkProdSeller(prodID);

        Log.d("StoreInfo.java: ", "prodID: " + prodID);
        Log.d("StoreInfo.java: ", "vendorID: " + vendorID);

        p = new ProductInfo();
        v = new VendorInfo();

        p =  db.readProduct(prodID);
        v = db.readVendor(p.getVendorID());

        txtSeller = findViewById(R.id.txtStoreName);
        txtSeller.setText(v.getName());

        mProdId = db.checkVendorProdIDList(vendorID);
        mProdNames = db.checkVendorProdNameList(vendorID);
        mProdPrice = db.checkVendorProdPriceList(vendorID);

        File imgFile[] = new File[mProdNames.size()];
        for(int i = 0; i<mProdNames.size();i++){
            imgFile[i] = new File(String.valueOf(db.checkVendorProdImgURIList(vendorID).get(i)));
            if(imgFile[i].exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile[i].getAbsolutePath());
                mProdImageURI.add(bitmap);
            }
        }

        RecyclerView recyclerView = findViewById(R.id.storeInfoRecyclerView);
        StoreInfoViewAdapter adapter = new StoreInfoViewAdapter(this, mProdId, mProdNames, mProdPrice, mProdImageURI, prodID);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}