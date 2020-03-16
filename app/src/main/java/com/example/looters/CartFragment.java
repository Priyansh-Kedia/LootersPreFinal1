package com.example.looters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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


public class CartFragment extends Fragment  {

    private List<CartData> itemdata = new ArrayList<>();
    int total = 0;
    CustLoad custLoad;
    TextView tot;
    private RecyclerView.Adapter adapter;
    TextView nocartitems;
    Button paybtn;


    public CartFragment() {

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        custLoad = new CustLoad(getActivity());
        View view = inflater.inflate(R.layout.fragment_cart,container,false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_viewcart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        int a = account.getEmail().toString().indexOf("@");
        String b = account.getEmail().toString().substring(0,a);
        String otp = getArguments().getString("otp");
        nocartitems = (TextView)view.findViewById(R.id.nocartitems);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(b);
        custLoad.showloader();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren())
                {
                    if (!(data.child("q").getValue().toString().equals("0"))) {
                    CartData cartData = new CartData(data.child("name").getValue().toString(), "₹ " + data.child("price").getValue().toString(), data.child("q").getValue().toString());
                    itemdata.add(cartData);
                    total = total + Integer.parseInt(data.child("price").getValue().toString()) * Integer.parseInt(data.child("q").getValue().toString());
                }
                    }
                tot = (TextView)view.findViewById(R.id.total);
                tot.setText(Integer.toString(total));
                if (itemdata.isEmpty()) {
                    nocartitems.setVisibility(View.VISIBLE);
                }
                adapter = new CartAdapter(itemdata,getActivity());
                recyclerView.setAdapter(adapter);
                custLoad.hideloader();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button button = (Button)view.findViewById(R.id.paybtn);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (tot.getText().toString().equals("0"))
                {
                    Toast.makeText(getContext(),"Please add items to the cart",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getContext(), PayActivity.class);
                    intent.putExtra("Price", tot.getText().toString());
                    intent.putExtra("otp", otp);
                    startActivity(intent);

                }
            }
        });
        return view;
    }


}
