package com.example.homework;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentContactInfo extends Fragment {
    private View view;
    private String name;
    private String phone;
    private String email;
    private Bitmap bitmap;

    public FragmentContactInfo() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            name = savedInstanceState.getString("Name");
            phone = savedInstanceState.getString("Phone");
            email = savedInstanceState.getString("Email");
            bitmap = savedInstanceState.getParcelable("Image");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Name", name);
        outState.putString("Phone", phone);
        outState.putString("Email", email);
        outState.putParcelable("Image", bitmap);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.info_fragment, container, false);
        TextView nameView = view.findViewById(R.id.info_name_id);
        TextView phoneView = view.findViewById(R.id.info_phone_id);
        TextView emailView = view.findViewById(R.id.info_email_id);
        ImageView imageView = view.findViewById(R.id.info_image);
        nameView.setText(name);
        phoneView.setText(phone);
        emailView.setText(email);
        imageView.setImageBitmap(bitmap);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
    }

    void setInfo(String name, String phone, String email, Bitmap bitmap) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.bitmap = bitmap;
    }
}
