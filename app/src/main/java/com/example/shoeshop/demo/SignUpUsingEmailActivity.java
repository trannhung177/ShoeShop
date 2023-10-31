package com.example.shoeshop.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoeshop.MainActivity;
import com.example.shoeshop.Models.UserModel;
import com.example.shoeshop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpUsingEmailActivity extends AppCompatActivity {

    Button btnSignUp;
    EditText edtFullName, edtEmail, edtPassword;
    TextView tvLinkToSignIn;
    FirebaseAuth auth;
    FirebaseDatabase db;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_using_email);
        Mapping();
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(SignUpUsingEmailActivity.this);
        progressDialog.setTitle("Tạo mới tài khoản");
        progressDialog.setMessage("Tài khoản của bạn đang được tạo...");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String fullname = edtFullName.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();

                //Tạo mới user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (!task.isSuccessful()){
                                    if( task.getException().getMessage().contains("already in use")){
                                        Toast.makeText(SignUpUsingEmailActivity.this,
                                                "Email đã được sử dụng", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(SignUpUsingEmailActivity.this,
                                                task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    String userType ="user";
                                    String phone ="";
                                    String address ="";
                                    UserModel user = new UserModel(fullname, email, password, userType, phone, address);
                                    String id = task.getResult().getUser().getUid();
                                    db.getReference().child("Users").child(id).setValue(user);
                                    Toast.makeText(SignUpUsingEmailActivity.this, "Tạo mới thành công",
                                            Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(SignUpUsingEmailActivity.this, SignInWithEmailActivity.class);
                                    startActivity(i);
                                }
                            }
                        });
            }
        });

        tvLinkToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpUsingEmailActivity.this, SignInWithEmailActivity.class);
                startActivity(i);
            }
        });
    }


    public  void Mapping(){
        edtFullName =  findViewById(R.id.edtFullname);
        edtPassword = findViewById(R.id.edtPasswordSignUp);
        edtEmail = findViewById(R.id.edtEmailSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLinkToSignIn = findViewById(R.id.tvLinkToSignIn);
    }
}