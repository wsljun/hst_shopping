package com.cj.reocrd.view.fragment;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.contract.MyContract;
import com.cj.reocrd.model.entity.UserBean;
import com.cj.reocrd.presenter.MyPrresenter;
import com.cj.reocrd.utils.ImageLoaderUtils;
import com.cj.reocrd.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cj.reocrd.base.BaseActivity.uid;
import static com.cj.reocrd.utils.Utils.isChinese;

/**
 * Created by Administrator on 2018/3/17.
 */

public class UpdateNameFragment extends BaseFragment<MyPrresenter> implements MyContract.View {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.update_name_et)
    EditText updateNameEt;
    InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                if (!isChinese(source.charAt(i))) {
                    ToastUtil.showShort("只能输入汉字！");
                    return "";
                }
            }
            return null;
        }
    };

    @Override
    protected void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_update_name;
    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.my_update_name));
        titleRight.setText(getString(R.string.confirm));
        titleRight.setVisibility(View.VISIBLE);
        updateNameEt.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(5)});
    }


    @OnClick({R.id.title_left, R.id.title_right, R.id.update_name_ib})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                getMyActivity().getViewPager().setCurrentItem(0);
                break;
            case R.id.title_right:
                String name = updateNameEt.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.showToastS(mActivity, getString(R.string.update_name_hint2));
                    break;
                }
                if (name.length() < 2) {
                    ToastUtil.showToastS(mActivity, getString(R.string.update_name_hint));
                    break;
                }
                mPresenter.updateName(UrlConstants.UrLType.UPDATE_NAME, uid, name);
                break;
            case R.id.update_name_ib:
                updateNameEt.setText("");
                break;
        }
    }



    @Override
    public void onSuccess(Object data) {
        ApiResponse response = (ApiResponse) data;
        ToastUtil.showToastS(mActivity, response.getMessage());
        if ("1".equals(response.getStatusCode())) {
            getMyActivity().getViewPager().setCurrentItem(0);
        }
    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showToastS(mActivity, msg);
    }
}
