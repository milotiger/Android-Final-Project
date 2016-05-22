package com.example.hmtri1312624.foodyapp;

/**
 * Created by M-Tae on 5/22/2016.
 */
public class ImageViewSingleItem {
    private String URL;
    public long id;

    public ImageViewSingleItem(long id, String url)
    {
        this.id = id;
        this.URL = url;
    }

    public long getID() {
        return id;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
