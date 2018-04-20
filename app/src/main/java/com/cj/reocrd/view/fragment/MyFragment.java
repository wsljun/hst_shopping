package com.cj.reocrd.view.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.contract.IndexContract;
import com.cj.reocrd.contract.MyContract;
import com.cj.reocrd.model.entity.UserBean;
import com.cj.reocrd.presenter.IndexPresenter;
import com.cj.reocrd.presenter.MyPrresenter;
import com.cj.reocrd.utils.IDCard;
import com.cj.reocrd.utils.ImageLoaderUtils;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.IndexActivity;
import com.cj.reocrd.view.activity.AddressActivity;
import com.cj.reocrd.view.activity.MainActivity;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.text.ParseException;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
import static com.cj.reocrd.base.BaseActivity.uid;
import static com.cj.reocrd.view.activity.MyActivity.pNumber;

/**
 * Created by Administrator on 2018/3/17.
 */

public class MyFragment extends BaseFragment<MyPrresenter> implements MyContract.View, View.OnClickListener {
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
    @BindView(R.id.my_update_ic_tv)
    TextView myUpdateIcTv;
    private Dialog dialog;
    private File file;
    private Uri imageUri;
    private int type;
    private boolean isCreated = false;
    private String sex;

    @Override
    protected void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initData() {
        super.initData();
        isCreated = true;
        type = 3;
        mPresenter.getMYHome(UrlConstants.UrLType.MY_HOME, uid);
    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.my));
    }

    @OnClick({R.id.my_update_ic_fl, R.id.title_left, R.id.my_icon_fl, R.id.my_name_fl, R.id.my_sex_fl, R.id.my_phone_fl, R.id.my_update_pwd_fl, R.id.my_address_fl, R.id.tv_signout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                mActivity.finish();
                break;
            case R.id.my_icon_fl:
                showDialog();
                break;
            case R.id.my_name_fl:
                getMyActivity().getViewPager().setCurrentItem(1);
                break;
            case R.id.my_sex_fl:
                inputSexDialog();
                break;
            case R.id.my_phone_fl:
                if (TextUtils.isEmpty(myPhoneTv.getText().toString())) {
                    //未绑定
                    getMyActivity().getViewPager().setCurrentItem(2);
                } else {
                    //绑定
                    getMyActivity().getViewPager().setCurrentItem(3);
                }
                break;
            case R.id.my_update_pwd_fl:
                getMyActivity().getViewPager().setCurrentItem(4);
                break;
            case R.id.my_address_fl:
                startActivity(AddressActivity.class);
                break;
            case R.id.tv_signout:
                showNormalDialog();
                break;
            case R.id.my_update_ic_fl:
                inputTitleDialog();
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
                new RxPermissions(mActivity).requestEach(Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Permission>() {
                            @Override
                            public void accept(@NonNull Permission permission) throws Exception {
                                if (permission.granted) {
                                } else if (permission.shouldShowRequestPermissionRationale) {
                                    // Denied permission without ask never again
                                    ToastUtil.showToastS(mActivity, "取消照相机授权");
                                } else {
                                    // Denied permission with ask never again
                                    // Need to go to the
                                    ToastUtil.showToastS(mActivity, "您已经禁止弹出照相机的授权操作,请在设置中手动开启");
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                Log.i("--->>", "onError", throwable);
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {
                                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                                    File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/hst/download");
                                    if (!folder.exists()) {
                                        folder.mkdirs();
                                    }
                                    if (Build.VERSION.SDK_INT >= 24) {
                                        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        Uri photoUri = FileProvider.getUriForFile(mActivity, "com.cj.reocrd.fileprovider", new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/hst/download",
                                                "hstHead.jpg"));
                                        takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                                        startActivityForResult(takeIntent, 2);
                                    } else {
                                        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/hst/download",
                                                "hstHead.jpg")));
                                        //指定照片存储路径
                                        startActivityForResult(intent2, 2);//采用ForResult打开
                                    }

                                    dialog.dismiss();
                                } else {
                                    ToastUtil.showToastS(mActivity, "外部储存没有挂载");
                                }
                            }
                        });
                break;
            case R.id.but_album:
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 1);
                dialog.dismiss();
                break;
            case R.id.but_cancel:
                dialog.dismiss();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("--->>", "onActivityResult:requestCode: " + requestCode + "--resultCode:" + resultCode);
        switch (requestCode) {
            case 1://从照片中选择
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }
                break;
            case 2://调用系统相机
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/hst/download",
                            "hstHead.jpg");
                    if (temp.exists()) {
                        if (Build.VERSION.SDK_INT >= 24) {
                            cropPhoto(FileProvider.getUriForFile(mActivity, "com.cj.reocrd.fileprovider", temp));//裁剪图片
                        } else {

                            cropPhoto(Uri.fromFile(temp));//裁剪图片
                        }
                    } else {
                        ToastUtil.showToastS(mActivity, "图片可能已经移位或删除");
                    }
                }
                break;
            case 3:
                if (file == null)
                    return;
                if (file.exists()) {
                    postPic(file);
                } else {
                    ToastUtil.showToastS(mActivity, "图片可能已经移位或删除");
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/hst/download",
                "hstHead.jpg");

        imageUri = Uri.fromFile(file);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        //不启用人脸识别
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, 3);
    }

    /**
     * 上传头像
     *
     * @param file
     */
    public void postPic(File file) {
        if (TextUtils.isEmpty(file.getAbsolutePath()))
            return;
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("img", file.getName(), imageBody);
        type = 2;
        mPresenter.updatePhoto(uid, imageBodyPart);
    }

    /**
     * 修改性别对话框
     */
    private void inputSexDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.my_sex)).setIcon(
                R.mipmap.ic_launcher).setNegativeButton(getString(R.string.man), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                type = 1;
                sex = "1";
                mPresenter.updateSex(UrlConstants.UrLType.UPDATE_SEX, uid, sex);
            }
        });
        builder.setPositiveButton(getString(R.string.gril),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        type = 1;
                        sex = "2";
                        mPresenter.updateSex(UrlConstants.UrLType.UPDATE_SEX, uid, sex);
                    }
                });
        builder.show();
    }


    private void inputTitleDialog() {

        final EditText inputServer = new EditText(getContext());
        inputServer.setLines(1);
        inputServer.setMaxEms(18);
        inputServer.setPadding(50, 50, 50, 50);
        inputServer.setFocusable(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.my_update_ic)).setView(inputServer).setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.confirm,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String inputName = inputServer.getText().toString();
                        if (TextUtils.isEmpty(inputName)) {
                            ToastUtil.showToastS(mActivity, getString(R.string.my_update_ic));
                            return;
                        }
                        String oldIc = myUpdateIcTv.getText().toString();
                        if(oldIc.equals(inputName)){
                            ToastUtil.showToastS(mActivity, getString(R.string.my_update_again));
                            return;
                        }
                        try {
                            String error = IDCard.IDCardValidate(inputName);
                            if (!TextUtils.isEmpty(error)) {
                                ToastUtil.showToastS(mActivity, error);
                                return;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        type = 4;
                        mPresenter.updateIc(UrlConstants.UrLType.UPDATE_IC, uid, inputName);
                    }
                });
        builder.show();
    }

    /**
     * 退出登陆
     */
    private void showNormalDialog() {
        if (mActivity == null)
            return;
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(getContext());
        normalDialog.setIcon(null);
        normalDialog.setTitle(R.string.logout_confirm);
        normalDialog.setPositiveButton(R.string.confirm,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SPUtils.put(mActivity, UrlConstants.key.USERID, "");
                        BaseActivity.uid = "";
                        startActivity(IndexActivity.class);
                        mActivity.finish();
                        MainActivity.mainActivity.finish();
                    }
                });
        normalDialog.setNegativeButton(R.string.cancel, null);
        // 显示
        normalDialog.show();
    }

    @Override
    public void onSuccess(Object data) {
        ApiResponse response = (ApiResponse) data;
        switch (type) {
            case 1://修改性别
                if ("1".equals(response.getStatusCode())) {
                    UserBean userBean = (UserBean) response.getResults();
                    if (userBean != null && !TextUtils.isEmpty(userBean.getSex())) {
                        mySexTv.setText("1".equals(userBean.getSex()) ? getString(R.string.man) : getString(R.string.gril));
                    }
                }else{
                    ToastUtil.showToastS(mActivity, response.getMessage());
                }
                break;
            case 2://上传照片
                if ("1".equals(response.getStatusCode())) {
                    UserBean userBean = (UserBean) response.getResults();
                    if (userBean != null && !TextUtils.isEmpty(userBean.getPhoto())) {
                        ImageLoaderUtils.displayRound(mActivity, myIconIv, UrlConstants.BASE_URL + userBean.getPhoto());
                    }
                }else{
                    ToastUtil.showToastS(mActivity, response.getMessage());
                }
                break;
            case 3://get userinfo
                if ("1".equals(response.getStatusCode())) {
                    UserBean userBean = (UserBean) response.getResults();
                    if (userBean != null) {
                        if (!TextUtils.isEmpty(userBean.getPhoto())) {
                            ImageLoaderUtils.displayRound(mActivity, myIconIv, UrlConstants.BASE_URL + userBean.getPhoto());
                        }
                        if (!TextUtils.isEmpty(userBean.getName())) {
                            myNameTv.setText(userBean.getName());
                        }
                        if (!TextUtils.isEmpty(userBean.getSex())) {
                            mySexTv.setText("1".equals(userBean.getSex()) ? getString(R.string.man) : getString(R.string.gril));
                        }
                        if (!TextUtils.isEmpty(userBean.getPhone())) {
                            myPhoneTv.setText(userBean.getPhone());
                            pNumber = userBean.getPhone();
                        }
                        if (!TextUtils.isEmpty(userBean.getIc())) {
                            myUpdateIcTv.setText(userBean.getIc());
                        }
                    }
                }else{
                    ToastUtil.showToastS(mActivity, response.getMessage());
                }
                break;
            case 4:
                if ("1".equals(response.getStatusCode())) {
                    UserBean userBean = (UserBean) response.getResults();
                    if (userBean != null && !TextUtils.isEmpty(userBean.getIc())) {
                        myUpdateIcTv.setText(userBean.getIc());
                    }
                }else{
                    ToastUtil.showToastS(mActivity, response.getMessage());
                }
                break;
        }

    }


    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showToastS(mActivity, msg);
    }

    /**
     * 此方法目前仅适用于标示ViewPager中的Fragment是否真实可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (!isCreated) {
            return;
        }

        if (isVisibleToUser) {
            type = 3;
            mPresenter.getMYHome(UrlConstants.UrLType.MY_HOME, uid);
        }
    }
}
