package com.example.homework.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.homework.domain.model.ShortContact;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ShortContactDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ShortContact> shortContactList);

    @Query("SELECT * FROM ShortContact WHERE Name || Phone LIKE '%' || :searchText || '%'")
    Single<List<ShortContact>> getSearchShortContact(String searchText);

    @Query("SELECT * FROM ShortContact")
    Single<List<ShortContact>> getAllShortContact();



}
