package com.example.homework.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import io.reactivex.Single;

@Dao
public interface MapContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMapContact(MapContact mapContact);

    @Query("SELECT * FROM MapContact WHERE MapContact.id LIKE :contactId")
    Single<MapContact> getMapContact(String contactId);

    @Update
    void updateMapContact(MapContact mapContact);

}
