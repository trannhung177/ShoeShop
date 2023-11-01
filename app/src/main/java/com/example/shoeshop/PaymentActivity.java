package com.example.shoeshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoeshop.Adapter.CartAdapter;
import com.example.shoeshop.Models.CartModel;
import com.example.shoeshop.Models.OrderModel;
import com.example.shoeshop.Models.ProductModel;
import com.example.shoeshop.Models.UserModel;
import com.example.shoeshop.demo.SignInWithEmailActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.type.DateTime;
import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

public class PaymentActivity extends AppCompatActivity {
    TextView tvFullNamePayment, tvPhonePayment, tvAddresslPayment, tv_total, tv_order;
    DatabaseReference dbPaymentReference, dbCartReference;
    ArrayList<CartModel> list;
    private CartAdapter adapter;
    ImageView ivBack;
    RecyclerView rvProductPayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        anhxa();
        PaymentProduct();
        PaymentUserDetail();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                super.onBackPressed();
//                finish();
            }
        });
        tv_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                dbPaymentReference = FirebaseDatabase.getInstance().getReference("Orders");
                String orderId = dbPaymentReference.push().getKey();
                String userId = user.getUid();
                String userName = tvFullNamePayment.getText().toString();
                Double totalPrice = Double.parseDouble(tv_total.getText().toString());
                // Lấy ngày đặt hàng và chuyển sang String
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String orderDate = now.format(formatter);
                //Ngày dự kiến nhận hàng sau 3 ngày
                // tiến thời gian đi 3 ngày
                LocalDateTime threeDaysNext = now.plus(3, ChronoUnit.DAYS);
                String orderReceived = threeDaysNext.format(formatter);

                String phone = tvPhonePayment.getText().toString();
                String address = tvAddresslPayment.getText().toString();
                OrderModel orderModel = new OrderModel(orderId,userId,userName,phone,address,
                        orderDate, orderReceived,totalPrice, "1");

                if (orderId != null) {
                    dbPaymentReference.child(orderId).setValue(orderModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            dbCartReference = FirebaseDatabase.getInstance().getReference("Carts");
                            dbCartReference.addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    CartModel cart = snapshot.getValue(CartModel.class);
                                    assert cart != null;
                                    cart.setCheckCart(false);
                                    Intent i = new Intent(PaymentActivity.this, OrderSuccessfulActivity.class);
                                    startActivity(i);
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PaymentActivity.this, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

    }
    private boolean CheckActivity(){
        Intent intent = getIntent();
        if (intent != null && intent.getBooleanExtra("isFromProductDetail", false)) {
            return true;
        }
            return false;

    }


    @SuppressLint("DefaultLocale")
    private void PaymentProduct() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(CheckActivity()){
            Intent i = getIntent();
            if (i != null) {
                CartModel cartModel = (CartModel) i.getSerializableExtra("cartModel");
                list = new ArrayList<>();
                adapter = new CartAdapter(this, list);
                //gridView.setAdapter(adapter);
                rvProductPayment.setAdapter(adapter);
                rvProductPayment.setLayoutManager(new LinearLayoutManager(this));
                list.add(cartModel);
                Double totalPayment = 25000.0;
                for (int k = 0; k<list.size(); k++){
                    totalPayment += list.get(k).getPrice()*list.get(k).getQuantity();
                }
                tv_total.setText(String.format("%.0f", totalPayment));
            }
        }else {
            // Kiểm tra xem người dùng đã đăng nhập hay chưa
            Toast.makeText(PaymentActivity.this, user.getUid(), Toast.LENGTH_SHORT).show();
            if (user.getUid().isEmpty()) {

                Intent i = new Intent(PaymentActivity.this, SignInWithEmailActivity.class);
                startActivity(i);
                finish();
            } else {

                String userId = user.getUid();
                dbPaymentReference = FirebaseDatabase.getInstance().getReference("Carts");
                list = new ArrayList<>();
                adapter = new CartAdapter(this, list);
                //gridView.setAdapter(adapter);
                rvProductPayment.setAdapter(adapter);
                rvProductPayment.setLayoutManager(new LinearLayoutManager(this));
                dbPaymentReference.addValueEventListener(new ValueEventListener() {
                    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        Double totalPayment = 25000.0;
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            CartModel cart = dataSnapshot.getValue(CartModel.class);
                                if (cart != null && cart.getUserId()!= null && cart.getUserId().equals(userId) && cart.isCheckCart() ) {
                                    list.add(cart);
                                    totalPayment += cart.getPrice()*cart.getQuantity();
                                }

                        }
                        tv_total.setText(String.format("%.0f", totalPayment));
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("FAILED", error.getMessage());
                    }
                });
            }
        }


    }
    String img="";
    private void PaymentUserDetail(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        dbPaymentReference= FirebaseDatabase.getInstance().getReference("Users");
        String userID= user.getUid();
        if (userID != null){
            dbPaymentReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserModel user = snapshot.getValue(UserModel.class);
                    assert user != null;
//                        String imageUrl = product.getProductImage(); // Đây là URL hình ảnh
//                        Picasso.get().load(imageUrl).into(imgProduct);
//                        img = imageUrl;
                    tvFullNamePayment.setText(user.getFullname());
                    tvAddresslPayment.setText(user.getAddress());
                    tvPhonePayment.setText(user.getPhone());
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


    private void anhxa(){
        tvFullNamePayment= findViewById(R.id.tv_nameUserPayment);
        tvPhonePayment= findViewById(R.id.tv_numberUser);
        tvAddresslPayment= findViewById(R.id.tv_addressUserPayment);
        rvProductPayment = findViewById(R.id.rvProductPayment);
        tv_total=findViewById(R.id.tv_total);
        tv_order= findViewById(R.id.tv_order);
        ivBack = findViewById(R.id.ivBackInPayment);
    }

}