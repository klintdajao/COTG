package com.example.gittest;

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

import java.util.ArrayList;

public class OrderDetailsViewAdapter extends RecyclerView.Adapter<OrderDetailsViewAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<Integer> mOrderID;
    private ArrayList<Integer> mOrderQty;
    private ArrayList<String> mOrderName;
    private ArrayList<Double> mOrderPrice;
    private ArrayList<Bitmap> mProdImageURI;
    DatabaseHelper db;
    private String userid;
    int count = 0;

    public OrderDetailsViewAdapter(Context mContext, ArrayList<Integer> mProdId, ArrayList<String> mProdNames, ArrayList<Double> mProdPrice, ArrayList<Bitmap> mProdImageURI, String userid) {
        this.mContext = mContext;
        this.mProdId = mProdId;
        this.mProdNames = mProdNames;
        this.mProdPrice = mProdPrice;
        this.mProdImageURI = mProdImageURI;
        this.userid = userid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_orderdeets, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

    }

    @Override
    public int getItemCount() {
        return mProdNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView orderName, orderPrice, orderId , orderQuant;
        ImageView prodImg;
        RelativeLayout prodItemLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.txtorderprodId);
            orderName = itemView.findViewById(R.id.txtorderdeetsprod);
            orderPrice = itemView.findViewById(R.id.txtorderprice);
            orderQuant = itemView.findViewById(R.id.txtorderquant);
            prodImg = itemView.findViewById(R.id.orderprod);
            prodItemLayout = itemView.findViewById(R.id.orderdeetslayout);
        }
    }
}
