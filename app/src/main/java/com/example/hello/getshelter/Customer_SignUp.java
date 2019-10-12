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

public class Customer_SignUp extends AppCompatActivity implements View.OnClickListener {
    ProgressBar progressBar;
    EditText editTextName, editTextEmail, editTextPassword, editTextPhone, editTextCnfPass;

    private FirebaseAuth mAuth;

    DatabaseReference databaseCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__sign_up);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        databaseCustomer = FirebaseDatabase.getInstance().getReference("Customers");

        editTextName = (EditText) findViewById(R.id.editText11);
        editTextEmail = (EditText) findViewById(R.id.editText12);
        editTextPhone = (EditText) findViewById(R.id.editText13);
        editTextPassword = (EditText) findViewById(R.id.editText18);
        editTextCnfPass = (EditText) findViewById(R.id.editText19);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.button5).setOnClickListener(this);
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
                    Intent intent = new Intent(Customer_SignUp.this, CustomerPage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    String id=databaseCustomer.push().getKey();
                    Customer cust= new Customer(Name,Password,Email,Mobile);
                    String uid = task.getResult().getUser().getUid();
                    databaseCustomer.child(uid).setValue(cust);

                    //databaseCustomer.child(id).setValue(cust);
                    Toast.makeText(getApplicationContext(), "Account Created Successfully", Toast.LENGTH_SHORT).show();


                    startActivity(intent);
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

    class Customer{
       // String CustomerId;
        String Name;
        String Password;
        String Email ;
        String Mobile ;

        public Customer(){

        }

        public Customer( String name, String password, String email, String mobile) {

            this.Name = name;
            this.Password = password;
            this.Email = email;
            this.Mobile = mobile;
        }

       /*public String getCustomerId() {
            return CustomerId;
        }
         */
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
    public void onClick(View view) {
           switch (view.getId()){
               case R.id.button5:
                    registerUser();
                   break;
           }
    }
}
