package com.example.homework.di.components;

import com.example.homework.di.modules.mapscreen.DirectionModule;
import com.example.homework.di.modules.mapscreen.MapContactProviderModule;
import com.example.homework.di.modules.mapscreen.MapDatabaseModule;
import com.example.homework.di.modules.mapscreen.GeoCodingServiceModule;
import com.example.homework.di.modules.mapscreen.MapInteractorModule;
import com.example.homework.di.modules.mapscreen.MapPresenterModule;
import com.example.homework.di.modules.mapscreen.MapRepositoryModule;
import com.example.homework.di.scopes.MapScreenScope;
import com.example.homework.presentation.fragment.MapFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {MapPresenterModule.class
        , GeoCodingServiceModule.class, MapContactProviderModule.class
        , MapRepositoryModule.class, MapInteractorModule.class
        , MapDatabaseModule.class, DirectionModule.class
})
@MapScreenScope
public interface FragmentMapComponent {
    void inject(MapFragment mapFragment);
}
