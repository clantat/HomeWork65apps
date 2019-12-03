package com.example.homework.request;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Objects;

public class RequestAccessLocation implements RequestPermissionFragment {
    private Boolean locationPermission;

    public RequestAccessLocation() {
        locationPermission = false;
    }

    public Boolean getLocationPermission() {
        return locationPermission;
    }

    @Override
    public boolean doRequestPermission(Fragment fragment) {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(fragment.getActivity()),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                fragment.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
                locationPermission = false;
                return false;
            }
        }
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(fragment.getActivity()),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                fragment.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
                locationPermission = false;
                return false;
            }
        }
        locationPermission = true;
        return true;
    }

    public boolean onRequestPermissionResult(int requestCode, int[] grantResults) {
        if (requestCode == 1) {
            locationPermission = grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED;
            return locationPermission;
        }
        locationPermission = false;
        return false;
    }
}
