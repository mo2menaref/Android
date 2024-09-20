package com.example.chatme;

public class UserModel {
String Id,Name,Password,Email;
    public UserModel() {
    }

    public UserModel(String uid, String name,String email, String password) {
        Id=uid;
        Name=name;
        Email=email;
        Password=password;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getId() {
        return Id;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getEmail() {
        return Email;
    }
}
