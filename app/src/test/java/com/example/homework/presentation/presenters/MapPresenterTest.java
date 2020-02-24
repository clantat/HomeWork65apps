package com.example.homework.presentation.presenters;

import com.example.homework.data.provider.MapContactProvider;
import com.example.homework.data.repository.MapRepositoryImpl;
import com.example.homework.domain.interactor.MapInteractor;
import com.example.homework.domain.interactor.MapInteractorImpl;
import com.example.homework.domain.model.MapContactModel;
import com.example.homework.domain.repository.MapRepository;
import com.example.homework.presentation.views.GMapView;
import com.example.homework.request.RequestAccessLocation;
import com.example.homework.stuff.TrampolineScheduler;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static io.reactivex.Single.error;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MapPresenterTest {
    private MapPresenter mapPresenter;
    private MapInteractor mapInteractor;
    private MapRepository mapRepository;
    private MapContactModel mapContactModel;
    private MapContactModel mapContactForDirection;
    private List<MapContactModel> mapContactModelList;
    @Mock
    private MapContactProvider mapContactProvider;
    @Mock
    private GMapView mapView;
    @Mock
    private RequestAccessLocation requestAccessLocation;
    private TrampolineScheduler trampolineScheduler;

    @Before
    public void setUp() {
        mapContactModel = new MapContactModel("228", "Faust", 1.448, 3.22, "Kolotushkina");
        mapContactForDirection = new MapContactModel("322", "Rgentina", 1.444, 3.21, "Pushkina");
        mapContactModelList = new ArrayList<>();
        mapContactModelList.add(mapContactModel);
        mapContactModelList.add(mapContactForDirection);
        trampolineScheduler = new TrampolineScheduler();
        mapRepository = new MapRepositoryImpl(mapContactProvider);
        mapInteractor = new MapInteractorImpl(mapRepository);
        mapPresenter = new MapPresenter(mapInteractor, trampolineScheduler, mapContactModel.getId(), mapContactModel.getName());
    }

    @Test
    public void addMapContactModelToDataBaseAndMarkerOnTheMapWhenRequestPermissionTrue() {
        when(requestAccessLocation.getLocationPermission()).thenReturn(true);
        when(mapContactProvider.getAddress(new LatLng(1.448, 3.22).toString())).thenReturn(Single.just("Kolotushkina"));
        when(mapContactProvider.addMapContact(mapContactModel.getId(), mapContactModel.getName(), new LatLng(mapContactModel.getLat(), mapContactModel.getLng()).toString(), mapContactModel.getAddress())).thenReturn(Completable.complete());
        mapPresenter.setRequestAccessLocation(requestAccessLocation);
        mapPresenter.attachView(mapView);
        mapPresenter.clickOnMap(new LatLng(1.448, 3.22));
        verify(mapContactProvider).addMapContact(mapContactModel.getId(), mapContactModel.getName(), new LatLng(mapContactModel.getLat(), mapContactModel.getLng()).toString(), mapContactModel.getAddress());
        verify(mapView).addMarker(new LatLng(1.448, 3.22), mapContactModel.getAddress());
    }

    @Test
    public void returnCurrentLocationAddress() {
        TestObserver<String> testObserver = TestObserver.create();
        when(mapContactProvider.getCurrentLocation()).thenReturn(Single.just("LatLng"));
        mapInteractor.getCurrentLocation().subscribe(testObserver);
        testObserver
                .assertSubscribed()
                .assertNoErrors()
                .assertComplete()
                .dispose();
    }

    @Test
    public void getCurrentAddressInStartLocationWhenPermissionTrue() {
        when(requestAccessLocation.getLocationPermission()).thenReturn(true);
        when(mapContactProvider.getMapContact("228")).thenReturn(Single.just(mapContactModel));
        mapPresenter.setRequestAccessLocation(requestAccessLocation);
        mapPresenter.attachView(mapView);
        mapPresenter.startLocation();
        verify(mapView).startLocationPlace(new LatLng(mapContactModel.getLat(), mapContactModel.getLng()), mapContactModel.getAddress());
    }

    @Test
    public void cantGetCurrentAddressInStartLocationWhenPermissionFalse() {
        when(requestAccessLocation.getLocationPermission()).thenReturn(false);
        mapPresenter.setRequestAccessLocation(requestAccessLocation);
        mapPresenter.attachView(mapView);
        mapPresenter.startLocation();
        verify(mapView).onError("Please, give your permission");
    }

    @Test
    public void returnAddLocationForNewAddressWhenDataBaseHasNotThisContactModel() {
        when(requestAccessLocation.getLocationPermission()).thenReturn(true);
        when(mapContactProvider.getMapContact("228")).thenReturn(error(NullPointerException::new));
        when(mapContactProvider.getCurrentLocation()).thenReturn(Single.just(new LatLng(mapContactModel.getLat(), mapContactModel.getLng()).toString()));
        TestObserver<MapContactModel> testObserver = TestObserver.create();
        mapContactProvider.getMapContact("228").subscribe(testObserver);
        mapPresenter.setRequestAccessLocation(requestAccessLocation);
        mapPresenter.attachView(mapView);
        mapPresenter.startLocation();
        testObserver.assertSubscribed();
        testObserver.assertNotComplete();
        testObserver.assertError(NullPointerException.class);
        testObserver.dispose();
        verify(mapView).addLocationForNewAddress();
        verify(mapView).onError("Please, click on the marker for mark a location");
    }

    @Test
    public void showAllContactMapModelWhenPressedTheButton() {
        when(mapContactProvider.getAllMapContact()).thenReturn(Single.just(mapContactModelList));
        mapPresenter.attachView(mapView);
        mapPresenter.getAllMapContact();
        verify(mapView).allMapContact(mapContactModelList);
    }

    @Test
    public void shouldSetDirectionBetweenTwoContacts() {
        List<String> polylineStringList = new ArrayList<>();
        polylineStringList.add(new LatLng(mapContactModel.getLat(), mapContactModel.getLng()).toString());
        polylineStringList.add(new LatLng(mapContactForDirection.getLat(), mapContactForDirection.getLng()).toString());
        when(mapContactProvider.getPolyline(new LatLng(mapContactModel.getLat(), mapContactModel.getLng()).toString(), new LatLng(mapContactForDirection.getLat(), mapContactForDirection.getLng()).toString())).thenReturn(Single.just(polylineStringList));
        mapPresenter.setCurrentLatLng(new LatLng(mapContactModel.getLat(), mapContactModel.getLng()));
        mapPresenter.attachView(mapView);
        mapPresenter.setDirection(new LatLng(mapContactForDirection.getLat(), mapContactForDirection.getLng()));
        verify(mapView).setDirection(mapPresenter.getPolylineOptions());
    }

    @Test
    public void shouldReturnErrorWhenMapPresenterCurrentLatLngUndefined() {
        mapPresenter.attachView(mapView);
        mapPresenter.setDirection(new LatLng(mapContactForDirection.getLat(), mapContactForDirection.getLng()));
        verify(mapView).onError("Please, sir, add an address");
    }
}