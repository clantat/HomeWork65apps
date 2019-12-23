package com.example.homework.domain.model;

import android.graphics.Bitmap;

public class Contact {
    private String Id;
    private String Name;
    private String Phone;
    private String Email;
    private Bitmap mBitmap; //TODO переделать в массив byte[]


    public Contact(String id, String name, String phone, String email, Bitmap bitmap) {
        Id = id;
        Name = name;
        Phone = phone;
        Email = email;
        mBitmap = bitmap;
    }

    //Getter
    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public String getName() {
        return Name;
    }

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


    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
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

    public void setId(String id) {
        Id = id;
    }
}
