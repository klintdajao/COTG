package com.example.gittest.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gittest.AccountInfo;
import com.example.gittest.CancelOrder;
import com.example.gittest.DatabaseHelper;
import com.example.gittest.Order_History;
import com.example.gittest.R;
import com.example.gittest.loginID;

import java.util.ArrayList;

public class OrderFragment extends Fragment {

    DatabaseHelper db;
    ArrayList<String> productList = new ArrayList<>();
    ArrayList<Integer> quantityList= new ArrayList<>();
    ArrayList<Double> amountList= new ArrayList<>();
    ArrayList<String> dateList= new ArrayList<>();
    Intent intent;
    String show = "";
    String show2 = "";
    String show3 = "";
    String show4 = "";
    String name = "";
    Integer quantity = 0;
    Double amount = 0.0;
    String date = "";
    TextView txtProduct, txtQuantity, txtAmount, txtDate, txtUser;
    int ctr=0;
    Button cancel, history;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        OrderViewModel dashboardViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        //calling variables' IDs
//        DatabaseHelper db = new DatabaseHelper(getActivity());


        txtProduct = (TextView) root.findViewById(R.id.txtProduct);
        txtQuantity = (TextView) root.findViewById(R.id.txtQuantity);
        txtAmount = (TextView) root.findViewById(R.id.txtAmount);
        txtDate = (TextView) root.findViewById(R.id.txtDate);
        txtUser= (TextView) root.findViewById(R.id.txtUser);
        cancel = (Button) root.findViewById(R.id.btnCancel);
        history = (Button) root.findViewById(R.id.btnHistory);


        cancel.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              intent = getActivity().getIntent();
              Intent intent1 = new Intent(getActivity(), CancelOrder.class);
              String temp = intent.getStringExtra("userid_key");
              intent1.putExtra("userid_key", temp);
              startActivity(intent1);

          }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHistory();
            }
        });

        db = new DatabaseHelper(getActivity());
        intent = getActivity().getIntent();
        AccountInfo a = new AccountInfo();
        a = db.readUser(loginID.id);
        String title = a.getFn() +" " + a.getLn() + "'s Order History";


        productList = db.checkOrderList(intent.getStringExtra("userid_key"));
        quantityList = db.checkOrderQuantity(intent.getStringExtra("userid_key"));
        amountList = db.checkOrderAmount(intent.getStringExtra("userid_key"));
        dateList = db.checkOrderDate(intent.getStringExtra("userid_key"));

//        Date currentTime = new Date();
//
//        DateFormat formatTime = new SimpleDateFormat("hh:mm:ss aa");
//        DateFormat formatDate = new SimpleDateFormat("MMMMM, dd, yyyy");
//        String formattedTimeStr = formatTime.format(currentTime);
//        String formattedDateStr = formatDate.format(currentTime);

        for (ctr=0;ctr<productList.size();ctr++) {
            name = productList.get(ctr);
            quantity = quantityList.get(ctr);
            amount = amountList.get(ctr);
            date = dateList.get(ctr);
            show += name + "\n";
            show2 +=  quantity + "\n";
            show3 +=  amount + "\n";
            show4 +=  date + "\n";
        }

        txtProduct.setText(show);
        txtQuantity.setText(show2);
        txtAmount.setText(show3);

        txtDate.setText(show4);
        txtUser.setText(title);

        return root;
    }

    public void openHistory(){
        intent = getActivity().getIntent();
        Intent intent1 = new Intent(getActivity(), Order_History.class);
        String temp = intent.getStringExtra("userid_key");
        intent1.putExtra("userid_key", temp);
        startActivity(intent1);
    }
}
