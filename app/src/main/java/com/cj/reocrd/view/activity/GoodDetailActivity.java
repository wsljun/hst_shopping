package com.cj.reocrd.view.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.GoodsDetailContract;
import com.cj.reocrd.model.entity.GoodsDetailsBean;
import com.cj.reocrd.model.entity.OrderBean;
import com.cj.reocrd.presenter.GoodsDetailPresenter;
import com.cj.reocrd.utils.ImageLoaderUtils;
import com.cj.reocrd.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/18.
 */

public class GoodDetailActivity extends BaseActivity<GoodsDetailPresenter> implements GoodsDetailContract.View {

    @BindView(R.id.title_left)
    TextView titleLeft;
    TextView tvGoodsDetailPrice;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.iv_good_detail)
    ImageView imgGoodDetail;
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
    @BindView(R.id.good_conllect_iv)
    ImageView ivCollect;
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
    @BindView(R.id.good_brand_tv)
    TextView goodBrandTv;
    @BindView(R.id.good_address_tv)
    TextView goodAddress;
    @BindView(R.id.good_car)
    ImageView goodCar;
    ImageView ivGoodsDetailUrl;
    ImageView btnGoodsDetailClose;
    @BindView(R.id.good_buy)
    TextView goodBuy;
    @BindView(R.id.good_addcar)
    TextView goodAddcar;
    @BindView(R.id.good_total_price)
    TextView goodTotalPrice;
    @BindView(R.id.rl_goods_detail_bottom)
    RelativeLayout rlBottomView;

    private Dialog dialog;
    private static String goodsID = "";// 商品ID
    private GoodsDetailsBean goodsDetailsBean;
    private String sid = ""; // 商品规格id
    private int num = 1; // 商品数据
    private View skuView; // 商品规格布局
    private PopupWindow popWindow;
    private  boolean isCollect;
    private String price;

    @Override
    public int getLayoutId() {
        return R.layout.activity_good_detail;
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getGoodsDetail(goodsID);
    }

    @Override
    public void initView() {
        goodOldPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
//        updateView();
    }

    private void updateView() {
        price = goodsDetailsBean.getPrice();
        goodOldPrice.setText(goodsDetailsBean.getOldprice());
        titleCenter.setText(goodsDetailsBean.getName());
        goodName.setText(goodsDetailsBean.getName());
        goodBrandTv.setText(goodsDetailsBean.getBrand());
        goodPrice.setText(price);
        goodSales.setText("销量："+goodsDetailsBean.getBlocknum());
        goodAddress.setText(goodsDetailsBean.getPlace());
        goodTotalPrice.setText(price);
        ImageLoaderUtils.display(getContext(),imgGoodDetail, UrlConstants.BASE_URL+goodsDetailsBean.getImgurl());
        if("1".equals(goodsDetailsBean.getIscollect())){
            setCollectImg(true);
        }
    }


    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        goodsID = getIntent().getStringExtra("goodsID");
        // TODO get goods details
    }

    public static Intent newIntent(Context context, String goodsID) {
        Intent intent = new Intent(context, GoodDetailActivity.class);
        intent.putExtra("goodsID", goodsID);
        return intent;
    }

    /**
     * @param context
     * @param goodsID 商品id
     */
    public static void intentTo(Context context, String goodsID) {
        context.startActivity(newIntent(context, goodsID));
    }


    @OnClick({R.id.title_left,R.id.good_conllect_iv, R.id.good_num_rl, R.id.good_buy, R.id.good_addcar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.good_conllect_iv:
                mPresenter.collectGoods(uid,goodsID);
                break;
            case R.id.good_num_rl:
                break;
            case R.id.good_buy:
//                showDialog();
//                showPopuView();
                mPresenter.orderByDetail(uid,goodsID,sid,num);
                break;
            case R.id.good_addcar:
                ToastUtil.showToastS(this, "good_addcar");
                mPresenter.addToCart(uid,sid,num,goodsID);
                break;
        }
    }

    private void showDialog() {
       View  view = getLayoutInflater().inflate(R.layout.dialog_good_detail, null);
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

    private void updateSkuView() {
        if(null != skuView && null != goodsDetailsBean){
            tvGoodsDetailPrice.setText(goodsDetailsBean.getPrice());
            ImageLoaderUtils.display(this,ivGoodsDetailUrl,UrlConstants.BASE_URL+goodsDetailsBean.getImgurl());
            btnGoodsDetailClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null!= popWindow){
                        popWindow.dismiss();
                    }
                }
            });
        }
    }

    private void showPopuView(){
        // 用于PopupWindow的View
        if(null == skuView){
            skuView = getLayoutInflater().inflate(R.layout.dialog_good_detail, null,false);
            tvGoodsDetailPrice = skuView.findViewById(R.id.good_detail_price);
            ivGoodsDetailUrl = skuView.findViewById(R.id.iv_goods_detail_url);
            btnGoodsDetailClose = skuView.findViewById(R.id.good_detail_close);
        }
        updateSkuView();
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
         popWindow=new PopupWindow(skuView,1080,800,true);
        // 设置PopupWindow的背景
        popWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        popWindow.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        popWindow.setTouchable(true);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
        popWindow.showAsDropDown(rlBottomView, 0, 0);
        // 或者也可以调用此方法显示PopupWindow，其中：
        // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
        // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
        // popWindow.showAtLocation(parent, gravity, x, y);
    }

    @Override
    public void onSuccess(Object data) {
        goodsDetailsBean = (GoodsDetailsBean) data;
//         if(CollectionUtils.isNullOrEmpty(goodsDetailsBean.getSlist())){
        sid = goodsDetailsBean.getSlist().get(0).getId(); // 默认设置
//         }else{
//             sid = "";
//         }
        updateView();
    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showShort(msg);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void acticonToSubmitOrder(ApiResponse apiResponse) {
        if(UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())){
            OrderBean orderBean = (OrderBean) apiResponse.getResults();
            ToastUtil.showShort("加入购物车成功！");
            Bundle b = new Bundle();
            b.putParcelable("orderBean",orderBean);
            b.putParcelable("goodsDetails",goodsDetailsBean);
            startActivity(SubmitOrderActivity.class,b);
        }else{
            ToastUtil.showShort("加入购物车失败！");
        }
    }

    @Override
    public void setCollectImg(boolean stuats) {
        if(stuats){ // 收藏成功
            ivCollect.setBackground(getResources().getDrawable(R.mipmap.collect_select));
        }
    }


}