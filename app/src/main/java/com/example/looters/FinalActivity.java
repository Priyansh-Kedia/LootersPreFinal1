package com.example.looters;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class FinalActivity extends AppCompatActivity {
    String OTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        int a = account.getEmail().toString().indexOf("@");
        if (getIntent().hasExtra("OTP"))
        {
            OTP = getIntent().getStringExtra("OTP");
        }
        String b = account.getEmail().toString().substring(0,a);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("items").child(b);

       // databaseReference.child("OTP").setValue(OTP);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Gaurav");
//                DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("items").child(b);
//                databaseReference2.child("OTP").setValue(OTP);

                String key = databaseReference1.child("gaurav").child(b).getKey();
               databaseReference1.child(key).setValue(dataSnapshot.getValue());
               databaseReference1.child(key).child("OTP").setValue(OTP);
               databaseReference.removeValue();
              // databaseReference1.push();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//
//                        Gaurav gaurav = new Gaurav(dataSnapshot1.child("name").getValue().toString(), dataSnapshot1.child("price").getValue().toString(), dataSnapshot1.child("q").getValue().toString(), OTP);
//                        databaseReference1.child("gaurav").child(OTP).setValue(gaurav);
//
//                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        DatabaseReference gauravcart = FirebaseDatabase.getInstance().getReference();
//        gauravcart.setValue(databaseReference);
        Random random = new Random();

        new AlertDialog.Builder(this)
                .setTitle("Order is Complete")
                .setMessage("Share this OTP when you take the order \n" + OTP )
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();


    }
}
