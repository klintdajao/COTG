package com.example.gittest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AboutUs extends AppCompatActivity implements  View.OnClickListener{
    Button Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        getSupportActionBar().hide();

        Back = (Button) findViewById(R.id.backButton);

        Back.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backButton:
                Toast.makeText(AboutUs.this, "Going back!", Toast.LENGTH_SHORT).show();
                Intent Back = new Intent(this, MainActivity.class);
                startActivity(Back);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}