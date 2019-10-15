package com.example.homework.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.homework.RequestPermissionFragment;
import com.example.homework.ShortContact;

import java.util.List;

public interface ContactsView extends MvpView {
    @StateStrategyType(OneExecutionStateStrategy.class)
    void onRequestPermission(RequestPermissionFragment requestPermissionFragment);

    void setContacts(List<ShortContact> list);
    void showLoading();
    void hideLoading();
}
