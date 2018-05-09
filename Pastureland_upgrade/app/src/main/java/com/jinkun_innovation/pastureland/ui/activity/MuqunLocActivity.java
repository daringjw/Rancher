package com.jinkun_innovation.pastureland.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.jinkun_innovation.pastureland.R;

/**
 * Created by Guan on 2018/5/4.
 */

public class MuqunLocActivity extends Activity {

    private MapView mMapView = null;
    BaiduMap mBaiduMap;
    private BitmapDescriptor mCurrentMarker;
    private BDLocation mLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_muqun_loc);

//获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();


        // 开启定位图层
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
        mBaiduMap.setMyLocationConfiguration(config);


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
