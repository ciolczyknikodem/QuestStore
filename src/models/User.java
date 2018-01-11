package models;

import java.io.Serializable;

public abstract class User implements Serializable{
    private String name;
    private String login;
    private String password;
    private String email;
    private String phoneNumber;

    User(String name, String login, String password, String email, String phoneNumber) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String toString(){
        return  "\nName: " + name +
                "\nLogin: " + login +
                "\nEmail: " + email +
                "\nPhone number: " + phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
