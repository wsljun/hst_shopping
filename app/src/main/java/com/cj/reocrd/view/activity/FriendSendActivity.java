package com.cj.reocrd.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.FriendsContract;
import com.cj.reocrd.model.entity.UserBean;
import com.cj.reocrd.presenter.FriendsPresenter;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.adapter.ReleaseMsgAdapter;
import com.cj.reocrd.view.fragment.FriendsFragment;
import com.cj.reocrd.view.view.ProgressWait.ProgressPopupWindow;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by Administrator on 2018/4/10.
 */

public class FriendSendActivity extends BaseActivity<FriendsPresenter> implements FriendsContract.View {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.friends_con)
    EditText friendsCon;
    @BindView(R.id.friends_photos)
    RecyclerView friendsPhotos;
    private static String TAG = "FriendSendActivity";
    private ReleaseMsgAdapter adapter;
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<String> listImagePath;
    private ArrayList<String> mList = new ArrayList<>();
    ProgressPopupWindow progressPopupWindow;

    @Override
    public int getLayoutId() {
        return R.layout.activity_friends_send;
    }

    @Override
    public void initView() {
        titleLeft.setText(getString(R.string.cancel));
        titleCenter.setText(getString(R.string.friends));
        titleRight.setText(getString(R.string.friends_send));
        titleRight.setVisibility(View.VISIBLE);
        progressPopupWindow = new ProgressPopupWindow(this);
        setRecyclerview();
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    private void setRecyclerview() {
        if (mList != null) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
            friendsPhotos.setLayoutManager(gridLayoutManager);
            adapter = new ReleaseMsgAdapter(FriendSendActivity.this, mList);
            friendsPhotos.setAdapter(adapter);
        }

    }

    //用户选中图片后，拿到回掉结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                listImagePath = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                LogUtil.e(TAG, "right->>" + listImagePath.size());
                compress(listImagePath);
            }
        }
    }

    //压缩 拿到返回选中图片的集合url，然后转换成file文件
    public void compress(ArrayList<String> list) {
        for (String imageUrl : list) {
            File file = new File(imageUrl);
            compressImage(file);
        }
        adapter.addMoreItem(list);
    }

    //压缩
    private void compressImage(File file) {
        Luban.with(this)//用的第三方的压缩，开源库  Luban 大家可以自行百度
                .load(file)                     //传人要压缩的图片
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        //TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(final File file) {
                        URI uri = file.toURI();
                        String[] split = uri.toString().split(":");
                        list.add(split[1]);//压缩后返回的文件，带file字样，所以需要截取
                    }

                    @Override
                    public void onError(Throwable e) {
                        //TODO 当压缩过去出现问题时调用
                    }
                }).launch();//启动压缩
    }

    @Override
    public void onSuccess(Object data) {
        if (progressPopupWindow != null) {
            progressPopupWindow.dismiss();
        }
        ApiResponse response = (ApiResponse) data;
        ToastUtil.showToastS(this, response.getMessage());
        if ("1".equals(response.getStatusCode())) {
            finish();
        }
    }

    @Override
    public void onFailureMessage(String msg) {
        if (progressPopupWindow != null) {
            progressPopupWindow.dismiss();
        }
        ToastUtil.showToastS(this, msg);
    }

    @Override
    public Context getContext() {
        return FriendSendActivity.this;
    }


    @OnClick({R.id.title_left, R.id.title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.title_right:
                String detail = friendsCon.getText().toString();
                if (list.size() == 0) {
                    ToastUtil.showToastS(this, "至少上传一张图片");
                    return;
                }
                //list为空时，Multipart body must have at least one part.
                List<MultipartBody.Part> partList = filesToMultipartBodyParts(list);
                mPresenter.friendSend(uid, detail, partList);
                progressPopupWindow.showPopupWindow();
                break;
        }
    }


    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<String> urls) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        List<File> files = new ArrayList<>();
        for (String url : urls) {
            LogUtil.e(TAG, "urls->>" + urls.size());
            File file = new File(url);
            if (file.exists()) {
                files.add(file);
            }
        }
        for (int i = 1; i <= files.size(); i++) {
            LogUtil.e(TAG, "files->>" + files.size());
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), files.get(i - 1));
            MultipartBody.Part part = MultipartBody.Part.createFormData("img" + i, files.get(i - 1).getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }
}
