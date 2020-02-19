package com.example.looters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminSections extends AppCompatActivity {
    Button chinese,sandwich;
    String acc,otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sections);
        chinese = (Button)findViewById(R.id.chinese);
        sandwich = (Button)findViewById(R.id.sandwich);
        if (getIntent().hasExtra("acc"))
        {
            acc= getIntent().getStringExtra("acc");
            otp = getIntent().getStringExtra("otp");
        }
        chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminSections.this,AdminUserOrders.class);
                intent.putExtra("acc",acc);
                intent.putExtra("otp",otp);
                intent.putExtra("section","1");
                startActivity(intent);
            }
        });
        sandwich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminSections.this,AdminUserOrders.class);
                intent.putExtra("acc",acc);
                intent.putExtra("otp",otp);
                intent.putExtra("section","2");
                startActivity(intent);
            }
        });
    }
}
