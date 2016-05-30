package com.example.hmtri1312624.foodyapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hmtri1312624.foodyapp.Model.CommentDetail;
import com.example.hmtri1312624.foodyapp.Model.FoodyItemInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by M-Tae on 5/22/2016.
 */
public class RVCAdapter extends RecyclerView.Adapter<RVCAdapter.ViewHolder>{

    List<CommentDetail> data;
    Context mContext;
    CustomCmtClickListener listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cmt_layout, parent, false);
        final ViewHolder mViewHolder = new ViewHolder(mView);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCmtClick(v, mViewHolder.getPosition());
            }
        });
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = data.get(position).Title;
        String content = data.get(position).Content;
        String username = data.get(position).UserName;
        String time = data.get(position).Time;

        holder.setItem(data.get(position).UserAva,title,content,username,time);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public RVCAdapter(Context mContext, List<CommentDetail> data, CustomCmtClickListener listener) {
        this.data = data;
        this.mContext = mContext;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public TextView txtContent;
        public TextView txtUsername;
        public TextView txtTime;
        public ImageView imageAva;

        ViewHolder(View v) {
            super(v);
            imageAva = (ImageView)v.findViewById(R.id.imageAvaUser);
            txtTitle = (TextView) v
                    .findViewById(R.id.txtTitle);
            txtContent = (TextView)v.findViewById(R.id.txtContent);
            txtUsername = (TextView)v.findViewById(R.id.txtUsername);
            txtTime = (TextView)v.findViewById(R.id.txtTime);

            txtTitle.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.ROBOTO));
            txtContent.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.ROBOTO));
            txtUsername.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.ROBOTO));
            txtTime.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.ROBOTO));
        }

        public void setItem(String urlAva, String title, String content, String username, String time) {
            txtTitle.setText(title);
            txtContent.setText(content);
            txtUsername.setText(username);
            txtTime.setText(time);
            LoadAva(urlAva);
        }

        private void LoadAva(String urlAva) {
            Picasso.with(MyApplication.getAppContext())
                    .load(urlAva)
                    .into(imageAva);
        }
    }
}
