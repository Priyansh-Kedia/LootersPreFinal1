package com.example.looters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    DatabaseReference cartref = FirebaseDatabase.getInstance().getReference().child("items");
   static GoogleSignInAccount account;
    static Order order;

    String key1 = null;
    //Order order;
    List<CartData> itemdata;
    Context context;
    static int i;
    static String key;
    private static final int FOOTER_VIEW = 1;

    public CartAdapter(List<CartData> itemdata, Context context) {
        this.itemdata = itemdata;
        this.context = context;
    }



    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_view_for_cart, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CartData cartData = itemdata.get(position);
        holder.itemname.setText(cartData.getName());
        holder.itemprice.setText(cartData.getPrice());
        holder.quantity.setText(cartData.getQuantity());



    }

    @Override
    public int getItemCount() {
        return itemdata.size();
    }





    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemname, itemprice, quantity;
        String b;
        Button addb,subb;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            order = new Order();
            itemname = (TextView)itemView.findViewById(R.id.itemtextcart);
            itemprice = (TextView)itemView.findViewById(R.id.itempricecart);
            quantity = (TextView)itemView.findViewById(R.id.quancart);
            account  = GoogleSignIn.getLastSignedInAccount(context);
            addb = (Button)itemView.findViewById(R.id.addBcart);
            subb = (Button)itemView.findViewById(R.id.subBcart);
            if (account.getEmail().toString().contains("@"))
            {
                int c = account.getEmail().toString().indexOf("@");
                b = account.getEmail().toString().substring(0,c);
            }
            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("items").child(b);
            addb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i = Integer.parseInt(quantity.getText().toString());
                    i++;
                    quantity.setText(Integer.toString(i));


                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                            {
                                if (i==1)
                                {
                                    key = dataSnapshot1.getKey();
                                    order.setAccount(account.getEmail().toString().substring(0,9));
                                    order.setName(itemname.getText().toString());
                                    order.setPrice(itemprice.getText().toString().substring(2));
                                    order.setQ(quantity.getText().toString());
                                    order.setSection(dataSnapshot1.child("section").getValue().toString());
                                    // order.setOtp(id);
                                    cartref.child(key).setValue(order);
                                }
                                key1 = dataSnapshot1.getKey();
                                // cartref.child(key1).removeValue();
                                // key = cartref.push().getKey();
                                order.setAccount(b);
                                order.setName(itemname.getText().toString());
                                order.setPrice(itemprice.getText().toString());
                                order.setQ(quantity.getText().toString());
                                order.setSection(dataSnapshot1.child("section").getValue().toString());
                                // order.setPending(true);
                                Map<String,Object> map = new HashMap<>();
                                map.put("q",quantity.getText().toString());
                                cartref.child(b).child(key1).updateChildren(map);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });

            subb.setOnClickListener(new View.OnClickListener() {
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
                                        cartref.child(b).child(key).updateChildren(map);
                                    }
                                    if (Integer.parseInt(quantity.getText().toString()) == 0)
                                    {
                                        cartref.child(b).child(key).removeValue();
                                        itemdata.remove(getAdapterPosition());
                                        notifyItemRemoved(getAdapterPosition());

                                        notifyItemRangeChanged(getAdapterPosition(),itemdata.size());
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

}