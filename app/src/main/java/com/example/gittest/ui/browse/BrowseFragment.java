package com.example.gittest.ui.browse;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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

    DatabaseHelper db;
    private ArrayList<Integer> mProdId = new ArrayList<>();
    private ArrayList<String> mProdNames  = new ArrayList<>();
    private ArrayList<Double> mProdPrice = new ArrayList<>();
    private ArrayList<Bitmap> mProdImageURI = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(BrowseViewModel.class);

        binding = FragmentBrowseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db = new DatabaseHelper(getActivity());
        String userid = getActivity().getIntent().getStringExtra("userid_key");

        Log.d(TAG, "initImageBitmaps: creating bitmaps...");
        mProdId = db.checkProdIDList();
        mProdNames = db.checkProdNameList();
        mProdPrice = db.checkProdPriceList();

        File imgFile[] = new File[mProdNames.size()];
        for(int i = 0; i<mProdNames.size();i++){
            imgFile[i] = new File(db.checkProdImgURIList().get(i));
            if(imgFile[i].exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile[i].getAbsolutePath());
                mProdImageURI.add(bitmap);
            }
        }
//        mProdImageURI = db.checkProdImgURIList();

        Log.d(TAG, "mProdNames size: " + mProdNames.size());
        Log.d(TAG, "mProdPrice size: " + mProdNames.size());
        Log.d(TAG, "mProdImageURI get(0): " + db.checkProdImgURIList().size());
        Log.d(TAG, "mProdImageURI toString: " + mProdImageURI.toString());

        Log.d(TAG, "initRecyclerView: init recyclerview called.");
        RecyclerView recyclerView = root.findViewById(R.id.browseRecyclerView);
        BrowseFragmentViewAdapter adapter = new BrowseFragmentViewAdapter(root.getContext(), mProdId, mProdNames, mProdPrice, mProdImageURI, userid);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}