package com.example.hello.getshelter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Owner_SignUp extends AppCompatActivity implements View.OnClickListener {
    ProgressBar progressBar;
    EditText editTextName, editTextEmail, editTextPassword, editTextPhone, editTextCnfPass;

    private FirebaseAuth mAuth;
    DatabaseReference databaseOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner__sign_up);

        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        databaseOwner = FirebaseDatabase.getInstance().getReference("Owners");

        editTextName = (EditText) findViewById(R.id.own_name_sign);
        editTextEmail = (EditText) findViewById(R.id.own_eamil_sign);
        editTextPhone = (EditText) findViewById(R.id.own_mob_sign);
        editTextPassword = (EditText) findViewById(R.id.own_pass_sign);
        editTextCnfPass = (EditText) findViewById(R.id.own_cnfpass_sign);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.own_signup).setOnClickListener(this);
    }

    private void registerUser(){
        final String Name = editTextName.getText().toString().trim();
        final String Password = editTextPassword.getText().toString().trim();
        final String Email = editTextEmail.getText().toString().trim();
        final String Mobile = editTextPhone.getText().toString().trim();
        String ConfirmPassword = editTextCnfPass.getText().toString().trim();

        if(Name.isEmpty()){
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }

        if (Email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            editTextEmail.setError("Please Enter valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (Mobile.isEmpty()){
            editTextPhone.setError("Mobile is required");
            editTextPhone.requestFocus();
            return;
        }
        if (Mobile.length()<10 || Mobile.length()>10){
            editTextPhone.setError("Invalid Mobile Number");
            editTextPhone.requestFocus();
            return;
        }

        if (Password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if (Password.length()<6){
            editTextPassword.setError("Minimum length of the password should be 6");
            editTextPassword.requestFocus();
            return;
        }


        if (ConfirmPassword.isEmpty()){
            editTextCnfPass.setError("Please confirm your password");
            editTextCnfPass.requestFocus();
            return;
        }
        if (!Password.equals(ConfirmPassword) ){
            editTextCnfPass.setError("Password and Confirm Password should be same");
            editTextCnfPass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    Intent intent = new Intent(Owner_SignUp.this, OwnerPage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                   // String id=databaseOwner.push().getKey();
                    Owner own= new Owner(Name,Password,Email,Mobile);

                   // databaseOwner.child(id).setValue(own);
                    String uid = task.getResult().getUser().getUid();
                    databaseOwner.child(uid).setValue(own);
                    Toast.makeText(getApplicationContext(), "Account Created Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    class Owner{
       // String OwnerId;
        String Name;
        String Password;
        String Email ;
        String Mobile ;

        public Owner(){

        }

        public Owner( String name, String password, String email, String mobile) {
           // this.OwnerId = OwnerId;
            this.Name = name;
            this.Password = password;
            this.Email = email;
            this.Mobile = mobile;
        }

       /* public String getOwnerId() {
            return OwnerId;
        }*/

        public String getName() {
            return Name;
        }

        public String getPassword() {
            return Password;
        }

        public String getEmail() {
            return Email;
        }

        public String getMobile() {
            return Mobile;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.own_signup:
                registerUser();
                break;
        }
    }
}
