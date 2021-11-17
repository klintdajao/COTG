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
    EditText editId, editEmail, editFN, editMN, editLN,editPassword1, editPassword2;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        db = new DatabaseHelper(this);

        editId = (EditText) findViewById(R.id.editTextID);
        editEmail = findViewById(R.id.editTextEmail);
        editFN = findViewById(R.id.editTextFirstName);
        editMN = findViewById(R.id.editTextMiddleName);
        editLN = findViewById(R.id.editTextLastName);
        editPassword1 = findViewById(R.id.editTextPassword1);
        editPassword2 = findViewById(R.id.editTextPassword2);
        btnRegister = findViewById(R.id.btnRegister);

        addUser();

        Drawable checkIcon;
        checkIcon = getResources().getDrawable(R.drawable.ic_baseline_check_circle_24);
        checkIcon.setBounds(new Rect(0, 0, checkIcon.getIntrinsicWidth(), checkIcon.getIntrinsicHeight()));

        btnRegister.setBackgroundColor(Color.LTGRAY);

        editId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editId.length() <= 0){
                    editId.setError("This field cannot be blank");
                }
                else{
                    editId.setError(null);
                }
            }
        });
        editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editEmail.length() <= 0){
                    editEmail.setError("This field cannot be blank");
                }
                else{
                    editEmail.setError(null);
                }
            }
        });

        editFN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editFN.length() <= 0){
                    editFN.setError("This field cannot be blank");
                }
                else{
                    editFN.setError(null);
                }
            }
        });

        editMN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editMN.length() <= 0){
                    editMN.setError("This field cannot be blank");
                }
                else{
                    editMN.setError(null);
                }
            }
        });

        editLN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editLN.length() <= 0){
                    editLN.setError("This field cannot be blank");
                }
                else{
                    editLN.setError(null);
                }
            }
        });

        editPassword1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editPassword1.length() <= 0){
                    editPassword1.setError("This field cannot be blank");
                }
                else{
                    editPassword1.setError(null);
                }
            }
        });

        editPassword1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editPassword2.length() <= 0){
                    editPassword2.setError("This field cannot be blank");
                    btnRegister.setBackgroundColor(Color.LTGRAY);
                    btnRegister.setEnabled(false);
                }
                else{
                    if (editPassword1.getText().toString().equals(editPassword2.getText().toString())){
                        btnRegister.setBackgroundColor(Color.parseColor("#ffcc33"));
                        btnRegister.setEnabled(true);
                        editPassword1.setError("Password match",checkIcon);
                        editPassword2.setError("Password match", checkIcon);
                    }else {
                        editPassword1.setError("Password does not match");
                        editPassword2.setError("Password does not match");
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
                        String id = editId.getText().toString();
                        String email = editEmail.getText().toString();
                        String fn = editFN.getText().toString();
                        String mn = editMN.getText().toString();
                        String ln = editLN.getText().toString();
                        String p1 = editPassword1.getText().toString();
                        String p2 = editPassword2.getText().toString();
                        if(p1.equals(p2)) {
                            insert = db.addUser(id, email, fn, mn, ln, p2);

                            if (insert == true)
                                Toast.makeText(Register.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(Register.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                            editPassword1.setText("");
                            editPassword2.setText("");
                        }

                        editId.setText("");
                        editEmail.setText("");
                        editFN.setText("");
                        editMN.setText("");
                        editLN.setText("");
                        editPassword1.setText("");
                        editPassword2.setText("");
                    }
                }
        );
    }
}