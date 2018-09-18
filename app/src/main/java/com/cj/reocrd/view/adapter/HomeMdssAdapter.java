package com.cj.reocrd.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.utils.ImageLoaderUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeMdssAdapter extends RecyclerView.Adapter<HomeMdssAdapter.MyHolder> {


    private List<GoodsBean> list;
    private Context mContext;
    private LayoutInflater inflater;

    public HomeMdssAdapter(Context context, List<GoodsBean> list) {
        this.mContext = context;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(inflater.inflate(R.layout.item_good_mdss, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        GoodsBean goodsBean = list.get(position);
        ImageLoaderUtils.display(mContext, holder.mdssPic, UrlConstants.BASE_URL + goodsBean.getImgurl());
        holder.mdssPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.doDetailClick(position);
            }
        });
    }

    private OnItemListener mOnItemListener;

    public void setOnItemListener(OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    public interface OnItemListener {
        void doDetailClick(int position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mdss_ll)
        LinearLayout mdssLl;
        @BindView(R.id.mdss_pic)
        ImageView mdssPic;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
