package com.jinkun_innovation.pastureland.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
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
import com.jinkun_innovation.pastureland.bean.LiveStock;
import com.jinkun_innovation.pastureland.bean.LoginSuccess;
import com.jinkun_innovation.pastureland.common.Constants;
import com.jinkun_innovation.pastureland.utilcode.constant.TimeConstants;
import com.jinkun_innovation.pastureland.utilcode.util.TimeUtils;
import com.jinkun_innovation.pastureland.utilcode.util.ToastUtils;
import com.jinkun_innovation.pastureland.utils.PrefUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Guan on 2018/3/24.
 */

public class RenlingDetailActivity extends Activity {

    private static final String TAG1 = RenlingDetailActivity.class.getSimpleName();

    @BindView(R.id.ivBack)
    ImageView mIvBack;
    @BindView(R.id.sdvYang)
    SimpleDraweeView mSdvYang;


    String mLogin_success;
    LoginSuccess mLoginSuccess;
    String mUsername;

    TextView tvVariety, tvDevcieNO, tvDetail, tvAge, tvLifeTime, tvMuchangName, tvPublishTime;

    private MapView mMapView = null;
    private BitmapDescriptor mCurrentMarker;
    BaiduMap map;

    String getDeviceNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_renling_detail);
        ButterKnife.bind(this);

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);

        map = mMapView.getMap();

        tvVariety = (TextView) findViewById(R.id.tvVariety);
        tvDevcieNO = (TextView) findViewById(R.id.tvDeviceNo);
        tvDetail = (TextView) findViewById(R.id.tvDetail);

        tvAge = (TextView) findViewById(R.id.tvAge);
        tvLifeTime = (TextView) findViewById(R.id.tvLifeTime);
        tvMuchangName = (TextView) findViewById(R.id.tvMuchangName);
        tvPublishTime = (TextView) findViewById(R.id.tvPublishTime);


        Intent intent = getIntent();
        getDeviceNo = intent.getStringExtra("getDeviceNo");

        mLogin_success = PrefUtils.getString(this, "login_success", null);
        Gson gson = new Gson();
        mLoginSuccess = gson.fromJson(mLogin_success, LoginSuccess.class);
        mUsername = PrefUtils.getString(this, "username", null);

        OkGo.<String>get(Constants.LIVESTOCK)
                .tag(this)
                .params("token", mLoginSuccess.getToken())
                .params("username", mUsername)
                .params("ranchID", mLoginSuccess.getRanchID())
                .params("deviceNO", getDeviceNo)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        String s = response.body().toString();
                        Log.d(TAG1, s);
                        Gson gson1 = new Gson();
                        LiveStock liveStock = gson1.fromJson(s, LiveStock.class);
                        String msg = liveStock.getMsg();
                        if (TextUtils.isEmpty(msg)) {
                            ToastUtils.showShort("抱歉，服务器的数据为空");
                        } else {

                            if (msg.equals("获取牲畜详情成功")) {

                                LiveStock.LivestockBean lives = liveStock.getLivestock();

                                String deviceNo = lives.getDeviceNo();
                                tvDevcieNO.setText("设备号：" + deviceNo);

                                String variety = lives.getVariety();
                                if (variety.equals("100")) {

                                    //乌珠木漆黑头羊
                                    tvVariety.setText("品种名称：乌珠木漆黑头羊");


                                } else if (variety.equals("101")) {

                                    //山羊
                                    tvVariety.setText("品种名称：山羊");

                                } else if (variety.equals("201")) {

                                    //西门塔尔牛
                                    tvVariety.setText("品种名称：西门塔尔牛");

                                } else if (variety.equals("301")) {
                                    //蒙古马
                                    tvVariety.setText("品种名称：蒙古马");


                                } else if (variety.equals("401")) {
                                    //草原黑毛猪
                                    tvVariety.setText("品种名称：草原黑毛猪");

                                } else if (variety.equals("701")) {
                                    //骆驼
                                    tvVariety.setText("品种名称：骆驼");
                                }

                                String livestockDetails = lives.livestockDetails;
                                PrefUtils.setString(getApplicationContext(), "introduce", livestockDetails);
                                tvDetail.setText(livestockDetails);

                                //homeImgUrl
                                String homeImgUrl = lives.homeImgUrl;
                                if (!TextUtils.isEmpty(homeImgUrl)) {
                                    homeImgUrl = Constants.BASE_URL + homeImgUrl;
                                    Uri uri1 = Uri.parse(homeImgUrl);
                                    mSdvYang.setImageURI(uri1);
                                }

                                String createTime = lives.getCreateTime();

                                long timeSpanByNow = TimeUtils.getTimeSpanByNow(createTime, TimeConstants.DAY);
                                int age = (int) timeSpanByNow / 30;
                                Log.d(TAG1, age + "个月");
                                if (age == 1) {
                                    age = 2;
                                } else if (age == 2) {
                                    age = 3;
                                } else if (age == 0) {
                                    age = 1;
                                }

                                tvAge.setText("年龄：" + age + "个月");
                                tvLifeTime.setText("一般寿命：" + lives.getLifeTime() + "个月");
                                tvMuchangName.setText("牧场：" + lives.getName());

                                if (lives.getIsClaimed().contains("0")) {
                                    tvPublishTime.setText("发布时间：" + lives.getUpdateTime());

                                } else if (lives.getIsClaimed().contains("1")) {

                                    tvPublishTime.setText("认领时间：" + lives.getUpdateTime());

                                }


                                String lantitudeBaidu = lives.getLantitudeBaidu();
                                String longtitudeBaidu = lives.getLongtitudeBaidu();

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


                            }


                        }


                    }
                });


        /*OkGo.<String>get(Constants.DianziDangan)
                .tag(this)
                .params("deviceNo",getDeviceNo)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        String result = response.body().toString();


                    }
                });*/

        webView = (WebView) findViewById(R.id.wvElectronicDangan);
        initWebView();
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);


        OkGo.<String>get(Constants.DianziDangan)
                .tag(this)
                .params("deviceNo", getDeviceNo)
                .params("ranchID", mLoginSuccess.getRanchID())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {


                    }
                });


    }

    private WebView webView;

    /**
     * 视频全屏参数
     */
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS =
            new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
    private View customView;
    private FrameLayout fullscreenContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;

    @Override
    protected void onStop() {
        super.onStop();
        webView.reload();
    }

    /**
     * 展示网页界面
     **/
    public void initWebView() {
        WebChromeClient wvcc = new WebChromeClient();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容

        webView.setWebChromeClient(wvcc);
        WebViewClient wvc = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }
        };
        webView.setWebViewClient(wvc);

        webView.setWebChromeClient(new WebChromeClient() {

            /*** 视频播放相关的方法 **/

            @Override
            public View getVideoLoadingProgressView() {
                FrameLayout frameLayout = new FrameLayout(RenlingDetailActivity.this);
                frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams
                        .MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                return frameLayout;
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                showCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {
                hideCustomView();
            }
        });

        // 加载Web地址
        webView.loadUrl(Constants.DianziDangan + "?deviceNo=" + getDeviceNo
                + "&ranchID=" + mLoginSuccess.getRanchID());


    }

    /**
     * 视频播放全屏
     **/
    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }

        RenlingDetailActivity.this.getWindow().getDecorView();

        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(RenlingDetailActivity.this);
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
    }

    /**
     * 隐藏视频全屏
     */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }

        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        webView.setVisibility(View.VISIBLE);
    }

    /**
     * 全屏容器界面
     */
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                /** 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 */
                if (customView != null) {
                    hideCustomView();
                } else if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }


    @OnClick(R.id.ivBack)
    public void onViewClicked() {

        finish();

    }


}
