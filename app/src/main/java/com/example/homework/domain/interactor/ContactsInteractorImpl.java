package com.example.homework.domain.interactor;

import com.example.homework.domain.repository.ContactsRepository;
import com.example.homework.domain.model.ShortContact;

import java.util.List;

import io.reactivex.Single;

public class ContactsInteractorImpl implements ContactsInteractor {
    private ContactsRepository contactsRepository;

    public ContactsInteractorImpl(ContactsRepository contactsRepository){
        this.contactsRepository = contactsRepository;
    }

    @Override
    public Single<List<ShortContact>> getContacts() {
        return contactsRepository.getContacts();
    }

    @Override
    public Single<List<ShortContact>> getContacts(String searchText) {
        return contactsRepository.getContacts(searchText);
    }
}
