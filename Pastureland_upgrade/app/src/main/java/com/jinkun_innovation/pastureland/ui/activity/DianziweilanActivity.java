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

import com.google.gson.Gson;
import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.bean.Dianziweilan;
import com.jinkun_innovation.pastureland.bean.LoginSuccess;
import com.jinkun_innovation.pastureland.common.Constants;
import com.jinkun_innovation.pastureland.utils.PrefUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * Created by Guan on 2018/5/8.
 */

public class DianziweilanActivity extends Activity {

    String mLogin_success;
    LoginSuccess mLoginSuccess;
    String mUsername;

    RecyclerView mRecyclerView;
    MyAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dianziweilan);

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

        OkGo.<String>get(Constants.DEVICEMSG)
                .tag(this)
                .params("token", mLoginSuccess.getToken())
                .params("username", mUsername)
                .params("ranchID", mLoginSuccess.getRanchID())
                .params("current", 1)
                .params("pagesize", 299)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        String result = response.body().toString();
                        Gson gson1 = new Gson();
                        Dianziweilan dianziweilan = gson1.fromJson(result, Dianziweilan.class);
                        List<Dianziweilan.DeviceAlarmMsgListBean> deviceAlarmMsgList = dianziweilan.getDeviceAlarmMsgList();

                        mRecyclerView = (RecyclerView) findViewById(R.id.rvList);
//创建默认的线性LayoutManager
                        mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        mRecyclerView.setLayoutManager(mLayoutManager);
//如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
                        mRecyclerView.setHasFixedSize(true);
//创建并设置Adapter
                        mAdapter = new MyAdapter(deviceAlarmMsgList);
                        mRecyclerView.setAdapter(mAdapter);


                    }
                });


    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        public List<Dianziweilan.DeviceAlarmMsgListBean> datas = null;

        public MyAdapter(List<Dianziweilan.DeviceAlarmMsgListBean> datas) {
            this.datas = datas;
        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dianziweilan, viewGroup, false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }

        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {

            viewHolder.tvMsgTitle.setText(datas.get(position).getMsgTitle());
            viewHolder.tvAddress.setText("位置：" + datas.get(position).getAddress());
            viewHolder.tvTime.setText("时间：" + datas.get(position).getReportTime());


        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return datas.size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvMsgTitle, tvAddress, tvTime;


            public ViewHolder(View view) {
                super(view);

                tvMsgTitle = view.findViewById(R.id.tvMsgTitle);
                tvAddress = view.findViewById(R.id.tvAddress);
                tvTime = view.findViewById(R.id.tvTime);

            }


        }
    }


}
