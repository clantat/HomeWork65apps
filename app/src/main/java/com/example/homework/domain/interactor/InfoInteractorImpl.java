package com.example.homework.domain.interactor;

import com.example.homework.domain.interactor.InfoInteractor;
import com.example.homework.domain.model.Contact;
import com.example.homework.domain.repository.ContactsRepository;

import io.reactivex.Single;

public class InfoInteractorImpl implements InfoInteractor {
    private ContactsRepository contactsRepository;

    public InfoInteractorImpl(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }

    @Override
    public Single<Contact> getInfoContact(String Id) {
        return contactsRepository.getInfoContact(Id);
    }
}
