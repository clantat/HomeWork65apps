package com.example.homework;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;

public class ViewHolder extends RecyclerView.ViewHolder {
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

    void onBind(Contact contact) {
        tv_name.setText(contact.getName());
        tv_phone.setText(contact.getPhone());
        img.setImageBitmap(contact.getmBitmap());
    }

    public LinearLayout getItem_contact() {
        return item_contact;
    }

    public void setItem_contact(LinearLayout item_contact) {
        this.item_contact = item_contact;
    }

}
