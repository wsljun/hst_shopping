package com.cj.reocrd.view.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.TextureView;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.base.BaseApplication;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.contract.GoodsDetailContract;
import com.cj.reocrd.model.entity.GoodsCommentBean;
import com.cj.reocrd.model.entity.GoodsDetailsBean;
import com.cj.reocrd.model.entity.OrderBean;
import com.cj.reocrd.model.entity.SkuBean;
import com.cj.reocrd.model.entity.UserBean;
import com.cj.reocrd.presenter.GoodsDetailPresenter;
import com.cj.reocrd.utils.ActivityUtils;
import com.cj.reocrd.utils.CollectionUtils;
import com.cj.reocrd.utils.ConstantsUtils;
import com.cj.reocrd.utils.GlideImageLoader;
import com.cj.reocrd.utils.ImageLoaderUtils;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.utils.Utils;
import com.cj.reocrd.view.fragment.CartFragment;
import com.cj.reocrd.view.view.AmountView.AmountView;
import com.donkingliang.labels.LabelsView;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/18.
 */

public class GoodDetailActivity extends BaseActivity<GoodsDetailPresenter> implements GoodsDetailContract.View
        ,View.OnClickListener{

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
    @BindView(R.id.rl_activity_goods_detail)
    RelativeLayout rlActivityGoodsDetail;
    @BindView(R.id.btn_comment)
    Button btnComment;
    @BindView(R.id.btn_goods_detail_webview)
    Button btnGoodsDetailWeb;
    @BindView(R.id.fl_fragment)
    FrameLayout mFrameLayout;
    @BindView(R.id.banner_detail)
    Banner banner;
    @BindView(R.id.tv_merAtt)
    TextView tvMerAtt;

    private static String goodsID = "";// 商品ID
    public static GoodsDetailsBean goodsDetailsBean;
    private String sid = ""; // 商品规格id
    private int num = 1; // 商品数据
    private View skuView; // 商品规格布局
    private PopupWindow popWindow;
    private  boolean isCollect;
    private String price;
    private  String unit;
    private LabelsView goodsLabelsView;
    private int skuStock ; // 当前sku库存
    private AmountView tvSkuNum;
    private int width;
    private int height;
    private TextView tvSkuTotalPrice,tvSkuAddCart,tvSkuBuy;
    private ImageView ivSkuGoCart;
    private String skuPrice; // 不同规格单价
    private  String countPrice; // 最终选择后的总价
    public static  List<GoodsCommentBean> goodsCommentBeans;
    public static  boolean  GO_CART = false;
    private boolean isTimelineCb;
    private byte[] bitmapByte;
    private static Bitmap shareGoodBitmap;
    private static File shareFile;
    private List<String> images = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_good_detail;
    }

    @Override
    public void initData() {
        super.initData();
        width  = ActivityUtils.getWidth(this);
        height = ActivityUtils.getHeight(this);
        mPresenter.getGoodsDetail(goodsID);
    }

    @Override
    public void initView() {
        goodOldPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        titleRight.setText("分享");
        titleRight.setVisibility(View.VISIBLE);
    }

    private void updateView() {
        countPrice = price = goodsDetailsBean.getPrice();
        unit = goodsDetailsBean.getUnit();
        goodOldPrice.setText(ConstantsUtils.RMB+goodsDetailsBean.getOldprice());
        titleCenter.setText(goodsDetailsBean.getName());
        titleCenter.setMaxEms(10);
        goodName.setText(goodsDetailsBean.getName());
        goodBrandTv.setText(goodsDetailsBean.getBrand());
        goodPrice.setText(ConstantsUtils.RMB+price);
        goodSales.setText("销量："+goodsDetailsBean.getBlocknum());
        goodAddress.setText(goodsDetailsBean.getPlace());
        goodTotalPrice.setText(ConstantsUtils.RMB+price);
//        ImageLoaderUtils.display(getContext(),imgGoodDetail, UrlConstants.BASE_URL+goodsDetailsBean.getImgurl());
        if("1".equals(goodsDetailsBean.getIscollect())){
            setCollectImg(true);
        }
        tvMerAtt.setText(goodsDetailsBean.getMerAtt());
        setBannerView(goodsDetailsBean.getImgUrlL());
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(null == bitmapByte){
                    shareGoodBitmap =  ImageLoaderUtils.getbitmap(UrlConstants.BASE_URL+goodsDetailsBean.getImgurl());
                    if(null != shareGoodBitmap){
//                        bitmapByte = ImageLoaderUtils.bitmap2Bytes(shareGoodBitmap,32);
                        shareFile = ImageLoaderUtils.saveImage(shareGoodBitmap,"share");
                    }
                }
            }
        }).start();
    }

    private void setBannerView(String imgUrlL) {
        if(TextUtils.isEmpty(imgUrlL)){   return; }
        String [] urls = imgUrlL.split("@#@");
        for (String s: urls) {
//            Log.e("imgUrlL", "setBannerView: " + UrlConstants.BASE_URL + s );
            images.add(UrlConstants.BASE_URL + s);
        }
        DisplayMetrics dm = getResources().getDisplayMetrics();
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) banner.getLayoutParams();
        params.height = dm.widthPixels;
        params.width = dm.widthPixels;
        banner.setLayoutParams(params);
        //轮播图
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);
        banner.setIndicatorGravity(BannerConfig.CENTER);
