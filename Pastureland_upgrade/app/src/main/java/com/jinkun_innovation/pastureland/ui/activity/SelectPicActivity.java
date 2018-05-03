package com.jinkun_innovation.pastureland.ui.activity;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.bean.ImgUrlBean;
import com.jinkun_innovation.pastureland.bean.LoginSuccess;
import com.jinkun_innovation.pastureland.common.Constants;
import com.jinkun_innovation.pastureland.utils.ImageUtils;
import com.jinkun_innovation.pastureland.utils.PrefUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Guan on 2018/5/2.
 */

public class SelectPicActivity extends Activity {

    private static final String TAG1 = SelectPicActivity.class.getSimpleName();

    String mLogin_success;
    LoginSuccess mLoginSuccess;
    String mUsername;

    String mImgUrl;
    private File mFile1;

    Bitmap bmp;
    private File mFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_pic);

        final ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });


        mLogin_success = PrefUtils.getString(getApplicationContext(), "login_success", null);
        Gson gson = new Gson();
        mLoginSuccess = gson.fromJson(mLogin_success, LoginSuccess.class);
        mUsername = PrefUtils.getString(getApplicationContext(), "username", null);

        final ImageView ivPic = (ImageView) findViewById(R.id.ivPic);

        final Resources res = getResources();

        ivPic.setImageResource(R.mipmap.yang_1);
        bmp = BitmapFactory.decodeResource(res, R.mipmap.yang_1);

        mFile = new File(Environment.getExternalStorageDirectory(), "/Pastureland/pic");

        Button btnPic1 = (Button) findViewById(R.id.btnPic1);
        btnPic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ivPic.setImageResource(R.mipmap.yang_1);
                bmp = null;
                bmp = BitmapFactory.decodeResource(res, R.mipmap.yang_1);

                String yang_1 = ImageUtils.savePhoto(bmp, mFile.getAbsolutePath(), "yang_1");
                mFile1 = null;
                mFile1 = new File(yang_1);

            }
        });

        Button btnPic2 = (Button) findViewById(R.id.btnPic2);
        btnPic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ivPic.setImageResource(R.mipmap.yang_2);
                bmp = null;
                bmp = BitmapFactory.decodeResource(res, R.mipmap.yang_2);

                String yang_1 = ImageUtils.savePhoto(bmp, mFile.getAbsolutePath(), "yang_1");
                mFile1 = null;
                mFile1 = new File(yang_1);

            }
        });

        Button btnPic3 = (Button) findViewById(R.id.btnPic3);
        btnPic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivPic.setImageResource(R.mipmap.yang_3);
                bmp = null;
                bmp = BitmapFactory.decodeResource(res, R.mipmap.yang_3);

                String yang_1 = ImageUtils.savePhoto(bmp, mFile.getAbsolutePath(), "yang_1");
                mFile1 = null;
                mFile1 = new File(yang_1);

            }
        });


        String yang_1 = ImageUtils.savePhoto(bmp, mFile.getAbsolutePath(), "yang_1");
        mFile1 = new File(yang_1);


        Button btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                OkGo.<String>post(Constants.HEADIMGURL)
                        .tag(this)
                        .isMultipart(true)
                        .params("token", mLoginSuccess.getToken())
                        .params("username", mUsername)
                        .params("uploadFile", mFile1)
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

                                PrefUtils.setString(getApplicationContext(), "pic", mImgUrl);

                                setResult(RESULT_OK);
                                finish();


                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);


                                new SweetAlertDialog(SelectPicActivity.this,
                                        SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("抱歉...")
                                        .setContentText("网络不稳定,上传图片失败,请重新拍摄")
                                        .show();


                            }
                        });

            }
        });


    }


}
