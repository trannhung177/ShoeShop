package com.example.shoeshop.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.shoeshop.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManageProductList extends AppCompatActivity {
    private DatabaseReference mDatabase;
    ListView lvProductList;
    Button btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_product_list);
        Mapping();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        

    }

    public  void Mapping(){
        lvProductList = findViewById(R.id.lvManagerProduct);
        btnExit = findViewById(R.id.btnExit);
    }
}