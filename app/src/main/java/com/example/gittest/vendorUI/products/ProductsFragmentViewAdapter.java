package com.example.gittest.vendorUI.products;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gittest.DatabaseHelper;

import com.example.gittest.ProductDesc_Vendor;
import com.example.gittest.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductsFragmentViewAdapter extends RecyclerView.Adapter<ProductsFragmentViewAdapter.ViewHolder>{

    private static final String TAG = "OrdersFragmentViewAdapter";
    private Context mContext;

    private DatabaseHelper db;
    private ArrayList<Integer> mProdId = new ArrayList<>();
    private ArrayList<String> mProdNames  = new ArrayList<>();
    private ArrayList<Double> mProdPrice = new ArrayList<>();
    private ArrayList<Bitmap> mProdImageURI = new ArrayList<>();
    private String userid;
    private String vendorid;
    private ArrayList<String> mVendorId = new ArrayList<>();
    int count = 0;

    public ProductsFragmentViewAdapter(Context mContext,   ArrayList<Integer> mProdId, ArrayList<String> mProdNames, ArrayList<Double> mProdPrice, ArrayList<Bitmap> mProdImageURI,String userid,ArrayList<String> mVendorId) {
        this.mContext = mContext;
        this.mProdId = mProdId;
        this.mProdNames = mProdNames;
        this.mProdPrice = mProdPrice;
        this.mProdImageURI = mProdImageURI;
        this.userid = userid;
        this.mVendorId = mVendorId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        db = new DatabaseHelper(mContext);
        Log.d(TAG, "onBindViewHolder: called.");
        holder.vendorId.setText(mVendorId.get(position));
        holder.prodImg.setImageBitmap(mProdImageURI.get(position));
        holder.prodImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prodId = mProdId.get(position);
                Intent intent = new Intent(mContext, ProductDesc_Vendor.class);
                intent.putExtra("prodID_key", prodId);
                mContext.startActivity(intent);
            }
        });
        holder.prodName.setText(mProdNames.get(position));
        holder.prodName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prodId = mProdId.get(position);
                Intent intent = new Intent(mContext, ProductDesc_Vendor.class);
                intent.putExtra("prodID_key", prodId);
                mContext.startActivity(intent);
            }
        });
        holder.prodPrice.setText(Double.toString(mProdPrice.get(position)));
        holder.prodPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prodId = mProdId.get(position);
                Intent intent = new Intent(mContext, ProductDesc_Vendor.class);
                intent.putExtra("prodID_key", prodId);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        int vendorprodcount=0;
        for (int i=0;i<mProdNames.size();i++){
            if(mVendorId.get(i).equals(vendorid)){

                vendorprodcount++;
            }
        }
        return vendorprodcount;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView prodName, prodPrice,vendorId;
        ImageView prodImg;
        RelativeLayout product_vendor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prodName = itemView.findViewById(R.id.prod_name);
            prodPrice = itemView.findViewById(R.id.txtProd_price);
            prodImg = itemView.findViewById(R.id.imgProduct);
            product_vendor = itemView.findViewById(R.id.product_vendor);
            vendorId = itemView.findViewById(R.id.txtVendorID_prod);
        }
    }
}