//        banner.setOnBannerListener(this);
        banner.start();
    }



    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        goodsID = getIntent().getStringExtra("goodsID");
        //  get goods details
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


    @OnClick({R.id.title_left,R.id.good_conllect_iv, R.id.good_num_rl, R.id.good_buy, R.id.good_addcar,
            R.id.btn_goods_detail_webview,R.id.btn_comment,R.id.good_car,R.id.title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.good_conllect_iv:
                if(isCollect){
                    mPresenter.collectDelete(uid, goodsID);
                }else{
                    mPresenter.collectGoods(uid,goodsID);
                }
                break;
            case R.id.good_num_rl:
                showPopuView();
                break;
            case R.id.good_buy:
                if(!TextUtils.isEmpty(sid)){
                    mPresenter.orderByDetail(uid,goodsID,sid,num);
                }else{
                    ToastUtil.showShort("请先选择商品规则");
                    showPopuView();
                }
                break;
            case R.id.good_addcar:
                if(null!=popWindow && popWindow.isShowing()){
                    popWindow.dismiss();
                    mPresenter.addToCart(uid,sid,num,goodsID);
                }else{
                    ToastUtil.showShort("请先选择商品规则");
                    showPopuView();
                }
                break;
            case R.id.btn_comment:
                mPresenter.getGoodsDetailComment(goodsID,20,0);
                break;
            case R.id.btn_goods_detail_webview:
                Bundle bundle = new Bundle();
                bundle.putString(WebViewActivity.BUNDLE_WEBVIEW_URL,goodsID);
                bundle.putInt(WebViewActivity.BUNDLE_WEBVIEW_TYPE,WebViewActivity.TYPE_GOODS_DETAILS);
                startActivity(WebViewActivity.class,bundle);
                break;
            case R.id.good_car:
                BaseFragment.isVisible = true;
                GO_CART = true;
                mFrameLayout.setVisibility(View.VISIBLE);
                replaceFragment(R.id.fl_fragment, CartFragment.class.getName());
                break;
            case R.id.title_right:
                showDialog();
                break;
            default:
                break;
        }
    }

    private void setSkuView() {
        if(null != skuView && null != goodsDetailsBean){
            tvGoodsDetailPrice.setText(ConstantsUtils.RMB+goodsDetailsBean.getPrice());
            if(goodsDetailsBean.getSlist().size()>0){
                setSkuPrice(goodsDetailsBean.getSlist().get(0));
            }else{
                ToastUtil.showShort("没有规格可选");
                return;
            }
            tvSkuAddCart.setOnClickListener(this::onViewClicked);
            tvSkuBuy.setOnClickListener(this::onViewClicked);
            ivSkuGoCart.setOnClickListener(this::onViewClicked);
            ImageLoaderUtils.display(this,ivGoodsDetailUrl,UrlConstants.BASE_URL+goodsDetailsBean.getImgurl());
            //设置默认选中
            goodsLabelsView.setSelects(0);
            goodsLabelsView.setLabels(goodsDetailsBean.getSlist(), new LabelsView.LabelTextProvider<SkuBean>() {
                @Override
                public CharSequence getLabelText(TextView label, int position, SkuBean data) {
                    //根据data和position返回label需要显示的数据。
                    String s = data.getNum();//+unit;
                    return s;
                }
            });
            //标签的选中监听
            goodsLabelsView.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
                @Override
                public void onLabelSelectChange(TextView label, Object data, boolean isSelect, int position) {
                    //label是被选中的标签，data是标签所对应的数据，isSelect是是否选中，position是标签的位置。
                    setSkuPrice((SkuBean) data);
                }
            });
            btnGoodsDetailClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null!= popWindow){
                        popWindow.dismiss();
                    }
                }
            });
            tvSkuNum.setGoods_storage(Integer.parseInt(goodsDetailsBean.getSlist().get(0).getStock()));
            tvSkuNum.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
                @Override
                public void onAmountChange(View view, int amount) {
                    num = amount;
                    setTextTotalPrice(skuPrice,num);
                }
            });
            popWindow.showAtLocation(rlActivityGoodsDetail, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    private void setSkuPrice(SkuBean data){
        SkuBean skuBean = data;
        sid = skuBean.getId();
        skuPrice = skuBean.getPrice();
        tvGoodsDetailPrice.setText(ConstantsUtils.RMB+skuPrice);
        tvSkuNum.setGoods_storage(Integer.parseInt(skuBean.getStock()));
        setTextTotalPrice(skuPrice,num);
    }

    private void setTextTotalPrice(String price,int num){
        if (TextUtils.isEmpty(price)){
            return;
        }
        double totalPrice = Utils.countPrice(price,String.valueOf(num));
        countPrice = String.valueOf(totalPrice);
        tvSkuTotalPrice.setText(ConstantsUtils.RMB+countPrice);
        goodTotalPrice.setText(ConstantsUtils.RMB+countPrice);
    }

    private void showPopuView(){
        // 用于PopupWindow的View
        if(null == skuView){
            skuView = getLayoutInflater().inflate(R.layout.dialog_good_detail, null,false);
            tvGoodsDetailPrice = skuView.findViewById(R.id.good_detail_price);
            ivGoodsDetailUrl = skuView.findViewById(R.id.iv_goods_detail_url);
            btnGoodsDetailClose = skuView.findViewById(R.id.good_detail_close);
            goodsLabelsView = skuView.findViewById(R.id.goods_labels);
            tvSkuNum = skuView.findViewById(R.id.good_detail_num);
            tvSkuTotalPrice = skuView.findViewById(R.id.good_total_price);
            tvSkuAddCart = skuView.findViewById(R.id.good_addcar);
            tvSkuBuy = skuView.findViewById(R.id.good_buy);
            ivSkuGoCart = skuView.findViewById(R.id.good_car);


            popWindow=new PopupWindow(skuView,WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,true);
            popWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            // 设置PopupWindow是否能响应外部点击事件
            popWindow.setOutsideTouchable(true);
            // 设置PopupWindow是否能响应点击事件
            popWindow.setTouchable(true);
        }
        setSkuView();
    }

    @Override
    public void onSuccess(Object data) {
        goodsDetailsBean = (GoodsDetailsBean) data;
        if(!CollectionUtils.isNullOrEmpty(goodsDetailsBean.getSlist())){
            sid = goodsDetailsBean.getSlist().get(0).getId(); // 默认设置
        }else{
            sid = "";
        }
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
            if(null != popWindow){
                popWindow.dismiss();
            }
            OrderBean orderBean = (OrderBean) apiResponse.getResults();
            Bundle b = new Bundle();//  确认订单
            b.putString(SubmitOrderActivity.BUNDLE_KEY_OID,orderBean.getOid());
            b.putString(SubmitOrderActivity.BUNDLE_KEY_TYPE,SubmitOrderActivity.TYPE_FOR_DETAIL);
            b.putString(SubmitOrderActivity.BUNDLE_KEY_TOTALPRICE,countPrice);
            startActivity(SubmitOrderActivity.class,b);
        }else{
            ToastUtil.showShort("购买失败！");
        }
    }

    @Override
    public void setCollectImg(boolean stuats) {
        isCollect = stuats;
        if(stuats){ // 收藏成功
            ivCollect.setBackground(getResources().getDrawable(R.mipmap.collect_select));
        }else{
            ivCollect.setBackground(getResources().getDrawable(R.mipmap.shoucang));
        }
    }

    @Override
    public void showComment(List<GoodsCommentBean> goodsCommentBeanList) {
        if(!CollectionUtils.isNullOrEmpty(goodsCommentBeanList)){
            goodsCommentBeans = goodsCommentBeanList;
            startActivity(AllCommentActivity.class);
        }else{
            ToastUtil.showShort("没有评论");
        }
    }
    Fragment cartFragment ;
    protected void replaceFragment(int viewResource, String fragmentName) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(null == cartFragment){
            cartFragment =  Fragment.instantiate(this, fragmentName);
        }
        ft.replace(viewResource, cartFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void hideFragment(){
        GO_CART = false;
        mFrameLayout.setVisibility(View.GONE);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(cartFragment);
        ft.commit();
    }

    private Dialog dialog;
    private void showDialog() {
        View view = GoodDetailActivity.this.getLayoutInflater().inflate(R.layout.share_choose_dialog, null);
        Button btn_save = (Button) view.findViewById(R.id.btn_save);
        Button btn_share_timeline = (Button) view.findViewById(R.id.btn_share_timeline);
        Button btn_share_fd = (Button) view.findViewById(R.id.btn_share_fd);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_save.setVisibility(View.GONE);
        btn_share_timeline.setOnClickListener(this);
        btn_share_fd.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        dialog = new Dialog(GoodDetailActivity.this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        if (window != null) {
            window.setWindowAnimations(R.style.main_menu_animstyle);
            WindowManager.LayoutParams wl = window.getAttributes();
            wl.x = 0;
            wl.y = this.getWindowManager().getDefaultDisplay().getHeight();
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



    @Override
    public void onClick(View v) {
        // 保存，分享到朋友圈，分享给朋友，取消
        int id = v.getId();
        switch (id){
            case R.id.btn_share_timeline:
                isTimelineCb = true;
                dialog.dismiss();
                shareWebPage();
                break;
            case R.id.btn_share_fd:
                isTimelineCb = false;
                dialog.dismiss();
                shareWebPage();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            default:
                break;
        }
    }


    /*
       * 分享链接
       */
    private void shareWebPage() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = UrlConstants.URL_SHARE+goodsID+"&code="+uid;
        LogUtil.d("webpageUrl",webpage.webpageUrl);
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = goodsDetailsBean.getName();
        msg.description = goodsDetailsBean.getIntroduct() ;

//        Bitmap thumb = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
//        if(bitmapByte == null) {
//            if(null != shareGoodBitmap){
//                bitmapByte = ImageLoaderUtils.bitmap2Bytes(shareGoodBitmap,32);
//            }else{
//                Toast.makeText(mContext, "图片不能为空", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            if(bitmapByte.length > 32){
//                bitmapByte = Util.bmpToByteArray(shareGoodBitmap, true);
//            }
//            msg.thumbData = bitmapByte;
//        }
        if (null == shareFile) {return;}
        Bitmap bmp = BitmapFactory.decodeFile(shareFile.getAbsolutePath());
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 100, 100, true);
        bmp.recycle();
        msg.thumbData = ImageLoaderUtils.getThumbData(thumbBmp);  // 设置所图；

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.scene = isTimelineCb ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        BaseApplication.api.sendReq(req);
    }



}