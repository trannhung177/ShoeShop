package com.example.shoeshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoeshop.Adapter.CartAdapter;
import com.example.shoeshop.Adapter.ProductAdapter;
import com.example.shoeshop.Models.CartModel;
import com.example.shoeshop.Models.ProductModel;
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

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    Intent i;
    private EditText edt_search;
    private ImageView ivHome,ivListProduct, ivProfile, ivSearchCart, ivDeleteProduct;
    private TextView tvPayment, tvTotal;
    private DatabaseReference dbreference;
    private CartAdapter adapter;
    private ArrayList<CartModel> list;
    private RecyclerView gridView;
    CheckBox cbProduct;
    Double totalPrice = 0.0;
    CheckBox cbSelectAll;
    Button btnUpdateCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        anhxa();
        ShowCart();
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        btnUpdateCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateQuantity();
            }
        });
        ivListProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartActivity.this, listProduct.class);
                startActivity(i);
            }
        });
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
        tvPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if cbSelectAll is checked
                boolean isSelectAllChecked = cbSelectAll.isChecked();
                // Create an Intent to start the CheckoutActivity
                if(isSelectAllChecked){
                    Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(CartActivity.this, "Bạn chưa chọn sản phẩm!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void UpdateQuantity() {
        DatabaseReference cartReference = FirebaseDatabase.getInstance().getReference("Carts");

        for (CartModel cartItem : list) {
            cartReference.child(cartItem.getId()).child("quantity").setValue(cartItem.getQuantity());
        }
    }

    private void anhxa() {

        gridView = findViewById(R.id.gvListCart);
        ivListProduct = findViewById(R.id.ivListProductInCart);
        ivHome = findViewById(R.id.img_home);
        tvTotal = findViewById(R.id.tvSumCartPrice);
        cbSelectAll = findViewById(R.id.cbSelectAll);
        btnUpdateCart = findViewById(R.id.btnUpdateCart);
        ivSearchCart = findViewById(R.id.ivSearchCart);
        ivProfile = findViewById(R.id.ivProfileCart);
        tvPayment = findViewById(R.id.tvPaymentCart);


    }

    private void ShowCart() {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        Toast.makeText(CartActivity.this, user.getUid(), Toast.LENGTH_SHORT).show();
        if (user.getUid().isEmpty()) {

            Intent i = new Intent(CartActivity.this, SignInWithEmailActivity.class);
            startActivity(i);
            finish();
        } else {
            String userId = user.getUid();
            dbreference = FirebaseDatabase.getInstance().getReference("Carts");
            list = new ArrayList<>();
            adapter = new CartAdapter(this, list);
            //gridView.setAdapter(adapter);
            gridView.setAdapter(adapter);
            gridView.setLayoutManager(new LinearLayoutManager(this));


            dbreference.addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        CartModel cart = dataSnapshot.getValue(CartModel.class);
                        if (cart != null && cart.isCheckCart()) {
                            if (cart.getUserId().equals(userId)) {
                                list.add(cart);
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("FAILED", error.getMessage());
                }
            });
           // Delete();



            //select all
            cbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @SuppressLint({"SetTextI18n", "DefaultLocale"})
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        for(int i =0; i <list.size(); i++){
                            totalPrice += list.get(i).getPrice()*list.get(i).getQuantity();
                        }
                    }
                    else {
                        totalPrice = 0.0;
                    }
                    tvTotal.setText(String.format("%.0f",totalPrice));
                }
            });
        }
    }

    int positionToRemove = -1;
    public void Delete(CartModel cartModel) {
         // Định vị trí sản phẩm cần xóa
        for (int i = 0; i < list.size(); i++) {
            CartModel item = list.get(i);
            if (item.getId().equals(cartModel.getId())) {
                positionToRemove = i;
                break;
            }
        }
        if (positionToRemove != -1) {
            // Tạo hộp thoại thông báo chắc chắn xóa
            AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
            builder.setTitle("Xác nhận xóa");
            builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng không?");

            // Nút Xác nhận xóa
            builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Tìm thấy sản phẩm, tiến hành xóa
                    list.remove(positionToRemove);
                    // Cập nhật adapter để hiển thị thay đổi
                    adapter.notifyDataSetChanged();
                    // Cập nhật cơ sở dữ liệu Firebase để đồng bộ hóa thay đổi
                    DatabaseReference cartReference = FirebaseDatabase.getInstance().getReference("Carts");
                    cartReference.child(cartModel.getId()).removeValue();
                }
            });

            // Nút Hủy
            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();

        } else {

        }
    }
}