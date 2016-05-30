package com.example.hmtri1312624.foodyapp.Model;

import java.util.List;

/**
 * Created by M-Tae on 5/22/2016.
 */
public class FoodyItemInfo {
    public String DetailUrl;
    public String Headline;
    public String Thumbnail;
    public String AddressLv1;
    public String AddressLv2;
    public String AddressLv3;
    public String Rating;
    public String Comments;
    public String Pictures;
    public List<String> Tag;
    public List<String> OpenTime;
    public List<String> Price;
    public List<String> MorePic;
    public List<String> MorePic_Full;
    public List<FoodyMenuItem> MenuItems;
    public List<CommentDetail> CommentDetails;
    public String Phone;
    public long ID;

    public FoodyItemInfo(long id, String detailUrl, String headline, String thumbnail, String addressLv1, String addressLv2, String addressLv3, String rating, String comments, String pictures, List<String> tag, List<String> openTime, List<String> price, List<String> morePic , List<String> morePic_Full, List<FoodyMenuItem> menuItems, List<CommentDetail> commentDetails, String phone) {
        ID = id;
        DetailUrl = detailUrl;
        Headline = headline;
        Thumbnail = thumbnail;
        AddressLv1 = addressLv1;
        AddressLv2 = addressLv2;
        AddressLv3 = addressLv3;
        Rating = rating;
        Comments = comments;
        Pictures = pictures;
        Tag = tag;
        OpenTime = openTime;
        Price = price;
        MorePic = morePic;
        MorePic_Full = morePic_Full;
        MenuItems = menuItems;
        CommentDetails = commentDetails;
        Phone = phone;
    }

    public long getID() {
        return ID;
    }
}
