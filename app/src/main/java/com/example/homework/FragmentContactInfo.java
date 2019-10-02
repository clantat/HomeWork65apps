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

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import presenters.InfoPresenter;
import views.InfoView;

public class FragmentContactInfo extends MvpAppCompatFragment implements InfoView {
    @InjectPresenter
    InfoPresenter infoPresenter;

    private View view;
    private TextView nameView;
    private TextView phoneView;
    private TextView emailView;
    private ImageView imageView;

    public FragmentContactInfo() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.info_fragment, container, false);
        nameView = view.findViewById(R.id.info_name_id);
        phoneView = view.findViewById(R.id.info_phone_id);
        emailView = view.findViewById(R.id.info_email_id);
        imageView = view.findViewById(R.id.info_image);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
        nameView = null;
        phoneView = null;
        emailView = null;
        imageView = null;
    }

    @ProvidePresenter
    InfoPresenter provideInfoPresenter() {
        return infoPresenter;
    }

    public static FragmentContactInfo newInstance(String Name, String Phone, String Email, Bitmap Image) {
        FragmentContactInfo myFragment = new FragmentContactInfo();
        myFragment.infoPresenter = new InfoPresenter(Name, Phone, Email, Image);
        return myFragment;
    }

    @Override
    public void showInfo(String name, String phone, String email, Bitmap bitmap) {
        nameView.setText(name);
        phoneView.setText(phone);
        emailView.setText(email);
        imageView.setImageBitmap(bitmap);
    }
}
