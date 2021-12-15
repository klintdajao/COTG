package com.example.gittest.vendorUI.orders;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gittest.DatabaseHelper;
import com.example.gittest.R;
import com.example.gittest.VendorInfo;
import com.example.gittest.ui.browse.BrowseFragmentViewAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    VendorInfo v;
    DatabaseHelper db;
    ArrayList<String> mOrderNotif;
    ArrayList<Integer> mOrderId;
    TextView txtNoOrder;
    ImageView imgNoOrder;

    public OrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new OrdersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_orders, container, false);
        Intent intent = getActivity().getIntent();
        db = new DatabaseHelper(getActivity());
        v = db.readVendor(intent.getStringExtra("vendorId_key"));
        String vendorId = intent.getStringExtra("vendorId_key");

        Log.d(TAG, "vendorId: " + intent.getStringExtra("vendorId_key"));
        TextView txtVendorName = (TextView) root.findViewById(R.id.txtVendorName);
        Log.d(TAG, "vendorName: " + v.getName());
        String vendorName = v.getName();
        txtVendorName.setText(vendorName);
        imgNoOrder = root.findViewById(R.id.imgNoOrder);
        txtNoOrder = root.findViewById(R.id.txtNoOrder);
        mOrderNotif = db.checkActiveOrders(vendorId);
        mOrderId= db.checkOrderCountId(vendorId);


        if(mOrderId.size()==0){
            txtNoOrder.setVisibility(View.VISIBLE);
            imgNoOrder.setVisibility(View.VISIBLE);
        }

        RecyclerView recyclerView = root.findViewById(R.id.ordersRecyclerview);
        OrdersFragmentViewAdapter adapter = new OrdersFragmentViewAdapter(root.getContext(), mOrderNotif, mOrderId);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;

    }
}