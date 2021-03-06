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
import com.cj.reocrd.view.view.AmountView.AmountView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/26.
 */

public class CarAdapter extends BaseQuickAdapter implements AmountView.OnAmountChangeListener{
    private GoodsBean cartGoods; // 购物车 商品集合
    private  int item_layoutid;
    public static   List<CheckBox> checkBoxList = new ArrayList<>();

    public CarAdapter(int layoutResId, List data) {
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
        cartGoods = (GoodsBean) item;
        int num = Integer.parseInt(cartGoods.getBuynum());
        ImageLoaderUtils.display(mContext,  helper.getView(R.id.iv_cart_pic), UrlConstants.BASE_URL+cartGoods.getImgurl());
        helper.setText(R.id.car_name,cartGoods.getName());
        helper.setText(R.id.car_weight,"规格:"+cartGoods.getSpecnum()+"/"+cartGoods.getUnit());
        helper.setText(R.id.car_num,"价格:"+ ConstantsUtils.RMB+cartGoods.getPrice());
        helper.getView(R.id.car_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseItemClickListener.onAdapterItemClickListener(v,position);
            }
        });
        CheckBox checkBox =  helper.getView(R.id.car_choose);
        checkBox.setTag(position);
        checkBox.setChecked(cartGoods.isChecked());
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = (int) v.getTag();
                mBaseItemClickListener.onAdapterItemClickListener(v,i);
            }
        });
        AmountView amountView =  helper.getView(R.id.car_amount);
        amountView.setGoods_storage(Integer.parseInt(cartGoods.getStock()));
        amountView.setTag(position);
        amountView.setText(cartGoods.getBuynum());
        amountView.setOnAmountChangeListener(this::onAmountChange);


    }

    @Override
    public void onAmountChange(View view, int amount) {
        mBaseItemClickListener.onAdapterItemClickListener(view,amount);
    }




}
