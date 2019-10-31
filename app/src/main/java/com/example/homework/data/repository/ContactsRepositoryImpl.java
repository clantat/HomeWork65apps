package com.example.homework.data.repository;

import com.example.homework.data.provider.ContactsProvider;
import com.example.homework.domain.model.Contact;
import com.example.homework.domain.model.ShortContact;
import com.example.homework.domain.repository.ContactsRepository;

import java.util.List;

import io.reactivex.Single;

public class ContactsRepositoryImpl implements ContactsRepository {
    private final ContactsProvider contactsProvider;

    public ContactsRepositoryImpl(ContactsProvider contactsProvider){
        this.contactsProvider = contactsProvider;
    }

    @Override
    public Single<List<ShortContact>> getContacts(String searchText) {
        return contactsProvider.getContacts(searchText);
    }

    @Override
    public Single<Contact> getInfoContact(String Id) {
        return contactsProvider.getInfoContact(Id);
    }
}
