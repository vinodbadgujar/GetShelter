package com.example.hello.getshelter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
     ArrayList<Product> list;
     private  onNoteListener mOnNoteListener;
     public ProductAdapter(ArrayList<Product> list,onNoteListener onNoteListener){
         this.list=list;
         this.mOnNoteListener=onNoteListener;
     }

     @NonNull
     @Override
     public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         View view =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_view,viewGroup,false);
         return new MyViewHolder(view,mOnNoteListener);
     }

     @Override
     public void onBindViewHolder(@NonNull MyViewHolder holder, final int i) {
         holder.city.setText("City: "+list.get(i).getCity());
         holder.cost.setText("Cost/Day: INR "+list.get(i).getCost());
         holder.capacity.setText("Max Capacity: "+list.get(i).getMaximum_peoples());
         holder.number.setText("Mobile Number: "+list.get(i).getMobile());

     }

     @Override
     public int getItemCount() {
         return list.size();
     }

     class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         TextView city,cost,capacity,number;
         ImageView dial;
         onNoteListener onNoteListener;

         public MyViewHolder(View itemView,onNoteListener onNoteListener) {
             super(itemView);
             city= itemView.findViewById(R.id.shw_place);
             cost= itemView.findViewById(R.id.shw_cost);
             capacity= itemView.findViewById(R.id.shw_capacity);
             number= itemView.findViewById(R.id.shw_number);
             dial=itemView.findViewById(R.id.call_button);
              dial.setOnClickListener(this);
              this.onNoteListener=onNoteListener;

         }

         @Override
         public void onClick(View v) {
                onNoteListener.onNoteClicked(getAdapterPosition());
         }
     }
        public interface onNoteListener{
         void onNoteClicked(int position);
        }
}
