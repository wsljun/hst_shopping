package com.cj.reocrd.view.activity;

import android.app.Dialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.utils.ToastUtil;
import com.youth.banner.Banner;
import com.ywp.addresspickerlib.AddressPickerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/18.
 */

public class GoodDetailActivity extends BaseActivity {

    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.good_name)
    TextView goodName;
    @BindView(R.id.good_price)
    TextView goodPrice;
    @BindView(R.id.good_old_price)
    TextView goodOldPrice;
    @BindView(R.id.good_sales)
    TextView goodSales;
    @BindView(R.id.good_conllect_tv)
    TextView goodConllectTv;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.good_act_tv)
    TextView goodActTv;
    @BindView(R.id.good_num_tv)
    TextView goodNumTv;
    @BindView(R.id.good_shit_tv)
    TextView goodShitTv;
    @BindView(R.id.good_brand)
    TextView goodBrand;
    @BindView(R.id.good_address)
    TextView goodAddress;
    @BindView(R.id.good_car)
    ImageView goodCar;
    @BindView(R.id.good_buy)
    TextView goodBuy;
    @BindView(R.id.good_addcar)
    TextView goodAddcar;
    @BindView(R.id.good_total_price)
    TextView goodTotalPrice;
    private Dialog dialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_good_detail;
    }

    @Override
    public void initView() {
        goodOldPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线

    }

    @Override
    public void initPresenter() {

    }

    @OnClick({R.id.good_conllect_iv, R.id.good_num_rl, R.id.good_buy, R.id.good_addcar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.good_conllect_iv:
                break;
            case R.id.good_num_rl:
                break;
            case R.id.good_buy:
                showDialog();
                break;
            case R.id.good_addcar:
                ToastUtil.showToastS(this, "good_addcar");
                break;
        }
    }

    private void showDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_good_detail, null);
        //布局中的控件
        dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        if (window != null) {
            window.setWindowAnimations(R.style.main_menu_animstyle);
            WindowManager.LayoutParams wl = window.getAttributes();
            wl.x = 0;
            wl.y = getWindowManager().getDefaultDisplay().getHeight();
            // 以下这两句是为了保证按钮可以水平满屏
            wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
            wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            // 设置显示位置
            dialog.onWindowAttributesChanged(wl);
        }
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}
