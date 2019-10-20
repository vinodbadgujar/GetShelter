package com.example.hello.getshelter;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addShelter extends AppCompatActivity implements View.OnClickListener {
    Button b11;
    EditText splace,scity,spincode,slandmark,smobno;

    private FirebaseAuth mAuth;
    DatabaseReference databaseShelter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shelter);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        splace = (EditText) findViewById(R.id.shelterplace);
        scity = (EditText) findViewById(R.id.sheltercity);
        spincode = (EditText) findViewById(R.id.pincode);
        slandmark = (EditText) findViewById(R.id.landmark);
        smobno=(EditText) findViewById(R.id.mob);
        mAuth = FirebaseAuth.getInstance();

        databaseShelter= FirebaseDatabase.getInstance().getReference("Shelters");

        findViewById(R.id.next1).setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        final String Place = splace.getText().toString().trim();
        final String City = scity.getText().toString().trim();
        final String Pincode = spincode.getText().toString().trim();
        final String Landmark = slandmark.getText().toString().trim();
        final String Mobile = smobno.getText().toString().trim();

        if(Place.isEmpty()){
            splace.setError("Required");
            splace.requestFocus();
            return;
        }
        if(City.isEmpty()){
            scity.setError("Required");
            scity.requestFocus();
            return;
        }
        if(Pincode.isEmpty()){
            spincode.setError("Required");
            spincode.requestFocus();
            return;
        }
        if(Pincode.length()!=6){
            spincode.setError("Invalid Pincode");
            spincode.requestFocus();
            return;
        }

        if(Landmark.isEmpty()){
            slandmark.setError("Required");
            slandmark.requestFocus();
            return;
        }
        if (Mobile.isEmpty()){
            smobno.setError("Mobile is required");
            smobno.requestFocus();
            return;
        }
        if (Mobile.length()<10 || Mobile.length()>10){
            smobno.setError("Invalid Mobile Number");
            smobno.requestFocus();
            return;
        }

        else{
            String u_id=mAuth.getCurrentUser().getUid();
            databaseShelter = FirebaseDatabase.getInstance().getReference().child("Shelters").child(u_id);
            databaseShelter.child("Place").setValue(Place);
            databaseShelter.child("City").setValue(City);
            databaseShelter.child("Pincode").setValue(Pincode);
            databaseShelter.child("Landmark").setValue(Landmark);
            databaseShelter.child("Mobile").setValue(Mobile);
        }


        Intent intent =new Intent(this,addimage.class);
        startActivity(intent);
        finish();
    }
}
