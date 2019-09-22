package com.example.homework;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {
    private String Name;
    private String Phone;
    private String Email;
    private Bitmap mBitmap;


    public Contact(String name, String phone, String email, Bitmap bitmap) {
        Name = name;
        Phone = phone;
        Email = email;
        mBitmap = bitmap;
    }

    protected Contact(Parcel in) {
        Name = in.readString();
        Phone = in.readString();
        Email = in.readString();
        mBitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Name);
        parcel.writeString(Phone);
        parcel.writeString(Email);
        parcel.writeParcelable(mBitmap, i);
    }
}
