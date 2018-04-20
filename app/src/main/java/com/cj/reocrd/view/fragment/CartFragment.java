package com.cj.reocrd.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.base.baseadapter.OnItemClickListener;
import com.cj.reocrd.base.baseadapter.SimpleClickListener;
import com.cj.reocrd.contract.CartContract;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.presenter.CartPresenter;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.adapter.CarAdapter;
import com.cj.reocrd.view.refresh.RefreshLayout;
import com.cj.reocrd.view.view.AmountView.AmountView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cj.reocrd.base.BaseActivity.uid;

/**
 * Created by Administrator on 2018/3/16.
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
    private List<GoodsBean> cartGoodsList; // 购物车 商品集合
    private CarAdapter carAdapter;
    private String currCartID = "";
    private final  static  String TAG = "CartFragment";

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
        mPresenter.getCartData(uid);
    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.car));
    }

    @OnClick({R.id.title_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                getMainActivity().getViewPager().setCurrentItem(0);
                break;
        }
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
        initRecycleView();
    }

    private void initRecycleView() {
        carAdapter = new CarAdapter(R.layout.item_car, cartGoodsList);
        carAdapter.setOnBaseAdapterItemClickListener(this);
        rvCartContent.setLayoutManager(new LinearLayoutManager(mActivity));
//        rvCartContent.setHasFixedSize(true);
        //设置适配器加载动画
        carAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        rvCartContent.setAdapter(carAdapter);
        carAdapter.loadComplete();
    }

//    @Override
//    public void onAmountChange(View view, int amount) {
//        mPresenter.addCartGoodsNum(currCartID,amount);
//    }

    @Override
    public void onAdapterItemClickListener(View view, int position) {
        Log.e(TAG, "onAdapterItemClickListener: positon= "+position );
    }
}
