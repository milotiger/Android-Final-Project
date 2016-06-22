package com.example.hmtri1312624.foodyapp.Model;

/**
 * Created by Anh Tu Vo on 06/21/2016.
 */
public class Account {
    public Account(String id,String first_name, String last_name, String gender, String email, String birthday, String location) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.email = email;
        this.birthday = birthday;
        this.location = location;
        userid = id;
    }

    public Account() {

    }

    public String first_name;
    public String last_name;
    public String gender;
    public String email;
    public String birthday;
    public String location;
    public String userid;
}
