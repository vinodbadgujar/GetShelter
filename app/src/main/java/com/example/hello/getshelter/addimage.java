package com.example.hello.getshelter;

import android.Manifest;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import java.util.List;
import java.util.UUID;


public class addimage extends AppCompatActivity {
    FirebaseStorage storage;
    StorageReference storageReference;
    private FirebaseAuth mAuth;
    DatabaseReference databaseShelter;
    private EditText scost,speoples;
    private Button btnChoose,btnUpload;
    private ImageView imageview;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST =71;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addimage);

        scost = (EditText) findViewById(R.id.cost);
        speoples = (EditText) findViewById(R.id.maximumpeople);

        btnChoose = (Button) findViewById(R.id.btnChoose);
        imageview = (ImageView) findViewById(R.id.imgView);
        btnUpload = (Button) findViewById(R.id.save);

        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);


        mAuth=FirebaseAuth.getInstance();
        databaseShelter= FirebaseDatabase.getInstance().getReference("Shelters");


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });


    }

    public void openOwnerPage(String cost,String peoples){

            String u_id=mAuth.getCurrentUser().getUid();
            databaseShelter = FirebaseDatabase.getInstance().getReference().child("Shelters").child(u_id);
            databaseShelter.child("Cost").setValue(cost);
            databaseShelter.child("maximum_peoples").setValue(peoples);
            Intent intent=new Intent(this,OwnerPage.class);
            startActivity(intent);




    }

    private void chooseImage() {


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 0, baos);
                imageview.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        final String cost = scost.getText().toString().trim();
        final String peoples = speoples.getText().toString().trim();
        if(cost.isEmpty()){
            scost.setError("Required");
            scost.requestFocus();
            return;
        }
        if(peoples.isEmpty()){
            speoples.setError("Required");
            speoples.requestFocus();
            return;
        }

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            String user_id = mAuth.getCurrentUser().getUid();

            StorageReference ref = storageReference.child(user_id.toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(addimage.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(addimage.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                            if (progress==100){


                                openOwnerPage(cost,peoples);
                            }

                        }
                    });
        }


    }
}