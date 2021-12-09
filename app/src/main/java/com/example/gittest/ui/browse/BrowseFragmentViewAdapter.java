package com.example.gittest.ui.browse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gittest.R;

import java.util.ArrayList;

public class BrowseFragmentViewAdapter extends RecyclerView.Adapter<BrowseFragmentViewAdapter.ViewHolder>{
    private ArrayList<String> mProdNames  = new ArrayList<>();
    private ArrayList<String> mProdPrice = new ArrayList<>();
    private ArrayList<String> mProdImageURI = new ArrayList<>();
    private Context mContext;

    public BrowseFragmentViewAdapter(Context mContext, ArrayList<String> mProdNames, ArrayList<String> mProdPrice, ArrayList<String> mProdImageURI) {
        this.mProdNames = mProdNames;
        this.mProdPrice = mProdPrice;
        this.mProdImageURI = mProdImageURI;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(mContext)
                .asBitmap()
                .load(mProdImageURI.get(position))
                .into(holder.prodImg);

        holder.prodName.setText(mProdNames.get(position));
        holder.prodPrice.setText(mProdPrice.get(position));
    }

    @Override
    public int getItemCount() {
        return mProdNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        Button btnProd;
        TextView prodName, prodPrice;
        ImageView prodImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnProd = itemView.findViewById(R.id.btnAddProd);
            prodName = itemView.findViewById(R.id.txtProdName);
            prodPrice = itemView.findViewById(R.id.txtProdPrice);
            prodImg = itemView.findViewById(R.id.imgProd);
        }
    }
}
