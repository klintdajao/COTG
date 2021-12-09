package com.example.gittest.ui.orders;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gittest.AccountInfo;
import com.example.gittest.DatabaseHelper;
import com.example.gittest.R;
import com.example.gittest.loginID;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class OrderFragment extends Fragment {

    DatabaseHelper db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        OrderViewModel dashboardViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        //calling variables' IDs
        TextView txtName = (TextView) root.findViewById(R.id.txtUser);
        DatabaseHelper db = new DatabaseHelper(getActivity());
        AccountInfo a = new AccountInfo();
        DatabaseHelper mydb = new DatabaseHelper(getActivity());
        a = mydb.readUser(loginID.id);
        String name = a.getFn() +" " + a.getLn() + "'s Order History";

        txtName.setText(name);









        return root;
    }
}
