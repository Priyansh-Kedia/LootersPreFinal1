package com.example.looters;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;


public class MenuActivity extends FragmentActivity {
    boolean doubleBackToExitPressedOnce = false;
    CartFragment cartFragment = new CartFragment();
    MenuFrag menuFrag = new MenuFrag();
    FragmentManager fragmentManager1 = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager1.beginTransaction();
    String paidwithcard;
    private static long backpressed;
    Fragment fragment;
    private RecyclerView.Adapter adapter;
    Random random = new Random();
    String id = String.format("%04d", random.nextInt(10000));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
//        Toast.makeText(this,"MNNNN",Toast.LENGTH_SHORT).show();
        fragmentTransaction = fragmentManager1.beginTransaction();
        fragmentTransaction.add(R.id.menuadd,menuFrag);
        fragmentTransaction.commit();
        if (getIntent().hasExtra("Card"))
        {
            paidwithcard = getIntent().getStringExtra("Card");
        }
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        ProfileFrag profileFrag = new ProfileFrag();

        navigation.setItemIconTintList(ColorStateList.valueOf(Color.parseColor("#FF8330")));
          navigation.setOnNavigationItemSelectedListener(menuItem -> {

            int id = menuItem.getItemId();
            switch(id)
            {
                case R.id.cart:
                    replacefragment(new CartFragment());
                    return true;

                case R.id.menu:
                    replacefragment(new MenuFrag());
                    return true;
                case R.id.pending:
                    replacefragment(new PendingFrag());

                    return true;

                case R.id.profile:
                    replacefragment(new ProfileFrag());
                    return true;

            }
            return false;
        });
 //  onBackPressed();


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

    private void replacefragment(Fragment fragment) {

      FragmentManager fragmentManager = getSupportFragmentManager();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      if (fragment instanceof CartFragment)
      {
          Bundle bundle = new Bundle();
          bundle.putString("otp",id);
          fragment.setArguments(bundle);
      }
      fragmentTransaction.replace(R.id.menuadd,fragment);
      int t = 2000;
      fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
      fragmentTransaction.commit();
    }

}