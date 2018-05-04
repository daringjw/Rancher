package com.jinkun_innovation.pastureland.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.jinkun_innovation.pastureland.R;

/**
 * Created by Guan on 2018/5/4.
 */

public class MuqunLocActivity extends Activity {

    private MapView mMapView = null;
    BaiduMap map;
    private BitmapDescriptor mCurrentMarker;

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


        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        map = mMapView.getMap();

        BDLocation bdLocation = new BDLocation();
        bdLocation.setLongitude(Double.parseDouble("116.397428"));
        bdLocation.setLatitude(Double.parseDouble("39.86923"));


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

        //创建InfoWindow展示的view
        Button button = new Button(getApplicationContext());
//        button.setBackgroundResource(R.mipmap.popup);
        button.setText("牲畜类型：羊\n"+"牲畜品种：乌珠木漆黑羊\n"+"设备编号：34134125\n"
                +"上传时间：2018-04-25 09:02:01\n"+"牲畜位置：北京海淀区五道口路");

//定义用于显示该InfoWindow的坐标点
        LatLng pt = new LatLng(39.86923, 116.397428);

//创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
        final InfoWindow mInfoWindow = new InfoWindow(button, pt, -47);

//显示InfoWindow
        map.showInfoWindow(mInfoWindow);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                map.clear();

            }
        });


        map.setOnMyLocationClickListener(new BaiduMap.OnMyLocationClickListener() {
            @Override
            public boolean onMyLocationClick() {

                map.showInfoWindow(mInfoWindow);
                return false;

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


}
