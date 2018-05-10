package com.jinkun_innovation.pastureland.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapView;
import com.google.gson.Gson;
import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.bean.LoginSuccess;
import com.jinkun_innovation.pastureland.bean.MuqunLoc;
import com.jinkun_innovation.pastureland.common.Constants;
import com.jinkun_innovation.pastureland.utils.PrefUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

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

    List<MuqunLoc.LivestockVarietyListBean> livestockVarietyList;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    MyAdapter mAdapter;

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

        OkGo.<String>get(Constants.queryLivestockList)
                .tag(this)
                .params("livestockType", 1)
                .params("ranchID", mLoginSuccess.getRanchID())
                .params("current", 0)
                .params("pagesize", 99999)
                .params("token", mLoginSuccess.getToken())
                .params("username", mUsername)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        String result = response.body().toString();
                        Gson gson1 = new Gson();
                        MuqunLoc muqunLoc = gson1.fromJson(result, MuqunLoc.class);
                        String msg = muqunLoc.getMsg();
                        if (msg.contains("按类型获取牲畜成功")) {

                            livestockVarietyList = muqunLoc.getLivestockVarietyList();
                            mAdapter = new MyAdapter(livestockVarietyList);
                            mRecyclerView.setAdapter(mAdapter);


                        }


                    }


                });


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


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        public List<MuqunLoc.LivestockVarietyListBean> datas = null;

        public MyAdapter(List<MuqunLoc.LivestockVarietyListBean> datas) {

            this.datas = datas;

        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_loc_yang, viewGroup, false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }

        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            viewHolder.ivIcon.setImageResource(R.mipmap.loc_yang);
            String deviceNo = datas.get(position).getDeviceNo();
            deviceNo = deviceNo.substring(deviceNo.length() - 6, deviceNo.length());
            viewHolder.tvId.setText(deviceNo);


        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return datas.size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            //            public TextView mTextView;
            TextView tvId;
            ImageView ivIcon;


            public ViewHolder(View view) {
                super(view);
//                mTextView = (TextView) view.findViewById(R.id.text);
                tvId = view.findViewById(R.id.tvId);
                ivIcon = view.findViewById(R.id.ivIcon);


            }
        }


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
