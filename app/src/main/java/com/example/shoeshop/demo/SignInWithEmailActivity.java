package com.example.shoeshop.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoeshop.MainActivity;
import com.example.shoeshop.Admin.ManageProductList;
import com.example.shoeshop.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInWithEmailActivity extends AppCompatActivity {
    EditText edtEmail, edtPassword;
    Button btnSignIn;
    TextView tvLinkToSignUp, tvForgotPw;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    private String email="",password="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_with_email);
        getSupportActionBar().hide();
        Mapping();

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
//        if (!user.getUid().isEmpty()){
//                auth.signOut();
//        }
        progressDialog = new ProgressDialog(SignInWithEmailActivity.this);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateData();

            }
        });
        tvLinkToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInWithEmailActivity.this, SignUpUsingEmailActivity.class));
                finish();
            }
        });
        tvForgotPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInWithEmailActivity.this, ForgotPasswordActivity.class));
                finish();
            }
        });

    }

    private void ValidateData(){
        email = edtEmail.getText().toString().trim();
        password = edtPassword.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Email không đúng định dạng....",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Vui lòng nhập mật khẩu...",Toast.LENGTH_SHORT).show();
        }
        else{
            loginUser();
        }
    }

    public void Mapping(){
        edtEmail = findViewById(R.id.edtEmailLogin);
        edtPassword = findViewById(R.id.edtPasswordLogin);
        tvLinkToSignUp = findViewById(R.id.tvLinkToSignUp);
        btnSignIn = findViewById(R.id.btnSignin);
        tvForgotPw = findViewById(R.id.tvForgotPw);
    }

    private void loginUser(){
        progressDialog.setMessage("Đang đăng nhập...");
        progressDialog.show();

        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                checkUser();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(SignInWithEmailActivity.this, ""+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    private void checkUser(){
        progressDialog.setMessage("Đang kiểm tra tài khoản....");
        FirebaseUser firebaseUser=auth.getCurrentUser();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users");
        db.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                String userType = "" + snapshot.child("userType").getValue();
                if(userType.equals("user")){
                    startActivity(new Intent(SignInWithEmailActivity.this, MainActivity.class));
                    finish();
                }
                if(userType.equals("admin")){
                    startActivity(new Intent(SignInWithEmailActivity.this, ManageProductList.class));
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}