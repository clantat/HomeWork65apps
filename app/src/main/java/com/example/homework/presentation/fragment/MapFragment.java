package com.example.homework.presentation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.homework.R;
import com.example.homework.core.MyApp;
import com.example.homework.presentation.presenters.MapPresenter;
import com.example.homework.presentation.views.GMapView;
import com.example.homework.request.RequestPermissionFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;
import javax.inject.Provider;

public class MapFragment extends MvpAppCompatFragment implements GMapView, OnMapReadyCallback {
    private static final String EXTRA_NUMBER = "extra_number";

    @Inject
    Provider<MapPresenter> mapPresenterProvider;
    @InjectPresenter
    MapPresenter mapPresenter;

    private View view;
    private GoogleMap map;
    private MapView mapView;
    private Marker coordinationMarker;
    private Marker currentAddressMarker;
    private View locationButton;
    private MarkerOptions markerOptions;
    private CameraPosition lastCameraPosition;

    public MapFragment() {
    }

    @ProvidePresenter
    MapPresenter provideMapPresenter() {
        return mapPresenterProvider.get();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        MyApp.get().plusFragmentMapComponent(this).inject(this);
        super.onCreate(savedInstanceState);
        markerOptions = new MarkerOptions();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map_fragment, container, false);
        mapView = view.findViewById(R.id.map);
        if (savedInstanceState != null) {
            lastCameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(savedInstanceState.getDouble("cameraLatitude"),
                            savedInstanceState.getDouble("cameraLongitude")))
                    .zoom(savedInstanceState.getFloat("cameraZoom"))
                    .bearing(savedInstanceState.getFloat("cameraBearing"))
                    .tilt(savedInstanceState.getFloat("cameraTilt"))
                    .build();
        }
        mapView.onCreate(savedInstanceState);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (map != null) {
            outState.putDouble("cameraLatitude", map.getCameraPosition().target.latitude);
            outState.putDouble("cameraLongitude", map.getCameraPosition().target.longitude);
            outState.putFloat("cameraBearing", map.getCameraPosition().bearing);
            outState.putFloat("cameraTilt", map.getCameraPosition().tilt);
            outState.putFloat("cameraZoom", map.getCameraPosition().zoom);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setOnMapClickListener(LatLng -> mapPresenter.clickOnMap(LatLng));
        if (lastCameraPosition != null) {
            mapPresenter.addCurrentContactAddress();
            map.moveCamera(CameraUpdateFactory.newCameraPosition(lastCameraPosition));
        } else
            mapPresenter.startLocation();
    }

    @Override
    public void addCurrentAddressMarker(LatLng latLng, String address) {
        if (map != null)
            if (currentAddressMarker == null) {
                currentAddressMarker = map.addMarker(markerOptions.position(latLng).draggable(false).title("Current address: " + address));
                currentAddressMarker.showInfoWindow();
            } else {
                currentAddressMarker.remove();
                currentAddressMarker = map.addMarker(markerOptions.position(latLng).draggable(false).title("Current address: " + address));
                currentAddressMarker.showInfoWindow();
            }
    }

    @Override
    public void addMarker(LatLng latLng, String title) {
        if (map != null) {
            if (coordinationMarker != null) {
                coordinationMarker.remove();
                coordinationMarker = map.addMarker(markerOptions.position(latLng).draggable(false).title(title));
            } else
                coordinationMarker = map.addMarker(markerOptions.position(latLng).draggable(false).title(title));
            if (currentAddressMarker != null)
                currentAddressMarker.remove();
            //TODO добавить галочку подтверждения
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        }
        if (coordinationMarker != null)
            coordinationMarker.showInfoWindow();
    }

    @Override
    public void mapAsync() {
        mapView.getMapAsync(this);
    }

    @Override
    public void onError(String msg) {
        Toast.makeText(this.getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void currentLocation(LatLng latLng) {
        if (map != null)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
    }

    @Override
    public void onRequestPermission(RequestPermissionFragment requestPermissionFragment) {
        if (requestPermissionFragment.doRequestPermission(this)) {
            mapPresenter.mapCreated();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mapPresenter.getRequestAccessLocation().onRequestPermissionResult(requestCode, grantResults))
            mapPresenter.mapCreated();
        else
            Toast.makeText(getActivity(), "Не ждите нас в гости", Toast.LENGTH_LONG).show();
    }

    public static MapFragment newInstance(int number, String id) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_NUMBER, number);
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        mapView.onStart();
        super.onStart();
    }

    @Override
    public void onStop() {
        mapView.onStop();
        super.onStop();
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onDestroy() {
        MyApp.get().clearFragmentMapComponent();
        mapView.onDestroy();
        super.onDestroy();
    }

}
