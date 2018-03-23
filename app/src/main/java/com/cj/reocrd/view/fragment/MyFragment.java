package com.cj.reocrd.view.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.view.activity.AddressActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/17.
 */

public class MyFragment extends BaseFragment implements View.OnClickListener{
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.my_icon_iv)
    ImageView myIconIv;
    @BindView(R.id.my_name_tv)
    TextView myNameTv;
    @BindView(R.id.my_sex_tv)
    TextView mySexTv;
    @BindView(R.id.my_phone_tv)
    TextView myPhoneTv;
    private Dialog dialog;

    @Override
    protected void initPresenter() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.my));
    }

    @OnClick({R.id.my_icon_fl, R.id.my_name_fl, R.id.my_sex_fl, R.id.my_phone_fl, R.id.my_update_pwd_fl, R.id.my_address_fl, R.id.tv_signout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_icon_fl:
                showDialog();
                break;
            case R.id.my_name_fl:
                getMyActivity().getViewPager().setCurrentItem(1);
                break;
            case R.id.my_sex_fl:
                break;
            case R.id.my_phone_fl:
                //未绑定
                getMyActivity().getViewPager().setCurrentItem(2);
                //绑定
                //getMyActivity().getViewPager().setCurrentItem(3);
                break;
            case R.id.my_update_pwd_fl:
                getMyActivity().getViewPager().setCurrentItem(4);
                break;
            case R.id.my_address_fl:
                Intent intent = new Intent(mActivity, AddressActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_signout:
                break;
        }
    }

    private void showDialog() {
        View view = mActivity.getLayoutInflater().inflate(R.layout.photo_choose_dialog, null);
        Button butCamera = (Button) view.findViewById(R.id.but_camera);
        Button butAlbum = (Button) view.findViewById(R.id.but_album);
        Button butCancel = (Button) view.findViewById(R.id.but_cancel);
        butCamera.setOnClickListener(this);
        butAlbum.setOnClickListener(this);
        butCancel.setOnClickListener(this);
        dialog = new Dialog(getContext(), R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        if (window != null) {
            window.setWindowAnimations(R.style.main_menu_animstyle);
            WindowManager.LayoutParams wl = window.getAttributes();
            wl.x = 0;
            wl.y = mActivity.getWindowManager().getDefaultDisplay().getHeight();
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
        switch (v.getId()) {
            case R.id.but_camera:
                dialog.dismiss();
                break;
            case R.id.but_album:
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();
                break;
            case R.id.but_cancel:
                dialog.dismiss();
                break;
        }
    }
}
