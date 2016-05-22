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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by M-Tae on 4/7/2016.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder>{

    ArrayList<ItemsListSingleItem> data;
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
        holder.setItem(data.get(position).Name,data.get(position).Sex);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public RVAdapter(Context mContext, ArrayList<ItemsListSingleItem> data, CustomItemClickListener listener) {
        this.data = data;
        this.mContext = mContext;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTitle;
        public TextView sex;
        public Button btnAdd;
        public LinearLayout layout;

        ViewHolder(View v) {
            super(v);
            itemTitle = (TextView) v
                    .findViewById(R.id.Title);
            sex = (TextView)v.findViewById(R.id.Sex);
            btnAdd = (Button)v.findViewById(R.id.btnAdd);
            layout = (LinearLayout)v.findViewById(R.id.listimage);

            itemTitle.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.ROBOTO));
            sex.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.ROBOTO));
            btnAdd.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.FONTAWESOME));

        }

        public void setItem(String item, String item2) {
            itemTitle.setText(item);
            sex.setText(item2);
            LoadImage();
        }

        public void LoadImage()
        {
            for (int i = 0; i < 10; i++) {
                ImageView imageView = new ImageView(MyApplication.getAppContext());
                imageView.setId(i);

                imageView.setPadding(5,5,5,5);
                Picasso.with(MyApplication.getAppContext())
                        .load("https://media.foody.vn/res/g5/49095/prof/s320x200/foody-mobile-banh-trang-long-an-banh-trang-thuy-tp-hcm-140307032944.jpg")
                        .into(imageView);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(600, 600);
                imageView.setLayoutParams(layoutParams);

                layout.addView(imageView);
            }
        }
    }
}
