package com.example.gittest.ui.browse;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gittest.DatabaseHelper;
import com.example.gittest.databinding.FragmentBrowseBinding;

public class BrowseFragment extends Fragment {

    private BrowseViewModel homeViewModel;
    private FragmentBrowseBinding binding;

    Button btnAddFood1,btnAddFood2, btnAddFood3;
    TextView food1, food2, food3;
    TextView priceF1, priceF2, priceF3;
    int count1, count2, count3;
    String t_f1, t_f2, t_f3;
    Double p_f1, p_f2, p_f3;
    DatabaseHelper db;

    TextView edit, cs, delete;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(BrowseViewModel.class);

        binding = FragmentBrowseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Intent intent = getActivity().getIntent();
        btnAddFood1 = binding.btnAddFood1;
        btnAddFood2 = binding.btnAddFood2;
        btnAddFood3 = binding.btnAddFood3;

        food1 = binding.txtFood1;
        food2 = binding.txtFood2;
        food3 = binding.txtFood3;

        priceF1 = binding.txtFood1Price;
        priceF2 = binding.txtFood2Price;
        priceF3 = binding.txtfood3Price;

        t_f1 = food1.getText().toString();
        t_f2 = food2.getText().toString();
        t_f3 = food3.getText().toString();

        p_f1 = Double.parseDouble(priceF1.getText().toString());
        p_f2 = Double.parseDouble(priceF2.getText().toString());
        p_f3 = Double.parseDouble(priceF3.getText().toString());

        btnAddFood1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count1 = db.checkOrderQuantity(t_f1, intent.getStringExtra("userid_key"));
                Log.d(TAG, "onClick: " + intent.getStringExtra("userid_key"));
                count1++;

                if(count1<=1)
                    db.addToCart(t_f1, count1,p_f1, intent.getStringExtra("userid_key"));
                else
                    db.updateOrder(intent.getIntExtra("userid_key", 0),t_f1, count1);

                if(count1<=1)
                    Toast.makeText(getContext(), "You ordered this "+count1+" time!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), "You ordered this "+count1+" times!", Toast.LENGTH_SHORT).show();
            }
        });

        btnAddFood2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count1 = db.checkOrderQuantity(t_f2, intent.getStringExtra("userid_key"));

                count1++;
                if(count1<=1)
                    db.addToCart(t_f2, count2,p_f2, intent.getStringExtra("userid_key"));
                else
                    db.updateOrder(intent.getIntExtra("userid_key", 0),t_f2, count1);

                if(count1<=1)
                    Toast.makeText(getContext(), "You ordered this "+count2+" time!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), "You ordered this "+count2+" times!", Toast.LENGTH_SHORT).show();
            }
        });

        btnAddFood3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count1 = db.checkOrderQuantity(t_f3, intent.getStringExtra("userid_key"));

                count1++;
                if(count1<=1)
                    db.addToCart(t_f3, count3,p_f3, intent.getStringExtra("userid_key"));
                else
                    db.updateOrder(intent.getIntExtra("userid_key", 0),t_f3, count1);

                if(count1<=1)
                    Toast.makeText(getContext(), "You ordered this "+count3+" time!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), "You ordered this "+count3+" times!", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}