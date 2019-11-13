package com.example.homework.domain.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ShortContact {
    @NonNull
    @PrimaryKey
    private String Id;
    private String Name;
    private String Phone;

    public ShortContact() {
    }

    public ShortContact(@NonNull String id, String name, String phone) {
        Id = id;
        Name = name;
        Phone = phone;
    }

    //Getter & Setter
    @NonNull
    public String getId() {
        return Id;
    }

    public void setId(@NonNull String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
