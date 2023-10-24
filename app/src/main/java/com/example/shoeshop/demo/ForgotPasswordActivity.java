package com.example.shoeshop.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.shoeshop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText edt_email_QMK;
    private Button btnn_QMK;
    LinearLayout DN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        edt_email_QMK= findViewById(R.id.email_QMK);
        btnn_QMK= findViewById(R.id.btn_QMK);
        DN= findViewById(R.id.layout_DN_QMK);

        btnn_QMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= edt_email_QMK.getText().toString().trim();
                if(email.isEmpty()){
                    edt_email_QMK.setError("Vui lòng nhập địa chỉ email.");
                    edt_email_QMK.requestFocus();
                }
                else {
                    sendPasswordResetEmail(email);
                }

            }
        });

        DN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ForgotPasswordActivity.this, SignInWithEmailActivity.class);
                startActivity(intent);
            }
        });
    }
    private void sendPasswordResetEmail(String email){
        FirebaseAuth auth= FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Yêu cầu đặt lại mật khẩu đã được gửi qua email.", Toast.LENGTH_SHORT).show();
                    edt_email_QMK.setText("");
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Không thể gửi yêu cầu đặt lại mật khẩu. Vui lòng kiểm tra địa chỉ email.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}