package com.example.looters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    static GoogleSignInAccount account ;



static int i;
    static String key;
    static String b = null;
    static String key1 = null;
    static Order order;
    static List<MyData> itemdata;
    static Context context;
    private final static int TYPE_HEADER = 0;
    private final static int TYPE_ITEM=1;

    final HashMap<String, Object> cart = new HashMap<>();


    public MenuAdapter(List<MyData> itemdata,Context context) {
        this.itemdata = itemdata;
        this.context=context;
    }
    private class VIEW_TYPE{
        public static final int Normal = 2;
        public static final int Footer = 3;
    }
    boolean isFooter;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MyData myData = itemdata.get(position);
        Random rnd = new Random();
//        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//        holder.relativeLayout.setBackgroundColor(currentColor);
        holder.itemname.setText(myData.getName());
        holder.itemprice.setText(myData.getPrice());
        holder.enabled.setText(myData.getEnabled());
        holder.section.setText(myData.getSection());
        holder.quantity.setText(myData.getQuantity());

    }



    @Override
    public int getItemCount() {
        return itemdata.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        TextView itemname, itemprice, quantity,enabled,section;
        Button addB, subB;
        RecyclerView recyclerView;
        RelativeLayout relativeLayout;
        ConstraintLayout constraintLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //recyclerView = (RecyclerView)itemView.findViewById(R.id.recycler_view);
            order = new Order();
            constraintLayout =(ConstraintLayout) itemView.findViewById(R.id.parent);
            enabled = (TextView)itemView.findViewById(R.id.enabled);
            itemname = (TextView) itemView.findViewById(R.id.itemtext);
            itemprice = (TextView) itemView.findViewById(R.id.itemprice);
            addB = (Button) itemView.findViewById(R.id.addB);
            subB = (Button) itemView.findViewById(R.id.subB);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            account  = GoogleSignIn.getLastSignedInAccount(context);
            section = (TextView)itemView.findViewById(R.id.section);
            char[] acc = account.getEmail().toString().trim().toCharArray();
            Random random = new Random();
            String id = String.format("%04d", random.nextInt(10000));

           if (account.getEmail().toString().contains("@"))
           {
               int c = account.getEmail().toString().indexOf("@");
               b = account.getEmail().toString().substring(0,c);
           }
            DatabaseReference cartref = FirebaseDatabase.getInstance().getReference().child("items").child(b);
            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("items").child(b);


            addB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i = Integer.parseInt(quantity.getText().toString());
                    i++;
                    quantity.setText(Integer.toString(i));

                    if (i==1)
                    {
                        key = cartref.push().getKey();
                        order.setAccount(account.getEmail().toString().substring(0,9));
                        order.setName(itemname.getText().toString());
                        order.setPrice(itemprice.getText().toString().substring(2));
                        order.setQ(quantity.getText().toString());
                        order.setSection(section.getText().toString());
                       // order.setOtp(id);
                        cartref.child(key).setValue(order);
                    }
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("items").child(b);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                            {
                                if (dataSnapshot1.child("name").getValue().toString().equals(itemname.getText().toString()))
                                {
                                    key1 = dataSnapshot1.getKey();
                                   // cartref.child(key1).removeValue();
                                   // key = cartref.push().getKey();
                                    order.setAccount(account.getEmail().toString().substring(0,9));
                                    order.setName(itemname.getText().toString());
                                    order.setPrice(itemprice.getText().toString());
                                    order.setQ(quantity.getText().toString());
                                    order.setSection(section.getText().toString());
                                   // order.setPending(true);
                                    Map<String,Object> map = new HashMap<>();
                                    map.put("q",quantity.getText().toString());
                                    cartref.child(key).updateChildren(map);

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });

            subB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i = Integer.parseInt(quantity.getText().toString());
                    i--;

                    if(i<0)
                    {
                        i=0;
                    }
                    quantity.setText(Integer.toString(i));

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("items").child(b);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                            {

                                   // Log.d("fuckyou", String.valueOf(dataSnapshot1.getChildren()));
                                if (dataSnapshot1.child("name").getValue().toString().equals(itemname.getText().toString())){
                                        key = dataSnapshot1.getKey();
                                      //  cartref.child(key).removeValue();
                                       // key = cartref.push().getKey();
                                        order.setAccount(account.getDisplayName().toString());
                                        order.setName(itemname.getText().toString());
                                        order.setPrice(itemprice.getText().toString().substring(1,2));
                                        order.setQ(quantity.getText().toString());
                                        Map<String,Object> map = new HashMap<>();
                                        map.put("q",quantity.getText().toString());
                                        if (Integer.parseInt(quantity.getText().toString()) != 0) {
                                            cartref.child(key).updateChildren(map);
                                        }
                                        if (Integer.parseInt(quantity.getText().toString()) == 0)
                                        {
                                            cartref.child(key).removeValue();
                                        }
                                    }
//                                else {
//                                    if (Integer.parseInt(quantity.getText().toString()) == 0)
//                                    {
//                                        key=dataSnapshot1.getKey();
//                                        cartref.child(key).removeValue();
//                                    }
//                                }

                                }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });







        }


    }
    public void updateq(MyViewHolder holder ,int pos,int q)
    {
        MyData myData = itemdata.get(pos);
        holder.quantity.setText(q);
    }

}
