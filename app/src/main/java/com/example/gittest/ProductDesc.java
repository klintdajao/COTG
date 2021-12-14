package com.example.gittest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

public class ProductDesc extends AppCompatActivity {

    ImageView imgView;
    DatabaseHelper db;
    private ArrayList<Integer> mProdId = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_desc);
        mProdId = db.checkProdIDList();
        imgView = findViewById(R.id.imgView);
        db = new DatabaseHelper(this);


        File imgFile[] = new File[mProdId.size()];
        for (int i = 0; i < mProdId.size(); i++) {
            imgFile[i] = new File(String.valueOf(db.checkProdImgURIList().get(i)));
            if(imgFile[i].exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile[i].getAbsolutePath());
            imgView.setImageBitmap(bitmap);
            }
        }
    }
}