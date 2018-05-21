package com.cj.reocrd.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.contract.CartContract;
import com.cj.reocrd.model.entity.AddressBean;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.presenter.CartPresenter;
import com.cj.reocrd.utils.CollectionUtils;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.activity.GoodDetailActivity;
import com.cj.reocrd.view.activity.SubmitOrderActivity;
import com.cj.reocrd.view.adapter.CarAdapter;
import com.cj.reocrd.view.refresh.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cj.reocrd.base.BaseActivity.uid;
import static com.cj.reocrd.utils.Utils.formatDouble2;
import static com.cj.reocrd.view.adapter.CarAdapter.checkBoxList;

/**
 * Created by Administrator on 2018/3/16.
 *  CartFragment 状态无法保存 。需要保存购物车选择情况
 */

public class CartFragment extends BaseFragment<CartPresenter> implements CartContract.View
        ,CarAdapter.OnBaseQuickAdapterItemClickListener{
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.refresh_layout)
    RefreshLayout mCartRefreshLayout;
    @BindView(R.id.rv_cart_content)
    RecyclerView rvCartContent;
    @BindView(R.id.img_none)
    ImageView imgNone;

    @BindView(R.id.car_all_choose)
    CheckBox cbCartAllChoose;
    @BindView(R.id.tv_submit_order)
    TextView btnSubmitCartToOrder;
    @BindView(R.id.good_total_price)
    TextView tvGoodsTotalPrice;

    public static List<GoodsBean> cartGoodsList; // 购物车 商品集合
    private CarAdapter carAdapter;
    private String currCartID = "";
    private final  static  String TAG = "CartFragment";
    private double totalPrice = 0;
    private int hisNum;
    public static boolean isChooseAll = false;

    @Override
    protected void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_car;
    }

    @Override
    public void initData() {
        super.initData();
        cartGoodsList = new ArrayList<>();
        updateCartData();
    }


    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.car));
        initRecycleView();
    }

    @OnClick({R.id.title_left,R.id.car_all_choose,R.id.tv_submit_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                if(GoodDetailActivity.GO_CART){
                    GoodDetailActivity activity = (GoodDetailActivity) this.getBaseActivity();
                    activity.hideFragment();
                }else{
                    getMainActivity().getViewPager().setCurrentItem(0);
                }
                break;
            case R.id.car_all_choose:
                setTotalPrice(true,0);
                break;
            case R.id.tv_submit_order:
                String cartID = "";
                int n = 0;
                for (int i = 0; i <checkBoxList.size() ; i++) {
                    if(checkBoxList.get(i).isChecked()){
                        n++;
                        if(i<cartGoodsList.size()){
                            if(n>1){
                                cartID = cartID+","+cartGoodsList.get(i).getBid();
                            }else{
                                cartID = cartGoodsList.get(i).getBid();
                            }
                        }
                    }
                }
                Log.e(TAG, "onViewClicked: cartID= "+cartID );
                if(!TextUtils.isEmpty(cartID)){
                    mPresenter.cartSubmitOrder(cartID,uid);
                }else{
                    ToastUtil.showShort("没有商品");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 所选商品改变时计算最终总价。
     * @param isAll  是否点击了全选按钮
     */
    private void setTotalPrice(boolean isAll,int position){
        totalPrice = 0;
        LogUtil.d(TAG,"cartGoodsList.size()= "+cartGoodsList.size()+"checkBoxList.size= "+checkBoxList.size());
//        for (int i = 0; i <cartGoodsList.size() ; i++) {
//            if(isAll){
//                cartGoodsList.get(i).setIsChecked(cbCartAllChoose.isChecked());
//            }else{
//                if(i == position){
//                    if(cartGoodsList.get(i).getIsChecked()){
//                        cartGoodsList.get(i).setIsChecked(false);
//                    }else{
//                        cartGoodsList.get(i).setIsChecked(true);
//                    }
//                }
//            }
//        }
//        for (int i = 0; i <cartGoodsList.size() ; i++) {
//            if(cartGoodsList.get(i).getIsChecked()){
//                totalPrice = totalPrice + countPrice(cartGoodsList.get(i).getPrice(),cartGoodsList.get(i).getBuynum());
//            }
//        }
        for (int i = 0; i <checkBoxList.size() ; i++) {
            if(isAll){
                checkBoxList.get(i).setChecked(cbCartAllChoose.isChecked());
            }
            if(checkBoxList.get(i).isChecked()){
                if(i<cartGoodsList.size()){
                    totalPrice = totalPrice + countPrice(cartGoodsList.get(i).getPrice(),cartGoodsList.get(i).getBuynum());
                }
            }
        }
        tvGoodsTotalPrice.setText(getString(R.string.RMB)+formatDouble2(totalPrice));
        // 设置完选中状态后更新adapter todo
//        carAdapter.setNewData(cartGoodsList);
    }

    //计算价格
    private double countPrice(String price,String num){
        if(TextUtils.isEmpty(price) || TextUtils.isEmpty(num)){
            return 0;
        }
        double totalPrice = 0 ;
        double p = Double.parseDouble(price);
        int count = Integer.parseInt(num);
        double itemPrice = p*count;
        totalPrice = totalPrice+itemPrice;
        return formatDouble2(totalPrice);
    }


    @Override
    public void onSuccess(Object data) {
        String msg = (String) data;
        ToastUtil.showShort(msg);
    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showShort(msg);
    }

    @Override
    public void showCartData(List<GoodsBean> goodsBeanList) {
        cartGoodsList  = goodsBeanList;
        setTotalPrice(false,0);
        carAdapter.setNewData(cartGoodsList);
        if(!CollectionUtils.isNullOrEmpty(cartGoodsList)){
            imgNone.setVisibility(View.GONE);
        }else{
            imgNone.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void updateCartData() {
        mPresenter.getCartData(uid);
    }

    @Override
    public void toSubmitOrder(AddressBean addressBean) {
        //{"consignee":"18814253636",
        // "fuladdress":"北京市北京市平谷区45623",
        // "oid":"d25c66f5-ae37-420b-b4a5-354a6462cb04"
        // ,"message":"操作成功","aid":"北京市北京市平谷区45623","statusCode":"1"}
        if(TextUtils.isEmpty(addressBean.getOid())){
//            ToastUtil.showShort("oid == null ");
            return;
        }else{
            Bundle b = new Bundle();
            b.putString(SubmitOrderActivity.BUNDLE_KEY_OID,addressBean.getOid());
            b.putString(SubmitOrderActivity.BUNDLE_KEY_TYPE,SubmitOrderActivity.TYPE_FOR_CART);
            b.putString(SubmitOrderActivity.BUNDLE_KEY_TOTALPRICE,String.valueOf(formatDouble2(totalPrice)));
            startActivity(SubmitOrderActivity.class,b);
        }
    }

    private void initRecycleView() {
        carAdapter = new CarAdapter(R.layout.item_car, cartGoodsList);
        carAdapter.setOnBaseAdapterItemClickListener(this);
        rvCartContent.setLayoutManager(new LinearLayoutManager(mActivity));
//        rvCartContent.setHasFixedSize(true);
        //设置适配器加载动画
//        carAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        rvCartContent.setAdapter(carAdapter);
        carAdapter.loadComplete();
    }


    public static Map<Integer,Boolean> isCheckedMap = new HashMap<>();

    @Override
    public void onAdapterItemClickListener(View view, int position) {
        switch (view.getId()){
            case R.id.car_delete:
                checkBoxList.remove(position);
                mPresenter.delCartGoods(cartGoodsList.get(position).getBid());
                break;
            case R.id.car_choose:
                CheckBox checkBox = (CheckBox) view;
                isCheckedMap.put(position,checkBox.isChecked());
                setTotalPrice(false,position);
                break;
            case R.id.car_amount:
                int p = (Integer) view.getTag();
                if(hisNum != position){
                    mPresenter.addCartGoodsNum(cartGoodsList.get(p).getBid(),position);
                }
                hisNum = position;
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(TAG,"onResume");
        if(isPause){
            updateCartData();
            isPause = false;
        }
    }

    boolean isPause = false;
    @Override
    public void onPause() {
        super.onPause();
        isPause = true;
        LogUtil.d(TAG,"onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG,"onDestroy");
    }
}
