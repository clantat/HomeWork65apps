package com.example.homework.domain.interactor;

import com.example.homework.domain.model.Contact;

import io.reactivex.Single;

public interface InfoInteractor {
    Single<Contact> getInfoContact(String Id);
}
