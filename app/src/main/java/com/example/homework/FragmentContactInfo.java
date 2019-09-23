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
        if (getArguments() != null) {
            name = getArguments().getString("Name");
            phone = getArguments().getString("Phone");
            email = getArguments().getString("Email");
            bitmap = getArguments().getParcelable("Image");
        }
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

    public static FragmentContactInfo newInstance(String Name, String Phone, String Email, Bitmap Image) {
        FragmentContactInfo myFragment = new FragmentContactInfo();
        Bundle bundle = new Bundle();
        bundle.putString("Name", Name);
        bundle.putString("Phone", Phone);
        bundle.putString("Email", Email);
        bundle.putParcelable("Image", Image);
        myFragment.setArguments(bundle);
        return myFragment;
    }
}
