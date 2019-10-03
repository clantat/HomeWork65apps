package com.example.homework.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.homework.Contact;
import com.example.homework.RequestPermissionFragment;

public interface InfoView extends MvpView {
    @StateStrategyType(OneExecutionStateStrategy.class)
    void onRequestPermission(RequestPermissionFragment requestPermissionFragment);

    void showInfo(Contact contact);
}
