package com.example.gittest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity implements  View.OnClickListener{
    Button Back, Send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        getSupportActionBar().hide();

        Back = (Button) findViewById(R.id.btnBack);
        Send = (Button) findViewById(R.id.btnSend);

        Back.setOnClickListener(this);
        Send.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                Toast.makeText(ContactUs.this, "Going back!", Toast.LENGTH_SHORT).show();
                Intent Back = new Intent(this, MainActivity.class);
                startActivity(Back);
                break;
            case R.id.btnSend:
                Toast.makeText(ContactUs.this, "Sending!", Toast.LENGTH_SHORT).show();
                Intent Send = new Intent(this, MainActivity.class);
                startActivity(Send);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}