package com.jinkun_innovation.pastureland.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapView;
import com.google.gson.Gson;
import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.bean.LoginSuccess;
import com.jinkun_innovation.pastureland.bean.QueryByYang;
import com.jinkun_innovation.pastureland.ui.locui.CamelLocActivity;
import com.jinkun_innovation.pastureland.utils.PrefUtils;

import java.util.List;

/**
 * Created by Guan on 2018/5/4.
 */

public class MuqunLocActivity extends Activity {

    private static final String TAG1 = MuqunLocActivity.class.getSimpleName();
    private MapView mMapView = null;
    BaiduMap mBaiduMap;
    private BitmapDescriptor mCurrentMarker;
    private BDLocation mLocation;

    String mLogin_success;
    LoginSuccess mLoginSuccess;
    String mUsername;

    private List<QueryByYang.LivestockVarietyListBean> mLivestockVarietyList;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    CamelLocActivity.MyAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_muqun_loc);

        ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        mLogin_success = PrefUtils.getString(this, "login_success", null);
        Gson gson = new Gson();
        mLoginSuccess = gson.fromJson(mLogin_success, LoginSuccess.class);
        mUsername = PrefUtils.getString(this, "username", null);



        mRecyclerView = (RecyclerView) findViewById(R.id.rvList);
//创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
//如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        /*OkGo.<String>get(Constants.QUERYLIVESTOCKVARIETYLIST)
                .tag(this)
                .params("token", mLoginSuccess.getToken())
                .params("username", mUsername)
                .params("ranchID", mLoginSuccess.getRanchID())
                .params("livestockType", 7)
                .params("current", 0)
                .params("pagesize", 9999)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        String s = response.body().toString();
                        Log.d(TAG1, s);

                        if (s.contains("imgUrl")) {
                            //有数据
                            Gson gson1 = new Gson();
                            QueryByYang queryByYang = gson1.fromJson(s, QueryByYang.class);
                            mLivestockVarietyList = queryByYang.getLivestockVarietyList();
                            String deviceNo = mLivestockVarietyList.get(0).getDeviceNo();
                            Log.d(TAG1, deviceNo);
                            //创建并设置Adapter
                            mAdapter = new CamelLocActivity.MyAdapter(mLivestockVarietyList);
                            mRecyclerView.setAdapter(mAdapter);
                            mRecyclerView.setVisibility(View.VISIBLE);


                        } else {


                        }


                    }
                });*/

//获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();





        /*// 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        mLocation = new BDLocation();
        mLocation.setLatitude(22.5366038785);
        mLocation.setLongitude(113.9381825394);


// 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(mLocation.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
//                .direction(100)
                .latitude(mLocation.getLatitude())
                .longitude(mLocation.getLongitude()).build();

// 设置定位数据
        mBaiduMap.setMyLocationData(locData);

// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）

        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_location_3);


        MyLocationConfiguration config = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING,
                true, mCurrentMarker);
        mBaiduMap.setMyLocationConfiguration(config);*/


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


}
