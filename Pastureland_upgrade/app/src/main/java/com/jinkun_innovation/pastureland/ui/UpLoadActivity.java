package com.jinkun_innovation.pastureland.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.base.BaseActivity;
import com.jinkun_innovation.pastureland.bean.ImgUrlBean;
import com.jinkun_innovation.pastureland.bean.LoginSuccess;
import com.jinkun_innovation.pastureland.common.Constants;
import com.jinkun_innovation.pastureland.utilcode.util.FileUtils;
import com.jinkun_innovation.pastureland.utilcode.util.LogUtils;
import com.jinkun_innovation.pastureland.utilcode.util.ToastUtils;
import com.jinkun_innovation.pastureland.utils.PrefUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by yangxing on 2018/1/15.
 */

public class UpLoadActivity extends BaseActivity {
    private static final String TAG1 = UpLoadActivity.class.getSimpleName();
    private File mPhotoFile;
    private int mPhase;
    private String mDeviceNo;
    private String url;

    @BindView(R.id.img_upload)
    ImageView mImgUpload;
    private int mCheckedItem;

    @Override
    protected void initToolBar() {
        setIsShowBack(true);
        switch (getIntent().getIntExtra(getString(R.string.checked_Item), 0)) {
            case 2:
                setTitle(TypeEnum.BIRTHDAY.getName());
                break;
            case 3:
                setTitle(TypeEnum.CROP.getName());
                break;
            case 0:
                setTitle(TypeEnum.LIFE.getName());
                break;
            case 4:
                setTitle(TypeEnum.PRIVATE.getName());
                break;

        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_upload;
    }

    String mLogin_success;
    LoginSuccess mLoginSuccess;
    String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mPhase = getIntent().getIntExtra(getString(R.string.checked_Item), 0);
        mDeviceNo = getIntent().getStringExtra(getString(R.string.scan_Message));
        cropImage(getIntent().getStringExtra(getString(R.string.img_Url)));

        TextView tvDeviceNo = (TextView) findViewById(R.id.tvDeviceNo);
        if (mPhase == 3) {
            tvDeviceNo.setText("设备号：" + mDeviceNo);
            tvDeviceNo.setVisibility(View.VISIBLE);
        } else {
            tvDeviceNo.setVisibility(View.GONE);
        }


        mLogin_success = PrefUtils.getString(this, "login_success", null);
        Gson gson = new Gson();
        mLoginSuccess = gson.fromJson(mLogin_success, LoginSuccess.class);
        mUsername = PrefUtils.getString(this, "username", null);


        mCheckedItem = getIntent().getIntExtra(getString(R.string.checked_Item), 0);

//        url = getIntent().getStringExtra(getString(R.string.img_Url));
//        Glide.with(UpLoadActivity.this).load(FileUtils.getFileByPath(url)).into(mImgUpload);
//        mPbLoading.setVisibility(View.GONE);
    }

    String mImgUrl;


    private void cropImage(final String imgUrl) {
        String rootDir = "/Pastureland/crop";
        File file = new File(Environment.getExternalStorageDirectory(), rootDir);

        if (FileUtils.createOrExistsDir(file)) {
            LogUtils.e(file.getAbsolutePath());
            Luban.with(this)
                    .load(FileUtils.getFileByPath(imgUrl))      // 传人要压缩的图片列表
                    .ignoreBy(100)                                  // 忽略不压缩图片的大小
                    .setTargetDir(file.getAbsolutePath())
                    .setCompressListener(new OnCompressListener() { //设置回调
                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                            LogUtils.e("onStart");


                        }

                        @Override
                        public void onSuccess(File file) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件
                            mPhotoFile = file;
                            LogUtils.e("onSuccess");
                            LogUtils.e(file.getAbsolutePath());

                            mLogin_success = PrefUtils.getString(getApplicationContext(), "login_success", null);
                            Gson gson = new Gson();
                            mLoginSuccess = gson.fromJson(mLogin_success, LoginSuccess.class);
                            mUsername = PrefUtils.getString(getApplicationContext(), "username", null);


                            OkGo.<String>post(Constants.HEADIMGURL)
                                    .tag(this)
                                    .isMultipart(true)
                                    .params("token", mLoginSuccess.getToken())
                                    .params("username", mUsername)
                                    .params("uploadFile", file)
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(Response<String> response) {

                                            String s = response.body().toString();
                                            Log.d(TAG1, s);
                                            Gson gson = new Gson();
                                            ImgUrlBean imgUrlBean = gson.fromJson(s, ImgUrlBean.class);
                                            mImgUrl = imgUrlBean.getImgUrl();
                                            int j = mImgUrl.indexOf("j");
                                            mImgUrl = mImgUrl.substring(j - 1, mImgUrl.length());
                                            Log.d(TAG1, mImgUrl);


                                        }

                                        @Override
                                        public void onError(Response<String> response) {
                                            super.onError(response);


                                        }
                                    });


                            Glide.with(UpLoadActivity.this).load(file).into(mImgUpload);
//                            FileUtils.deleteFile(imgUrl);


                        }

