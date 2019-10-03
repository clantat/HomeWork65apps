package com.example.homework.views;

import android.graphics.Bitmap;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.homework.Contact;

public interface InfoView extends MvpView {
    @StateStrategyType(OneExecutionStateStrategy.class)
    void onRequestPermission();
    void showInfo(Contact contact);
}
