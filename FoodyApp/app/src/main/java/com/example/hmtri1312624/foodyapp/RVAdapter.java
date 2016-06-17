package com.example.hmtri1312624.foodyapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hmtri1312624.foodyapp.Global.Global;
import com.example.hmtri1312624.foodyapp.ImageGallery.GalleryActivity;
import com.example.hmtri1312624.foodyapp.Model.CommentDetail;
import com.example.hmtri1312624.foodyapp.Model.FoodyItemInfo;
import com.example.hmtri1312624.foodyapp.Service.RestService;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by M-Tae on 4/7/2016.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder>{

    List<FoodyItemInfo> data;
    Context mContext;
    CustomItemClickListener listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        final ViewHolder mViewHolder = new ViewHolder(mView);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getPosition());
            }
        });
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String Address = data.get(position).AddressLv1 + ", " + data.get(position).AddressLv2 + ", " + data.get(position).AddressLv3;
        String Country = "";
        for(int i = 0; i < data.get(position).Tag.size(); i++)
        {
            Country += data.get(position).Tag.get(i);
            if(i < data.get(position).Tag.size() - 1)
                Country += ", ";
        }
        String time = "";
        for(int i = 0; i < data.get(position).OpenTime.size(); i++)
        {
            time += data.get(position).OpenTime.get(i);
            if(i < data.get(position).OpenTime.size() - 1)
                time += " - ";
        }
        String stt = ""; // will change in holder.setItem method;

        holder.setItem(data.get(position),Address,Country,time,stt);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public RVAdapter(Context mContext, List<FoodyItemInfo> data, CustomItemClickListener listener) {
        this.data = data;
        this.mContext = mContext;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtAddress,txtCountry,txtTimeOpen,txtStt,txtNumCmt,txtNumCamera,txtRate, txtPrice;
        public ImageView imageAva;
        public Button btnLove, btnLocation, btnCmt, btnCamera, btnStar;
        public LinearLayout layout;
        public RecyclerView rvcmt;
        public RVCAdapter Cadapter;
        public Context context;

        ViewHolder(View v) {
            super(v);
            imageAva = (ImageView)v.findViewById(R.id.imageAva);
            txtName = (TextView) v
                    .findViewById(R.id.txtName);
            txtAddress = (TextView)v.findViewById(R.id.txtAddress);
            txtCountry = (TextView)v.findViewById(R.id.txtCountry);
            txtNumCmt = (TextView)v.findViewById(R.id.txtNumCmt);
            txtNumCamera = (TextView)v.findViewById(R.id.txtNumCamera);

            txtTimeOpen = (TextView)v.findViewById(R.id.txtTimeOpen);
            txtStt = (TextView)v.findViewById(R.id.txtStatus);
            txtRate = (TextView)v.findViewById(R.id.txtRate);
            txtPrice = (TextView)v.findViewById(R.id.txtPrice);

            btnLove = (Button)v.findViewById(R.id.btnLove);
            btnLocation = (Button)v.findViewById(R.id.btnLocation);
            btnCmt = (Button)v.findViewById(R.id.btnCmt);
            btnCamera = (Button)v.findViewById(R.id.btnCamera);
            btnStar = (Button)v.findViewById(R.id.btnStar);

            layout = (LinearLayout)v.findViewById(R.id.listimage);

            txtName.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.ROBOTO));
            txtAddress.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.ROBOTO));
            txtCountry.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.ROBOTO));
            txtPrice.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),"Roboto-Medium.ttf"));

            txtTimeOpen.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.ROBOTO));
            txtStt.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),"Roboto-Medium.ttf"));
            txtRate.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),"Roboto-Medium.ttf"));

            btnLove.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.FONTAWESOME));
            btnLocation.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.FONTAWESOME));
            btnCmt.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.FONTAWESOME));
            btnCamera.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.FONTAWESOME));
            btnStar.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.FONTAWESOME));

            //Important line, fix Unable to add window — token null is not valid for dialog
            // dont use AppContext for new Dialog, should use ...Activity.this
            context = v.getContext();
        }

        public void setItem(final FoodyItemInfo data, String address, String country, String time, String stt) {
            txtName.setText(data.Headline);
            txtAddress.setText(address);
            txtCountry.setText(country);
            txtNumCmt.setText(data.Comments);
            txtNumCamera.setText(data.Pictures);
            txtTimeOpen.setText(time);
            txtRate.setText(data.Rating);

            String Times[] = time.split(" - ");

            if (Times.length == 2) {
                String openTime = Times[0];
                String closeTime = Times[1];

                if (!isOpen(openTime, closeTime)) {
                    stt = "Closed now";
                    txtStt.setTextColor(Color.parseColor("#F44336"));
                } else {
                    stt = "Opened now";
                    txtStt.setTextColor(Color.parseColor("#0cce2c"));
                }
            }
            else stt = "N/A";

            if (data.Price.size() == 2)
            {
                txtPrice.setText(data.Price.get(0) + "đ - " + data.Price.get(1) + "đ");
            }


            txtStt.setText(stt);
            LoadAva(data.Thumbnail);
            LoadImage(data.MorePic, data.MorePic_Full);
            btnCmt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowComment(data.CommentDetails);
                }
            });
            btnCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RestService restService = new RestService();

                    Call<FoodyItemInfo> Result = restService.getService().GetAlbum(data);

                    Result.enqueue(new Callback<FoodyItemInfo>() {
                        @Override
                        public void onResponse(Call<FoodyItemInfo> call, Response<FoodyItemInfo> response) {
                            Global.currentImageList = response.body().FullSizePics;

                            Intent i = new Intent(context, GalleryActivity.class);
                            context.startActivity(i);
                        }

                        @Override
                        public void onFailure(Call<FoodyItemInfo> call, Throwable t) {

                        }
                    });
                }
            });
            btnLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, MapsActivity.class);
                    context.startActivity(i);
                }
            });
        }

        private void ShowComment(List<CommentDetail> cmts)
        {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.cmt_rv_layout);
            dialog.setTitle("Top Comment");

            rvcmt = (RecyclerView) dialog.findViewById(R.id.rvcmt);
            rvcmt.setHasFixedSize(true);

            LinearLayoutManager llm = new LinearLayoutManager(MyApplication.getAppContext());
            rvcmt.setLayoutManager(llm);

            Cadapter = new RVCAdapter(MyApplication.getAppContext(), cmts, new CustomCmtClickListener(){
                @Override
                public void onCmtClick(View v, int position) {
                    Log.d("", "clicked position:" + position);
                    // do what ever you want to do with it
                }
            });

            rvcmt.setAdapter(Cadapter);

            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }

        private void LoadAva(String urlAva) {
            Picasso.with(MyApplication.getAppContext())
                    .load("http:" + urlAva)
                    .into(imageAva);
        }

        public void LoadImage(final List<String> urls, final List<String> Highurl)
        {
            for (int i = 0; i < urls.size(); i++) {
                final ImageView imageView = new ImageView(MyApplication.getAppContext());
                imageView.setId(i);


                imageView.setPadding(5,5,5,5);
                Picasso.with(MyApplication.getAppContext())
                        .load("http:" + urls.get(i).toString())
                        .into(imageView);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(120, 120);
                imageView.setLayoutParams(layoutParams);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(context,"ABC",Toast.LENGTH_LONG).show();
                        showFullImage(Highurl, v.getId());
                    }
                });

                layout.addView(imageView);
            }
        }

        private void showFullImage(final List<String> urlImages,final int Index){
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom_fullimage_dialog);
            final int CurrIndex = Index;


            final ImageView imageShow = (ImageView) dialog.findViewById(R.id.fullimage);

            //imageShow.setPadding(5,5,5,5);
            Picasso.with(MyApplication.getAppContext())
                    .load("http:" + urlImages.get(CurrIndex))
                    .into(imageShow);

            Button CloseButton = (Button) dialog.findViewById(R.id.btnClose);
            // if button is clicked, close the custom dialog
            CloseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            final Button NextButton = (Button) dialog.findViewById(R.id.btnNext);
            NextButton.setId(Index + 1);
            NextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int Index = v.getId();
                    if (Index < urlImages.size()) {
                        Picasso.with(MyApplication.getAppContext())
                                .load("http:" + urlImages.get(Index))
                                .into(imageShow);
                        if (Index == urlImages.size() - 1)
                            v.setId(0);
                        else v.setId(++Index);
                    }
                }
            });

            final Button PrevButton = (Button) dialog.findViewById(R.id.btnPrev);
            PrevButton.setId(Index - 1);
            PrevButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int Index = v.getId();
                    if (Index >= 0) {
                        Picasso.with(MyApplication.getAppContext())
                                .load("http:" + urlImages.get(Index))
                                .into(imageShow);
                        if (Index == 0)
                            v.setId(urlImages.size() - 1);
                        else v.setId(--Index);
                    }
                }
            });


            dialog.show();
        }
        private boolean isOpen(String openTime, String closeTime){
            // returns a TimeZone based on the time zone where the program is running.
            //(TimeZone.getDefault())
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00"));
            Date currentLocalTime = cal.getTime();
            DateFormat date = new SimpleDateFormat("KK:mm a");

            date.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
            String localTime = date.format(currentLocalTime);
            if(TimeDistance(openTime,closeTime) < 0) // Ex: 6:00 AM - 3:00 AM (21 hours)
            {
                if(TimeDistance(localTime,closeTime) > 0 && TimeDistance(localTime,openTime) < 0)
                    return true;
                else
                    return false;
            }
            else if(TimeDistance(openTime,localTime) > 0 && TimeDistance(localTime,closeTime) > 0)
                return true;
            return false;
        }

        private int ConvertToSecond (String Time){
            int second = 0;
            //Time HH:MM AM
            String units1[] = Time.split(":");
            String part1 = units1[0]; // HH
            String part2 = units1[1]; // MM AM

            String units2[] = units1[1].split(" ");
            String part3 = units2[0];// MM
            String part4 = units2[1];// AM or PM or SA or CH //Vietnamese and English case;

            int hour = Integer.parseInt(part1);
            int mm = Integer.parseInt(part3);

            if(part4.compareTo("PM") == 0 || part4.compareTo("CH") == 0)
                hour = hour + 12;

            second = hour * 3600 + mm * 60;
            return second;
        }

        private int TimeDistance(String Start, String End)
        {
            int secondTime1 = ConvertToSecond(Start);
            int secondTime2 = ConvertToSecond(End);
            return secondTime2 - secondTime1;
        }
    }

}
