package com.cj.reocrd.view.adapter;



import android.widget.ImageView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.base.baseadapter.BaseViewHolder;
import com.cj.reocrd.model.entity.FirstBean;
import com.cj.reocrd.model.entity.GirlData;
import com.cj.reocrd.utils.ImageLoaderUtils;

import java.util.List;


/**
 * Created by Lyndon.Li on 2018/3/22.
 *  login register
 */
public class HomeAdapter extends BaseQuickAdapter {

    public HomeAdapter(int layoutResId, List<GirlData> listData) {
        super(layoutResId, listData);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        FirstBean data=(FirstBean)item;
        ImageLoaderUtils.display(mContext,(ImageView) helper.getView(R.id.good_pic)
                ,data.getUrl());
    }
}
