package com.example.homework.domain.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.ByteArrayInputStream;

@Entity
public class Contact {
    @NonNull
    @PrimaryKey
    private String Id;
    private String Name;
    private String Phone;
    private String Email;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] imageBites;
    private long Coordination;

    public Contact() {
    }

    public Contact(@NonNull String id, String name, String phone, String email, byte[] image) {
        Id = id;
        Name = name;
        Phone = phone;
        Email = email;
        imageBites = image;
    }


    //Getter

    public Bitmap getBitmap() {
        if (this.imageBites != null)
            return BitmapFactory.decodeStream(new ByteArrayInputStream(this.imageBites));
        else return null;
    }

    public long getCoordination() {
        return Coordination;
    }

    public byte[] getImageBites() {
        return this.imageBites;
    }

    public String getName() {
        return Name;
    }

    @NonNull
    public String getId() {
        return Id;
    }

    public String getPhone() {
        return Phone;
    }

    public String getEmail() {
        return Email;
    }


    //Setter

    public void setCoordination(long coordination) {
        Coordination = coordination;
    }

    public void setImageBites(byte[] bytes) {
        imageBites = bytes;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setId(@NonNull String id) {
        Id = id;
    }
}
