package com.jinkun_innovation.pastureland.ui.locui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.bean.LiveStock;
import com.jinkun_innovation.pastureland.bean.LoginSuccess;
import com.jinkun_innovation.pastureland.bean.MessageEvent;
import com.jinkun_innovation.pastureland.bean.QueryByYang;
import com.jinkun_innovation.pastureland.common.Constants;
import com.jinkun_innovation.pastureland.utils.PrefUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Guan on 2018/5/4.
 */

public class DonkeyLocActivity extends Activity {

    private static final String TAG1 = DonkeyLocActivity.class.getSimpleName();

    private MapView mMapView = null;
    BaiduMap map;
    private BitmapDescriptor mCurrentMarker;

    boolean expansion = true;
    private Button mButton;
    private BDLocation mBdLocation;
    private MyLocationData mLocData;
    private MyLocationConfiguration mConfig;
    private LatLng mPt;
    private InfoWindow mMInfoWindow;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
//        mText.setText(messageEvent.getMessage());

        Log.d(TAG1, "messageEvent.getMessage()=" + messageEvent.getMessage());

    }

    String mLogin_success;
    LoginSuccess mLoginSuccess;
    String mUsername;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);

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

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        map = mMapView.getMap();

        mBdLocation = new BDLocation();


        //创建InfoWindow展示的view
        mButton = new Button(getApplicationContext());
        mButton.setBackgroundColor(Color.WHITE);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                map.clear();

            }
        });


        map.setOnMyLocationClickListener(new BaiduMap.OnMyLocationClickListener() {
            @Override
            public boolean onMyLocationClick() {

                map.showInfoWindow(mMInfoWindow);
                return false;

            }
        });


        mRecyclerView = (RecyclerView) findViewById(R.id.rvList);
