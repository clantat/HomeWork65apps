package com.example.homework.data.repository;

import com.example.homework.domain.model.Contact;
import com.example.homework.domain.model.ShortContact;

import java.util.List;

import io.reactivex.Single;

public interface ContactsRepository {

    Single<List<ShortContact>> getContacts(String searchText);

    Single<Contact> getInfoContact(String Id);
}
