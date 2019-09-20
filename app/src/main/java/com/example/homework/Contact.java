package com.example.homework;

import android.graphics.Bitmap;

public class Contact {
    private String Name;
    private String Phone;
    private String Email;
    private Bitmap mBitmap;


    public Contact(String name, String phone, String email,Bitmap bitmap) {
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


}
