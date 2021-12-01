package com.example.gittest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gittest.ui.account.AccountFragment2;

public class EditAccountChangePassword extends AppCompatActivity {

    EditText old,newPW,confirmNewPW;
    Button btnChangePW;
    DatabaseHelper db;
    AccountInfo a;
    String checkPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account_change_password);
        getSupportActionBar().hide();

        Drawable checkIcon;
        checkIcon = getResources().getDrawable(R.drawable.ic_baseline_check_circle_24);
        checkIcon.setBounds(new Rect(0, 0, checkIcon.getIntrinsicWidth(), checkIcon.getIntrinsicHeight()));

        old = findViewById(R.id.editChangePWOld);
        newPW = findViewById(R.id.editChangePWNew);
        confirmNewPW = findViewById(R.id.editConfirmChangePW);
        btnChangePW = findViewById(R.id.btnChangePass);

        a = new AccountInfo();
        db = new DatabaseHelper(this);
        a = db.readUser(loginID.id);

        confirmNewPW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(confirmNewPW.length() <= 0){
                    confirmNewPW.setError("This field cannot be blank");
                    btnChangePW.setBackgroundColor(Color.LTGRAY);
                    btnChangePW.setEnabled(false);
                }
                else{
                    if (newPW.getText().toString().equals(confirmNewPW.getText().toString())){
                        btnChangePW.setBackgroundColor(Color.parseColor("#FFCC33"));
                        btnChangePW.setEnabled(true);
                        confirmNewPW.setError("Password match",checkIcon);
                        newPW.setError("Password match", checkIcon);
                    }else {
                        confirmNewPW.setError("Password does not match");
                        btnChangePW.setBackgroundColor(Color.LTGRAY);
                        btnChangePW.setEnabled(false);

                    }
                }
            }
        });

        checkPW = a.getP();
        btnChangePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EditAccountChangePassword.this);
                builder.setMessage("Are you sure u wana change ur PW bruh?")
                        .setPositiveButton("yeah cuh", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String oldPW = old.getText().toString();
                                String newP = newPW.getText().toString();
                                String confPW = confirmNewPW.getText().toString();
                                String confOldPW = checkPW;
                                String id = a.getId();

                                if(oldPW.equals(confOldPW)){
                                    if(newP.equals(confPW)){
                                        Boolean updatePass = db.updatePW(confPW,id);
                                        if(updatePass){
                                            Toast.makeText(EditAccountChangePassword.this, "Password changed succsessfully!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), Home.class);
                                            startActivity(intent);
                                        }
                                        else{
                                            Toast.makeText(EditAccountChangePassword.this, "Password not changed!", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{

                                    }
                                }else{
                                    Toast.makeText(EditAccountChangePassword.this, "Wrong Old Password!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("naurrr",null);

                AlertDialog ad = builder.create();
                ad.show();
            }
        });

    }
}