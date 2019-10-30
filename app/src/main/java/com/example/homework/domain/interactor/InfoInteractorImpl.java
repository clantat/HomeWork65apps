package com.example.homework.domain.interactor;

import com.example.homework.data.repository.ContactsRepository;
import com.example.homework.domain.model.Contact;

import io.reactivex.Single;

public class InfoInteractorImpl implements InfoInteractor {
    private final ContactsRepository contactsRepository;

    public InfoInteractorImpl(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }

    @Override
    public Single<Contact> getInfoContact(String Id) {
        return contactsRepository.getInfoContact(Id);
    }
}
