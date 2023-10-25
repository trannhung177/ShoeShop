package com.example.shoeshop.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.shoeshop.Adapter.ProductSimpleAdapter;
import com.example.shoeshop.Models.ProductModel;
import com.example.shoeshop.R;
import com.example.shoeshop.demo.SignInWithEmailActivity;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageProductList extends AppCompatActivity {
    private DatabaseReference mDatabase;
    ListView lvProductList;
    List<ProductModel> productList;

    Button btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_product_list);
        Mapping();

        productList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference("Products");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    ProductModel product = ds.getValue(ProductModel.class);
                    productList.add(product);
                }

                ProductSimpleAdapter adapter = new ProductSimpleAdapter(ManageProductList.this, productList);
                lvProductList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        lvProductList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ManageProductList.this, AddProduct.class);
                startActivity(intent);
                return false;
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                    mAuth.signOut();
                    Intent i = new Intent(ManageProductList.this, SignInWithEmailActivity.class);
                    startActivity(i);
                }
            }
        });

    }

    public  void Mapping(){
        lvProductList = findViewById(R.id.lvManagerProduct);
        btnExit = findViewById(R.id.btnExit);
    }
}