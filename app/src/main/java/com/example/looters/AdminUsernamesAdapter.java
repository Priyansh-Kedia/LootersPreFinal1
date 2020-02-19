package com.example.looters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdminUsernamesAdapter extends RecyclerView.Adapter<AdminUsernamesAdapter.MyViewHolder> {
    List<AdminUsername> itemdata;
    Context context;


    public AdminUsernamesAdapter(List<AdminUsername> itemdata,Context context) {
        this.itemdata = itemdata;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.cart_view_for_admin_orders, parent, false);
      MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AdminUsername adminUsername = itemdata.get(position);
        holder.name.setText(adminUsername.getName());
        holder.theotp.setText(adminUsername.getOtp());

    }

    @Override
    public int getItemCount() {
        return itemdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView empty;
        RecyclerView recyclerView;
        Button done;
        TextView theotp;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView)itemView.findViewById(R.id.adminusers);
            done = (Button)itemView.findViewById(R.id.done);
            empty = (TextView)itemView.findViewById(R.id.nodata);
            theotp = (TextView)itemView.findViewById(R.id.theotp);
            recyclerView = (RecyclerView)itemView.findViewById(R.id.recycler_viewcart_admin);

//            if (itemdata.isEmpty())
//            {
//                recyclerView.setVisibility(View.INVISIBLE);
//                empty.setVisibility(View.VISIBLE);
//            }

            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Looters").child("Gaurav").child(theotp.getText().toString());
                    databaseReference.removeValue();
                    itemdata.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(),itemdata.size());
                }
            });




        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,AdminSections.class);
            intent.putExtra("acc",name.getText().toString());
            intent.putExtra("otp",theotp.getText().toString());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getContext().startActivity(intent);
        }
    }
}
