package com.example.homework.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.homework.domain.model.Contact;
import com.example.homework.domain.model.ShortContact;

@Database(entities = {Contact.class, ShortContact.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
    public abstract ShortContactDao shortContactDao();
}
