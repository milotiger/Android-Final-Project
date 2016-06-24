package com.example.hmtri1312624.foodyapp.Model;

/**
 * Created by M-Tae on 5/22/2016.
 */
public class FoodyMenuItem {
    public String ImageUrl;
    public String Price;
    public String Name;

    public FoodyMenuItem(String avatar, String price, String name)
    {
        ImageUrl = avatar;
        Price = price;
        Name = name;
    }
}
