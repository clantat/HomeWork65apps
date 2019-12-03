package com.example.homework.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = MapContact.class, version = 1)
public abstract class MapDatabase extends RoomDatabase {
    public abstract MapContactDao mapContactDao();
}
