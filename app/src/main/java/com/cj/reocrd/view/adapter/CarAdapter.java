package com.cj.reocrd.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.base.baseadapter.BaseViewHolder;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.utils.ImageLoaderUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.view.AmountView.AmountView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/26.
 */

public class CarAdapter extends BaseQuickAdapter implements AmountView.OnAmountChangeListener {
    private GoodsBean cartGoods; // 购物车 商品集合
    private int item_layoutid;

    public CarAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        item_layoutid = layoutResId;
    }

    @Override
    public void setOnBaseAdapterItemClickListener(OnBaseQuickAdapterItemClickListener onBaseQuickAdapterItemClickListener) {
        super.setOnBaseAdapterItemClickListener(onBaseQuickAdapterItemClickListener);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        cartGoods = (GoodsBean) item;
        ImageLoaderUtils.display(mContext, helper.getView(R.id.iv_cart_pic), UrlConstants.BASE_URL + cartGoods.getImgurl());
        helper.setText(R.id.car_name, cartGoods.getName());
        helper.setText(R.id.car_num, "数量:" + cartGoods.getBuynum());
        helper.getView(R.id.car_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseItemClickListener.onAdapterItemClickListener(v, position);
            }
        });

        CheckBox checkBox = helper.getView(R.id.car_choose);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBaseItemClickListener.onAdapterItemClickListener(buttonView, position);
            }
        });
        AmountView amountView = helper.getView(R.id.car_amount);
        amountView.setGoods_storage(Integer.parseInt(cartGoods.getStock()));
        amountView.setOnAmountChangeListener(this::onAmountChange);


    }

    @Override
    public void onAmountChange(View view, int amount) {
        ToastUtil.showShort("onAmountChange: " + amount);
        mBaseItemClickListener.onAdapterItemClickListener(view, amount);
    }


}
