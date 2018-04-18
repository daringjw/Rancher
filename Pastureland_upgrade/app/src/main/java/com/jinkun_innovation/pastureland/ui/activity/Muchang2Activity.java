package com.jinkun_innovation.pastureland.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
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

import static com.jinkun_innovation.pastureland.R.id.tvDetail;

/**
 * Created by Guan on 2018/4/4.
 */

public class Muchang2Activity extends Activity {

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
    @BindView(R.id.tvOwner)
    TextView tvOwner;

    @BindView(tvDetail)
    TextView mTvDetail;

    private LoginSuccess mLoginSuccess;
    private String mLogin_success;
    private String mUsername;


    private MapView mMapView = null;
    private BitmapDescriptor mCurrentMarker;
    BaiduMap map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_muchang2);
        ButterKnife.bind(this);

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);

        map = mMapView.getMap();


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

                            mTvRancher.setText("牧场名：" + muqunDetail.getRanch().getName());
                            mTvPhone.setText("联系电话：" + muqunDetail.getRanch().getRancherAccount());

                            mTvRiverNearby.setText("附近河流：" + muqunDetail.getRanch().getIntroduceRiver());
                            mTvMianji.setText("面积：" + muqunDetail.getRanch().getAcreage() + "亩");

                            mTvNum.setText("数量：" + muqunDetail.getRanch().getIntroduceAnimalCount() + "头");
                            mTvVariety.setText("种类：" + muqunDetail.getRanch().getIntroduceAnimalType());

                            tvOwner.setText("牧场主：" + muqunDetail.getRanch().getRancherName());

                            mTvDetail.setText(muqunDetail.getRanch().getIntroduce());

                            String lantitudeBaidu = muqunDetail.getRanch().getLantitudeBaidu();
                            String longtitudeBaidu = muqunDetail.getRanch().getLongtitudeBaidu();

                            BDLocation bdLocation = new BDLocation();
                            bdLocation.setLongitude(Double.parseDouble(longtitudeBaidu));
                            bdLocation.setLatitude(Double.parseDouble(lantitudeBaidu));


                            // 开启定位图层
                            map.setMyLocationEnabled(true);

                            // 构造定位数据
                            MyLocationData locData = new MyLocationData.Builder()
                                    .accuracy(bdLocation.getRadius())
                                    // 此处设置开发者获取到的方向信息，顺时针0-360
                                    //.direction(100)
                                    .latitude(bdLocation.getLatitude())
                                    .longitude(bdLocation.getLongitude()).build();

                            // 设置定位数据
                            map.setMyLocationData(locData);

                            // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
                            mCurrentMarker = BitmapDescriptorFactory
                                    .fromResource(R.mipmap.icon_location_3);
                            MyLocationConfiguration config = new MyLocationConfiguration(
                                    MyLocationConfiguration.LocationMode.FOLLOWING,
                                    true, mCurrentMarker);

                            map.setMyLocationConfiguration(config);


                        } else {

                            ToastUtils.showShort("获取牧场详情失败");

                        }


                    }
                });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }


    @OnClick({R.id.ivBack, R.id.ivEdit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:

                finish();

                break;
            case R.id.ivEdit:

                Intent intent = new Intent(getApplicationContext(), EditMuchangActivity.class);
                startActivityForResult(intent, 1001);

                break;

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case 1001:

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

                                        mTvRancher.setText("牧场名：" + muqunDetail.getRanch().getName());
                                        mTvPhone.setText("联系电话：" + muqunDetail.getRanch().getRancherAccount());

                                        mTvRiverNearby.setText("附近河流：" + muqunDetail.getRanch().getIntroduceRiver());
                                        mTvMianji.setText("面积：" + muqunDetail.getRanch().getAcreage() + "亩");

                                        mTvNum.setText("数量：" + muqunDetail.getRanch().getIntroduceAnimalCount() + "头");
                                        mTvVariety.setText("种类：" + muqunDetail.getRanch().getIntroduceAnimalType());

                                        tvOwner.setText("牧场主：" + muqunDetail.getRanch().getRancherName());

                                        mTvDetail.setText(muqunDetail.getRanch().getIntroduce());


                                    } else {

                                        ToastUtils.showShort("获取牧场详情失败");

                                    }


                                }
                            });


                    break;


            }

        }

    }


}
