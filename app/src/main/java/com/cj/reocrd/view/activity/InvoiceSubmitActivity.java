package com.cj.reocrd.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.InvoiceContract;
import com.cj.reocrd.presenter.InvoicePresenter;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 提交发票
 */
public class InvoiceSubmitActivity extends BaseActivity<InvoicePresenter> implements
        InvoiceContract.View {


    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.tv_invoice_type)
    TextView tvInvoiceType;
    @BindView(R.id.fl_invoice_type)
    FrameLayout flInvoiceType;
    @BindView(R.id.tv_invoice_info)
    TextView tvInvoiceInfo;
    @BindView(R.id.fl_invoice_info)
    FrameLayout flInvoiceInfo;
    @BindView(R.id.tv_invoice_value)
    TextView tvInvoiceValue;
    @BindView(R.id.cb_invoice_1)
    CheckBox cbInvoice1;
    @BindView(R.id.cb_invoice_2)
    CheckBox cbInvoice2;
    @BindView(R.id.edit_c_name)
    EditText editCName;
    @BindView(R.id.edit_c_num)
    EditText editCNum;
    @BindView(R.id.tv_person_invoice)
    TextView tvPersonInvoice;
    @BindView(R.id.fl_address)
    LinearLayout flAddress;
    @BindView(R.id.tv_invoice_name)
    TextView tvInvoiceName;
    @BindView(R.id.tv_invoice_phone)
    TextView tvInvoicePhone;
    @BindView(R.id.ll_email)
    LinearLayout llEmail;
    @BindView(R.id.edit_invoice_email)
    EditText edInvoiceEmail;
    @BindView(R.id.tv_btn_invocie_submit)
    TextView tvBtnInvocieSubmit;

    private String invoiceType ,invoiceInfo,invoiceValue,email,
    invoiceName,invoicePhone,invoiceFullAddr;
    public  final static String KEY_INVALUE = "INVALUE";
    private final int IN_TYPE = 1;
    private final int IN_INFO = 2;
    private  int  type = 0;
    public final static String TAG = "INVOICE_SUBMIT";
    private final  String IN_DZ = "电子发票";
    private final  String IN_ZZ = "纸质发票";


    @Override
    public int getLayoutId() {
        return R.layout.activity_invoice_submit;
    }

    @Override
    public void initData() {
        super.initData();
        invoiceValue = getIntent().getStringExtra(KEY_INVALUE);
    }

    @Override
    public void initView() {
        titleCenter.setText("开票信息");
        tvInvoiceValue.setText(invoiceValue);
    }

    @Override
    public void initPresenter() {
        // TODO: 2018/9/11
        mPresenter.setVM(this);
    }

    @OnClick({R.id.title_left, R.id.fl_invoice_type, R.id.fl_invoice_info, R.id.cb_invoice_1,
            R.id.cb_invoice_2, R.id.fl_address, R.id.tv_btn_invocie_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.fl_invoice_type:
                //  发票类型
                type = IN_TYPE;
                chooseDialog("请选择发票类型",IN_DZ,IN_ZZ);
                break;
            case R.id.fl_invoice_info:
                //  发票内容
                type = IN_INFO;
                chooseDialog("请选择发票内容","商品明细","商品类别");
                break;
            case R.id.cb_invoice_1:
                //  抬头 个人
                cbInvoice1.setChecked(true);
                cbInvoice2.setChecked(false);
                editCName.setVisibility(View.GONE);
                editCNum.setVisibility(View.GONE);
                break;
            case R.id.cb_invoice_2:
                // : 2018/9/11  企业
                cbInvoice1.setChecked(false);
                cbInvoice2.setChecked(true);
                editCName.setVisibility(View.VISIBLE);
                editCNum.setVisibility(View.VISIBLE);
                break;
            case R.id.fl_address:
                // TODO: 2018/9/11  收件人信息，姓名，地址，电话。
                Bundle bundle = new Bundle();
                bundle.putString("type", TAG);
                startActivityForResult(AddressActivity.class, bundle, 1);
                break;
            case R.id.tv_btn_invocie_submit:
                // TODO: 2018/9/11 提交
                break;
        }
    }

    private void chooseDialog(String title,String v1,String v2) {
        MaterialDialog.Builder materialDialog = new MaterialDialog.Builder(this)
                .title(title)
                .neutralText(v1)
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if(type == IN_TYPE){
                            invoiceType = v1;
                            tvInvoiceType.setText(v1);
                            llEmail.setVisibility(View.VISIBLE);
                            flAddress.setVisibility(View.GONE);
                        }else{
                            invoiceInfo = v1;
                            tvInvoiceInfo.setText(v1);
                        }
//                        if(IN_DZ.equals(invoiceType)){
//                            llEmail.setVisibility(View.VISIBLE);
//                            flAddress.setVisibility(View.GONE);
//                        }else{
//                            llEmail.setVisibility(View.GONE);
//                            flAddress.setVisibility(View.VISIBLE);
//                        }
                    }
                })
                .positiveText(v2)
                .canceledOnTouchOutside(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if(type == IN_TYPE){
                            invoiceType = v2;
                            tvInvoiceType.setText(v2);
                            // 纸质发票
                            llEmail.setVisibility(View.GONE);
                            flAddress.setVisibility(View.VISIBLE);
                        }else{
                            invoiceInfo = v2;
                            tvInvoiceInfo.setText(v2);
                        }
                    }
                });
        materialDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                Bundle b = data.getExtras();
                String str = b.getString("personInfo");
                String [] strs = str.split(",");
                invoiceName = strs[0];
                invoicePhone = strs[1];
                invoiceFullAddr = strs[2];
                tvPersonInvoice.setText(invoiceFullAddr);
                tvInvoiceName.setText(invoiceName);
                tvInvoicePhone.setText(invoicePhone);
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public void onFailureMessage(String msg) {

    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }
}
