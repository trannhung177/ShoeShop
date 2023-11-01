//package com.example.shoeshop.Adapter;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Context;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.bumptech.glide.Glide;
//import com.example.shoeshop.CartActivity;
//import com.example.shoeshop.Models.CartModel;
//import com.example.shoeshop.R;
//
//import java.util.List;
//
//public class CartAdapter extends BaseAdapter {
//    private Activity mContext;
//    List<CartModel> cartList;
//    @Override
//    public int getCount() {
//        return cartList.size();
//    }
//    public CartAdapter(Activity mContext, List<CartModel> cartList){
//        super();
//        this.mContext = mContext;
//        this.cartList = cartList;
//    }
//    @Override
//    public Object getItem(int i) {
//        return cartList.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    @SuppressLint({"CheckResult", "SetTextI18n", "DefaultLocale"})
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//        LayoutInflater inflater = mContext.getLayoutInflater();
//        View listItemView = inflater.inflate(R.layout.cart_product,null,true);
//
//        TextView tvName = listItemView.findViewById(R.id.tvProductName);
//        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvProductSize = listItemView.findViewById(R.id.tvCartSize);
//        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvPrice = listItemView.findViewById(R.id.tvProductPrice);
//        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
//        ImageView ivImg =  listItemView.findViewById(R.id.ivProductImg_MP);
//        EditText  edtQuantity = listItemView.findViewById(R.id.edtQuantityCart);
//
//
//        CartModel cartModel = cartList.get(position);
//
//        tvName.setText(cartModel.getProductName());
//        tvPrice.setText(String.format("%.0f", cartModel.getPrice()));
//        Glide.with(mContext).load(cartModel.getProductImage())
//                .placeholder(R.drawable.baseline_image_24)
//                .error(R.drawable.baseline_image_24_2).into(ivImg);
//        edtQuantity.setText(cartModel.getQuantity().toString());
//        tvProductSize.setText(cartModel.getSize().toString());
//        listItemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                // Gọi phương thức xóa sản phẩm của CartActivity và truyền dữ liệu sản phẩm được long click
//                ((CartActivity) mContext).Delete(cartModel);
//                // Trả về true để chỉ xử lý sự kiện long click mà không gọi sự kiện click thông thường
//                return true;
//            }
//        });
//
//
//
//        return listItemView;
//    }
//}



package com.example.shoeshop.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoeshop.CartActivity;
import com.example.shoeshop.Models.CartModel;
import com.example.shoeshop.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context mContext;
    private List<CartModel> cartList;

    public CartAdapter(Context context, List<CartModel> cartList) {
        this.mContext = context;
        this.cartList = cartList;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product, null, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        CartModel cartModel = cartList.get(position);

        holder.tvName.setText(cartModel.getProductName());
        holder.tvPrice.setText(String.format("%.0f", cartModel.getPrice()));
        Glide.with(mContext).load(cartModel.getProductImage())
                .placeholder(R.drawable.baseline_image_24)
                .error(R.drawable.baseline_image_24_2).into(holder.ivImg);
        holder.edtQuantity.setText(cartModel.getQuantity().toString());
        holder.tvProductSize.setText(cartModel.getSize().toString());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // Call the delete method in CartActivity and pass the clicked product
                ((CartActivity) mContext).Delete(cartModel);
                return true;
            }
        });
        //thay đổi số lượng
        holder.edtQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    // Xử lý khi người dùng xóa hết dữ liệu ở đây
                    // Ví dụ: Thiết lập số lượng mặc định hoặc thông báo lỗi
                    int newQuantity = 1;
                    cartModel.setQuantity(1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    int newQuantity = Integer.parseInt(editable.toString());
                    if(newQuantity <= 0){
                        newQuantity = 1;
                    }
                    else {
                        cartModel.setQuantity(newQuantity);
                    }

                }catch (NumberFormatException e){

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvProductSize, tvPrice;
        ImageView ivImg;
        EditText edtQuantity;

        public CartViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvProductSize = itemView.findViewById(R.id.tvCartSize);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            ivImg = itemView.findViewById(R.id.ivProductImg_MP);
            edtQuantity = itemView.findViewById(R.id.edtQuantityCart);
        }
    }

}

