package com.cj.reocrd.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.base.baseadapter.BaseViewHolder;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.utils.ImageLoaderUtils;
import com.cj.reocrd.utils.ToastUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/3/26.
 */

public class CarAdapter extends BaseQuickAdapter {
    private GoodsBean cartGoods; // 购物车 商品集合
    private  int item_layoutid;
    public CarAdapter(int layoutResId, List data) {
        super(layoutResId, data);
         item_layoutid = layoutResId;
    }



    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        cartGoods = (GoodsBean) item;
        ImageLoaderUtils.display(mContext,  helper.getView(R.id.iv_cart_pic), UrlConstants.BASE_URL+cartGoods.getImgurl());
        helper.setText(R.id.car_name,cartGoods.getName());
        helper.setText(R.id.car_weight,"规格:"+cartGoods.getUnit());
        helper.setText(R.id.car_num,"数量:"+cartGoods.getBuynum());
    }



//    @Override
//    protected void convert(com.chad.library.adapter.base.BaseViewHolder helper, GoodsBean item) {
//        cartGoods =  item;
//        ImageLoaderUtils.display(mContext,  helper.getView(R.id.iv_cart_pic), UrlConstants.BASE_URL+cartGoods.getImgurl());
//        helper.setText(R.id.car_name,cartGoods.getName());
//        helper.setText(R.id.car_weight,"规格:"+cartGoods.getUnit());
//        helper.setText(R.id.car_num,"数量:"+cartGoods.getBuynum());
//    }
}
