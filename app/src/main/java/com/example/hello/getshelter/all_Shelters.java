package com.example.hello.getshelter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class all_Shelters extends AppCompatActivity {

    private ImageView image;
    private TextView place;
    private TextView city;
    private TextView cost;
    private TextView capacity;
    private TextView landmark;
    private TextView pincode;
    FirebaseStorage storage= FirebaseStorage.getInstance();;
    StorageReference storageReference = storage.getReference();;



    private DatabaseReference getShelterInfo;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__shelters);
        mAuth=FirebaseAuth.getInstance();

        final String cur_user=mAuth.getCurrentUser().getUid();


        getShelterInfo= FirebaseDatabase.getInstance().getReference().child("Shelters").child(cur_user);

        image=(ImageView)findViewById(R.id.pic);
        place=(TextView)findViewById(R.id.all_place);
        city=(TextView)findViewById(R.id.all_city);
        cost=(TextView)findViewById(R.id.all_cost);
        capacity=(TextView)findViewById(R.id.all_nop);
        landmark=(TextView)findViewById(R.id.all_lmark);
        pincode=(TextView)findViewById(R.id.all_pin);

        storageReference = storage.getReferenceFromUrl("gs://getshelter.appspot.com");
        String userId = mAuth.getCurrentUser().getUid().toString();
        Picasso.with(all_Shelters.this).load(storageReference + userId).into(image);


        getShelterInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String vplace=dataSnapshot.child("Place").getValue().toString();
                String vcity=dataSnapshot.child("City").getValue().toString();
                String vcost=dataSnapshot.child("Cost").getValue().toString();
                String vcapacity=dataSnapshot.child("maximum_peoples").getValue().toString();
                String vlandmark=dataSnapshot.child("Landmark").getValue().toString();
                String vpincode=dataSnapshot.child("Pincode").getValue().toString();

                place.setText("Place:  "+vplace);
                city.setText("City:  "+vcity);
                cost.setText("Cost:  "+vcost);
                capacity.setText("Capacity:  "+vcapacity);
                landmark.setText("Landmark:  "+vlandmark);
                pincode.setText("Pincode:  "+vpincode);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
