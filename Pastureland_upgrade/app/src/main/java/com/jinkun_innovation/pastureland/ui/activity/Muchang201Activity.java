package com.jinkun_innovation.pastureland.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
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

public class Muchang201Activity extends Activity {

    @BindView(R.id.ivBack)
    ImageView mIvBack;
    @BindView(R.id.ivEdit)
    ImageView mIvEdit;
    @BindView(R.id.sdvYang)
    SimpleDraweeView mSdvYang;
    @BindView(R.id.tvRancher)
    TextView mTvRancher;
    @BindView(R.id.tvPhone)
    TextView mTvPhone;
    @BindView(R.id.tvMianji)
    TextView mTvMianji;
    @BindView(R.id.tvRiverNearby)
    TextView mTvRiverNearby;
    @BindView(R.id.tvNum)
    TextView mTvNum;
    @BindView(R.id.tvVariety)
    TextView mTvVariety;

    @BindView(R.id.tvDetail)
    TextView mTvDetail;


    @BindView(R.id.tvMuchangLoc)
    TextView tvMuchangLoc;

    private LoginSuccess mLoginSuccess;
    private String mLogin_success;
    private String mUsername;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_muchang2);
        ButterKnife.bind(this);

        mIvEdit.setVisibility(View.GONE);
        tvMuchangLoc.setVisibility(View.GONE);


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

                            String ranchImgUrl = muqunDetail.getRanch().getRanchImgUrl();
                            ranchImgUrl = Constants.BASE_URL + ranchImgUrl;
                            mSdvYang.setImageURI(Uri.parse("http://s16.sinaimg.cn/large/4c74484dx738034dca2ef&690"));

                            mTvRancher.setText("牧场：" + muqunDetail.getRanch().getName());
                            mTvPhone.setText("联系电话：" + muqunDetail.getRanch().getRancherAccount());

                            mTvRiverNearby.setText("附近河流：" + muqunDetail.getRanch().getIntroduceRiver());
                            mTvMianji.setText("面积：" + muqunDetail.getRanch().getAcreage() + "平方公里");

                            mTvNum.setText("数量：" + muqunDetail.getRanch().getIntroduceAnimalCount() + "头");
                            mTvVariety.setText("种类：" + muqunDetail.getRanch().getIntroduceAnimalType());

                            mTvDetail.setText(muqunDetail.getRanch().getIntroduce());

                        } else {

                            ToastUtils.showShort("获取牧场详情失败");

                        }


                    }
                });

    }


    @OnClick({R.id.ivBack, R.id.ivEdit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:

                finish();

                break;
            case R.id.ivEdit:

                startActivity(new Intent(getApplicationContext(), EditMuchangActivity.class));

                break;

        }


    }
}
