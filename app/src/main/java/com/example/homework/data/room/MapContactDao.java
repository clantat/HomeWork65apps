package com.example.homework.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface MapContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMapContact(MapContact mapContact);

    @Query("SELECT * FROM MapContact WHERE MapContact.id LIKE :contactId")
    Single<MapContact> getMapContact(String contactId);

    @Query("SELECT * FROM MapContact")
    Single<List<MapContact>> getAllMapContact();

}
