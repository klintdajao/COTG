package com.example.gittest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity{
    EditText id, pass;
    TextView forgot;
    Button btnActivity1;
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        id = findViewById(R.id.txtlogin);
        pass = findViewById(R.id.txtpassword);

        btnActivity1 = (Button)findViewById(R.id.btnlogin);
        forgot= findViewById(R.id.forgot);
        mydb = new DatabaseHelper(this);
        btnActivity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnlogin:

                        String idnum = id.getText().toString();
                        String password = pass.getText().toString();
                        if (idnum.equals("") || password.equals("")) {
                            Toast.makeText(Login.this, "Enter Username or Password", Toast.LENGTH_SHORT).show();
                        } else {
                            boolean check = mydb.checkUser(idnum,password);
                            if(check==true){
                                Intent intent1= new Intent(getApplicationContext(),Home.class);
                                startActivity(intent1);
                                Toast.makeText(Login.this, "Welcome!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(Login.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                                id.setText("");
                                pass.setText("");
                            }

                        }
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + v.getId());
                }
            }
        });

        }
}