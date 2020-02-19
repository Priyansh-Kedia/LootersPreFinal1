package com.example.looters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    ArrayList<String> arrayList;
    TextView textView;
    String itemname;
    GoogleSignInAccount account;
    int total = 0;
    TextView tot;
    private List<CartData> itemdata = new ArrayList<>();
    private RecyclerView recyclerView;
    Button paybtn;
    private RecyclerView.Adapter adapter;

    DatabaseReference databaseReference ;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        paybtn = (Button)findViewById(R.id.paybtn);

        account= GoogleSignIn.getLastSignedInAccount(this);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_viewcart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        int a = account.getEmail().toString().indexOf("@");
        String b = account.getEmail().toString().substring(0,a);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("items").child(b);
       // Toast.makeText(this,"heyrgefy",Toast.LENGTH_LONG).show();
        // getvalues();
       // CartData cartitem = new CartData("dfsgdf","dfgdsf","fdgfsd");


      //  itemdata.add(cartitem);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren())
                {
                    CartData cartData = new CartData(data.child("name").getValue().toString(),data.child("price").getValue().toString(),data.child("q").getValue().toString());
//                    Log.d("TAAAAG",cartData.getName().toString().trim());
//                    Log.d("TAAAAG",cartData.getPrice().toString().trim());
                    itemdata.add(cartData);


                    total = total + Integer.parseInt(data.child("price").getValue().toString())*Integer.parseInt(data.child("q").getValue().toString());


                }
                tot = (TextView)findViewById(R.id.total);
                tot.setText(Integer.toString(total));
                adapter = new CartAdapter(itemdata,CartActivity.this);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this,PayActivity.class);
                intent.putExtra("Price",tot.getText().toString());
                startActivity(intent);
            }
        });


  //      Log.d("TAAAAG",itemdata.toString());




    }

    public void getvalues() {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren())
                {
                    CartData cartData = new CartData(data.child("name").getValue().toString(),data.child("price").getValue().toString(),data.child("q").getValue().toString());
//                    Log.d("TAAAAG",cartData.getName().toString().trim());
//                    Log.d("TAAAAG",cartData.getPrice().toString().trim());
//                    Log.d("TAAAAG",cartData.getQuantity().toString().trim());
                      itemdata.add(cartData);
                //    Log.d("TAAAAG",itemdata.toString());
                   // return itemdata;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
      //  Log.d("TAAAAG",itemdata.toString());
       // return itemdata;
    }


}
