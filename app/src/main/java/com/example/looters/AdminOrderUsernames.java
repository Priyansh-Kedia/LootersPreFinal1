package com.example.looters;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdminOrderUsernames extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;
    private List<CartData> itemdata = new ArrayList<>();
    List<AdminUsername> listdata;
    Random random = new Random();
    String id = String.format("%04d", random.nextInt(10000));



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cart);
        TextView textView = (TextView) findViewById(R.id.nodata);
        //  textView.setVisibility(View.INVISIBLE);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_viewcart_admin);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        // adapter = new AdminUsernamesAdapter();
        listdata = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_viewcart_admin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final String[] name = new String[1];
        final String[] otp = new String[1];
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Looters").child("Gaurav");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Toast.makeText(getApplicationContext(), , Toast.LENGTH_LONG).show();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren())
                            name[0] = dataSnapshot3.child("account").getValue().toString();

                            //  otp[0] = dataSnapshot.getValue().toString().substring(1,5);
//                            AdminUsername adminUsername = new AdminUsername(dataSnapshot2.child("account").getValue().toString(),dataSnapshot.getValue().toString().substring(1,5));
//                            listdata.add(adminUsername);

                        }
                        AdminUsername adminUsername = new AdminUsername(name[0], dataSnapshot1.getValue().toString().substring(1, 5));
                        listdata.add(adminUsername);

                    }


                adapter = new AdminUsernamesAdapter(listdata, getApplicationContext());
                recyclerView.setAdapter(adapter);
                TextView textView = (TextView) findViewById(R.id.nodata);
                textView.setVisibility(View.VISIBLE);
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        if (databaseReference.child("Gaurav") != null) {
//            textView.setText("");
//


        }
    }

