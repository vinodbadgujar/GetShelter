package com.example.hello.getshelter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Search_Result extends AppCompatActivity implements ProductAdapter.onNoteListener{
    private static final int REQUEST_PHONE_CALL =1 ;
    DatabaseReference reference;
    ArrayList<Product> list;
    RecyclerView recyclerView;
    SearchView searchView;
    ImageView dial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__result);
        reference=FirebaseDatabase.getInstance().getReference().child("Shelters");

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
                        list = new ArrayList<>();
                        for (DataSnapshot ds:dataSnapshot.getChildren()){
                            list.add(ds.getValue(Product.class));
                        }
                        ProductAdapter productAdapter=new ProductAdapter(list ,Search_Result.this);
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
            if (object.getCity().toLowerCase().contains(str.toLowerCase())||object.getPlace().toLowerCase().contains(str.toLowerCase())){

                myList.add(object);
            }
        }
        list=myList;
        ProductAdapter productAdapter=new ProductAdapter(myList,this);
        recyclerView.setAdapter(productAdapter);

    }

    @Override
    public void onNoteClicked(int position) {
        Intent intent =new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+list.get(position).getMobile()));
        if (ContextCompat.checkSelfPermission(Search_Result.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Search_Result.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
        }
        else
        {
            startActivity(intent);
        }
    }

}
