package com.example.gittest.vendorUI.orders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gittest.DatabaseHelper;
import com.example.gittest.OrderDetails;
import com.example.gittest.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrdersFragmentViewAdapter extends RecyclerView.Adapter<OrdersFragmentViewAdapter.ViewHolder>{

    private static final String TAG = "OrdersFragmentViewAdapter";
    private Context mContext;
    private ArrayList<String> mOrderNotif;
    private ArrayList<Integer> mOrderId;
    private DatabaseHelper db;

    public OrdersFragmentViewAdapter(Context mContext, ArrayList<String> mOrderNotif, ArrayList<Integer> mOrderId) {
        this.mContext = mContext;
        this.mOrderNotif = mOrderNotif;
        this.mOrderId = mOrderId;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        db = new DatabaseHelper(mContext);
        holder.orderId.setText(Integer.toString(mOrderId.get(position)));
        holder.orderNotif.setText("User " + mOrderNotif.get(position) + " has ordered!");
        holder.notifLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderDetails.class);
                intent.putExtra("countId_key", mOrderId.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrderNotif.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView orderNotif, orderId;

        RelativeLayout notifLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            orderId = itemView.findViewById(R.id.txtOrderId);
            orderNotif = itemView.findViewById(R.id.orderNotif);
            notifLayout = itemView.findViewById(R.id.notifLayout);
        }
    }
}
