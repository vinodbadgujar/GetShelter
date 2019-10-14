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

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context mtx;
    private List<Product> productList;

    public ProductAdapter(Context mtx, List<Product> productList) {
        this.mtx = mtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mtx);
        View view=inflater.inflate(R.layout.list_view,null);
        ProductViewHolder holder=new ProductViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product=productList.get(position);
        holder.place.setText(product.getPplace());
        holder.cost.setText(product.getPcost());
        holder.capacity.setText(product.getPcapacity());
        holder.number.setText(product.getPnumber());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{

        EditText place,city,landmark,pincode,cost,capacity,number;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            place=itemView.findViewById(R.id.shw_place);
            cost=itemView.findViewById(R.id.shw_cost);
            capacity=itemView.findViewById(R.id.shw_capacity);
            number=itemView.findViewById(R.id.shw_number);
            imageView=itemView.findViewById(R.id.imageView);

        }
    }
}
