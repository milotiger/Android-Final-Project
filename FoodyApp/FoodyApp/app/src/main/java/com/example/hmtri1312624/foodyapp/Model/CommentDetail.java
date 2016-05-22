package com.example.hmtri1312624.foodyapp.Model;

/**
 * Created by M-Tae on 5/22/2016.
 */
public class CommentDetail {
    public String UserAva;
    public String Title;
    public String Content;
    public String ContentPic;
    public String UserName;
    public String Time;

    public CommentDetail(String userAva, String time, String title, String content, String contentPic, String userName) {
        UserAva = userAva;
        Time = time;
        Title = title;
        Content = content;
        ContentPic = contentPic;
        UserName = userName;
    }
}
