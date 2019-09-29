package com.example.homework;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import presenters.ViewHolderPresenter;
import views.ViewHolderView;

public class ViewHolder extends RecyclerView.ViewHolder implements ViewHolderView {
    @InjectPresenter
    ViewHolderPresenter viewHolderPresenter;
    private LinearLayout item_contact;
    private TextView tv_name;
    private TextView tv_phone;
    private ImageView img;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        item_contact = itemView.findViewById(R.id.contact_item_id);
        tv_name = itemView.findViewById(R.id.name_contact);
        tv_phone = itemView.findViewById(R.id.phone_contact);
        img = itemView.findViewById(R.id.img_contact);
    }

    @Override
    public void setName(String name) {
        tv_name.setText(name);
    }

    @Override
    public void setPhone(String phone) {
        tv_phone.setText(phone);
    }

    @Override
    public void setImage(Bitmap bitmap) {
        img.setImageBitmap(bitmap);
    }
}
