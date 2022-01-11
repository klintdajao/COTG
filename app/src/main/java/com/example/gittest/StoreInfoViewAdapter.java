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

import com.example.gittest.ui.browse.BrowseFragmentViewAdapter;

import java.util.ArrayList;

public class StoreInfoViewAdapter extends RecyclerView.Adapter<StoreInfoViewAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<Integer> mProdId;
    private ArrayList<String> mProdNames;
    private ArrayList<Double> mProdPrice;
    private ArrayList<Bitmap> mProdImageURI;
    DatabaseHelper db;
    private int userid;
    int count = 0;

    public StoreInfoViewAdapter(Context mContext, ArrayList<Integer> mProdId, ArrayList<String> mProdNames, ArrayList<Double> mProdPrice, ArrayList<Bitmap> mProdImageURI, int userid) {
        this.mContext = mContext;
        this.mProdId = mProdId;
        this.mProdNames = mProdNames;
        this.mProdPrice = mProdPrice;
        this.mProdImageURI = mProdImageURI;
        this.userid = userid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_proditem, parent, false);
        StoreInfoViewAdapter.ViewHolder holder = new StoreInfoViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        db = new DatabaseHelper(mContext);
        Log.d(TAG, "onBindViewHolder: called.");
        holder.prodId.setText(Integer.toString(mProdId.get(position)));
        holder.prodImg.setImageBitmap(mProdImageURI.get(position));
        holder.prodImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prodId = mProdId.get(position);
                Intent intent = new Intent(mContext, ProductDesc.class);
                intent.putExtra("prodID_key", prodId);
                mContext.startActivity(intent);
            }
        });
        holder.prodName.setText(mProdNames.get(position));
        holder.prodName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prodId = mProdId.get(position);
                Intent intent = new Intent(mContext, ProductDesc.class);
                intent.putExtra("prodID_key", prodId);
                mContext.startActivity(intent);
            }
        });
        holder.prodPrice.setText(Double.toString(mProdPrice.get(position)));
        holder.prodPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prodId = mProdId.get(position);
                Intent intent = new Intent(mContext, ProductDesc.class);
                intent.putExtra("prodID_key", prodId);
                mContext.startActivity(intent);
            }
        });
        holder.btnAddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = db.checkOrderQuantity(mProdId.get(position), loginID.id);
                count++;

//                Toast.makeText(mContext, "Count: "+ count+ " User " + userid + " clicked on: ProdID: " +mProdId.get(position)+ "ProdName: " + mProdNames.get(position) + ", ProdPrice: " + mProdPrice.get(position), Toast.LENGTH_SHORT).show();
                if (count <= 1) {
                    db.addToCart(mProdId.get(position), count, loginID.id);
                    Toast.makeText(mContext, "Ordered this " + count + " time!", Toast.LENGTH_SHORT).show();
                } else {
                    db.updateOrder(loginID.id, mProdId.get(position), count);
                    Toast.makeText(mContext, "Ordered this " + count + " times!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() { return mProdNames.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtStoreName;
        Button btnAddProd;
        TextView prodName, prodPrice, prodId;
        ImageView prodImg;
        RelativeLayout prodItemLayout;

        public ViewHolder(View itemView){
            super(itemView);

            txtStoreName = itemView.findViewById(R.id.txtStoreName);
            prodId = itemView.findViewById(R.id.txtProdID);
            prodName = itemView.findViewById(R.id.txtProdName);
            prodPrice = itemView.findViewById(R.id.txtOrderPrice);
            prodImg = itemView.findViewById(R.id.imgProd);
            prodItemLayout = itemView.findViewById(R.id.prodItemLayout);
            btnAddProd = itemView.findViewById(R.id.btnAddProd);

        }

    }
}
