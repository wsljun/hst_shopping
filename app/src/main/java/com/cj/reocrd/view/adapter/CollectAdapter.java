package com.cj.reocrd.view.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.base.baseadapter.BaseViewHolder;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.utils.ImageLoaderUtils;
import com.cj.reocrd.utils.TimeUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/4/20.
 */
public class CollectAdapter extends BaseQuickAdapter {
    private GoodsBean cartGoods; // 购物车 商品集合
    public static final int COLLECT = 1;
    public static final int HISTORY = 2;
    private int type;

    public CollectAdapter(int layoutResId, List data,int type) {
        super(layoutResId, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        cartGoods = (GoodsBean) item;
        ImageLoaderUtils.display(mContext, helper.getView(R.id.collect_img), UrlConstants.BASE_URL + cartGoods.getImgurl());
        helper.setText(R.id.collect_name, cartGoods.getName());
        helper.setText(R.id.collect_price, "￥" + cartGoods.getPrice());
        switch (type){
            case 1:
                helper.getView(R.id.collect_delete).setVisibility(View.VISIBLE);
                helper.getView(R.id.collect_car).setVisibility(View.GONE);
                break;
            case 2:
                if(cartGoods.getIsShowDate()){
                    helper.setText(R.id.tv_date,cartGoods.getCreatetime());
                    helper.getView(R.id.tv_date).setVisibility(View.VISIBLE);
                }else{
                    helper.getView(R.id.tv_date).setVisibility(View.GONE);
                }
                helper.getView(R.id.collect_delete).setVisibility(View.GONE);
                helper.getView(R.id.collect_car).setVisibility(View.GONE);
                break;
            default:
                break;
        }
        helper.getView(R.id.collect_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseItemClickListener.onAdapterItemClickListener(v, position);
            }
        });
        helper.getView(R.id.collect_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseItemClickListener.onAdapterItemClickListener(v, position);
            }
        });

    }
}
