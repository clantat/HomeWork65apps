package com.example.homework.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.homework.domain.interactor.MapInteractor;
import com.example.homework.presentation.views.GMapView;
import com.example.homework.request.RequestAccessLocation;
import com.example.homework.schedulers.SchedulerManager;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

@InjectViewState
public class MapPresenter extends MvpPresenter<GMapView> {
    private RequestAccessLocation requestAccessLocation;
    private final MapInteractor mapInteractor;
    private CompositeDisposable compositeDisposable;
    private SchedulerManager schedulerManager;
    private String contactId;

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
        compositeDisposable.add(mapInteractor.getAddress(coordination)
                .subscribeOn(schedulerManager.ioThread())
                .observeOn(schedulerManager.mainThread())
                .subscribe(address -> {
                            getViewState().addMarker(coordination, address);
                            setMapContact(contactId, coordination, address);
                        }
                        , __ -> getViewState().onError("Cant add address for this marker")));
    }

    private void setMapContact(String id, LatLng latLng, String address) {
        compositeDisposable.add(mapInteractor.setMapContact(id, latLng, address)
                .subscribeOn(schedulerManager.ioThread())
                .observeOn(schedulerManager.mainThread())
                .subscribe(() -> getViewState().onError("Address added"),
                        __ -> getViewState().onError("Can't add an address"))
        );
    }

    public void addCurrentContactAddress() {
        compositeDisposable.add(mapInteractor.getMapContact(contactId)
                .subscribeOn(schedulerManager.ioThread())
                .observeOn(schedulerManager.mainThread())
                .subscribe(mapContact -> {
                            getViewState().addCurrentAddressMarker(new LatLng(mapContact.getLat(), mapContact.getLng()), mapContact.getAddress());
                        }
                        , __ -> getViewState().onError("Please, add an address")));
    }

    public void startLocation() {
        compositeDisposable.add(mapInteractor.getMapContact(contactId)
                .subscribeOn(schedulerManager.ioThread())
                .observeOn(schedulerManager.mainThread())
                .subscribe(mapContact -> {
                            getViewState().addMarker(new LatLng(mapContact.getLat(), mapContact.getLng()), mapContact.getAddress());
                        }
                        , __ -> {
                            getViewState().currentLocation(mapInteractor.getCurrentLocation());
                            getViewState().onError("Cant find your location");
                        }));

    }

    public RequestAccessLocation getRequestAccessLocation() {
        return this.requestAccessLocation;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }
}
