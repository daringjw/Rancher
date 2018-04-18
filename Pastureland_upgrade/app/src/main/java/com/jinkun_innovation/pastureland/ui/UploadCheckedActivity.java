package com.jinkun_innovation.pastureland.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;
import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.base.BaseActivity;
import com.jinkun_innovation.pastureland.utilcode.AppManager;
import com.jinkun_innovation.pastureland.utilcode.util.FileUtils;
import com.jinkun_innovation.pastureland.utilcode.util.LogUtils;
import com.jinkun_innovation.pastureland.utilcode.util.ToastUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * Created by yangxing on 2018/1/17.
 */

public class UploadCheckedActivity extends BaseActivity {
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    private File mPhotoFile;
    private int mPhase;
    private String mDeviceNo;
    private String variety;


    @BindView(R.id.rb_black_sheep)
    RadioButton mRbBlackSheep;
    @BindView(R.id.rb_sheep)
    RadioButton mRbSheep;
    @BindView(R.id.rb_horse)
    RadioButton mRbHorse;
    @BindView(R.id.rb_bull)
    RadioButton mRbBull;
    @BindView(R.id.img_upload)
    ImageView mImgUpload;

    @Override
    protected void initToolBar() {
        setTitle("接羔照上传");
        setIsShowBack(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_upload_checked;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        mPhase = getIntent().getIntExtra(getString(R.string.checked_Item), 0);
        mDeviceNo = getIntent().getStringExtra(getString(R.string.scan_Message));
        cropImage(getIntent().getStringExtra(getString(R.string.img_Url)));
    }

    private void initView() {

        mRbHorse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mRbSheep.setChecked(false);
                    mRbBlackSheep.setChecked(false);
                    mRbBull.setChecked(false);
                    variety = "301";
                }
            }
        });

        mRbSheep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mRbHorse.setChecked(false);
                    mRbBlackSheep.setChecked(false);
                    mRbBull.setChecked(false);
                    variety = "101";
                }
            }
        });
        mRbBlackSheep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mRbHorse.setChecked(false);
                    mRbSheep.setChecked(false);
                    mRbBull.setChecked(false);
                    variety = "100";
                }
            }
        });
        mRbBull.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mRbHorse.setChecked(false);
                    mRbSheep.setChecked(false);
                    mRbBlackSheep.setChecked(false);
                    variety = "201";
                }
            }
        });
        mRbBlackSheep.setChecked(true);
    }

    private void cropImage(final String imgUrl) {
        String rootDir = "/Pastureland/crop";
        File file = new File(Environment.getExternalStorageDirectory(), rootDir);

        if (FileUtils.createOrExistsDir(file)) {
            LogUtils.e(file.getAbsolutePath());
            Luban.with(this)
                    .load(FileUtils.getFileByPath(imgUrl))                                   // 传人要压缩的图片列表
                    .ignoreBy(100)                                  // 忽略不压缩图片的大小
                    .setTargetDir(file.getAbsolutePath())
                    .setCompressListener(new OnCompressListener() { //设置回调
                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                            LogUtils.e("onStart");
                            mProgressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onSuccess(File file) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件
                            LogUtils.e("onSuccess");
                            LogUtils.e(file.getAbsolutePath());
                            mPhotoFile = file;
                            Glide.with(UploadCheckedActivity.this).load(file).into(mImgUpload);
//                            FileUtils.deleteFile(imgUrl);
                            mProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Throwable e) {
                            // TODO 当压缩过程出现问题时调用
                            LogUtils.e(e.getMessage());
                            ToastUtils.showShort("压缩出现问题，请重新拍摄");
                            AppManager.getAppManager().finishActivity();
                            mProgressBar.setVisibility(View.GONE);
                        }
                    }).launch();
        }
    }


    @OnClick(R.id.btn_submit)
    public void onClick() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("上传中...");
        pDialog.setCancelable(false);
        pDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pDialog.cancel();
                finish();

            }
        }, 2000);



        /*RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/jpg"), mPhotoFile);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("imgUrl", mPhotoFile.getName(), photoRequestBody);

        RetrofitManger.getInstance().createReq(ApiCall.class)
                .uploadPhoto(photo, RequestBody.create(null, mDeviceNo),
                        RequestBody.create(null, variety),
                        RequestBody.create(null, String.valueOf(mPhase)),
                        RequestBody.create(null, SpUtil.getToken()),
                        RequestBody.create(null, SpUtil.getUserId()),
                        RequestBody.create(null, SpUtil.getAccount()),
                        RequestBody.create(null, SpUtil.getCompanyId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ConfirmClaimBean>() {
                    @Override
                    public void onStart() {
                        showProgress("正在上传");
                    }

                    @Override
                    public void onCompleted() {
                        hiddenProgress();

                    }


                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable throwable) {
                        hiddenProgress();
                        ToastUtils.showShort(throwable.message);
                        LogUtils.e(throwable.getMessage(), throwable.code, throwable.message);
                    }

                    @Override
                    public void onNext(ConfirmClaimBean confirmClaimBean) {
                        if (confirmClaimBean.getCode().equals("200")) {
                            AppManager.getAppManager().finishActivity();
                            ToastUtils.showShort("上传成功");
                        } else {
                            ToastUtils.showShort("上传失败，" + confirmClaimBean.getMsg());
                        }
                    }
                });*/

    }
}
