package com.example.gittest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {
    Button change;
    EditText changepass, confirmpass;
    AlertDialog.Builder builder;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        change= findViewById(R.id.btnChange);
        changepass = findViewById(R.id.txtchangepass);
        confirmpass = findViewById(R.id.txtconfirmpass);
        db = new DatabaseHelper(this);
        builder = new AlertDialog.Builder(this);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = changepass.getText().toString();
                String confirm = confirmpass.getText().toString();

                builder.setMessage("Are you sure you want to change password?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(pass.equals(confirm)){
                                    Boolean checkpasswordupdate = db.updatePassword(pass);
                                    if (checkpasswordupdate==true){
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        Toast.makeText(ChangePassword.this, "Password Updated!", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(ChangePassword.this, "Password not Updated!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    Toast.makeText(ChangePassword.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ChangePassword.this, "NO button is clicked!", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });
                AlertDialog alert  = builder.create();
                alert.setTitle("CONFIRM CHANGE PASSWORD");
                alert.show();
            }
        });
    }
}