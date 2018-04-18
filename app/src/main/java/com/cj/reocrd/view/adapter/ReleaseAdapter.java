package com.cj.reocrd.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.model.entity.Friends;
import com.cj.reocrd.model.entity.FriendsBean;
import com.cj.reocrd.utils.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * 圈子图片显示
 */
public class ReleaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity context;
    private final LayoutInflater inflater;
    private List<String> strList = new ArrayList<>();

    public ReleaseAdapter(Activity context, List<Friends.UrlBean> mList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        for (Friends.UrlBean urlBean : mList) {
            strList.add(UrlConstants.BASE_URL + urlBean.getUrl());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(inflater.inflate(R.layout.release_pic_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindItemMyHolder((MyHolder) holder, position);
    }


    private void bindItemMyHolder(MyHolder holder, int position) {
        ImageLoaderUtils.display(context, holder.imageview, strList.get(position));
        holder.imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPreview.builder()
                        .setPhotos((ArrayList<String>) strList)
                        .setCurrentItem(position)
                        .setShowDeleteButton(false)
                        .start(context);
            }
        });
    }


    @Override
    public int getItemCount() {
        return strList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private final ImageView imageview;

        public MyHolder(View itemView) {
            super(itemView);
            imageview = (ImageView) itemView.findViewById(R.id.imageview);
        }
    }
}
