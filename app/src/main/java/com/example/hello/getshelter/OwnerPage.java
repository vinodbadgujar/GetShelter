package com.example.hello.getshelter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class OwnerPage extends AppCompatActivity {
    private Button b1,b2;
    static OwnerPage ownerPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ownerPage=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_page);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        b1=(Button) findViewById(R.id.addShelter);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddshelter();
            }
        });

        b2=(Button) findViewById(R.id.addedshelter);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openallshelter();
            }
        });
    }

    public static OwnerPage getInstance(){
        return   ownerPage;
    }
    public void openAddshelter(){
        Intent intent=new Intent(this,addShelter.class);
        startActivity(intent);
    }

    public void openallshelter(){
        Intent intent=new Intent(this,all_Shelters.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater .inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent=new Intent(this,HomeActivity.class);
        this.startActivity(intent);
        finish();
        return true;
    }
}
