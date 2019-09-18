package com.example.homework;

import android.graphics.Bitmap;

public class Contact {
    private String Name;
    private String Phone;
    private String Email;
    private int Photo;
    private Bitmap mBitmap;

    public Contact() {
    }

    public Contact(String name, String phone, String email, int photo) {
        Name = name;
        Phone = phone;
        Email = email;
        Photo = photo;
    }

    public Contact(String name, String phone, String email) {
        Name = name;
        Phone = phone;
        Email = email;

    }
    public Contact(String name, String phone, String email,Bitmap bitmap) {
        Name = name;
        Phone = phone;
        Email = email;
        mBitmap = bitmap;


    }
    public Contact(String name, String phone) {
        Name = name;
        Phone = phone;
    }
    public Contact(String name, String phone, int photo) {
        Name = name;
        Phone = phone;
        Photo = photo;
    }
    //Getter
    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public String getName() {
        return Name;
    }

    public String getPhone() {
        return Phone;
    }

    public String getEmail() {
        return Email;
    }

    public int getPhoto() {
        return Photo;
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

    public void setPhoto(int photo) {
        Photo = photo;
    }


}
