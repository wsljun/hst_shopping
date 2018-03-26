package com.cj.reocrd.view.activity;

import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseActivity;
import com.ywp.addresspickerlib.AddressPickerView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/17.
 */

public class AddressEditActivity extends BaseActivity implements AddressPickerView.OnAddressPickerSureListener {
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
    @BindView(R.id.address_logo)
    EditText addressLogo;
    private Dialog dialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_edit;
    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.address_edit));
    }

    @Override
    public void initPresenter() {

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
                break;
        }
    }

    private void showDialog() {
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
}
