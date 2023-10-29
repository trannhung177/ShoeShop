package com.example.shoeshop.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.shoeshop.CartActivity;
import com.example.shoeshop.Models.CartModel;
import com.example.shoeshop.Models.ProductModel;
import com.example.shoeshop.R;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    private Activity mContext;
    List<CartModel> cartList;
    @Override
    public int getCount() {
        return cartList.size();
    }
    public CartAdapter(Activity mContext, List<CartModel> cartList){
        super();
        this.mContext = mContext;
        this.cartList = cartList;
    }
    @Override
    public Object getItem(int i) {
        return cartList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"CheckResult", "SetTextI18n", "DefaultLocale"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.item_product,null,true);

        TextView tvName = listItemView.findViewById(R.id.tvProductName);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvPrice = listItemView.findViewById(R.id.tvProductPrice);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView ivImg =  listItemView.findViewById(R.id.ivProductImg_MP);
        EditText  edtQuantity = listItemView.findViewById(R.id.edtQuantityCart);
        //CheckBox cbProduct = listItemView.findViewById(R.id.cbProductCart);


        CartModel cartModel = cartList.get(position);

        tvName.setText(cartModel.getProductName());
        tvPrice.setText(String.format("%.0f", cartModel.getPrice()));
        Glide.with(mContext).load(cartModel.getProductImage())
                .placeholder(R.drawable.baseline_image_24)
                .error(R.drawable.baseline_image_24_2).into(ivImg);
        edtQuantity.setText(cartModel.getQuantity().toString());
        // Gán onCheckedChangeListener cho CheckBox
//        cbProduct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                // Xử lý khi CheckBox thay đổi trạng thái
//                // Ví dụ: cập nhật tổng tiền
//                if (isChecked) {
//                    // CheckBox được chọn, thực hiện cập nhật tổng tiền
//                    // Ví dụ: cập nhật tổng tiền khi sản phẩm được chọn
//                    cartModel.setCheckCart(true);
//
//                } else {
//                    // CheckBox không được chọn, thực hiện cập nhật tổng tiền
//                    // Ví dụ: cập nhật tổng tiền khi sản phẩm bị bỏ chọn
//                    cartModel.setCheckCart(true);
//                }
//            }
//        });



        return listItemView;
    }
}
