package com.jinkun_innovation.pastureland.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.bean.LiveStock;
import com.jinkun_innovation.pastureland.bean.LoginSuccess;
import com.jinkun_innovation.pastureland.common.Constants;
import com.jinkun_innovation.pastureland.utils.PrefUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/**
 * Created by Guan on 2018/4/25.
 */

public class PublishClaimActivity2 extends Activity {

    String mLogin_success;
    LoginSuccess mLoginSuccess;
    String mUsername;

    String isbn;
    private SimpleDraweeView mIvTakePhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_publish_claim2);

        initView();


        mLogin_success = PrefUtils.getString(this, "login_success", null);
        Gson gson = new Gson();
        mLoginSuccess = gson.fromJson(mLogin_success, LoginSuccess.class);
        mUsername = PrefUtils.getString(this, "username", null);

        Intent intent = getIntent();
        isbn = intent.getStringExtra("isbn");


        OkGo.<String>get(Constants.LIVESTOCK)
                .tag(this)
                .params("token", mLoginSuccess.getToken())
                .params("username", mUsername)
                .params("ranchID", mLoginSuccess.getRanchID())
                .params("deviceNO", isbn)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        String result = response.body().toString();

                        Gson gson1 = new Gson();
                        LiveStock liveStock = gson1.fromJson(result, LiveStock.class);
                        String msg = liveStock.getMsg();
                        if (msg.contains("获取牲畜详情成功")) {

                            LiveStock.LivestockBean livestockBean = liveStock.getLivestock();
                            String homeImgUrl = livestockBean.homeImgUrl;
                            homeImgUrl = Constants.BASE_URL + homeImgUrl;
                            mIvTakePhoto.setImageURI(homeImgUrl);

                            String variety = livestockBean.getVariety();



                        }


                    }
                });


    }

    private void initView() {

        mIvTakePhoto = findViewById(R.id.ivTakePhoto);
        TextView viewById = (TextView) findViewById(R.id.etDeviceNo);


    }


}
