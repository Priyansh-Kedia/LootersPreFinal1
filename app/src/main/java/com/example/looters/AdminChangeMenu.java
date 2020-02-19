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

public class AdminChangeMenu extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;
    private List<CartChangeData> itemdata = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_menu);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_viewcart_admin_change);
        linearLayoutManager = new LinearLayoutManager(this);

        getdata();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    public void getdata()
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("menu").child("items");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Log.d("fucku", dataSnapshot1.child("price").getValue().toString());

                    CartChangeData cartChangeData = new CartChangeData(dataSnapshot1.child("name").getValue().toString(),dataSnapshot1.child("price").getValue().toString(),dataSnapshot1.child("enabled").getValue().toString());

                    itemdata.add(cartChangeData);
//                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
//                        for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
////                            Log.d("fucku", dataSnapshot3.child("name").getValue().toString());
//                        }
//                    }
                }
                adapter = new AdminMenuChangeAdapter(itemdata, AdminChangeMenu.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
