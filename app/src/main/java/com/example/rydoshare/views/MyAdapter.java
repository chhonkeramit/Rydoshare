package com.example.rydoshare.views;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rydoshare.R;
import com.example.rydoshare.views.models.Provider;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myviewholder> {

    ArrayList<Provider> datalist;
    ItemClickListener mItemListener;

    public MyAdapter(ArrayList<Provider> datalist, ItemClickListener itemClickListener) {
        this.datalist = datalist;
        this.mItemListener = itemClickListener;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_items, parent, false);
        return new myviewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.t1.setText(datalist.get(position).getName());
        String price = String.valueOf(datalist.get(position).getPrice());
        holder.t2.setText(price);

        holder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(datalist.get(position)); // this will get position of our item in recyclerview
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }


    public interface ItemClickListener {
        void onItemClick(Provider provider);
    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView t1, t2;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
        }
    }

}


