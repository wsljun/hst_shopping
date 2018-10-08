package com.cj.reocrd.view.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.base.baseadapter.BaseViewHolder;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.model.entity.InvoiceInfo;
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
        InvoiceInfo invoiceInfo = (InvoiceInfo) item;
        helper.setText(R.id.invoice_value,"开票金额：￥"+invoiceInfo.getInvoicemoney());
        helper.setText(R.id.order_num,"订单号: "+invoiceInfo.getSn());
        helper.setText(R.id.order_time,"订单时间: "+invoiceInfo.getCreatetime());
        CheckBox checkBox =  helper.getView(R.id.invoice_item_choose);
        checkBox.setTag(position);
        checkBox.setChecked(invoiceInfo.isChecked());
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = (int) v.getTag();
                invoiceInfo.setChecked(checkBox.isChecked());
                mBaseItemClickListener.onAdapterItemClickListener(v,i);
            }
        });
        TextView isapply2 = helper.getView(R.id.tv_apply);
        if("1".equals(invoiceInfo.getIsapply())){
            checkBox.setVisibility(View.GONE);
            isapply2.setVisibility(View.VISIBLE);
        }else{
            checkBox.setVisibility(View.VISIBLE);
            isapply2.setVisibility(View.GONE);
        }


    }





}
