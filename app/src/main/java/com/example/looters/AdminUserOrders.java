package com.example.looters;

import android.os.Bundle;
import android.util.Log;

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

public class AdminUserOrders extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    String acc,otp,section;
    private RecyclerView.Adapter adapter;
    private List<CartData> itemdata = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_orders);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_expand);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (getIntent().hasExtra("acc"))
        {
            acc= getIntent().getStringExtra("acc");
            otp = getIntent().getStringExtra("otp");
            section = getIntent().getStringExtra("section");
        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Looters").child("Gaurav");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.child(otp).getChildren())
                    {
                        if (dataSnapshot2.child("section").getValue().toString().equals(section)) {
                            CartData cartData = new CartData(dataSnapshot2.child("name").getValue().toString(), dataSnapshot2.child("price").getValue().toString(), dataSnapshot2.child("q").getValue().toString());
                            itemdata.add(cartData);
                            Log.d("Admingg", acc);
                        }

                }
                    adapter = new AdminUserOrdersAdapter(itemdata, AdminUserOrders.this);
                    recyclerView.setAdapter(adapter);
}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    }



