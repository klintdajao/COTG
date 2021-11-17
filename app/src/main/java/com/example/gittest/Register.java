package com.example.gittest;

import androidx.appcompat.app.AppCompatActivity;

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

public class Register extends AppCompatActivity {
    DatabaseHelper db;
    EditText idnum, email, fname, mname, lname, password, confPass;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        db = new DatabaseHelper(this);

        btnRegister = findViewById(R.id.btnRegister);
        idnum = findViewById(R.id.txtIdNum);
        email = findViewById(R.id.txtEmail);
        fname = findViewById(R.id.txtFName);
        mname = findViewById(R.id.txtMName);
        lname = findViewById(R.id.txtLName);
        password = findViewById(R.id.txtPassword);
        confPass = findViewById(R.id.txtConfPass);

        btnRegister.setBackgroundColor(Color.LTGRAY);
        btnRegister.setEnabled(false);

        Drawable checkIcon;
        checkIcon = getResources().getDrawable(R.drawable.ic_baseline_check_circle_24);
        checkIcon.setBounds(new Rect(0, 0, checkIcon.getIntrinsicWidth(), checkIcon.getIntrinsicHeight()));
        addUser();
        idnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(idnum.length() <= 0){
                    idnum.setError("This field cannot be blank");
                }
                else{
                    idnum.setError(null);
                }
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(email.length() <= 0){
                    email.setError("This field cannot be blank");
                }
                else{
                    email.setError(null);
                }
            }
        });

        fname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(fname.length() <= 0){
                    fname.setError("This field cannot be blank");
                }
                else{
                    fname.setError(null);
                }
            }
        });

        mname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mname.length() <= 0){
                    mname.setError("This field cannot be blank");
                }
                else{
                    mname.setError(null);
                }
            }
        });

        lname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(lname.length() <= 0){
                    lname.setError("This field cannot be blank");
                }
                else{
                    lname.setError(null);
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(password.length() <= 0){
                    password.setError("This field cannot be blank");
                }
                else{
                    password.setError(null);
                }
            }
        });

        confPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(confPass.length() <= 0){
                    confPass.setError("This field cannot be blank");
                    btnRegister.setBackgroundColor(Color.LTGRAY);
                    btnRegister.setEnabled(false);
                }
                else{
                    if (password.getText().toString().equals(confPass.getText().toString())){
                        btnRegister.setBackgroundColor(Color.parseColor("#FFCC33"));
                        btnRegister.setEnabled(true);
                        confPass.setError("Password match",checkIcon);
                        password.setError("Password match", checkIcon);
                    }else {
                        confPass.setError("Password does not match");
                        btnRegister.setBackgroundColor(Color.LTGRAY);
                        btnRegister.setEnabled(false);

                    }
                }
            }
        });
    }

    public void addUser(){
        btnRegister.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean insert=false;
                        String id = idnum.getText().toString();
                        String e = email.getText().toString();
                        String fn = fname.getText().toString();
                        String mn = mname.getText().toString();
                        String ln = lname.getText().toString();
                        String p1 = password.getText().toString();
                        String p2 = confPass.getText().toString();
                        if(p1.equals(p2)) {
                            insert = db.addUser(id, e, fn, mn, ln, p2);

                            if (insert == true)
                                Toast.makeText(Register.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(Register.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                            password.setText("");
                            confPass.setText("");
                        }

                        idnum.setText("");
                        email.setText("");
                        fname.setText("");
                        mname.setText("");
                        lname.setText("");
                        password.setText("");
                        confPass.setText("");
                    }
                }
        );
    }
}