                        @Override
                        public void onError(Throwable e) {
                            // TODO 当压缩过程出现问题时调用
                            LogUtils.e(e.getMessage());
                            ToastUtils.showShort("压缩出现问题，请重新拍摄");


                        }
                    }).launch();
        }


    }

    @OnClick(R.id.btn_submit)
    public void onClick() {
        // TODO: 2018/1/16 上传图片

        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("上传中...");
        pDialog.setCancelable(true);
        pDialog.show();

        String imgUrl = mPhotoFile.getAbsolutePath();


        switch (mCheckedItem) {
            case 2:

                break;
            case 3:
                //剪毛
                if (!TextUtils.isEmpty(mImgUrl)) {

                    OkGo.<String>post(Constants.SHEARING)
                            .tag(this)
                            .params("token", mLoginSuccess.getToken())
                            .params("username", mUsername)
                            .params("ranchID", mLoginSuccess.getRanchID())
                            .params("deviceNO", mDeviceNo)
                            .params("imgUrl", mImgUrl)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {

                                    String s = response.body().toString();
                                    Log.d(TAG1, s);
                                    pDialog.cancel();

                                    if (s.contains("success")) {
                                        //
                                        Toast.makeText(getApplicationContext(),
                                                "剪毛登记成功",
                                                Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                        finish();


                                    } else {
                                        //fail
                                        Toast.makeText(getApplicationContext(),
                                                "没有找到此牲畜无法剪毛",
                                                Toast.LENGTH_SHORT).show();


                                    }

                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);

                                    ToastUtils.showShort("没有网络，请检查网络");

                                }
                            });

                } else {

                    pDialog.cancel();
                    new SweetAlertDialog(UpLoadActivity.this,
                            SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("抱歉...")
                            .setContentText("网络不稳定,上传图片失败,请返回重新拍摄")
                            .show();
                }

                break;
            case 0:
                //拍照
                Log.d(TAG1, "mImgUrl==" + mImgUrl);
                if (!TextUtils.isEmpty(mImgUrl)) {
                    OkGo.<String>post(Constants.RANCHIMGVIDEO)
                            .tag(this)
                            .params("token", mLoginSuccess.getToken())
                            .params("username", mUsername)
                            .params("ranchID", mLoginSuccess.getRanchID())
                            .params("fileType", 1)
                            .params("imgUrl", mImgUrl)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {

                                    String s = response.body().toString();
                                    pDialog.cancel();
                                    Log.d(TAG1, s);

                                    if (s.contains("success")) {
                                        Toast.makeText(getApplicationContext(), "上传成功", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                        finish();

                                    } else {

                                        Toast.makeText(getApplicationContext(), "上传失败", Toast.LENGTH_SHORT).show();

                                    }

                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);

                                    ToastUtils.showShort("没有网络，请检查网络");
                                }
                            });
                } else {

                    pDialog.cancel();
                    new SweetAlertDialog(UpLoadActivity.this,
                            SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("抱歉...")
                            .setContentText("网络不稳定,上传图片失败,请返回重新拍摄")
                            .show();

                }


                break;
            case 4:


                break;


        }


    }


    private enum TypeEnum {
        BIRTHDAY("接羔照上传"), CROP("剪毛照上传"), LIFE("生活照上传"), PRIVATE("私人定制上传");

        private final String name;

        TypeEnum(String name) {
            this.name = name;
        }

        private String getName() {
            return name;
        }
    }

}
