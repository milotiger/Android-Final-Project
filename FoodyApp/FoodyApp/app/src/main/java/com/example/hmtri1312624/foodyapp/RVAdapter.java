package com.example.hmtri1312624.foodyapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.example.hmtri1312624.foodyapp.Model.CommentDetail;
import com.example.hmtri1312624.foodyapp.Model.FoodyItemInfo;
import com.example.hmtri1312624.foodyapp.Service.RestService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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
        String Address = data.get(position).AddressLv1 + " " + data.get(position).AddressLv2 + " " + data.get(position).AddressLv3;
        List<String> urls = data.get(position).MorePic;
        String Country = "";
        String urlAva = data.get(position).Thumbnail;
        List<CommentDetail> cmts = data.get(position).CommentDetails;
        for(int i = 0; i < data.get(position).Tag.size(); i++)
        {
            Country += data.get(position).Tag.get(i);
            Country += " ";
        }

        holder.setItem(urlAva,data.get(position).Headline,Address,Country,urls,cmts);
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
        public TextView txtName;
        public TextView txtAddress;
        public TextView txtCountry;
        public ImageView imageAva;
        public Button btnLove;
        public Button btnLocation;
        public Button btnCmt;
        public Button btnCamera;
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
            btnLove = (Button)v.findViewById(R.id.btnLove);
            btnLocation = (Button)v.findViewById(R.id.btnLocation);
            btnCmt = (Button)v.findViewById(R.id.btnCmt);
            btnCamera = (Button)v.findViewById(R.id.btnCamera);

            layout = (LinearLayout)v.findViewById(R.id.listimage);

            txtName.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.ROBOTO));
            txtAddress.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.ROBOTO));
            txtCountry.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.ROBOTO));

            btnLove.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.FONTAWESOME));
            btnLocation.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.FONTAWESOME));
            btnCmt.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.FONTAWESOME));
            btnCamera.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.FONTAWESOME));
            context = v.getContext();
        }

        public void setItem(String urlAva, String name, String address, String country, List<String> morepics, final List<CommentDetail> cmts) {
            txtName.setText(name);
            txtAddress.setText(address);
            txtCountry.setText(country);
            LoadAva(urlAva);
            LoadImage(morepics);
            btnCmt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowComment(cmts);
                }
            });
        }

        private void ShowComment(List<CommentDetail> cmts)
        {
            Dialog dialog = new Dialog(context);
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
            dialog.show();
        }

        private void LoadAva(String urlAva) {
            Picasso.with(MyApplication.getAppContext())
                    .load("http:" + urlAva)
                    .into(imageAva);
        }

        public void LoadImage(List<String> urls)
        {
            for (int i = 0; i < urls.size(); i++) {
                ImageView imageView = new ImageView(MyApplication.getAppContext());
                imageView.setId(i);

                imageView.setPadding(5,5,5,5);
                Picasso.with(MyApplication.getAppContext())
                        .load("http:" + urls.get(i).toString())
                        .into(imageView);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 200);
                imageView.setLayoutParams(layoutParams);

                layout.addView(imageView);
            }
        }
    }
}
