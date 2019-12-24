package com.example.busy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.busy.restaurant.Signup_Restaurant;
import com.example.busy.users.Home_users;
import com.example.busy.users.Uform.Users_Form;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class signUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private FirebaseAuth mAuth;

    private Switch aSwitch;
    private Boolean flag=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextFirstName = findViewById(R.id.firstNameText);
        editTextLastName = findViewById(R.id.lastNameText);
        editTextEmail = findViewById(R.id.emailText);
        editTextPassword = findViewById(R.id.passwordText);
        findViewById(R.id.signUpButton).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

        aSwitch = (Switch)findViewById(R.id.switch2);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {flag=true;}
                else {flag=false;}
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            //handle the alredy login user
        }
    }

    private void registerUser(){
        final String FirstName = editTextFirstName.getText().toString().trim();
        final String LastName =  editTextLastName.getText().toString().trim();
        final String Email = editTextEmail.getText().toString().trim();
        final String Password = editTextPassword.getText().toString().trim();


        if(FirstName.isEmpty()){
            editTextFirstName.setError("First Name Required");
            editTextFirstName.requestFocus();
            return;
        }
        if(LastName.isEmpty()){
            editTextLastName.setError("Last Name Required");
            editTextLastName.requestFocus();
            return;
        }
        if (Email.isEmpty()){
            editTextEmail.setError("Email Required");
            editTextEmail.requestFocus();
            return;
        }
        if (Password.isEmpty()){
            editTextPassword.setError("password is required");
            editTextPassword.requestFocus();
            return;
        }
        if (Password.length() < 6){
            editTextPassword.setError("password length shuld be higer than 6");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //we will store the additional fileds in firebase database
                            Users_Form user = new Users_Form(FirstName,Email,LastName,Password);
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(signUpActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                                        if(flag==false){
                                            Intent i = new Intent(signUpActivity.this, Home_users.class);
                                            startActivity(i);
                                        }
                                        else{
                                            Intent i = new Intent(signUpActivity.this, Signup_Restaurant.class);
                                            startActivity(i);
                                        }
                                    }

                                    else {
                                        //display a faliure messege
                                        Toast.makeText(signUpActivity.this, "Error, couldn't sign up",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                        else{
                            Toast.makeText(signUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.signUpButton:
                registerUser();
                break;
        }
    }
}