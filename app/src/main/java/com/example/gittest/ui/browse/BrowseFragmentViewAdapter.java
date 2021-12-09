package com.example.gittest.ui.browse;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gittest.DatabaseHelper;
import com.example.gittest.R;

import java.io.File;
import java.util.ArrayList;

public class BrowseFragmentViewAdapter extends RecyclerView.Adapter<BrowseFragmentViewAdapter.ViewHolder>{
    private ArrayList<String> mProdNames  = new ArrayList<>();
    private ArrayList<Double> mProdPrice = new ArrayList<>();
    private ArrayList<Bitmap> mProdImageURI = new ArrayList<>();
    DatabaseHelper db;
    private Context mContext;

    public BrowseFragmentViewAdapter(Context mContext, ArrayList<String> mProdNames, ArrayList<Double> mProdPrice, ArrayList<Bitmap> mProdImageURI) {
        this.mProdNames = mProdNames;
        this.mProdPrice = mProdPrice;
        this.mProdImageURI = mProdImageURI;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_proditem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.prodImg.setImageBitmap(mProdImageURI.get(position));
        holder.prodName.setText(mProdNames.get(position));
        holder.prodPrice.setText(Double.toString(mProdPrice.get(position)));
    }

    @Override
    public int getItemCount() {
        return mProdNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView prodName, prodPrice;
        ImageView prodImg;
        RelativeLayout prodItemLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            prodName = itemView.findViewById(R.id.txtProdName);
            prodPrice = itemView.findViewById(R.id.txtProdPrice);
            prodImg = itemView.findViewById(R.id.imgProd);
            prodItemLayout = itemView.findViewById(R.id.prodItemLayout);
        }
    }
}
