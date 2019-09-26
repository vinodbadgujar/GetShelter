package com.example.hello.getshelter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Customer_Login extends AppCompatActivity implements View.OnClickListener{
    TextView textView;
    FirebaseAuth mAuth;
    EditText editTextEmail, editTextPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__login);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.editText);
        editTextPassword = (EditText) findViewById(R.id.editText2);
        progressBar = (ProgressBar) findViewById(R.id.progressbar1);

        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        findViewById(R.id.button3).setOnClickListener(this);

        textView=(TextView)findViewById(R.id.txt_view);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Customer_Login.this,Customer_SignUp.class);
                startActivity(intent);


            }
        });
    }

    private void customerLogin(){

        String Password = editTextPassword.getText().toString().trim();
        String Email = editTextEmail.getText().toString().trim();

        if (Password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
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


        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                 Intent intent = new Intent(Customer_Login.this, CustomerPage.class);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button3:
                customerLogin();
                break;
        }
    }
}
