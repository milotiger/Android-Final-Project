package com.example.hmtri1312624.foodyapp.Model;

import java.util.List;

/**
 * Created by M-Tae on 6/19/2016.
 */
public class FoodyMenuSet {
    public String Name;
    public List<FoodyMenuItem> Dishes;

    public FoodyMenuSet(String setName, List<FoodyMenuItem> items)
    {
        Name = setName;
        Dishes = items;
    }


}
