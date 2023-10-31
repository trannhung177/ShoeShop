package com.example.shoeshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.GridView;

import com.example.shoeshop.Adapter.ProductAdapter;
import com.example.shoeshop.Models.ProductModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class search_product extends AppCompatActivity {

    EditText editText;
    GridView gridView;
    private DatabaseReference dbreference;
    private ProductAdapter adapter;
    private ArrayList<ProductModel> list;
    SearchView searchView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        searchView=findViewById(R.id.edt_searchItem);
        gridView= findViewById(R.id.lstItemSearch);

        init();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        

    }
    //filter tim kiếm
    private void filter(String txt){
        //xoa du lieumang
        list.clear();
        ArrayList<ProductModel> ftArr= new ArrayList<>();
        for (ProductModel item: list ){
            if(item.getProductName().toLowerCase().contains(txt.toLowerCase())){
                //them item vao filtrelst
                ftArr.add(item);
                //them vao mang
                list.add(item);
            }
        }
        adapter.filterList(ftArr);

    }

    //lay du lieu tu db
    private void init(){
        dbreference= FirebaseDatabase.getInstance().getReference("Products");

        //recyclerView.setHasFixedSize(true);

        list = new ArrayList<>();
        adapter=  new ProductAdapter(this, list);
        gridView.setAdapter(adapter);
        dbreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ProductModel productModel= dataSnapshot.getValue(ProductModel.class);
                    list.add(productModel);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}