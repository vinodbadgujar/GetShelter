package com.example.hello.getshelter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

 public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

     ArrayList<Product> list;
     public ProductAdapter(ArrayList<Product> list){
         this.list=list;
     }

     @NonNull
     @Override
     public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         View view =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_view,viewGroup,false);
         return new MyViewHolder(view);
     }

     @Override
     public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
         holder.city.setText("City: "+list.get(i).getCity());
         holder.cost.setText("Cost/Day: INR "+list.get(i).getCost());
         holder.capacity.setText("Max Capacity: "+list.get(i).getMaximum_peoples());
         holder.number.setText("Mobile Number: "+list.get(i).getMobile());
     }

     @Override
     public int getItemCount() {
         return list.size();
     }

     class MyViewHolder extends RecyclerView.ViewHolder{
         TextView city,cost,capacity,number;
         public MyViewHolder(View itemView) {
             super(itemView);
             city= itemView.findViewById(R.id.shw_place);
             cost= itemView.findViewById(R.id.shw_cost);
             capacity= itemView.findViewById(R.id.shw_capacity);
             number= itemView.findViewById(R.id.shw_number);

         }
     }

}
