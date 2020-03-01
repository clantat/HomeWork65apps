package com.example.homework.presentation.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.homework.domain.model.Contact;
import com.example.homework.request.RequestPermissionFragment;

public interface InfoView extends MvpView {
    @StateStrategyType(OneExecutionStateStrategy.class)
    void onRequestPermission(RequestPermissionFragment requestPermissionFragment);

    void showInfo(Contact contact);

    void onError(String msg);
}
