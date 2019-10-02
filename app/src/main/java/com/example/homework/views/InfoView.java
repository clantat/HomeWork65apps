package com.example.homework.views;

import android.graphics.Bitmap;

import com.arellomobile.mvp.MvpView;
import com.example.homework.Contact;

public interface InfoView extends MvpView {
    void showInfo(Contact contact);
}
