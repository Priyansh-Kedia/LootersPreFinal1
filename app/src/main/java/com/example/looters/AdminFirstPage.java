package com.example.looters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminFirstPage extends AppCompatActivity {
    Button orders, change;
    private static long backpressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_one);

        orders = (Button)findViewById(R.id.orders);
        change = (Button)findViewById(R.id.change);

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminFirstPage.this, AdminOrderUsernames.class));
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminFirstPage.this, AdminChangeMenu.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (backpressed + 2000 >= System.currentTimeMillis()){
            super.onBackPressed();
            finishAffinity();
        }
        else
            //long start = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"Press again to exit",Toast.LENGTH_SHORT).show();
        //long stop = System.currentTimeMillis();
        backpressed = System.currentTimeMillis();

    }
}
