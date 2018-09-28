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
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.contract.InvoiceContract;
import com.cj.reocrd.model.entity.InvoiceInfo;
import com.cj.reocrd.presenter.InvoicePresenter;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.utils.Utils;
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
        InvoiceAdapter.OnBaseQuickAdapterItemClickListener, BaseQuickAdapter.RequestLoadMoreListener{

    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.ll_invoice_msg)
    LinearLayout llInvoiceMsg;
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
    @BindView(R.id.rg_ivoice)
    RadioGroup rgInvoice;

    private final String TAG = "InvoiceActivity";
    private InvoiceAdapter invoiceAdapter;
    private double totalInvoiceValue = 0; // 发票总金额
    private List<InvoiceInfo> invoiceInfoList;
    private int pageno = 0;
    private boolean loading;
    private String countSN;
    private boolean isCan = false;
    private String isapply ="2";
    private int num;


    @Override
    public int getLayoutId() {
        return R.layout.activity_invoice;
    }

    @Override
    public void initData() {
        super.initData();
        invoiceInfoList  = new ArrayList<>();
        updateList();
    }

    @Override
    public void initView() {
        titleCenter.setText("发票开具");
//        titleRight.setText("发票规则");
//        titleRight.setVisibility(View.VISIBLE);
//        titleRight.setTextColor(getResources().getColor(R.color.colorWhite));
        initRecycleView();
        rgInvoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                isapply = (String) group.findViewById(checkedId).getTag();
                switch (checkedId){
                    case R.id.rb_isapply1: // 已开票
                        num = 0;
                        totalInvoiceValue = 0;
                        rlInvoiceBottom.setVisibility(View.GONE);
                        setBottomView();
                        break;
                    case R.id.rb_isapply2: // 未开票
                        rlInvoiceBottom.setVisibility(View.VISIBLE);
                        break;
                }
                invoiceInfoList.clear();
                invoiceAdapter.notifyDataSetChanged();
                pageno = 0;
                updateList();
            }
        });
    }

    @Override
    public void initPresenter() {
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
                //  开票全选
                setTotalPrice(true,0);
                break;
            case R.id.next:
                if(!isCan){
                    return;
                }
                //  进入开票界面
                Bundle b = new Bundle();
                b.putString(InvoiceSubmitActivity.KEY_MONEY,String.valueOf(totalInvoiceValue));
                b.putString(InvoiceSubmitActivity.KEY_SN,countSN);
                startActivity(InvoiceSubmitActivity.class,b);
                break;
        }
    }


    private void initRecycleView() {
        invoiceAdapter = new InvoiceAdapter(R.layout.item_invoice, invoiceInfoList);
        invoiceAdapter.setOnBaseAdapterItemClickListener(this);
        rvInvoiceContent.setLayoutManager(new LinearLayoutManager(this));
        rvInvoiceContent.setAdapter(invoiceAdapter);
        invoiceAdapter.setOnLoadMoreListener(this);
        invoiceAdapter.loadComplete();
    }



    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(TAG,"onResume");
        if(isPause){
            isPause = false;
            invoiceInfoList.clear();
            num = 0;
            totalInvoiceValue = 0;
            pageno = 0;
            setBottomView();
            updateList();
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
        if(view.getId() == R.id.invoice_item_choose){
            setTotalPrice(false,0);
        }
    }

    /**
     * 所选商品改变时计算最终总价。
     * @param isAll  是否点击了全选按钮
     */
    private void setTotalPrice(boolean isAll,int position){
        num = 0;
        countSN = "";
        totalInvoiceValue = 0;
        for (int i = 0; i <invoiceInfoList.size() ; i++) {
            if(isAll){
                invoiceInfoList.get(i).setChecked(allChooseInvoice.isChecked());
            }
            if(invoiceInfoList.get(i).isChecked()){
                num ++;
                totalInvoiceValue = totalInvoiceValue + countPrice(invoiceInfoList.get(i).getInvoicemoney(), "1");
                if(i == 0){
                    countSN = invoiceInfoList.get(i).getSn();
                }else{
                    countSN = countSN+","+invoiceInfoList.get(i).getSn();
                }
            }
        }
        invoiceAdapter.notifyDataSetChanged();
        setBottomView();
    }

    private void setBottomView(){
        // 0个订单,共￥0
        tvTotalInvoice.setText(num+"个订单，共￥"+ Utils.formatDouble2(totalInvoiceValue));
        if(totalInvoiceValue>0){
            isCan = true;
            next.setBackgroundColor(getResources().getColor(R.color.color1));
        }else{
            isCan = false;
            next.setBackgroundColor(getResources().getColor(R.color.colorTexthintGrey));
        }
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
//        totalPrice = totalPrice+itemPrice;
        return formatDouble2(itemPrice);
    }

    @Override
    public void onSuccess(Object data) {
        List<InvoiceInfo> invoiceInfos = (List<InvoiceInfo>) data;
        if(null != invoiceInfos && invoiceInfos.size()>0){
            if(invoiceInfos.size()<20){
                loading = false;
                refreshLayout.endLoadingMore();
                invoiceAdapter.loadComplete();
                ToastUtil.showShort("已全部加载！");
            }
            loading = true;
            invoiceInfoList.addAll(invoiceInfos);
            invoiceAdapter.setNewData(invoiceInfoList);
        }else{
            loading = false;
            refreshLayout.endLoadingMore();
            invoiceAdapter.loadComplete();
            ToastUtil.showShort("已全部加载！");
        }
    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showShort(msg);
    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }

    @Override
    public void onLoadMoreRequested() {
        if(loading){
            pageno++;
            updateList();
        }
    }

    private void updateList(){
        mPresenter.getInvoiceList(uid,String.valueOf(pageno),isapply);
    }
}
