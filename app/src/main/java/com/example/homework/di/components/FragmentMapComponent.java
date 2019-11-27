package com.example.homework.di.components;

import com.example.homework.di.modules.MapPresenterModule;
import com.example.homework.di.scopes.MapScreenScope;
import com.example.homework.presentation.fragment.MapFragment;

import dagger.Subcomponent;

@Subcomponent(modules = MapPresenterModule.class)
@MapScreenScope
public interface FragmentMapComponent {
    void inject(MapFragment mapFragment);
}
