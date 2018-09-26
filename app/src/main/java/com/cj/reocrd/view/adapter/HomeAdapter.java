package com.cj.reocrd.view.adapter;


import android.graphics.Paint;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

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
 * Created by Lyndon.Li on 2018/3/22.
 * login register
 */
public class HomeAdapter extends BaseQuickAdapter {

    public HomeAdapter(int layoutResId, List<GoodsBean> listData) {
        super(layoutResId, listData);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        GoodsBean data = (GoodsBean) item;
        ImageLoaderUtils.display(mContext, helper.getView(R.id.good_pic), UrlConstants.BASE_URL + data.getImgurl());
        helper.setText(R.id.good_name, data.getName());
        helper.setText(R.id.good_price, "￥" + data.getPrice());
        helper.setText(R.id.good_num, "已售：" + data.getBlocknum());
    }
}
