package com.example.hello.getshelter;

import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Search_Result extends AppCompatActivity {
    DatabaseReference reference;
    ArrayList<Product> list;
    RecyclerView recyclerView;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__result);
        reference=FirebaseDatabase.getInstance().getReference().child("Shelters");

        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        recyclerView=findViewById(R.id.recycler_view);
        searchView= findViewById(R.id.search_view);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (reference!=null){
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        list=new ArrayList<>();
                        for (DataSnapshot ds:dataSnapshot.getChildren()){
                            list.add(ds.getValue(Product.class));
                        }
                        ProductAdapter productAdapter=new ProductAdapter(list);
                        recyclerView.setAdapter(productAdapter);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Search_Result.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });


        }

        if (searchView!=null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);

                    return true;
                }
            });
        }
    }
    private void search(String str){
        ArrayList<Product> myList=new ArrayList<>();
        for (Product object:list){
            if (object.getCity().toLowerCase().contains(str.toLowerCase())){
                myList.add(object);
            }
        }

        ProductAdapter productAdapter=new ProductAdapter(myList);
        recyclerView.setAdapter(productAdapter);
    }
}
