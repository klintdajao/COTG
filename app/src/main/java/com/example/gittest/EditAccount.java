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

public class EditAccount extends AppCompatActivity implements View.OnClickListener {
    EditText email, fn, mn, ln, pw;
    TextView id;
    Button update;
    AccountInfo a;
    DatabaseHelper db;
    String checkPW;
    TextView changePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        getSupportActionBar().hide();

        changePass = findViewById(R.id.txtEditChangePass);
        email = findViewById(R.id.editEmail);
        fn = findViewById(R.id.editFN);
        mn = findViewById(R.id.editMN);
        ln = findViewById(R.id.editLN);
        id = findViewById(R.id.txtIDNum);
        update = findViewById(R.id.btnUpdate);
        pw = findViewById(R.id.editPW);

        a = new AccountInfo();
        db = new DatabaseHelper(this);
        a = db.readUser(loginID.id);

        id.setText(a.getId());
        email.setText(a.getEmail().toString());
        fn.setText(a.getFn().toString());
        mn.setText(a.getMn().toString());
        ln.setText(a.getLn().toString());
        checkPW = a.getP();

        update.setOnClickListener(this);
        changePass.setOnClickListener(this);



        }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnUpdate:
                AlertDialog.Builder builder = new AlertDialog.Builder(EditAccount.this);
                builder.setMessage("Update Account?")
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String e = email.getText().toString();
                                String fname = fn.getText().toString();
                                String mname = mn.getText().toString();
                                String lname = ln.getText().toString();
                                String password = pw.getText().toString();
                                String id = a.getId();

                                if(password.equals(checkPW)){
                                    if(db.updateUser(id,e,fname,mname,lname)){
                                        Toast.makeText(EditAccount.this, "Account updated successfully!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else
                                        Toast.makeText(EditAccount.this, "Account not updated!", Toast.LENGTH_SHORT).show();
                                }else
                                    pw.setError("Password Incorrect!");
                                    pw.setText("");

                            }
                        }).setNegativeButton("No",null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.txtEditChangePass:
                Intent intent = new Intent(this, EditAccountChangePassword.class);
                startActivity(intent);
        }
    }
}

