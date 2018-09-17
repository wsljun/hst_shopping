package com.cj.reocrd.view.adapter;

import android.view.View;
import android.widget.CheckBox;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.base.baseadapter.BaseViewHolder;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.utils.ConstantsUtils;
import com.cj.reocrd.utils.ImageLoaderUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.view.AmountView.AmountView;

import java.util.ArrayList;
import java.util.List;

/**
 */

public class InvoiceAdapter extends BaseQuickAdapter {
    private  int item_layoutid;
    public volatile static List<CheckBox> checkBoxList = new ArrayList<>();

    public InvoiceAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        item_layoutid = layoutResId;
        checkBoxList.clear();
    }

    @Override
    public void setOnBaseAdapterItemClickListener(OnBaseQuickAdapterItemClickListener onBaseQuickAdapterItemClickListener) {
        super.setOnBaseAdapterItemClickListener(onBaseQuickAdapterItemClickListener);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
         String d = (String) item;
        helper.setText(R.id.invoice_value,"开票金额：￥50."+d);
        helper.setText(R.id.order_num,"订单号:9876543321");
        helper.setText(R.id.order_time,"订单时间:2018-09-10 10:37:45");
//        helper.getView(R.id.invoice_enter).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                mBaseItemClickListener.onAdapterItemClickListener(v,position);
////                ToastUtil.showShort("invoice_enter");
//            }
//        });
        CheckBox checkBox =  helper.getView(R.id.invoice_item_choose);
        checkBox.setTag(position);
        if(!checkBoxList.contains(checkBox)){
            checkBoxList.add(checkBox);
        }
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = (int) v.getTag();
                mBaseItemClickListener.onAdapterItemClickListener(v,i);
                ToastUtil.showShort("invoice_item_choose");
            }
        });


    }





}
