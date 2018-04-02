package com.cj.reocrd.view.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
    @BindView(R.id.rl_goods_detail_bottom)
    RelativeLayout rlBottomView;

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
//                showDialog();
                showPopuView();
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
            wl.y = 0;
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

    private void showPopuView(){
        // 用于PopupWindow的View
        View contentView = getLayoutInflater().inflate(R.layout.dialog_good_detail, null,false);
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        PopupWindow window=new PopupWindow(contentView,1080,800,true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
        window.showAsDropDown(rlBottomView, 0, 0);
        // 或者也可以调用此方法显示PopupWindow，其中：
        // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
        // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
        // window.showAtLocation(parent, gravity, x, y);
    }
}