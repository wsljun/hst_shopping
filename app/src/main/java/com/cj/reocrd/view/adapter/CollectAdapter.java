package com.cj.reocrd.view.adapter;

import android.view.View;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.base.baseadapter.BaseViewHolder;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.utils.ImageLoaderUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/4/20.
 */
public class CollectAdapter extends BaseQuickAdapter {
    private GoodsBean cartGoods; // 购物车 商品集合
    private int item_layoutid;
    private boolean isCollect;

    public CollectAdapter(int layoutResId, List data, boolean isCollect) {
        super(layoutResId, data);
        this.item_layoutid = layoutResId;
        this.isCollect = isCollect;
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        cartGoods = (GoodsBean) item;
        ImageLoaderUtils.display(mContext, helper.getView(R.id.collect_img), UrlConstants.BASE_URL + cartGoods.getImgurl());
        helper.setText(R.id.collect_name, cartGoods.getName());
        helper.setText(R.id.collect_price, "￥" + cartGoods.getPrice());
        if (isCollect) {
            helper.getView(R.id.collect_delete).setVisibility(View.VISIBLE);
            helper.getView(R.id.collect_car).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.collect_delete).setVisibility(View.GONE);
            helper.getView(R.id.collect_car).setVisibility(View.VISIBLE);
        }
        helper.getView(R.id.collect_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseItemClickListener.onAdapterItemClickListener(v, position);
            }
        });
        helper.getView(R.id.collect_car).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseItemClickListener.onAdapterItemClickListener(v, position);
            }
        });

    }
}
