package com.example.gittest.vendorUI.orders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gittest.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrdersFragmentViewAdapter extends RecyclerView.Adapter<OrdersFragmentViewAdapter.ViewHolder>{

    private static final String TAG = "OrdersFragmentViewAdapter";

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

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
