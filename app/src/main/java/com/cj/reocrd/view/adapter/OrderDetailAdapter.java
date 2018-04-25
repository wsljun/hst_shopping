package com.cj.reocrd.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.model.entity.OrderBean;
import com.cj.reocrd.model.entity.OrderDetail;
import com.cj.reocrd.utils.CollectionUtils;
import com.cj.reocrd.utils.ConstantsUtils;
import com.cj.reocrd.utils.ImageLoaderUtils;

import java.util.List;

/**
 * Created by Lyndon.Li on 2018/4/21.
 */

public class OrderDetailAdapter extends  RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>
{

    private LayoutInflater mInflater;
    private List<OrderBean.OdlistBean> mDatas;
    private Context mContext ;

    public OrderDetailAdapter(Context context, List<OrderBean.OdlistBean> datats)
    {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas =  datats;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        ImageView mImg;
        TextView tvGoodsName,tvGoodsUnit,tvGoodsNum,tvTotalPrice;
    }

    public void updateData(List<OrderBean.OdlistBean> data){
        if(!CollectionUtils.isNullOrEmpty(mDatas)){
           mDatas.clear();
        }
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_order_detail_gallery_item,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mImg = (ImageView) view
                .findViewById(R.id.id_order_detail_item_image);
        viewHolder.tvGoodsName = view.findViewById(R.id.tv_goods_name);
        viewHolder.tvGoodsUnit = view.findViewById(R.id.tv_goods_unit);
        viewHolder.tvGoodsNum = view.findViewById(R.id.tv_goods_num);
        viewHolder.tvTotalPrice = view.findViewById(R.id.tv_total_price);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i)
    {
        ImageLoaderUtils.display(mContext,  viewHolder.mImg, UrlConstants.BASE_URL+mDatas.get(i));
        viewHolder.tvGoodsName.setText(mDatas.get(i).getMname());
        viewHolder.tvGoodsUnit.setText(mDatas.get(i).getSpec());
        viewHolder.tvGoodsNum.setText("x"+mDatas.get(i).getNum());
        viewHolder.tvTotalPrice.setText(ConstantsUtils.RMB+mDatas.get(i).getTotalPrice());
    }

}