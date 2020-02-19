package com.example.looters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminUserOrdersAdapter extends RecyclerView.Adapter<AdminUserOrdersAdapter.MyViewHolder> {
    List<CartData> itemdata;
    Context context;

    public AdminUserOrdersAdapter(List<CartData> itemdata, Context context) {
        this.itemdata = itemdata;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_view_for_admin, parent, false);
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
        public TextView itemname, itemprice, quantity;
        Button done;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemname = (TextView)itemView.findViewById(R.id.itemtextcartadmin);
            itemprice = (TextView)itemView.findViewById(R.id.itempricecartadmin);
            quantity = (TextView)itemView.findViewById(R.id.quancartadmin);

            TextView otp = (TextView)itemView.findViewById(R.id.adminusers);

        }
    }
}

