package com.example.homework.domain.interactor;

import com.example.homework.domain.model.ShortContact;

import java.util.List;

import io.reactivex.Single;

public interface ContactsInteractor {
    Single<List<ShortContact>> getContacts(String searchText);
}
