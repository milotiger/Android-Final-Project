package com.example.hmtri1312624.foodyapp;

/**
 * Created by M-Tae on 4/7/2016.
 */
public class ItemsListSingleItem {
    public String Name,Sex;
    public long id;

    public ItemsListSingleItem(long id, String name, String sex) {
        this.id = id;
        this.Name = name;
        this.Sex = sex;
    }

    public String getName() {
        return Name;
    }

    public long getID() {
        return id;
    }

    public String getSex(){
        return Sex;
    }
}