package com.example.looters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.List;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.MyViewHolder> {

    GoogleSignInAccount account;

    String key1 = null;
    Order order;
    List<PendingData> itemdata;
    Context context;



    public PendingAdapter(List<PendingData> itemdata, Context context) {
        this.itemdata = itemdata;
        this.context = context;
    }

    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view_for_pending, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;






    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PendingData pendingData = itemdata.get(position);
        holder.itemname.setText(pendingData.getName());
     //   holder.itemprice.setText(pendingData.getPrice());
        holder.quantity.setText(pendingData.getQuantity());



    }

    @Override
    public int getItemCount() {
        return itemdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemname, itemprice, quantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemname = (TextView)itemView.findViewById(R.id.itemtextpend);
          //  itemprice = (TextView)itemView.findViewById(R.id.itempricepend);
            quantity = (TextView)itemView.findViewById(R.id.pendingq);

        }
    }

}