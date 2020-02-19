package com.example.looters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class PendingFrag extends Fragment{
List<PendingData> itemdata = new ArrayList<>();
TextView textView;
CustLoad custLoad;
RecyclerView.Adapter adapter;
TextView pendingvis;
public PendingFrag(){}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView)view.findViewById(R.id.no_pending_orders);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        custLoad = new CustLoad(getActivity());
        View view = inflater.inflate(R.layout.fragment_pending,container,false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_pending);
        TextView textView = (TextView)view.findViewById(R.id.no_pending_orders);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        int a = account.getEmail().toString().indexOf("@");
        String b = account.getEmail().toString().substring(0,a);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Looters").child("Gaurav");

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    custLoad.showloader();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot1 : data.getChildren())
                            for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren())
                            if (dataSnapshot2.child("account").getValue().equals(b))
                            {
                        PendingData pendingData = new PendingData(dataSnapshot2.child("name").getValue().toString(),dataSnapshot2.child("q").getValue().toString());
                        itemdata.add(pendingData);
                    }}
                    if (itemdata.isEmpty())
                    {
                        textView.setText("No Pending Orders");
                    }
                    adapter = new PendingAdapter(itemdata, getContext());
                    recyclerView.setAdapter(adapter);
                    custLoad.hideloader();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




        return view;
    }


}

