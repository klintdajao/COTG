package com.example.gittest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FindId extends AppCompatActivity implements View.OnClickListener{
    TextView login;
    EditText id;
    Button search, Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);
        login= findViewById(R.id.forgotlogin);
        id= findViewById(R.id.txtId);
        search = findViewById(R.id.btnSearch);
        Back = (Button) findViewById(R.id.btnBackFindID);

        Back.setOnClickListener(this);

        DatabaseHelper db = new DatabaseHelper(this);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid = id.getText().toString();

                Boolean checkid = db.checkId(userid);

                if(checkid==true){
                    Intent intent= new Intent(getApplicationContext(),ChangePassword.class);
                    intent.putExtra("id",userid);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(FindId.this, "User does not exist!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBackFindID:
                Toast.makeText(FindId.this, "Going back to Main Menu!", Toast.LENGTH_SHORT).show();
                Intent Back = new Intent(this, MainActivity.class);
                startActivity(Back);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}