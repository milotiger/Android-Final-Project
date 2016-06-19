package com.example.hmtri1312624.foodyapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hmtri1312624.foodyapp.Model.FoodyMenuItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by M-Tae on 6/19/2016.
 */
public class RVMAdapter extends RecyclerView.Adapter<RVMAdapter.ViewHolder>{
    List<FoodyMenuItem> data;
    Context mContext;
    CustomItemClickListener listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_layout, parent, false);
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
        holder.setItem(data.get(position));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public RVMAdapter(Context mContext, List<FoodyMenuItem> data, CustomItemClickListener listener) {
        this.data = data;
        this.mContext = mContext;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView foodName,foodPrice;
        public ImageView foodAva;

        ViewHolder(View v) {
            super(v);
            foodAva = (ImageView)v.findViewById(R.id.menuAva);
            foodName = (TextView) v
                    .findViewById(R.id.txtmenuName);
            foodPrice = (TextView)v.findViewById(R.id.txtmenuPrice);

            foodName.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),"Roboto-Medium.ttf"));
            foodPrice.setTypeface(FontManager.getTypeface(MyApplication.getAppContext(),FontManager.ROBOTO));
        }

        public void setItem(FoodyMenuItem item) {
            foodName.setText(item.Name);
            foodPrice.setText(item.Price);
            LoadAva(item.ImageUrl);
        }

        private void LoadAva(String urlAva) {
            Picasso.with(MyApplication.getAppContext())
                    .load("http:" + urlAva)
                    .into(foodAva);
        }
    }
}
