package com.example.homework.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.homework.Contact;

import java.util.List;

public interface ContactsView extends MvpView {
    @StateStrategyType(OneExecutionStateStrategy.class)
    void onRequestPermission();
    void setContacts(List<Contact> list);
}
