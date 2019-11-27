package com.example.homework.presentation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.homework.R;
import com.example.homework.core.MyApp;
import com.example.homework.presentation.presenters.MapPresenter;
import com.example.homework.presentation.views.MapView;

import javax.inject.Inject;
import javax.inject.Provider;

public class MapFragment extends MvpAppCompatFragment implements MapView {
    private static final String EXTRA_NUMBER = "extra_number";

    @Inject
    Provider<MapPresenter> mapPresenterProvider;
    @InjectPresenter
    MapPresenter mapPresenter;

    private View view;

    public MapFragment() {
    }

    @ProvidePresenter
    MapPresenter provideMapPresenter() {
        return mapPresenterProvider.get();
    }

    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(int number) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_NUMBER, number);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        MyApp.get().plusFragmentMapComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map_fragment, container, false);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        MyApp.get().clearFragmentMapComponent();
        super.onDestroy();
    }
}
