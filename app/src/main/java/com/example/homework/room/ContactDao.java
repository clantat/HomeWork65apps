package com.example.homework.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.homework.domain.model.Contact;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContact(Contact contact);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Contact> contacts);

    @Query("SELECT * FROM Contact WHERE Contact.Id = :shortContactId")
    Single<Contact> getContact(String shortContactId);
}
