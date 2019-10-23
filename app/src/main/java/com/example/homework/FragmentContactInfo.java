package com.example.homework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.homework.presenters.InfoPresenter;
import com.example.homework.views.InfoView;

import javax.inject.Inject;
import javax.inject.Provider;

public class FragmentContactInfo extends MvpAppCompatFragment implements InfoView {

    @Inject
    Provider<InfoPresenter> infoPresenterProvider;
    @InjectPresenter
    InfoPresenter infoPresenter;

    private View view;
    private TextView nameView;
    private TextView phoneView;
    private TextView emailView;
    private ImageView imageView;

    public FragmentContactInfo() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        MyApp.get().plusFragmentInfoComponent().inject(this);
        super.onCreate(savedInstanceState);
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

    @Override
    public void onDestroy() {
        MyApp.get().clearFragmentInfoComponent();
        super.onDestroy();
    }

    @ProvidePresenter
    InfoPresenter provideInfoPresenter() {
        String id = null;
        if (getArguments() != null) id = getArguments().getString("id");
        InfoPresenter infoPresenter = infoPresenterProvider.get();
        infoPresenter.setId(id);
        return infoPresenter;
    }

    public static FragmentContactInfo newInstance(String id) {
        FragmentContactInfo myFragment = new FragmentContactInfo();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Override
    public void showInfo(Contact contact) {
        nameView.setText(contact.getName());
        phoneView.setText(contact.getPhone());
        emailView.setText(contact.getEmail());
        imageView.setImageBitmap(contact.getmBitmap());
    }

    @Override
    public void onRequestPermission(RequestPermissionFragment requestPermissionFragment) {
        if (requestPermissionFragment.doRequestPermission(this)) {
            infoPresenter.init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (infoPresenter.getRequestReadContact().onRequestPermissionResult(requestCode, grantResults))
            infoPresenter.init();
        else
            Toast.makeText(getActivity(), "Третьего шанса не будет", Toast.LENGTH_LONG).show();
    }
}
