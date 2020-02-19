package com.example.looters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AdminLogin extends AppCompatActivity {
    EditText admin,pass;
    Button log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        admin = (EditText)findViewById(R.id.admin);
        pass = (EditText)findViewById(R.id.pass);
        log=(Button)findViewById(R.id.loginadmin);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (admin.getText().toString().equals("a") && pass.getText().toString().equals("a"))
                {
                    startActivity(new Intent(AdminLogin.this, AdminFirstPage.class));
                }
            }
        });

    }
}
