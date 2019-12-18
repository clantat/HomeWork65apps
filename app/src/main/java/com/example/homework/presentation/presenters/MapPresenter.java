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
    private LatLng currentLatLng;

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
            compositeDisposable.add(mapInteractor.setDirection(currentLatLng, direction)
                    .subscribeOn(schedulerManager.ioThread())
                    .observeOn(schedulerManager.mainThread())
                    .subscribe(polyline -> getViewState().setDirection(polyline),
                            throwable -> {
                                throwable.printStackTrace();
                                getViewState().onError("Cant find polyline");
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
                            getViewState().addMarker(currentLatLng, mapContact.getAddress());
                        }
                        , __ -> {
                            getCurrentLocation();
                            getViewState().onError("Please, match your location");
                        }));

    }

    public RequestAccessLocation getRequestAccessLocation() {
        return this.requestAccessLocation;
    }

    private void setMapContact(String id, LatLng latLng, String address) {
        compositeDisposable.add(mapInteractor.setMapContact(id, latLng, address)
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
                .subscribe(coordination -> getViewState().currentLocation(coordination)
                        , __ -> getViewState().onError("Cant find your location"))
        );

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }
}
