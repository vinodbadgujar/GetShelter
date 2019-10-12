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

public class addShelter extends AppCompatActivity {
    Button b11;
    EditText splace,scity,spincode,slandmark;

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
        mAuth = FirebaseAuth.getInstance();

        databaseShelter= FirebaseDatabase.getInstance().getReference("Shelters");

//        b11=(Button) findViewById(R.id.next1);

        //databaseOwner = FirebaseDatabase.getInstance().getReference("Owners");

    }


    private void addhome(String place, String city, String pincode, String landmark) {
        if(place.isEmpty()){
            splace.setError("Required");
            splace.requestFocus();
            return;
        }
        if(city.isEmpty()){
            scity.setError("Required");
            scity.requestFocus();
            return;
        }
        if(pincode.isEmpty()){
            spincode.setError("Required");
            spincode.requestFocus();
            return;
        }
        if(landmark.isEmpty()){
            slandmark.setError("Required");
            slandmark.requestFocus();
            return;
        }

            String u_id=mAuth.getCurrentUser().getUid();
            databaseShelter = FirebaseDatabase.getInstance().getReference().child("Shelters").child(u_id);
            databaseShelter.child("Place").setValue(place);
            databaseShelter.child("City").setValue(city);
            databaseShelter.child("Pincode").setValue(pincode);
            databaseShelter.child("Landmark").setValue(landmark);

    }

    public void next(View view) {
        final String Place = splace.getText().toString().trim();
        final String City = scity.getText().toString().trim();
        final String Pincode = spincode.getText().toString().trim();
        final String Landmark = slandmark.getText().toString().trim();

        addhome(Place,City,Pincode,Landmark);
        Intent intent =new Intent(this,addimage.class);
        startActivity(intent);
    }
}
