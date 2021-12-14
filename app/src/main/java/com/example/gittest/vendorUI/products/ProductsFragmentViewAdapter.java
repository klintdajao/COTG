package com.example.gittest.vendorUI.products;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gittest.DatabaseHelper;
import com.example.gittest.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductsFragmentViewAdapter extends RecyclerView.Adapter<ProductsFragmentViewAdapter.ViewHolder>{

    private static final String TAG = "OrdersFragmentViewAdapter";
    private Context mContext;
    private ArrayList<String> mOrderNotif;
    private DatabaseHelper db;

    public ProductsFragmentViewAdapter(Context mContext, ArrayList<String> mOrderNotif) {
        this.mContext = mContext;
        this.mOrderNotif = mOrderNotif;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        db = new DatabaseHelper(mContext);
        holder.orderNotif.setText("User " + mOrderNotif.get(position) + " has ordered!");
    }

    @Override
    public int getItemCount() {
        return mOrderNotif.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView orderNotif;
        RelativeLayout notifLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            orderNotif = itemView.findViewById(R.id.orderNotif);
            notifLayout = itemView.findViewById(R.id.notifLayout);
        }
    }
}
