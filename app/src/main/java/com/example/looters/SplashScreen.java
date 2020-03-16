package com.example.looters;

import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Explode;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.xml.transform.Templates;

public class SplashScreen extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    TextView textView;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
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
                    int c = account.getEmail().toString().indexOf("@");
                   String b = account.getEmail().toString().substring(0,c);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("menu").child("items");
                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int i =0;
                            for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                            {
                                databaseReference.child(b).child(String.valueOf(i)).setValue(dataSnapshot1.getValue());
                                i++;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Intent intent = new Intent(SplashScreen.this,MenuActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fadein,R.anim.fadeout);


                }
                else {
                    startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                }
            }
        },2000);
    }
}
