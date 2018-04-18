package com.cj.reocrd.view.activity;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.AddressContract;
import com.cj.reocrd.model.entity.AddressBean;
import com.cj.reocrd.presenter.AddressPresenter;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.utils.Utils;
import com.ywp.addresspickerlib.AddressPickerView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cj.reocrd.view.activity.AddressActivity.addrForEdit;


/**
 * Created by Administrator on 2018/3/17.
 */

public class AddressEditActivity extends BaseActivity<AddressPresenter> implements AddressPickerView.OnAddressPickerSureListener
        ,AddressContract.View{
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.address_name_et)
    EditText addressNameEt;
    @BindView(R.id.address_phone_et)
    EditText addressPhoneEt;
    @BindView(R.id.address_dz)
    TextView addressDz;
    @BindView(R.id.address_dz_detail)
    EditText addressDzDetail;
    @BindView(R.id.address_post)
    EditText addressPost;
    private Dialog dialog;
    private List<AddressBean.Regions> regions ;
    private String consignee ;// 收货人
    private  String phone ;
    private String postCode ;
    private String detailAddress;
    private String addressID;
    private String type ;

    /*
    layer 1
    * "name":"湖南省",
　　　　　　"id":"080c36e4-0e36-11e3-977a-d43d7e9c965f"
    layer 2
    "name":"长沙市",
　　　　　　"id":"080c1b84-0e36-11e3-977a-d43d7e9c965f"
    layer 3
　　"name":"芙蓉区",
　　　　　　"id":"1b2250a0-0e36-11e3-977a-d43d7e9c965f"
    * */

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_edit;
    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.address_edit));
        if(null != addrForEdit){
            addressNameEt.setText(addrForEdit.getConsignee());
            addressPhoneEt.setText(addrForEdit.getPhone());
            addressDzDetail.setText(addrForEdit.getFuladdress());
            addressPost.setText(addrForEdit.getPostcode());
        }
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
        type = getIntent().getExtras().getString("type");
    }

    @Override
    public void initData() {
        super.initData();
        //一级列表
//        mPresenter.selectAddressMap("","1");
        // 二级列表
//        mPresenter.selectAddressMap("080c36e4-0e36-11e3-977a-d43d7e9c965f","2");
        //三级列表
        mPresenter.selectAddressMap("080c1b84-0e36-11e3-977a-d43d7e9c965f","3");
    }

    @OnClick({R.id.title_left, R.id.address_clear, R.id.address_dz_rl, R.id.address_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.address_clear:
                addressNameEt.setText("");
                break;
            case R.id.address_dz_rl:
                showDialog();
                break;
            case R.id.address_save:
                String  rid = "1b2253bb-0e36-11e3-977a-d43d7e9c965f"; // 第三级地址id
                consignee = addressNameEt.getText().toString().trim();
                if(TextUtils.isEmpty(consignee)){
                    ToastUtil.showShort("收货人不能为空！");
                    return;
                }
                phone = addressPhoneEt.getText().toString().trim();
                if(!Utils.checkMobileNumber(phone)){
                    ToastUtil.showShort("请填写正确的手机号！");
                    return;
                }
                detailAddress = addressDzDetail.getText().toString().trim();
                if(TextUtils.isEmpty(detailAddress)){
                    ToastUtil.showShort("请填写完整收货地址！");
                    return;
                }
                postCode = addressPost.getText().toString().trim();
                if(TextUtils.isEmpty(postCode)){
                    ToastUtil.showShort("邮编不能为空！");
                    return;
                }
                //{"regions":[{"name":"芙蓉区","id":"1b2250a0-0e36-11e3-977a-d43d7e9c965f"},{"name":"天心区","id":"1b2253bb-0e36-11e3-977a-d43d7e9c965f"},{"name":"岳麓区","id":"1b225596-0e36-11e3-977a-d43d7e9c965f"},{"name":"开福区","id":"1b22574e-0e36-11e3-977a-d43d7e9c965f"},{"name":"雨花区","id":"1b2258ee-0e36-11e3-977a-d43d7e9c965f"},{"name":"长沙县","id":"1b225a96-0e36-11e3-977a-d43d7e9c965f"},{"name":"望城县","id":"1b225c3c-0e36-11e3-977a-d43d7e9c965f"},{"name":"宁乡县","id":"1b225ddc-0e36-11e3-977a-d43d7e9c965f"},{"name":"浏阳市","id":"1b225f6c-0e36-11e3-977a-d43d7e9c965f"},{"name":"开发区","id":"1b226118-0e36-11e3-977a-d43d7e9c965f"}]}
                if("add".equals(type)){
                    mPresenter.addAddress(uid,rid,consignee,phone,detailAddress,postCode);
                }else{
                    rid = "1b2258ee-0e36-11e3-977a-d43d7e9c965f";
                    mPresenter.updateAddress(addrForEdit.getId(),rid,consignee,phone,detailAddress,postCode);
                }
                break;
        }
    }

    private void showDialog() {  // todo 修改地址三级联动
        View view = getLayoutInflater().inflate(R.layout.dialog_choose_address, null);
        AddressPickerView addressPickerView = view.findViewById(R.id.apvAddress);
        addressPickerView.setOnAddressPickerSure(this);
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

    @Override
    public void onSureClick(String address, String provinceCode, String cityCode, String districtCode) {
        addressDz.setText(address);
        dialog.dismiss();
    }

    @Override
    public void onSuccess(Object data) {
       ToastUtil.showShort((String) data);
    }

    @Override
    public void onFailureMessage(String msg) {
         ToastUtil.showShort(msg);
    }

    @Override
    public Context getContext() {
        return this.mContext;
    }

    @Override
    public void showAddressList(List<AddressBean> beans) {

    }
}
