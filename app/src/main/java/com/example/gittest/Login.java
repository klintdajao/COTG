package com.example.gittest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity implements View.OnClickListener{
    EditText id, pass;
    TextView forgot;
    Button btnActivity1;
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        btnActivity1 = (Button)findViewById(R.id.btnlogin);
        forgot= findViewById(R.id.forgot);

        btnActivity1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btnlogin:
                mydb= new DatabaseHelper(this);
                String idnum= id.getText().toString();
                String password= pass.getText().toString();
                if(idnum.equals("")|| password.equals("")){
                    Toast.makeText(Login.this, "Enter Username or Password", Toast.LENGTH_SHORT).show();
                }
                else{
                        Toast.makeText(Login.this, "Welcome!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }

    }
}