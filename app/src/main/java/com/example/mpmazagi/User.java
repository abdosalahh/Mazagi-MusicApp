package com.example.mpmazagi;

public class User {

    int id ;
    String email;
    String image;
    String password;

    public User(  String name, String password) {

        this.email = name;
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }

    public String getPassword() {
        return password;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
