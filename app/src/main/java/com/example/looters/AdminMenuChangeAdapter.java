package com.example.looters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminMenuChangeAdapter extends RecyclerView.Adapter<AdminMenuChangeAdapter.ViewHolder> {
    List<CartChangeData> itemdata;
    Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view_for_menu_change, parent, false);
        //AdminUserOrdersAdapter.MyViewHolder myViewHolder = new AdminUserOrdersAdapter.MyViewHolder(view);
        AdminMenuChangeAdapter.ViewHolder viewHolder = new AdminMenuChangeAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartChangeData cartChangeData = itemdata.get(position);
        holder.name.setText(cartChangeData.getName());
        holder.price.setText(cartChangeData.getPrice());
        holder.enable.setText(cartChangeData.getOnoff());
        if (cartChangeData.getOnoff().equals("true"))
        {
            holder.onoffbtn.setChecked(true);
        }




    }

    public AdminMenuChangeAdapter(List<CartChangeData> itemdata, Context context) {
        this.itemdata = itemdata;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return itemdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,price,enable;
        ToggleButton onoffbtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            enable = (TextView)itemView.findViewById(R.id.enabled);
            name = (TextView)itemView.findViewById(R.id.itemtextcartadminchange);
            price = (TextView)itemView.findViewById(R.id.itempricecartadminchange);
            onoffbtn = (ToggleButton)itemView.findViewById(R.id.onoffbtn);

            onoffbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onoffbtn.isChecked()) {
                        enable.setText("true");
                    }
                    if (!onoffbtn.isChecked())
                    {
                        enable.setText("false");
                    }
                    if (enable.getText().toString().equals("false"))
                    {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("menu").child("items");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                                {
                                    Map<String,Object> map = new HashMap<>();
                                    map.put("enabled","false");
                                    if (dataSnapshot1.child("name").getValue().toString().equals(name.getText().toString()))
                                    {
                                        String key = dataSnapshot1.getKey();
                                        databaseReference.child(key).child("enabled").removeValue();
                                        databaseReference.child(key).child("enabled").setValue("false");
                                        enable.setText("false");
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    if (enable.getText().toString().equals("true"))
                    {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("menu").child("items");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                                {
                                    Map<String,Object> map = new HashMap<>();
                                    map.put("enabled","true");
                                    if (dataSnapshot1.child("name").getValue().toString().equals(name.getText().toString()))
                                    {
                                        String key = dataSnapshot1.getKey();
                                        databaseReference.child(key).child("enabled").removeValue();
                                        databaseReference.child(key).child("enabled").setValue("true");
                                        enable.setText("true");
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }
            });

        }
    }
}
