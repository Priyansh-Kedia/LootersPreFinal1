package com.example.looters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private static final int GOOGLE_SIGNIN_CODE = 1000;
    Button signInButton;
    ProgressDialog progressDialog;
    int RC_SIGN_IN=108;

    CustLoad custLoad;
    GoogleSignInClient mGoogleSignInClient;
    Handler handler = new Handler();
    Runnable refresh = null;
    String done;
    public static final String PREFS_NAME = "LoginPrefs";
    MenuFrag menuFrag = new MenuFrag();
    FragmentManager fragmentManager1 = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager1.beginTransaction();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        custLoad = new CustLoad(LoginActivity.this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        if (getIntent().hasExtra("Done"))
        {
            done = getIntent().getStringExtra("Done");
        }
        signInButton = (Button) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        signInButton.setOnLongClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, AdminLogin.class));
            return true;
        });












    }
    private void signIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        mGoogleSignInClient.signOut();
        custLoad.showloader();
        startActivityForResult(signInIntent, RC_SIGN_IN);

        }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      //  custLoad.hideloader();

        super.onActivityResult(requestCode, resultCode, data);


        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);


        if (requestCode == RC_SIGN_IN) {
                // The Task returned from this call is always completed, no need to attach
                // a listener.


            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    handleSignInResult(task);



            }


    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {




        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
           if (account.getEmail().toString().contains("@pilani.bits-pilani.ac.in")) {
//               fragmentTransaction = fragmentManager1.beginTransaction();
//               fragmentTransaction.add(R.id.menuadd,menuFrag);
//               fragmentTransaction.commit();
               DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("menu").child("items");
               DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
              int  c = account.getEmail().toString().indexOf("@");
              String b = account.getEmail().toString().substring(0, c);
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
               startActivity(new Intent(LoginActivity.this,MenuActivity.class));
               SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
               SharedPreferences.Editor editor = sharedPreferences.edit();
               editor.putString("logged", "yes");
               editor.commit();
           }
           else {
               Toast.makeText(LoginActivity.this, "Use Bits Mail", Toast.LENGTH_LONG).show();
               // Signed in successfully, show authenticated UI.
                custLoad.hideloader();
           }

                //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            Toast.makeText(LoginActivity.this,"Invalid Login",Toast.LENGTH_LONG).show();
            custLoad.hideloader();


            // Please refer to the GoogleSignInStatusCodes class reference for more information.
          //  Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
          //  updateUI(null);
        }


    }
    @Override
    public void onBackPressed() {
        if (done.equals("Done")){
            super.onBackPressed();
            finishAffinity();
        }

    }


}