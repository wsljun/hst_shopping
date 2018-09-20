package com.cj.reocrd.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.InvoiceContract;
import com.cj.reocrd.model.entity.InvoiceInfo;
import com.cj.reocrd.presenter.InvoicePresenter;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.adapter.InvoiceAdapter;
import com.cj.reocrd.view.refresh.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cj.reocrd.utils.Utils.formatDouble2;
import static com.cj.reocrd.view.adapter.CarAdapter.checkBoxList;

/**
 * 发票中心
 */
public class InvoiceActivity extends BaseActivity<InvoicePresenter> implements InvoiceContract.View,
        InvoiceAdapter.OnBaseQuickAdapterItemClickListener{

    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.tv_invoice_msg)
    TextView tvInvoiceMsg;
    @BindView(R.id.ll_invoice_msg)
    LinearLayout llInvoiceMsg;
    @BindView(R.id.img_none)
    ImageView imgNone;
    @BindView(R.id.rv_invoice_content)
    RecyclerView rvInvoiceContent;
    @BindView(R.id.refresh_layout)
    RefreshLayout refreshLayout;
    @BindView(R.id.all_choose_invoice)
    CheckBox allChooseInvoice;
    @BindView(R.id.tv_total_invoice)
    TextView tvTotalInvoice;
    @BindView(R.id.next)
    TextView next;
    @BindView(R.id.rl_invoice_bottom)
    RelativeLayout rlInvoiceBottom;
    private final String TAG = "InvoiceActivity";
    public static Map<Integer,Boolean> isCheckedMap = new HashMap<>();
    private InvoiceAdapter invoiceAdapter;
    private String totalInvoiceValue = "523.1"; // 发票总金额
    private List<InvoiceInfo> invoiceInfoList;
    private int pageno = 0;


    @Override
    public int getLayoutId() {
        return R.layout.activity_invoice;
    }

    @Override
    public void initData() {
        super.initData();
        invoiceInfoList  = new ArrayList<>();
        mPresenter.getInvoiceList(uid,String.valueOf(pageno));
    }

    @Override
    public void initView() {
        // todo
        titleCenter.setText("发票开具");
        titleRight.setText("发票规则");
        titleRight.setVisibility(View.VISIBLE);
//        titleRight.setTextColor(getResources().getColor(R.color.colorWhite));
        initRecycleView();
    }

    @Override
    public void initPresenter() {
      // todo p
        mPresenter.setVM(this);
    }

    @OnClick({R.id.title_left, R.id.title_right, R.id.all_choose_invoice, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.title_right:
                // todo 发票规则

                break;
            case R.id.all_choose_invoice:
                // todo 开票全选

                break;
            case R.id.next:
                // todo 进入开票界面
                Bundle b = new Bundle();
                b.putString(InvoiceSubmitActivity.KEY_MONEY,totalInvoiceValue);
                b.putString(InvoiceSubmitActivity.KEY_SN,totalInvoiceValue);
                startActivity(InvoiceSubmitActivity.class,b);
                break;
        }
    }


    private void initRecycleView() {
        invoiceAdapter = new InvoiceAdapter(R.layout.item_invoice, invoiceInfoList);
        invoiceAdapter.setOnBaseAdapterItemClickListener(this);
        rvInvoiceContent.setLayoutManager(new LinearLayoutManager(this));
//        rvCartContent.setHasFixedSize(true);
        rvInvoiceContent.setAdapter(invoiceAdapter);
        invoiceAdapter.loadComplete();
    }



    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(TAG,"onResume");
        if(isPause){
//            updateCartData();
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


    @Override
    public void onAdapterItemClickListener(View view, int position) {
        // todo 列表子项被选中，更新发票价格
        if(view.getId() == R.id.invoice_item_choose){

        }
    }

    /**
     * 所选商品改变时计算最终总价。
     * @param isAll  是否点击了全选按钮
     */
    private void setTotalPrice(boolean isAll,int position){
//        for (int i = 0; i <checkBoxList.size() ; i++) {
//            if(isAll){
//                checkBoxList.get(i).setChecked(cbCartAllChoose.isChecked());
//            }
//            if(checkBoxList.get(i).isChecked()){
//                if(i<cartGoodsList.size()){
//                    totalPrice = totalPrice + countPrice(cartGoodsList.get(i).getPrice(),cartGoodsList.get(i).getBuynum());
//                    cartGoodsImgs.add(cartGoodsList.get(i).getImgurl());
//                }
//            }
//        }
//        tvGoodsTotalPrice.setText(getString(R.string.RMB)+formatDouble2(totalPrice));
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
        invoiceInfoList  = (List<InvoiceInfo>) data;
//        invoiceAdapter.notifyDataSetChanged();
        invoiceAdapter.notify();

    }

    @Override
    public void onFailureMessage(String msg) {
       ToastUtil.showShort(msg);
    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }
}
