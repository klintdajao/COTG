package com.example.gittest.vendorUI.accountsettings;

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

import com.example.gittest.AccountInfo;
import com.example.gittest.DatabaseHelper;
import com.example.gittest.EditAccount;
import com.example.gittest.Login;
import com.example.gittest.R;
import com.example.gittest.VendorInfo;
import com.example.gittest.loginID;
import com.example.gittest.vendorUI.orders.OrdersFragment;

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
        TextView txtVendorName2 = (TextView) view.findViewById(R.id.txtVendorName2);
        TextView txtVendorID = (TextView) view.findViewById(R.id.txtVendorID);
        TextView accountSettings = (TextView) view.findViewById(R.id.txtAccountSettings);
        TextView performance = (TextView) view.findViewById(R.id.txtPerformance);
        TextView orders = (TextView) view.findViewById(R.id.txtOrders);
        TextView logout = (TextView) view.findViewById(R.id.txtLogOut);
//        AccountInfo a = new VendorInfo();
//        DatabaseHelper db = new DatabaseHelper(getActivity());
//        a = db.readVendor(loginID.id);
//        String name = a.getFn() +" " + a.getLn();
//
//        txtEmail.setText(a.getEmail());
//        txtName.setText(name);
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
                Intent intent = new Intent(getActivity(), VendorInfo.class);
                startActivity(intent);
                break;
//            case R.id.txtPerformance:
//                Intent perf = new Intent(getActivity(), VPerformance.class);
//                startActivity(perf);
//                break;
            case R.id.txtOrders:
                Intent orders = new Intent(getActivity(), OrdersFragment.class);
                startActivity(orders);
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