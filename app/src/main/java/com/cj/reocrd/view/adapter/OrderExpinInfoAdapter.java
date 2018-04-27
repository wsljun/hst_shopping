package com.cj.reocrd.view.adapter;

import com.cj.reocrd.R;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.base.baseadapter.BaseViewHolder;
import com.cj.reocrd.model.entity.OrderDetail;

import java.util.List;

/**
 * Created by Lyndon.Li on 2018/4/27
 * 快递信息
 */

public class OrderExpinInfoAdapter extends BaseQuickAdapter {
    private List<OrderDetail.ExpinfoBean.DataBean>  mDatas;
    private OrderDetail.ExpinfoBean.DataBean itemInfo;
    public OrderExpinInfoAdapter(int contentView, List<OrderDetail.ExpinfoBean.DataBean> data) {
        super(contentView, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        itemInfo = (OrderDetail.ExpinfoBean.DataBean) item;
        if (position==0){
            helper.setBackgroundRes(R.id.img_expinStatus,R.mipmap.jinxingzhong);
        }else{
            helper.setBackgroundRes(R.id.img_expinStatus,R.mipmap.yiwanc);
        }
        helper.setText(R.id.tv_ex_context,itemInfo.getContext());
        helper.setText(R.id.tv_ex_time,itemInfo.getTime());

    }
}
