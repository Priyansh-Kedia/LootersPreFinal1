package com.example.looters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
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
import java.util.Random;


public class MenuFrag extends Fragment {

    RecyclerView recyclerView;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> quantities = new ArrayList<>();
    RelativeLayout relativeLayout;
    private RecyclerView.Adapter adapter;
    MyData myData;
    NestedScrollView nestedScrollView;
    private List<MyData> data;
    private List<MyData> itemdata ;
    ProgressBar progressBar;
    DatabaseReference databaseReference,databaseReference1;
    CustLoad loader;
  //  CustLoad1 load1;
    Random random = new Random();
    String id = String.format("%04d", random.nextInt(10000));


    public MenuFrag() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_menu,container,false);

        loader = new CustLoad(getActivity());
//        loader.hideloader();
        Toast.makeText(getContext(),"This is menufrag",Toast.LENGTH_LONG).show();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());

       Network network = new Network(getContext());

      //  load1 = new CustLoad1(getActivity());
        int a = account.getEmail().toString().indexOf("@");
        String b = account.getEmail().toString().substring(0,a);
        relativeLayout = (RelativeLayout)view.findViewById(R.id.parent);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("menu").child("items");
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("items").child(b);
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    names.add(dataSnapshot1.child("name").getValue().toString());
                    quantities.add(dataSnapshot1.child("q").getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        itemdata = new ArrayList<>();
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        entries();

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getActivity());
      //  linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        return view;
    }

    private void entries() {
          loader.showloader();
          databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (names.size()!=0) {
                            for (int i = 0; i < names.size(); i++) {

                                if (names.get(i).equals(snapshot.child("name").getValue().toString())) {
                                    MyData myData = new MyData(snapshot.child("name").getValue().toString(), "₹ " + snapshot.child("price").getValue().toString(), snapshot.child("enabled").getValue().toString(), snapshot.child("section").getValue().toString(), quantities.get(i));
                                    itemdata.add(myData);
                                    Toast.makeText(getContext(), Integer.toString(i), Toast.LENGTH_SHORT).show();
                                } else {
                                    itemdata.add(new MyData(snapshot.child("name").getValue().toString(), "₹ " + snapshot.child("price").getValue().toString(), snapshot.child("enabled").getValue().toString(), snapshot.child("section").getValue().toString(), "0"));
                                }
                            }
                        }
                        else {
                            itemdata.add(new MyData(snapshot.child("name").getValue().toString(), "₹ " +snapshot.child("price").getValue().toString(),snapshot.child("enabled").getValue().toString(),snapshot.child("section").getValue().toString(),"0"));
                        }

                    }


                    adapter = new MenuAdapter(itemdata, getContext());
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                    loader.hideloader();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }



}
