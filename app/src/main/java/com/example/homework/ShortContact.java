package com.example.homework;

public class ShortContact {
    private String Id;
    private String Name;
    private String Phone;

    public ShortContact(String id, String name, String phone) {
        Id = id;
        Name = name;
        Phone = phone;
    }

    //Getter & Setter
    public String getId() {
        return Id;
    }

    public void setId(String id) {
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
