package com.example.shoeshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.shoeshop.Adapter.ProductAdapter;
import com.example.shoeshop.Adapter.ProductItemAdapter;
import com.example.shoeshop.Enum.FilterProduct;
import com.example.shoeshop.Models.ProductModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class listProduct extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference dbreference;

    ProductAdapter adapter, adapterAdidas, adapterNike, adapterBalen;
    ArrayList<ProductModel> list;
    GridView gridView;
    EditText edtSearch;
    Spinner spinSize;
    ArrayList<String> spinnerList;
    ImageView homeicon, ivSearch;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);

        anhxa();
        ShowList();
        SpinnerItem();
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterList();
            }
        });



        homeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(listProduct.this, MainActivity.class);
                startActivity(i);
            }
        });
        //click item
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = list.get(i).getId();
                Intent intent = new Intent(listProduct.this, ProductDetailActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }

    String itemSelect ="";
    private void SpinnerItem() {
        spinnerList = new ArrayList<>();
        spinnerList.add("Tên");
        spinnerList.add("Giá");
        spinnerList.add("Thương hiệu");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, spinnerList);
        spinSize.setAdapter(spinnerAdapter);

        spinSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                itemSelect = spinnerList.get(i);

                if (itemSelect.equals("Tên")) {
                    // Sắp xếp sản phẩm theo tên sản phẩm ở đây
                    sortByProductName();
                } else if (itemSelect.equals("Giá")) {
                    // Sắp xếp sản phẩm theo giá ở đây
                    sortByPrice();
                } else if (itemSelect.equals("Thương hiệu")) {
                    // Sắp xếp sản phẩm theo thương hiệu ở đây
                    sortByBrand();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Xử lý khi không có mục nào được chọn
            }
        });
    }

    private void sortByProductName() {
        // Sắp xếp danh sách sản phẩm theo tên sản phẩm
        Collections.sort(list, new Comparator<ProductModel>() {
            @Override
            public int compare(ProductModel product1, ProductModel product2) {
                // So sánh tên sản phẩm của hai sản phẩm và trả về kết quả sắp xếp
                return product1.getProductName().compareToIgnoreCase(product2.getProductName());
            }
        });

        // Cập nhật giao diện hoặc danh sách sản phẩm sau khi sắp xếp
        adapter.notifyDataSetChanged();
    }

    private void sortByPrice() {
        Collections.sort(list, new Comparator<ProductModel>() {
            @Override
            public int compare(ProductModel product1, ProductModel product2) {
                // So sánh giá của hai sản phẩm và trả về kết quả sắp xếp
                if (product1.getProductPrice() < product2.getProductPrice()) {
                    return -1; // Trả về -1 nếu giá sản phẩm 1 nhỏ hơn giá sản phẩm 2
                } else if (product1.getProductPrice() > product2.getProductPrice()) {
                    return 1; // Trả về 1 nếu giá sản phẩm 1 lớn hơn giá sản phẩm 2
                } else {
                    return 0; // Trả về 0 nếu giá sản phẩm 1 và giá sản phẩm 2 bằng nhau
                }
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void sortByBrand() {
        Collections.sort(list, new Comparator<ProductModel>() {
            @Override
            public int compare(ProductModel product1, ProductModel product2) {
                // So sánh tên sản phẩm của hai sản phẩm và trả về kết quả sắp xếp
                return product1.getProductBrand().compareTo(product2.getProductBrand());
            }
        });

        // Cập nhật giao diện hoặc danh sách sản phẩm sau khi sắp xếp
        adapter.notifyDataSetChanged();
    }


    private void FilterList() {
        dbreference= FirebaseDatabase.getInstance().getReference("Products");
        list = new ArrayList<>();
        adapter=  new ProductAdapter(this, list);
        gridView.setAdapter(adapter);
        String searchByName = edtSearch.getText().toString().trim().toLowerCase();
        if (searchByName.isEmpty()){
            ShowList();
        }
        else {

            dbreference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    list.clear(); // Xóa dữ liệu cũ trước khi thêm dữ liệu mới
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Xử lý dữ liệu từ snapshot và thêm vào danh sách list
                        ProductModel product = snapshot.getValue(ProductModel.class);
                        if(product.getProductName().toLowerCase().contains(searchByName)){
                            list.add(product);
                        }

                    }
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Xử lý lỗi nếu có.
                }
            });
        }
    }

    public void anhxa(){
        homeicon = findViewById(R.id.imgHomeP);
        gridView = findViewById(R.id.recycleviewProduct);
        ivSearch = findViewById(R.id.ivSearchIconInList);
        edtSearch   = findViewById(R.id.edtSearchInList);
        spinSize = findViewById(R.id.spinSize);

    }
    public void ShowList(){
        dbreference= FirebaseDatabase.getInstance().getReference("Products");
        list = new ArrayList<>();
        adapter=  new ProductAdapter(this, list);
        gridView.setAdapter(adapter);
        //recyclerView.setAdapter(adepter);
        dbreference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
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