package com.jinkun_innovation.pastureland.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jinkun_innovation.pastureland.R;
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

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by Guan on 2018/4/8.
 */

public class QuPaizhaoActivity extends Activity {

    String mLogin_success;
    LoginSuccess mLoginSuccess;
    String mUsername;
    private ImageView mIvQupaizhao;
    private String mImgUrl1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_qupaizhao);

        Intent intent = getIntent();
        String qupaizhao = intent.getStringExtra("qupaizhao");
        final String mDeviceNo = intent.getStringExtra("mDeviceNo");


        mIvQupaizhao = (ImageView) findViewById(R.id.ivQupaizhao);
//        mIvQupaizhao.setImageBitmap(BitmapFactory.decodeFile(qupaizhao));

        mLogin_success = PrefUtils.getString(this, "login_success", null);
        Gson gson = new Gson();
        mLoginSuccess = gson.fromJson(mLogin_success, LoginSuccess.class);
        mUsername = PrefUtils.getString(this, "username", null);


        cropImage(qupaizhao);

        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(mImgUrl1)) {

                    ToastUtils.showShort("正在上传图片，请稍后点击");
                } else {

                    OkGo.<String>post(Constants.updLivestockClaim)
                            .tag(this)
                            .params("token", mLoginSuccess.getToken())
                            .params("username", mUsername)
                            .params("deviceNO", mDeviceNo)
                            .params("businessType", 1)
                            .params("livestockImgUrl", mImgUrl1)
//                            .params("videoUrl", "")
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {

                                    String result = response.body().toString();
                                    if (result.contains("success")) {

                                        ToastUtils.showShort("上传成功");
                                        setResult(RESULT_OK);
                                        finish();

                                    }

                                }
                            });


                }

            }
        });


    }

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

                            LogUtils.e("onSuccess");
                            LogUtils.e(file.getAbsolutePath());
                            Glide.with(QuPaizhaoActivity.this).load(file).into(mIvQupaizhao);


                            //上传图片
                            OkGo.<String>post(Constants.HEADIMGURL)
                                    .tag(this)
                                    .params("token", mLoginSuccess.getToken())
                                    .params("username", mUsername)
                                    .params("uploadFile", file)
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(Response<String> response) {

                                            String result = response.body().toString();
                                            Gson gson = new Gson();
                                            ImgUrlBean imgUrlBean = gson.fromJson(result, ImgUrlBean.class);
                                            mImgUrl1 = imgUrlBean.getImgUrl();


                                        }
                                    });


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


}
