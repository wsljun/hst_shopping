package com.cj.reocrd.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.model.entity.FuliBean;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.utils.ImageLoaderUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeShhhAdapter extends RecyclerView.Adapter<HomeShhhAdapter.MyHolder> {


    private List<GoodsBean> list;
    private Context mContext;
    private LayoutInflater inflater;

    public HomeShhhAdapter(Context context, List<GoodsBean> list) {
        this.mContext = context;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(inflater.inflate(R.layout.item_good, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        GoodsBean goodsBean = list.get(position);
        ImageLoaderUtils.display(mContext, holder.goodPic, UrlConstants.BASE_URL + goodsBean.getImgurl());
        holder.goodName.setText(goodsBean.getName());
        holder.goodPrice.setText("会员价￥" + goodsBean.getPrice());
        holder.goodOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.goodOldPrice.setText("零售价："+goodsBean.getOldprice());
        holder.goodPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.doDetailClick(position);
            }
        });
        holder.homeCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.doCarClick(position);
            }
        });
        holder.homeShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.doShareClick(position);
            }
        });
    }

    private OnItemListener mOnItemListener;

    public void setOnItemListener(OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    public interface OnItemListener {
        void doDetailClick(int position);

        void doCarClick(int position);

        void doShareClick(int position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.good_ll)
        LinearLayout goodLl;
        @BindView(R.id.good_name)
        TextView goodName;
        @BindView(R.id.good_price)
        TextView goodPrice;
        @BindView(R.id.good_old_price)
        TextView goodOldPrice;
        @BindView(R.id.home_car)
        ImageView homeCar;
        @BindView(R.id.home_share)
        ImageView homeShare;
        @BindView(R.id.good_pic)
        ImageView goodPic;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
