package com.example.gittest.vendorUI.accountsettings;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gittest.DatabaseHelper;
import com.example.gittest.Login;
import com.example.gittest.R;
import com.example.gittest.VendorAccountSettings;
import com.example.gittest.VendorPerformance;
import com.example.gittest.Vendor_OrdersHistory;
import com.example.gittest.loginID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountSettingsFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    VendorInfo v;
    DatabaseHelper db;

    public AccountSettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountSettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountSettingsFragment newInstance(String param1, String param2) {
        AccountSettingsFragment fragment = new AccountSettingsFragment();
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
        View view =  inflater.inflate(R.layout.fragment_account_settings, container, false);
        Intent intent = getActivity().getIntent();
        db = new DatabaseHelper(getActivity());
        v = new VendorInfo();
        v = db.readVendor(intent.getStringExtra("vendorId_key"));
        String vendorId = intent.getStringExtra("vendorId_key");

        Log.d(TAG, "vendorId: " + intent.getStringExtra("vendorId_key"));
        TextView txtVendorName2 = (TextView) view.findViewById(R.id.txtVendorName2);
        Log.d(TAG, "vendorName: " + v.getName());
        String vendorName = v.getName();
        txtVendorName2.setText(vendorName);
//        TextView txtVendorName2 = (TextView) view.findViewById(R.id.txtVendorName2);
//        TextView txtVendorID = (TextView) view.findViewById(R.id.txtVendorID);
        TextView accountSettings = (TextView) view.findViewById(R.id.txtAccountSettings);
        TextView performance = (TextView) view.findViewById(R.id.txtPerformance);
        TextView orders = (TextView) view.findViewById(R.id.txtOrders);
        TextView logout = (TextView) view.findViewById(R.id.txtLogOut);

        accountSettings.setOnClickListener(this);
        performance.setOnClickListener(this);
        orders.setOnClickListener(this);
        logout.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View view) {
        DatabaseHelper db = new DatabaseHelper(getActivity());
        switch (view.getId()){
            case R.id.txtAccountSettings:
                Intent intent = new Intent(getActivity(), VendorAccountSettings.class);
                startActivity(intent);
                break;

            case R.id.txtPerformance:
                Intent perf = new Intent(getActivity(), VendorPerformance.class);
                startActivity(perf);
                break;

            case R.id.txtOrders:
                intent = getActivity().getIntent();
                Intent intent1= new Intent(getActivity(), Vendor_OrdersHistory.class);
                String id = intent.getStringExtra("vendorId_key");
                intent1.putExtra("vendorId_key", id);
                startActivity(intent1);
                break;

            case R.id.txtLogOut:
                boolean delete = db.deleteUser(loginID.id);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Confirm Logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(delete){
                                    Toast.makeText(getActivity(), "Vendor Logged Out Successfully", Toast.LENGTH_SHORT).show();
                                    Intent in = new Intent(getActivity(), Login.class);
                                    startActivity(in);
                                }
                            }
                        }).setNegativeButton("No",null);

                AlertDialog ad = builder.create();
                ad.show();
                break;
        }
    }
}