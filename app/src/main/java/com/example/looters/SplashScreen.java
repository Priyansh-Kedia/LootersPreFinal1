package com.example.looters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    TextView textView;
    CardView cardView;
    Boolean doit=false;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        constraintLayout = (ConstraintLayout)findViewById(R.id.splashscreen);
//        textView = (TextView)findViewById(R.id.textsplash);
//        cardView = (CardView)findViewById(R.id.cardsplash);
        constraintLayout = (ConstraintLayout)findViewById(R.id.splashscreen);

        constraintLayout.animate().alpha(1f).setDuration(3000);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        int c = account.getEmail().toString().indexOf("@");
        String b = account.getEmail().toString().substring(0,c);
        if (account!=null && databaseReference.child(b).getKey().toString().equals(b))
        {
            doit = true;
        }

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("menu").child("items");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (account!=null) {
                    if (doit) {
                        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int i = 0;
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    databaseReference.child(b).child(String.valueOf(i)).setValue(dataSnapshot1.getValue());
                                    i++;
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        Intent intent = new Intent(SplashScreen.this, MenuActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }
                    else {
                        Intent intent = new Intent(SplashScreen.this, MenuActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }

                }
                else {
                    startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                    overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                }
            }
        },2000);
    }
}
