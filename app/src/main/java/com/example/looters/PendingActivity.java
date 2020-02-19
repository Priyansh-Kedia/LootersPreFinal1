package com.example.looters;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PendingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;
    private List<PenOrderList> itemdata;

    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_for_pending);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        itemdata = new ArrayList<>();
       // getdata();
    }



//    public void getdata()
//    {
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(PendingActivity.this);
//        int a = account.getEmail().toString().indexOf("@");
//        String b = account.getEmail().toString().substring(0,a);
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("items").child(b);
//
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
//                {
//                    CartData cartData = new CartData(dataSnapshot1.child("name").getValue().toString(),dataSnapshot1.child("price").getValue().toString(),dataSnapshot1.child("q").getValue().toString());
//                    itemdata.add(cartData);
//                }
//                adapter = new CartAdapter(itemdata, getApplicationContext());
//                recyclerView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
}