//创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
//如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        OkGo.<String>get(Constants.QUERYLIVESTOCKVARIETYLIST)
                .tag(this)
                .params("token", mLoginSuccess.getToken())
                .params("username", mUsername)
                .params("ranchID", mLoginSuccess.getRanchID())
                .params("livestockType", 8)
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
                            mAdapter = new MyAdapter(mLivestockVarietyList);
                            mRecyclerView.setAdapter(mAdapter);
                            mRecyclerView.setVisibility(View.VISIBLE);


                        } else {


                        }


                    }
                });


        final Button btnExpansion = (Button) findViewById(R.id.btnExpansion);
        btnExpansion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (expansion) {
                    expansion = false;
                    btnExpansion.setText("展开");

                    mRecyclerView.setVisibility(View.GONE);

                } else {

                    expansion = true;
                    btnExpansion.setText("收起");

                    //创建并设置Adapter
                    //访问服务器数据
                    //通过牲畜类型查询所有牲畜
                    OkGo.<String>get(Constants.QUERYLIVESTOCKVARIETYLIST)
                            .tag(this)
                            .params("token", mLoginSuccess.getToken())
                            .params("username", mUsername)
                            .params("ranchID", mLoginSuccess.getRanchID())
                            .params("livestockType", 8)
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
                                        mAdapter = new MyAdapter(mLivestockVarietyList);
                                        mRecyclerView.setAdapter(mAdapter);
                                        mRecyclerView.setVisibility(View.VISIBLE);


                                    } else {


                                    }


                                }
                            });


                }


            }
        });


    }

    private List<QueryByYang.LivestockVarietyListBean> mLivestockVarietyList;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    MyAdapter mAdapter;

    private String[] getDummyDatas() {

        String[] arr = {"北京", "上海", "广州", "深圳"};

        return arr;

    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        public List<QueryByYang.LivestockVarietyListBean> datas = null;

        public MyAdapter(List<QueryByYang.LivestockVarietyListBean> datas) {

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
//            viewHolder.mTextView.setText(datas[position]);


            String deviceNo = datas.get(position).getDeviceNo();
            deviceNo = deviceNo.substring(deviceNo.length() - 6, deviceNo.length());
            viewHolder.tvId.setText(deviceNo);

            viewHolder.ivIcon.setImageResource(R.mipmap.donkey_loc);

            final String deviceNo1 = datas.get(position).getDeviceNo();
            OkGo.<String>get(Constants.LIVESTOCK)
                    .tag(this)
                    .params("token", mLoginSuccess.getToken())
                    .params("username", mUsername)
                    .params("ranchID", mLoginSuccess.getRanchID())
                    .params("deviceNO", deviceNo1)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {


                            String result = response.body().toString();
                            Gson gson1 = new Gson();
                            LiveStock liveStock = gson1.fromJson(result, LiveStock.class);
                            String msg = liveStock.getMsg();

                            LiveStock.LivestockBean lives = liveStock.getLivestock();
                            String longtitudeBaidu = lives.getLongtitudeBaidu();
                            String lantitudeBaidu = lives.getLantitudeBaidu();


                            mBdLocation.setLongitude(Double.parseDouble(longtitudeBaidu));
                            mBdLocation.setLatitude(Double.parseDouble(lantitudeBaidu));


                            if (position != 0) {
                                //不是第一个可以增加
                                //增加Marker
                                //定义Maker坐标点
                                LatLng point = new LatLng(mBdLocation.getLatitude(), mBdLocation.getLongitude());
//构建Marker图标
                                BitmapDescriptor bitmap = BitmapDescriptorFactory
                                        .fromResource(R.mipmap.icon_location_3);
//构建MarkerOption，用于在地图上添加Marker
                                OverlayOptions option = new MarkerOptions()
                                        .position(point)
                                        .icon(bitmap);
//在地图上添加Marker，并显示

                                map.addOverlay(option);

                            } else {
                                //是第一个不可以增加


                            }


                            // 开启定位图层
                            map.setMyLocationEnabled(true);

                            // 构造定位数据
                            // 此处设置开发者获取到的方向信息，顺时针0-360
//.direction(100)
                            mLocData = new MyLocationData.Builder()
                                    .accuracy(mBdLocation.getRadius())
                                    // 此处设置开发者获取到的方向信息，顺时针0-360
                                    //.direction(100)
                                    .latitude(mBdLocation.getLatitude())
                                    .longitude(mBdLocation.getLongitude()).build();

                            // 设置定位数据
                            map.setMyLocationData(mLocData);

                            // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
                            mCurrentMarker = BitmapDescriptorFactory
                                    .fromResource(R.mipmap.icon_location_3);
                            mConfig = new MyLocationConfiguration(
                                    MyLocationConfiguration.LocationMode.FOLLOWING,
                                    true, mCurrentMarker);

                            map.setMyLocationConfiguration(mConfig);


                        }
                    });


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    ToastUtils.showShort("点击条目：" + position);

                    final String deviceNo1 = datas.get(position).getDeviceNo();
                    OkGo.<String>get(Constants.LIVESTOCK)
                            .tag(this)
                            .params("token", mLoginSuccess.getToken())
                            .params("username", mUsername)
                            .params("ranchID", mLoginSuccess.getRanchID())
                            .params("deviceNO", deviceNo1)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {


                                    String result = response.body().toString();
                                    Gson gson1 = new Gson();
                                    LiveStock liveStock = gson1.fromJson(result, LiveStock.class);
                                    String msg = liveStock.getMsg();

                                    LiveStock.LivestockBean lives = liveStock.getLivestock();
                                    String longtitudeBaidu = lives.getLongtitudeBaidu();
                                    String lantitudeBaidu = lives.getLantitudeBaidu();


                                    mBdLocation.setLongitude(Double.parseDouble(longtitudeBaidu));
                                    mBdLocation.setLatitude(Double.parseDouble(lantitudeBaidu));

                                    // 开启定位图层
                                    map.setMyLocationEnabled(true);

                                    // 构造定位数据
                                    // 此处设置开发者获取到的方向信息，顺时针0-360
//.direction(100)
                                    mLocData = new MyLocationData.Builder()
                                            .accuracy(mBdLocation.getRadius())
                                            // 此处设置开发者获取到的方向信息，顺时针0-360
                                            //.direction(100)
                                            .latitude(mBdLocation.getLatitude())
                                            .longitude(mBdLocation.getLongitude()).build();

                                    // 设置定位数据
                                    map.setMyLocationData(mLocData);

                                    // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
                                    mCurrentMarker = BitmapDescriptorFactory
                                            .fromResource(R.mipmap.icon_location_3);
                                    mConfig = new MyLocationConfiguration(
                                            MyLocationConfiguration.LocationMode.FOLLOWING,
                                            true, mCurrentMarker);

                                    map.setMyLocationConfiguration(mConfig);

                                    //创建InfoWindow展示的view
                                    mButton = new Button(getApplicationContext());
                                    mButton.setBackgroundColor(Color.WHITE);
//        button.setBackgroundResource(R.mipmap.popup);
                                    mButton.setTextColor(Color.BLACK);
                                    mButton.setText("牲畜类型：骆驼\n" + "牲畜品种：骆驼\n" + "   设备编号：" + deviceNo1 + "   \n"
                                            + "   上传时间：" + lives.getUpdateTime() + "   \n" + "   牲畜位置：" + lives.getAddress() + "   ");

//定义用于显示该InfoWindow的坐标点

                                    mPt = new LatLng(Double.parseDouble(lantitudeBaidu), Double.parseDouble(longtitudeBaidu));

//创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
                                    mMInfoWindow = new InfoWindow(mButton, mPt, -47);

//显示InfoWindow
                                    map.showInfoWindow(mMInfoWindow);


                                    mButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            map.clear();

                                        }
                                    });


                                    map.setOnMyLocationClickListener(new BaiduMap.OnMyLocationClickListener() {
                                        @Override
                                        public boolean onMyLocationClick() {

                                            map.showInfoWindow(mMInfoWindow);
                                            return false;

                                        }
                                    });


                                }
                            });


                }
            });


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

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

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
