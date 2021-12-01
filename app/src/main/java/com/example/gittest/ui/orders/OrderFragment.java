package com.example.gittest.ui.orders;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.gittest.DatabaseHelper;
import com.example.gittest.R;

import java.text.DecimalFormat;
public class OrderFragment extends Fragment {

    DatabaseHelper db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        OrderViewModel dashboardViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        //calling variables' IDs
        Intent intent = getActivity().getIntent();
        DecimalFormat df = new DecimalFormat("#.##");
        db = new DatabaseHelper(getActivity());

        return root;
    }
}
