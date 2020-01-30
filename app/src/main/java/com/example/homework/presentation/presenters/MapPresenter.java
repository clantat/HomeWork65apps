package com.example.homework.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.homework.domain.interactor.MapInteractor;
import com.example.homework.presentation.views.GMapView;
import com.example.homework.request.RequestAccessLocation;
import com.example.homework.schedulers.SchedulerManager;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

@InjectViewState
public class MapPresenter extends MvpPresenter<GMapView> {
    private RequestAccessLocation requestAccessLocation;
    private final MapInteractor mapInteractor;
    private CompositeDisposable compositeDisposable;
    private SchedulerManager schedulerManager;
    private String contactId;
    private LatLng currentLatLng;

    private static final double PADDING_WIDTH_COEFFICIENT = 0.20;
    private static final float AVERAGE_CITY_ZOOM = 15;

    @Inject
    public MapPresenter(MapInteractor mapInteractor, SchedulerManager schedulerManager, String contactId) {
        compositeDisposable = new CompositeDisposable();
        this.mapInteractor = mapInteractor;
        this.schedulerManager = schedulerManager;
        this.contactId = contactId;
        this.requestAccessLocation = new RequestAccessLocation();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().onRequestPermission(requestAccessLocation);
    }

    public void mapCreated() {
        if (requestAccessLocation.getLocationPermission()) {
            getViewState().mapAsync();
        }
    }

    public void clickOnMap(LatLng coordination) {
        compositeDisposable.add(mapInteractor.getAddress(coordination.toString())
                .subscribeOn(schedulerManager.ioThread())
                .observeOn(schedulerManager.mainThread())
                .subscribe(address -> {
                            currentLatLng = coordination;
                            getViewState().addMarker(coordination, address);
                            setMapContact(contactId, coordination, address);
                        }
                        , __ -> getViewState().onError("Cant add address for this marker")));
    }

    public void addCurrentContactAddress() {
        compositeDisposable.add(mapInteractor.getMapContact(contactId)
                .subscribeOn(schedulerManager.ioThread())
                .observeOn(schedulerManager.mainThread())
                .subscribe(mapContact -> {
                            currentLatLng = new LatLng(mapContact.getLat(), mapContact.getLng());
                            getViewState().addCurrentAddressMarker(currentLatLng, mapContact.getAddress());
                        }
                        , __ -> getViewState().onError("Please, add an address")));
    }

    public void getAllMapContact() {
        compositeDisposable.add(mapInteractor.getAllMapContact()
                .subscribeOn(schedulerManager.ioThread())
                .observeOn(schedulerManager.mainThread())
                .subscribe(mapContacts -> getViewState().allMapContact(mapContacts)
                        , __ -> getViewState().onError("Cant find your contacts"))
        );
    }

    public boolean setDirection(LatLng direction) {
        if (currentLatLng != null) {
            compositeDisposable.add(mapInteractor.setDirection(currentLatLng.toString(), direction.toString())
                    .subscribeOn(schedulerManager.ioThread())
                    .observeOn(schedulerManager.mainThread())
                    .subscribe(polyline -> {
                                PolylineOptions polylineOptions = new PolylineOptions();
                                for (int i = 0; i < polyline.size(); i++) {
                                    polylineOptions.add(getLatLngFromString(polyline.get(i)));
                                }
                                getViewState().setDirection(polylineOptions);
                            },
                            throwable -> {
                                throwable.printStackTrace();
                                getViewState().onError("Cant find route");
                            }));
            return true;
        } else {
            getViewState().onError("Please, sir, add an address");
            return false;
        }
    }

    public void startLocation() {
        compositeDisposable.add(mapInteractor.getMapContact(contactId)
                .subscribeOn(schedulerManager.ioThread())
                .observeOn(schedulerManager.mainThread())
                .subscribe(mapContact -> {
                            currentLatLng = new LatLng(mapContact.getLat(), mapContact.getLng());
                            getViewState().startLocationPlace(currentLatLng, mapContact.getAddress());
                        }
                        , __ -> {
                            getCurrentLocation();
                            getViewState().onError("Please, match your location");
                        }));

    }

    public RequestAccessLocation getRequestAccessLocation() {
        return this.requestAccessLocation;
    }

    private LatLng getLatLngFromString(String latLng) {
        StringBuilder stringBuilder = new StringBuilder(latLng);
        stringBuilder.delete(latLng.length() - 1, latLng.length());
        stringBuilder.delete(0, 10);
        String[] stringLatLng = stringBuilder.toString().split(",");
        return new LatLng(Double.parseDouble(stringLatLng[0]), Double.parseDouble(stringLatLng[1]));
    }

    private void setMapContact(String id, LatLng latLng, String address) {
        compositeDisposable.add(mapInteractor.setMapContact(id, latLng.toString(), address)
                .subscribeOn(schedulerManager.ioThread())
                .observeOn(schedulerManager.mainThread())
                .subscribe(() -> getViewState().onError("Address added"),
                        __ -> getViewState().onError("Can't add an address"))
        );
    }

    private void getCurrentLocation() {
        compositeDisposable.add(mapInteractor.getCurrentLocation()
                .subscribeOn(schedulerManager.ioThread())
                .observeOn(schedulerManager.mainThread())
                .subscribe(coordination -> getViewState().currentLocation(getLatLngFromString(coordination))
                        , __ -> getViewState().onError("Cant find your location"))
        );

    }

    public double getPaddingWidthCoefficient() {
        return PADDING_WIDTH_COEFFICIENT;
    }

    public float getAverageCityZoom() {
        return AVERAGE_CITY_ZOOM;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }
}
