package com.example.hello.getshelter;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class addShelter extends AppCompatActivity {
Button b11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shelter);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        b11=(Button) findViewById(R.id.next1);
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddImage();
            }
        });
    }
    public void openAddImage(){
        Intent intent=new Intent(this,addimage.class);
        startActivity(intent);
    }
}
