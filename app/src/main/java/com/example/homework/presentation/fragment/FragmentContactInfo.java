package com.example.homework.presentation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.homework.R;
import com.example.homework.core.MyApp;
import com.example.homework.domain.model.Contact;
import com.example.homework.presentation.presenters.InfoPresenter;
import com.example.homework.presentation.views.InfoView;
import com.example.homework.request.RequestPermissionFragment;

import javax.inject.Inject;
import javax.inject.Provider;

public class FragmentContactInfo extends MvpAppCompatFragment implements InfoView {
    private static final String EXTRA_NUMBER = "extra_number";
    @Inject
    Provider<InfoPresenter> infoPresenterProvider;
    @InjectPresenter
    InfoPresenter infoPresenter;
    private View view;
    private TextView nameView;
    private TextView phoneView;
    private TextView emailView;
    private ImageView imageView;
    private Button button;

    public FragmentContactInfo() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        MyApp.get().plusFragmentInfoComponent(this).inject(this);
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
        button = view.findViewById(R.id.map_btn);
        button.setOnClickListener(__ -> infoPresenter.clickMapButton());
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
        button = null;
    }

    @Override
    public void onDestroy() {
        MyApp.get().clearFragmentInfoComponent();
        super.onDestroy();
    }

    @ProvidePresenter
    InfoPresenter provideInfoPresenter() {
        return infoPresenterProvider.get();
    }

    public static FragmentContactInfo newInstance(String id, int number) {
        FragmentContactInfo myFragment = new FragmentContactInfo();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_NUMBER, number);
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
