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
    private MapInteractor mapInteractor;
    private CompositeDisposable compositeDisposable;
    private SchedulerManager schedulerManager;
    @Inject
    public MapPresenter(MapInteractor mapInteractor,SchedulerManager schedulerManager) {
        compositeDisposable = new CompositeDisposable();
        this.mapInteractor = mapInteractor;
        this.schedulerManager = schedulerManager;
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

    public void clickOnMap(LatLng coordination){
        compositeDisposable.add(mapInteractor.getAddress(coordination)
                .subscribeOn(schedulerManager.ioThread())
                .observeOn(schedulerManager.mainThread())
                .subscribe(address->getViewState().addMarker(coordination,address)
//                        ,throwable -> getViewState().onError(throwable.toString())
                ));
    }

    public void clickOnMap(LatLng coordination,String title){
    }

    public LatLng startLocation() {
        return new LatLng(56.8497581, 53.2044792);

    }

    public RequestAccessLocation getRequestAccessLocation() {
        return this.requestAccessLocation;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(compositeDisposable!=null)
            compositeDisposable.dispose();
    }
}
