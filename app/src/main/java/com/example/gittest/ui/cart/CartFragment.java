package com.example.gittest.ui.cart;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.gittest.R;
import com.example.gittest.DatabaseHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartFragment extends Fragment {

    DatabaseHelper db;
    AlertDialog.Builder builder;
    RadioButton radioPaymentMode1;
    ArrayList<String> orderList = new ArrayList<>();
    ArrayList<Double> priceList = new ArrayList<>();
    ArrayList<Integer> quantityList = new ArrayList<>();
    TextView orderL, priceL, quantL, orderSubtotal, overallAmount, addMore;
    Button placeOrder;
    LinearLayout linearLayout;
    int x = 0;
    double txtOrderSubTotal = 0;

    String txtOrderCompile = "";
    String txtQuantityCompile = "";
    String txtPriceCompile = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CartViewModel dashboardViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        //calling variables' IDs
        Intent intent = getActivity().getIntent();
        DecimalFormat df = new DecimalFormat("#.##");
        db = new DatabaseHelper(getActivity());
        linearLayout = root.findViewById(R.id.rootContainer);
        builder = new AlertDialog.Builder(getActivity());
        placeOrder = root.findViewById(R.id.btnPlaceOrder);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification","My Notification",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        //------Strings------//
        String txtOrder = "";
        int txtQuantity;
        double txtPrice;
        //-------------------//

        radioPaymentMode1 = root.findViewById(R.id.radioPaymentMode1);
        if(!radioPaymentMode1.isChecked()){
            radioPaymentMode1.setError("Select payment Method");
        }

        //------------TextViews-------------//
        orderL = root.findViewById(R.id.prodList);
        priceL = root.findViewById(R.id.priceList);
        quantL = root.findViewById(R.id.quantList);
        orderSubtotal = root.findViewById(R.id.priceSubtotal);
        overallAmount = root.findViewById(R.id.orderAmount);
        addMore = root.findViewById(R.id.addMore);
        //-------------------------------//

        //------------Arrays-------------//
        Log.d("CREATION", "onCreateView: " + intent.getStringExtra("userid_key"));
        orderList = db.checkCartList(intent.getStringExtra("userid_key"));
        quantityList = db.checkCartQuantity(intent.getStringExtra("userid_key"));
        priceList = db.checkPrice(intent.getStringExtra("userid_key"));
        //-------------------------------//

//        Toast.makeText(getContext(), " "+ orderList.size(), Toast.LENGTH_SHORT).show();
        //compiling the array into strings
        for(int i=0; i<orderList.size(); i++){
            txtOrder = orderList.get(i);
            txtQuantity = quantityList.get(i);
            txtPrice = priceList.get(i);

            txtOrderCompile += txtOrder + "\n";
            txtQuantityCompile += String.valueOf(txtQuantity) + " x" + "\n";
            txtPriceCompile += "₱" + String.valueOf(txtPrice) + "\n";

            txtOrderSubTotal += txtQuantity * txtPrice;
        }

        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.navigation_browse);

            }
        });


        Button btnShow[] = new Button[orderList.size()];
        for (int i=0;i<orderList.size();i++) {
            btnShow[i] = new Button(getActivity());
            btnShow[i].setTag(i);
            btnShow[i].setBackground(getContext().getResources().getDrawable(R.drawable.ic_baseline_remove_circle_24));
//            btnShow.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            btnShow[i].setLayoutParams(new LinearLayout.LayoutParams(85,85));

            btnShow[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    x = (Integer) v.getTag();
                    db.deleteCartEntry((intent.getStringExtra("userid_key")), orderList.get(x));
                    Toast.makeText(getActivity(), "Successfully deleted cart item " + (x + 1), Toast.LENGTH_LONG).show();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    orderL.setText(null);
                    quantL.setText(null);
                    priceL.setText(null);
                    txtOrderCompile = "";
                    txtQuantityCompile ="";
                    txtPriceCompile = "";
                    ft.detach(CartFragment.this).attach(CartFragment.this).commit();

                }
            });

            // Add Button to LinearLayout
            if (linearLayout != null) {
                linearLayout.addView(btnShow[i]);
            }
        }

        if(orderList.size()<1)
            orderL.setText("Cart is Empty...");
        else
            orderL.setText(txtOrderCompile);

        radioPaymentMode1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                radioPaymentMode1.setError(null);
                if(orderL.getText().toString().equals("Cart is Empty..."))
                    placeOrder.setEnabled(false);
                else
                    placeOrder.setEnabled(true);
            }
        });

        quantL.setText(txtQuantityCompile);
        priceL.setText(txtPriceCompile);
        orderSubtotal.setText("₱" + df.format((txtOrderSubTotal)));
        overallAmount.setText("₱" + df.format(txtOrderSubTotal+30.00));
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage("Are you sure you want to place your order?").setCancelable(false)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "Successfully placed your order! It will arrive shortly.", Toast.LENGTH_LONG).show();
                                Navigation.findNavController(root).navigate(R.id.navigation_browse);
                                db.placeOrder(intent.getStringExtra("userid_key"));
                                db.deleteCart(intent.getStringExtra("userid_key"));
                                NotificationCompat.Builder not = new NotificationCompat.Builder(getContext(),"My Notification");
                                not.setContentTitle("Thank you for your Order!");
                                not.setContentText("Please wait, vendor will prepare your order");
                                not.setSmallIcon(R.drawable.ic_baseline_home_24);
                                not.setAutoCancel(true);

                                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
                                managerCompat.notify(1, not.build());


                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "Cancelled placing order.", Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("PLACE ORDER?");
                alert.show();
            }
        });
        return root;
    }
}
