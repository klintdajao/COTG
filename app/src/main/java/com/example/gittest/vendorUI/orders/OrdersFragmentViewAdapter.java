package com.example.gittest.vendorUI.orders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gittest.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrdersFragmentViewAdapter extends RecyclerView.Adapter<OrdersFragmentViewAdapter.ViewHolder>{

    private static final String TAG = "OrdersFragmentViewAdapter";

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
        Log.d(TAG, "onBindViewHolder: ");



    }

    @Override
    public int getItemCount() {
        return 0;
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
