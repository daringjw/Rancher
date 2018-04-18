package com.jinkun_innovation.pastureland.ui.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.bean.LoginSuccess;
import com.jinkun_innovation.pastureland.bean.MuqunDetail;
import com.jinkun_innovation.pastureland.common.Constants;
import com.jinkun_innovation.pastureland.utilcode.util.ToastUtils;
import com.jinkun_innovation.pastureland.utils.PrefUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Guan on 2018/4/4.
 */

public class EditMuchangActivity extends Activity {

    @BindView(R.id.ivBack)
    ImageView mIvBack;
    @BindView(R.id.tvSave)
    TextView mTvSave;
    @BindView(R.id.sdvYang)
    SimpleDraweeView mSdvYang;

    @BindView(R.id.etMuChangName)
    EditText etMuChangName;
    @BindView(R.id.tvMianji)
    EditText mTvMianji;
    @BindView(R.id.tvRiverNearby)
    EditText mTvRiverNearby;
    @BindView(R.id.tvNum)
    EditText mTvNum;
    @BindView(R.id.tvVariety)
    EditText mTvVariety;
    @BindView(R.id.tvWeilan)
    EditText mTvWeilan;
    @BindView(R.id.tvDetail)
    EditText mTvDetail;
    private LoginSuccess mLoginSuccess;
    private String mLogin_success;
    private String mUsername;
    private String mRanchImgUrl;
    private String mImgUrl;
    private String mRanchurl;
    private String mName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_muchang_edit);
        ButterKnife.bind(this);

        mLogin_success = PrefUtils.getString(this, "login_success", null);
        Gson gson = new Gson();
        mLoginSuccess = gson.fromJson(mLogin_success, LoginSuccess.class);
        mUsername = PrefUtils.getString(this, "username", null);

        OkGo.<String>get(Constants.RANCH)
                .tag(this)
                .params("token", mLoginSuccess.getToken())
                .params("username", mUsername)
                .params("ranchID", mLoginSuccess.getRanchID())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        String result = response.body().toString();
                        Gson gson1 = new Gson();
                        MuqunDetail muqunDetail = gson1.fromJson(result, MuqunDetail.class);
                        String msg = muqunDetail.getMsg();
                        if (msg.equals("获取牧场详情成功")) {

                            String imgUrl = muqunDetail.getRanch().getImgUrl();
                            imgUrl = Constants.BASE_URL + imgUrl;
                            mSdvYang.setImageURI(Uri.parse(imgUrl));

                            mSdvYang.setImageURI(Uri.parse(imgUrl));

                            mImgUrl = muqunDetail.getRanch().getImgUrl();

                            mName = muqunDetail.getRanch().getName();


                        } else {

                            ToastUtils.showShort("获取牧场详情失败");
                        }
                    }
                });


    }


    @OnClick({R.id.ivBack, R.id.tvSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:

                finish();

                break;
            case R.id.tvSave:

                //更新牧场
                OkGo.<String>post(Constants.UPDRANCH)
                        .tag(this)
                        .params("token", mLoginSuccess.getToken())
                        .params("username", mUsername)
                        .params("ranchID", mLoginSuccess.getRanchID())
                        .params("imgUrl", mImgUrl)
                        .params("name", etMuChangName.getText().toString())    //牧场名称
                        .params("acreage", mTvMianji.getText().toString())      //面积
                        .params("introduceAnimalCount", mTvNum.getText().toString())    //数量
                        .params("introduceRiver", mTvRiverNearby.getText().toString())   // 河流  0：无，1有
                        .params("isFenceClose", mTvWeilan.getText().toString())     // 围栏封闭  0：封闭，1开放
                        .params("introduce", mTvDetail.getText().toString())      //介绍
                        .params("longtitudeBaidu", 117.6039815171)
                        .params("lantitudeBaidu", 43.7456692790)
                        .params("ranchImgUrl", mRanchurl)
                        .params("videoUrl", "")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {

                                String result = response.body().toString();
                                if (result.contains("success")) {

                                    ToastUtils.showShort("牧场信息更新成功");
                                    setResult(RESULT_OK);
                                    finish();


                                } else {

                                    ToastUtils.showShort("牧场信息更新失败");
                                }

                            }
                        });


                break;
        }
    }
}
