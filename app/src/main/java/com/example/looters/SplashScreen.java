package com.example.looters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import javax.xml.transform.Templates;

public class SplashScreen extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        constraintLayout = (ConstraintLayout)findViewById(R.id.splashscreen);
        textView = (TextView)findViewById(R.id.textsplash);
        textView.animate().alpha(1f).setDuration(3000);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (account!=null)
                {
//            fragmentTransaction = fragmentManager1.beginTransaction();
//            fragmentTransaction.add(R.id.menuadd,menuFrag);
//            fragmentTransaction.commit();
                    startActivity(new Intent(SplashScreen.this,MenuActivity.class));
                }
                else {
                    startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                }
            }
        },2000);
    }
}