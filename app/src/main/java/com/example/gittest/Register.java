package com.example.gittest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    DatabaseHelper db;
    EditText editId, editEmail, editFN, editMN, editLN,editPassword1, editPassword2;
    Button register;
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
        register = findViewById(R.id.btnRegister);

        addUser();
    }

    public void addUser(){
        register.setOnClickListener(
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