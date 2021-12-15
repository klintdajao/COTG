package com.example.gittest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gittest.vendorUI.accountsettings.AccountSettingsFragment;

public class VendorAccountSettings extends AppCompatActivity implements View.OnClickListener{
    EditText email, pw;
    TextView IDNum;
    Button update,back;
    VendorInfo v;
    DatabaseHelper db;
    String checkPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_account_settings);
        getSupportActionBar().hide();
        email = findViewById(R.id.editEmail);
        update = findViewById(R.id.btnUpdate);
        back = findViewById(R.id.btnBackVaccount);
        pw = findViewById(R.id.editPW);
        IDNum = findViewById(R.id.txtIDNum);
        v = new VendorInfo();
        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        String id = intent.getStringExtra("vendorId_key");

        v = db.readVendor(id);
        IDNum.setText(id);
        email.setText(v.getEmail().toString());
        checkPW = v.getP();

        update.setOnClickListener(this);
        back.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnUpdate:
                AlertDialog.Builder builder = new AlertDialog.Builder(VendorAccountSettings.this);
                builder.setMessage("Update Vendor Email?")
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String e = email.getText().toString();
                                String password = pw.getText().toString();
                                String id = v.getId();

                                if(password.equals(checkPW)){
                                    if(db.updateVendor(id,e)){
                                        Toast.makeText(VendorAccountSettings.this, "Email updated successfully!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else
                                        Toast.makeText(VendorAccountSettings.this, "Email not updated successfully!", Toast.LENGTH_SHORT).show();
                                }else
                                    pw.setError("Password Incorrect!");
                                pw.setText("");

                            }
                        }).setNegativeButton("No",null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.btnBackVaccount:
                Intent intent = new Intent(this, AccountSettingsFragment.class);
                startActivity(intent);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}