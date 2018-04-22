package com.cj.reocrd.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.model.entity.OrderBean;
import com.cj.reocrd.utils.ImageLoaderUtils;

import java.util.List;

/**
 * Created by Lyndon.Li on 2018/4/21.
 */

public class GalleryAdapter  extends  RecyclerView.Adapter<GalleryAdapter.ViewHolder>
{

    private LayoutInflater mInflater;
    private List<OrderBean.OdlistBean> mDatas;
    private Context mContext ;

    public GalleryAdapter(Context context, List<OrderBean.OdlistBean> datats)
    {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View arg0)
        {
            super(arg0);
        }

        ImageView mImg;
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.order_gallery_item,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mImg = (ImageView) view
                .findViewById(R.id.id_index_gallery_item_image);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i)
    {
        ImageLoaderUtils.display(mContext,  viewHolder.mImg, UrlConstants.BASE_URL+mDatas.get(i).getImgurl());
    }

}