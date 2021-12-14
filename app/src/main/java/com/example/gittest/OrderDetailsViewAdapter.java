package com.example.gittest;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderDetailsViewAdapter extends RecyclerView.Adapter<OrderDetailsViewAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<Integer> mOrderID;
    private ArrayList<Integer> mOrderQty;
    private ArrayList<String> mOrderName;
    private ArrayList<Double> subTotal;
    private ArrayList<Double> mOrderPrice;
    private ArrayList<Bitmap> mOrderImageURI;
    DatabaseHelper db;
    private String userid;
    int count = 0;

    public OrderDetailsViewAdapter(Context mContext, ArrayList<Integer> mOrderID, ArrayList<Integer> mOrderQty, ArrayList<String> mOrderName, ArrayList<Double> subTotal, ArrayList<Double> mOrderPrice, ArrayList<Bitmap> mOrderImageURI) {
        this.mContext = mContext;
        this.mOrderID = mOrderID;
        this.mOrderQty = mOrderQty;
        this.mOrderName = mOrderName;
        this.subTotal = subTotal;
        this.mOrderPrice = mOrderPrice;
        this.mOrderImageURI = mOrderImageURI;
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
        holder.orderId.setText(Integer.toString(mOrderID.get(position)));
        holder.orderName.setText(mOrderName.get(position));
        holder.subTotal.setText(Double.toString(subTotal.get(position)));
        holder.orderPrice.setText(Double.toString(mOrderPrice.get(position)));
        holder.orderQuant.setText(Integer.toString(mOrderQty.get(position)));
        holder.prodImg.setImageBitmap(mOrderImageURI.get(position));
    }

    @Override
    public int getItemCount() {
        return mOrderName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView orderName, orderPrice, orderId , orderQuant, subTotal;
        ImageView prodImg;
        RelativeLayout prodItemLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.txtorderprodId);
            orderName = itemView.findViewById(R.id.txtorderdeetsprod);
            orderPrice = itemView.findViewById(R.id.txtorderprice);
            subTotal = itemView.findViewById(R.id.txtSubtotal);
            orderQuant = itemView.findViewById(R.id.txtorderquant);
            prodImg = itemView.findViewById(R.id.orderprod);
            prodItemLayout = itemView.findViewById(R.id.orderdeetslayout);
        }
    }
}
