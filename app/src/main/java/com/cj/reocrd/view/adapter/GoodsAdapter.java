package com.cj.reocrd.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.base.baseadapter.BaseViewHolder;
import com.cj.reocrd.model.entity.FirstBean;
import com.cj.reocrd.utils.ImageLoaderUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/3/19.
 *
 */

public class GoodsAdapter extends BaseQuickAdapter {

    public GoodsAdapter (int layoutId, List<FirstBean> firstBeans){
        super(layoutId,firstBeans);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        FirstBean data=(FirstBean)item;
        ImageLoaderUtils.display(mContext,(ImageView) helper.getView(R.id.two_pic),data.getUrl());
    }

}
