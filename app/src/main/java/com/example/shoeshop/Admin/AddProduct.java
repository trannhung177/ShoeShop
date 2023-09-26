package com.example.shoeshop.Admin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shoeshop.Models.ProductModel;
import com.example.shoeshop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.Format;
import java.util.UUID;

public class AddProduct extends AppCompatActivity {
    ImageView ivProductImg;
     Button btnAddProduct;
    Uri uri;
    String imgURL;
    EditText edtProductName, edtProductBrand, edtProductDescription, edtProductQuantity, edtProductPrice;
     FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        Mapping();
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if( result.getResultCode() == Activity.RESULT_OK){
                            Intent i = result.getData();
                            uri = i.getData();
                            ivProductImg.setImageURI(uri);
                        }
                        else {
                            Toast.makeText(AddProduct.this, "Chưa có bức ảnh nào", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        ivProductImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

    }

  private void saveData(){
      StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
              .child(uri.getLastPathSegment());

      storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImg = uriTask.getResult();
                imgURL = urlImg.toString();
                uploadToFirebase();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
  }

    private void uploadToFirebase(){
        String productName = edtProductName.getText().toString();
        String productBrand =  edtProductBrand.getText().toString();
        String productDescription = edtProductDescription.getText().toString();
        Float productPrice = Float.parseFloat(edtProductPrice.getText().toString().trim());
        Integer productQuantity =  Integer.parseInt(edtProductQuantity.getText().toString().trim());
        if( productName.isEmpty() || productBrand.isEmpty() || productDescription.isEmpty()
            || productPrice.toString().isEmpty() || productQuantity.toString().isEmpty()){
            Toast.makeText(AddProduct.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Products");
        String productId = db.push().getKey();
        ProductModel product =
                new ProductModel(productName, productBrand, productDescription, productQuantity, productPrice, imgURL);
        if (productId != null){
            FirebaseDatabase.getInstance().getReference().child("Products").child(productId).setValue(product)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){

                                Toast.makeText(AddProduct.this, "Thêm thành công!!!", Toast.LENGTH_SHORT).show();
                                reset();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddProduct.this, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                        }
                    });
        }


    }

    public void Mapping(){
        ivProductImg = findViewById(R.id.ivProductImg);
        edtProductName = findViewById(R.id.edtProductName);
        edtProductBrand = findViewById(R.id.edtBrand);
        edtProductDescription = findViewById(R.id.edtDescription);
        edtProductPrice =  findViewById(R.id.edtPrice);
        edtProductQuantity = findViewById(R.id.edtQuantity);
        btnAddProduct = findViewById(R.id.btnAddProduct);
    }
    public void reset(){
        edtProductName.setText("");
        edtProductBrand.setText("");
        edtProductDescription.setText("");
        edtProductPrice.setText("");
        edtProductQuantity.setText("");
        ivProductImg.setImageResource(R.drawable.add_picture);
    }
}