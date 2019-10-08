package com.example.homework;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Objects;

public class RequestReadContact implements RequestPermissionFragment {
    @Override
    public boolean doRequestPermission(Fragment fragment) {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(fragment.getActivity()),
                Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                fragment.requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                        1);
                return false;
            }
        }
        return true;
    }

    public static boolean onRequestPermissionResult(int requestCode, int[] grantResults) {
        if (requestCode == 1) {
            return grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }
}
