package com.example.looters;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SecondActivity extends AppCompatActivity {

    private int i=1;
    private DrawerLayout drawerLayout;
    DatabaseReference databaseReference;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    //MenuItem menuItem;
    //Menu menu;
    GoogleSignInClient mGoogleSignInClient;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        boolean firstStart = sharedPreferences.getBoolean("firstStart",true);
        if(firstStart) {
            showStartDialog();
        }




















        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Member");






        //menu=(Menu)findViewById(R.menu.item_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_nav);
       // menuItem= menu.findItem(R.id.hello);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String name = account.getAccount().name;

        navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setTitle("Hello " + account.getDisplayName() + "!");
        navigationView.getMenu().getItem(0).setCheckable(false);
        
    }

    private void showStartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("App instructions")
                .setMessage("Hey there")
                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
        new AlertDialog.Builder(this)
                .setTitle("App instructions")
                .setMessage("Hey there")
                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();
    }

    private void signOut() {
        Log.d("PK123","entered");
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        startActivity(new Intent(SecondActivity.this, LoginActivity.class));
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
