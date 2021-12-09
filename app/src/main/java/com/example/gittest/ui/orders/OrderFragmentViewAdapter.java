package com.example.gittest.ui.orders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gittest.R;
import com.example.gittest.vendorUI.orders.OrdersFragmentViewAdapter;

public class OrderFragmentViewAdapter extends RecyclerView.Adapter<OrdersFragmentViewAdapter.ViewHolder>{

    @NonNull
    @Override
    public OrdersFragmentViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersFragmentViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
