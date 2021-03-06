package com.example.homework.presentation.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.homework.R;
import com.example.homework.core.MyApp;
import com.example.homework.domain.model.MapContactModel;
import com.example.homework.presentation.presenters.MapPresenter;
import com.example.homework.presentation.views.GMapView;
import com.example.homework.request.RequestPermissionFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

public class MapFragment extends MvpAppCompatFragment implements GMapView, OnMapReadyCallback {

    @Inject
    Provider<MapPresenter> mapPresenterProvider;
    @InjectPresenter
    MapPresenter mapPresenter;

    private View view;
    private ImageView locationImageView;
    private GoogleMap map;
    private MapView mapView;
    private String currentAddressMarkerId;
    private Marker currentAddressMarker;
    private MarkerOptions markerOptions;
    private CameraPosition lastCameraPosition;
    private Button contactsBtn;
    private Boolean contactsBtnFlag;
    private Polyline polyline;
    private List<Marker> allContactsMarkerList;

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
        if (savedInstanceState != null) {
            contactsBtnFlag = savedInstanceState.getBoolean("contactsBtnFlag");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map_fragment, container, false);
        mapView = view.findViewById(R.id.map);
        if (savedInstanceState != null) {
            lastCameraPosition = savedInstanceState.getParcelable("cameraPosition");
        }
        mapView.onCreate(savedInstanceState);
        locationImageView = view.findViewById(R.id.location_icon_red);
        locationImageView.setVisibility(View.GONE);
        contactsBtn = view.findViewById(R.id.contacts_btn);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (map != null) {
            outState.putParcelable("cameraPosition", map.getCameraPosition());
        }
        outState.putBoolean("contactsBtnFlag", contactsBtnFlag);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setOnMapClickListener(LatLng -> mapPresenter.clickOnMap(LatLng));
        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                mapPresenter.clickOnMap(marker.getPosition());
            }
        });
        if (lastCameraPosition != null) {
            mapPresenter.addCurrentContactAddress();
            map.moveCamera(CameraUpdateFactory.newCameraPosition(lastCameraPosition));
        } else
            mapPresenter.startLocation();
        if (contactsBtnFlag == null)
            contactsBtnFlag = true;
        contactsBtn.setOnClickListener(__ -> {
            if (contactsBtnFlag) {
                contactsBtn.setText(getResources().getString(R.string.btn_remove_contacts));
                mapPresenter.getAllMapContact();
                contactsBtnFlag = false;
            } else {
                contactsBtn.setText(getResources().getString(R.string.btn_all_contacts));
                for (int i = 0; i < allContactsMarkerList.size(); i++) {
                    allContactsMarkerList.get(i).setVisible(false);
                    allContactsMarkerList.get(i).remove();
                    if (polyline != null)
                        polyline.remove();
                    if (currentAddressMarker != null) {
                        map.animateCamera(CameraUpdateFactory.newLatLng(currentAddressMarker.getPosition()));
                        currentAddressMarker.showInfoWindow();
                    }
                }
                contactsBtnFlag = true;
            }

        });
    }


    @Override
    public void startLocationPlace(LatLng latLng, String address) {
        if (map != null) {
            if (currentAddressMarker != null)
                currentAddressMarker.remove();
            currentAddressMarker = map.addMarker(markerOptions.position(latLng).draggable(true).title("Contact address: " + address));
            currentAddressMarkerId = currentAddressMarker.getId();
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, mapPresenter.getAverageCityZoom()));
            currentAddressMarker.showInfoWindow();
        }
    }

    @Override
    public void addLocationForNewAddress() {
        locationImageView.setVisibility(View.VISIBLE);
        if (map != null) {
            locationImageView.setOnClickListener(__ -> mapPresenter.clickOnMap(map.getCameraPosition().target));
        }

    }

    @Override
    public void addCurrentAddressMarker(LatLng latLng, String address) {
        if (map != null) {
            if (currentAddressMarker != null)
                currentAddressMarker.remove();
            if (!contactsBtnFlag) {
                mapPresenter.getAllMapContact();
                contactsBtn.setText(getResources().getString(R.string.btn_remove_contacts));
            }
            currentAddressMarker = map.addMarker(markerOptions.position(latLng).draggable(true).title("Contact address: " + address));
            currentAddressMarkerId = currentAddressMarker.getId();
            currentAddressMarker.showInfoWindow();
        }
    }

    @Override
    public void addMarker(LatLng latLng, String title) {
        if (map != null) {
            if (locationImageView.isEnabled()) {
                locationImageView.setVisibility(View.GONE);
                locationImageView.setEnabled(false);
            }
            if (allContactsMarkerList != null)
                for (int i = 0; i < allContactsMarkerList.size(); i++) {
                    allContactsMarkerList.get(i).setVisible(false);
                    allContactsMarkerList.get(i).remove();
                }
            if (polyline != null)
                polyline.remove();
            if (currentAddressMarker != null)
                currentAddressMarker.remove();
            currentAddressMarker = map.addMarker(markerOptions.position(latLng).draggable(true).title(title));
            currentAddressMarkerId = currentAddressMarker.getId();
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
        if (currentAddressMarker != null)
            currentAddressMarker.showInfoWindow();
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
        if (map != null && latLng != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, mapPresenter.getAverageCityZoom()));
        }

    }

    @Override
    public void allMapContact(List<MapContactModel> mapContactList) {
        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();
        allContactsMarkerList = new ArrayList<>();
        if (map != null) {
            for (int i = 0; i < allContactsMarkerList.size(); i++) {
                allContactsMarkerList.get(i).setVisible(false);
                allContactsMarkerList.get(i).remove();
            }
            for (int i = 0; i < mapContactList.size(); i++) {
                TextView text = new TextView(getContext());
                text.setTextColor(getResources().getColor(R.color.colorBlack));
                text.setText(getResources().getText(R.string.build_route) + mapContactList.get(i).getName());
                IconGenerator generator = new IconGenerator(getContext());
                generator.setColor(getResources().getColor(R.color.colorLightBlue));
                generator.setContentView(text);
                Bitmap icon = generator.makeIcon();
                LatLng pos = new LatLng(mapContactList.get(i).getLat(), mapContactList.get(i).getLng());
                MarkerOptions tp = new MarkerOptions().position(pos).icon(BitmapDescriptorFactory.fromBitmap(icon));
                Marker allContactsMarker = map.addMarker(tp);
                allContactsMarkerList.add(allContactsMarker);
                latLngBuilder.include(allContactsMarkerList.get(i).getPosition());
                if (mapContactList.get(i).getId().equals(mapPresenter.getContactId())) {
                    allContactsMarker.remove();
                }
            }
            map.animateCamera(moveCameraWithBounds(latLngBuilder));
            map.setOnMarkerClickListener(markerLatLng -> {
                if (markerLatLng.getId().equals(currentAddressMarkerId))
                    return false;
                return mapPresenter.setDirection(markerLatLng.getPosition());
            });
            Toast.makeText(getActivity(), "Match a contact for direction", Toast.LENGTH_LONG).show();
        }

    }

    private CameraUpdate moveCameraWithBounds(LatLngBounds.Builder builder) {
        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * mapPresenter.getPaddingWidthCoefficient());
        return CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
    }

    @Override
    public void setDirection(PolylineOptions polylineOptions) {
        if (map != null) {
            if (polylineOptions != null) {
                if (polyline != null)
                    polyline.remove();
                polyline = map.addPolyline(polylineOptions);
                LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();
                for (int i = 0; i < polyline.getPoints().size(); i++) {
                    latLngBuilder.include(polyline.getPoints().get(i));
                }
                map.animateCamera(moveCameraWithBounds(latLngBuilder));
            } else
                onError("Didnt get polyline");
        }
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

    public static MapFragment newInstance(String id, String name) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("name", name);
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
