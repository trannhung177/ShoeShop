package com.example.shoeshop.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoeshop.Models.ProductModel;
import com.example.shoeshop.Models.UserModel;
import com.example.shoeshop.R;

import java.util.List;

public class UserAdapter extends BaseAdapter {
    private Activity mContext;
    List<UserModel> userModelList;

    public UserAdapter(Activity mContext, List<UserModel> userModelList) {
        this.mContext = mContext;
        this.userModelList = userModelList;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.activity_profile,null,true);

        TextView tvName = listItemView.findViewById(R.id.tv_nameProfile);
        TextView tvEmail = listItemView.findViewById(R.id.tv_emailProfile);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView ivImg =  listItemView.findViewById(R.id.cImg_profile);


        UserModel userModel = userModelList.get(position);

        tvName.setText(userModel.getFullname());
        tvEmail.setText(userModel.getEmail());
//        Glide.with(mContext).load(userModel.)
//                .placeholder(R.drawable.baseline_image_24)
//                .error(R.drawable.baseline_image_24_2).into(ivImg);
//        ;


        return listItemView;
    }
}
