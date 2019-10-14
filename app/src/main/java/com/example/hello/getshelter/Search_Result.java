package com.example.hello.getshelter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Search_Result extends AppCompatActivity {

    RecyclerView recyclerView;
    ProductAdapter adapter;

    List<Product> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__result);

        productList=new ArrayList<>();
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList.add(
                new Product(
                        "pimpalgoan",
                        "870",
                        "5",
                        "9096905052"
                        ));

        productList.add(
                new Product(
                        "pimpalgoan",
                        "870",
                        "5",
                        "9096905052"
                ));

        productList.add(
                new Product(
                        "pimpalgoan",
                        "870",
                        "5",
                        "9096905052"
                ));
        adapter=new ProductAdapter(this,productList);
        recyclerView.setAdapter(adapter);
    }
}
