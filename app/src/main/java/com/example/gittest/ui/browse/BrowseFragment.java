package com.example.gittest.ui.browse;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gittest.DatabaseHelper;
import com.example.gittest.R;
import com.example.gittest.databinding.FragmentBrowseBinding;

import java.io.File;
import java.util.ArrayList;

public class BrowseFragment extends Fragment {

    private BrowseViewModel homeViewModel;
    private FragmentBrowseBinding binding;



//    Button btnAddFood1,btnAddFood2, btnAddFood3;
//    TextView food1, food2, food3;
//    TextView priceF1, priceF2, priceF3;
//    int count1, count2, count3;
//    String t_f1, t_f2, t_f3;
//    Double p_f1, p_f2, p_f3;
    DatabaseHelper db;

    private ArrayList<String> mProdNames  = new ArrayList<>();
    private ArrayList<Double> mProdPrice = new ArrayList<>();
    private ArrayList<Bitmap> mProdImageURI = new ArrayList<Bitmap>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(BrowseViewModel.class);

        binding = FragmentBrowseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db = new DatabaseHelper(getActivity());
        Intent intent = getActivity().getIntent();
        String userid = getActivity().getIntent().getStringExtra("userid_key");


//        btnAddFood1 = binding.btnAddFood1;
//        btnAddFood2 = binding.btnAddFood2;
//        btnAddFood3 = binding.btnAddFood3;
//
//        food1 = binding.txtFood1;
//        food2 = binding.txtFood2;
//        food3 = binding.txtFood3;
//
//        priceF1 = binding.txtFood1Price;
//        priceF2 = binding.txtFood2Price;
//        priceF3 = binding.txtFood3Price;
//
//        t_f1 = food1.getText().toString();
//        t_f2 = food2.getText().toString();
//        t_f3 = food3.getText().toString();
//
//        p_f1 = Double.parseDouble(priceF1.getText().toString());
//        p_f2 = Double.parseDouble(priceF2.getText().toString());
//        p_f3 = Double.parseDouble(priceF3.getText().toString());
//
//        btnAddFood1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                count1 = db.checkOrderQuantity(t_f1, intent.getStringExtra("userid_key"));
//                count1++;
//
//                if(count1<=1)
//                    db.addToCart(t_f1, count1,p_f1, intent.getStringExtra("userid_key"));
//                else
//                    db.updateOrder(intent.getStringExtra("userid_key"),t_f1, count1);
//
//                if(count1<=1)
//                    Toast.makeText(getContext(), "You ordered this "+count1+" time!", Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(getContext(), "You ordered this "+count1+" times!", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        btnAddFood2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                count2 = db.checkOrderQuantity(t_f2, intent.getStringExtra("userid_key"));
//                count2++;
//
//                if(count2<=1)
//                    db.addToCart(t_f2, count2, p_f2, intent.getStringExtra("userid_key"));
//                else
//                    db.updateOrder(intent.getStringExtra("userid_key"),t_f2, count2);
//
//                if(count1<=1)
//                    Toast.makeText(getContext(), "You ordered this "+count2+" time!", Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(getContext(), "You ordered this "+count2+" times!", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        btnAddFood3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                count3 = db.checkOrderQuantity(t_f3, intent.getStringExtra("userid_key"));
//                count3++;
//
//                if(count3<=1)
//                    db.addToCart(t_f3, count3,p_f3, intent.getStringExtra("userid_key"));
//                else
//                    db.updateOrder(intent.getStringExtra("userid_key"),t_f3, count3);
//
//                if(count3<=1)
//                    Toast.makeText(getContext(), "You ordered this "+count3+" time!", Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(getContext(), "You ordered this "+count3+" times!", Toast.LENGTH_SHORT).show();
//            }
//        });
//        initImageBitMaps();

        Log.d(TAG, "initImageBitmaps: creating bitmaps...");
        mProdNames = db.checkProdNameList();
        mProdPrice = db.checkProdPriceList();

        File imgFile[] = new File[mProdNames.size()];
        for(int i = 0; i<mProdNames.size();i++){
            imgFile[i] = new File(String.valueOf(db.checkProdImgURIList().get(i)));
            if(imgFile[i].exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile[i].getAbsolutePath());
                mProdImageURI.add(bitmap);
            }
        }
//        mProdImageURI = db.checkProdImgURIList();

        Log.d(TAG, "mProdNames size: " + mProdNames.size());
        Log.d(TAG, "mProdPrice size: " + mProdNames.size());
        Log.d(TAG, "mProdImageURI get(0): " + db.checkProdImgURIList().get(0));
        Log.d(TAG, "mProdImageURI toString: " + mProdImageURI.toString());

        Log.d(TAG, "initRecyclerView: init recyclerview called.");
        RecyclerView recyclerView = root.findViewById(R.id.browseRecyclerView);
        BrowseFragmentViewAdapter adapter = new BrowseFragmentViewAdapter(root.getContext(), mProdNames, mProdPrice, mProdImageURI);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    public void initImageBitMaps(){
//
//
//        initRecyclerView();
//    }
//
//    public void initRecyclerView(){
//
//    }
}