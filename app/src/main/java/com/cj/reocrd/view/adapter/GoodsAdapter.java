package com.cj.reocrd.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.base.baseadapter.BaseViewHolder;
import com.cj.reocrd.model.entity.FirstBean;
import com.cj.reocrd.model.entity.GirlData;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.model.entity.HomeBean;
import com.cj.reocrd.utils.ImageLoaderUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/3/19.
 *
 */

public class GoodsAdapter extends BaseQuickAdapter {

    public GoodsAdapter (int layoutId, List<GoodsBean> listData){
        super(layoutId,listData);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        GoodsBean data=(GoodsBean)item;
        ImageLoaderUtils.display(mContext,(ImageView) helper.getView(R.id.itme_all_goods_pic),
                UrlConstants.BASE_URL+data.getImgurl());
        helper.setText(R.id.item_all_goods_name,data.getName());
    }

}
