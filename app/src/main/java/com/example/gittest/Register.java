package com.example.gittest;

import androidx.appcompat.app.AppCompatActivity;

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

public class Register extends AppCompatActivity implements View.OnClickListener{
    DatabaseHelper db;
    EditText idnum, email, fname, mname, lname, password, confPass;
    Button btnRegister, btnBack, btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        db = new DatabaseHelper(this);

        btnBack = (Button) findViewById(R.id.btnBackRegister);
        btnLogin = (Button) findViewById(R.id.btnExitRegister);
        btnRegister = findViewById(R.id.btnRegister);
        idnum = findViewById(R.id.editTextID);
        email = findViewById(R.id.editTextEmail);
        fname = findViewById(R.id.editTextFirstName);
        mname = findViewById(R.id.editTextMiddleName);
        lname = findViewById(R.id.editTextLastName);
        password = findViewById(R.id.editTextPassword1);
        confPass = findViewById(R.id.editTextPassword2);

        btnBack.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnRegister.setBackgroundColor(Color.LTGRAY);
        btnRegister.setEnabled(false);

        Drawable checkIcon;
        checkIcon = getResources().getDrawable(R.drawable.ic_baseline_check_circle_24);
        checkIcon.setBounds(new Rect(0, 0, checkIcon.getIntrinsicWidth(), checkIcon.getIntrinsicHeight()));

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
        addUser();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBackRegister:
                Toast.makeText(Register.this, "Going back!", Toast.LENGTH_SHORT).show();
                Intent Back = new Intent(this, MainActivity.class);
                startActivity(Back);
                break;
            case R.id.btnExitRegister:
                Toast.makeText(Register.this, "Going to Login Page!", Toast.LENGTH_SHORT).show();
                Intent Login = new Intent(this, Login.class);
                startActivity(Login);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
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

                            if (insert == true){
                                Toast.makeText(Register.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                                Intent Signup = new Intent(getApplicationContext(), MainActivity.class);
                                Signup.putExtra("userid_key", id);
                                startActivity(Signup);}
                            else
                                Toast.makeText(Register.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();

                        }

                    }
                }
        );
    }
}