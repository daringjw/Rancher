package com.jinkun_innovation.pastureland.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.bean.LoginSuccess;
import com.jinkun_innovation.pastureland.bean.QueryByYang;
import com.jinkun_innovation.pastureland.common.Constants;
import com.jinkun_innovation.pastureland.ui.YangDetailActivity;
import com.jinkun_innovation.pastureland.utilcode.util.ToastUtils;
import com.jinkun_innovation.pastureland.utils.PrefUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * Created by Guan on 2018/3/16.
 */

public class MaListActivity extends AppCompatActivity {

    private static final String TAG1 = MaListActivity.class.getSimpleName();

    private List<QueryByYang.LivestockVarietyListBean> mLivestockVarietyList;

    int index = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ma_list);

        ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


        RefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {


                //通过牲畜类型查询所有牲畜
                OkGo.<String>get(Constants.QUERYLIVESTOCKVARIETYLIST)
                        .tag(this)
                        .params("token", mLoginSuccess.getToken())
                        .params("username", mUsername)
                        .params("ranchID", mLoginSuccess.getRanchID())
                        .params("livestockType", 3)
                        .params("current", 0)
                        .params("pagesize", 10)
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


                                    mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {

                                            Intent intent = new Intent(getApplicationContext(), YangDetailActivity.class);
                                            intent.putExtra("getVariety", mLivestockVarietyList.get(position).getVariety());

                                            intent.putExtra("getImgUrl", mLivestockVarietyList.get(position).getImgUrl());
                                            intent.putExtra("getDeviceNo", mLivestockVarietyList.get(position).getDeviceNo());
                                            intent.putExtra("getWeight", mLivestockVarietyList.get(position).getWeight());
                                            intent.putExtra("getBindStatus", mLivestockVarietyList.get(position).getBindStatus());
                                            intent.putExtra("getIsClaimed", mLivestockVarietyList.get(position).getIsClaimed());
                                            intent.putExtra("getUpdateTime", mLivestockVarietyList.get(position).getUpdateTime());
                                            startActivity(intent);

                                        }
                                    });

                                } else {


                                }


                            }
                        });


                refreshlayout.finishRefresh(2000);//传入false表示刷新失败

            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {


                //通过牲畜类型查询所有牲畜
                OkGo.<String>get(Constants.QUERYLIVESTOCKVARIETYLIST)
                        .tag(this)
                        .params("token", mLoginSuccess.getToken())
                        .params("username", mUsername)
                        .params("ranchID", mLoginSuccess.getRanchID())
                        .params("livestockType", 3)
                        .params("current", index)
                        .params("pagesize", 10)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {

                                index++;
                                String s = response.body().toString();
                                Log.d(TAG1, s);

                                if (s.contains("imgUrl")) {
                                    //有数据
                                    Gson gson1 = new Gson();
                                    QueryByYang queryByYang = gson1.fromJson(s, QueryByYang.class);
                                    List<QueryByYang.LivestockVarietyListBean> mylist =
                                            queryByYang.getLivestockVarietyList();


                                    if (mylist.size() == 0) {

                                        ToastUtils.showShort("没有更多数据了");

                                    } else {
                                        for (int i = 0; i < mylist.size(); i++) {
                                            mLivestockVarietyList.add(mylist.get(i));
                                        }
                                        MoveToPosition(mLayoutManager, 10 * (index - 1));


                                    }


                                    //创建并设置Adapter
                                    mAdapter = new MyAdapter(mLivestockVarietyList);
                                    mRecyclerView.setAdapter(mAdapter);

                                    mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {

                                            Intent intent = new Intent(getApplicationContext(), YangDetailActivity.class);
                                            intent.putExtra("getVariety", mLivestockVarietyList.get(position).getVariety());

                                            intent.putExtra("getImgUrl", mLivestockVarietyList.get(position).getImgUrl());
                                            intent.putExtra("getDeviceNo", mLivestockVarietyList.get(position).getDeviceNo());
                                            intent.putExtra("getWeight", mLivestockVarietyList.get(position).getWeight());
                                            intent.putExtra("getBindStatus", mLivestockVarietyList.get(position).getBindStatus());
                                            intent.putExtra("getIsClaimed", mLivestockVarietyList.get(position).getIsClaimed());
                                            intent.putExtra("getUpdateTime", mLivestockVarietyList.get(position).getUpdateTime());
                                            startActivity(intent);

                                        }
                                    });


                                } else {

                                    ToastUtils.showShort("没有更多数据了");


                                }


                            }
                        });

                refreshLayout.finishLoadMore();//传入false表示加载失败

            }
        });


        mRecyclerView = findViewById(R.id.my_recycler_view);
//创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
//如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);

        initData();


    }

    String mLogin_success;
    LoginSuccess mLoginSuccess;
    String mUsername;

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager 设置RecyclerView对应的manager
     * @param n       要跳转的位置
     */
    public static void MoveToPosition(LinearLayoutManager manager, int n) {
        manager.scrollToPositionWithOffset(n, 0);
        manager.setStackFromEnd(true);
    }


    private void initData() {

        mLogin_success = PrefUtils.getString(this, "login_success", null);
        final Gson gson = new Gson();
        mLoginSuccess = gson.fromJson(mLogin_success, LoginSuccess.class);
        mUsername = PrefUtils.getString(this, "username", null);


        //通过牲畜类型查询所有牲畜
        OkGo.<String>get(Constants.QUERYLIVESTOCKVARIETYLIST)
                .tag(this)
                .params("token", mLoginSuccess.getToken())
                .params("username", mUsername)
                .params("ranchID", mLoginSuccess.getRanchID())
                .params("livestockType", 3)
                .params("current", 0)
                .params("pagesize", 10)
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

                            mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {

                                    Intent intent = new Intent(getApplicationContext(), YangDetailActivity.class);
                                    intent.putExtra("getVariety", mLivestockVarietyList.get(position).getVariety());
                                    intent.putExtra("getImgUrl", mLivestockVarietyList.get(position).getImgUrl());
                                    intent.putExtra("getDeviceNo", mLivestockVarietyList.get(position).getDeviceNo());
                                    intent.putExtra("getWeight", mLivestockVarietyList.get(position).getWeight());
                                    intent.putExtra("getBindStatus", mLivestockVarietyList.get(position).getBindStatus());
                                    intent.putExtra("getIsClaimed", mLivestockVarietyList.get(position).getIsClaimed());
                                    intent.putExtra("getUpdateTime", mLivestockVarietyList.get(position).getUpdateTime());
                                    startActivity(intent);

                                }
                            });

                        } else {


                        }


                    }
                });


    }

    private String[] getDummyDatas() {

        String[] arr = {"北京", "上海", "广州", "深圳"};

        return arr;

    }

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter mAdapter;

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);


    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;


    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {

        private OnRecyclerViewItemClickListener mOnItemClickListener = null;


        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            this.mOnItemClickListener = listener;

        }

        public List<QueryByYang.LivestockVarietyListBean> datas = null;

        public MyAdapter(List<QueryByYang.LivestockVarietyListBean> datas) {

            this.datas = datas;
        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(
                    viewGroup.getContext()).inflate(R.layout.item_yang_list,
                    viewGroup, false);

            ViewHolder vh = new ViewHolder(view);

            //将创建的View注册点击事件
            view.setOnClickListener(this);

            return vh;
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {

//            viewHolder.mTextView.setText(datas[position]);
            //将数据保存在itemView的Tag中，以便点击时进行获取
            viewHolder.itemView.setTag(position);

            String imgUrl = datas.get(position).getImgUrl();
            imgUrl = Constants.BASE_URL + imgUrl;
            Uri uri = Uri.parse(imgUrl);
            viewHolder.dvYang.setImageURI(uri);

            String variety = datas.get(position).getVariety();

            Log.d(TAG1, "variety = " + variety);
            if (variety.equals("301")) {
                viewHolder.tvYangName.setText("品种：蒙古马");
            } else {
                viewHolder.tvYangName.setText("品种：其他");
            }

            viewHolder.tvDeviceNo.setText("设备号：" + datas.get(position).getDeviceNo());
            viewHolder.tvPublishTime.setText("发布时间：" + datas.get(position).getUpdateTime());
            viewHolder.tvLocation.setText("位置：" + datas.get(position).address);


        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                //注意这里使用getTag方法获取数据
                mOnItemClickListener.onItemClick(v, (int) v.getTag());

            }
        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return datas.size();
        }


        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public SimpleDraweeView dvYang;
            public TextView tvYangName, tvDeviceNo, tvPublishTime, tvLocation;


            public ViewHolder(View view) {
                super(view);
//                mTextView = view.findViewById(R.id.tvClaim);
                dvYang = view.findViewById(R.id.dvYang);
                tvYangName = view.findViewById(R.id.tvYangName);
                tvDeviceNo = view.findViewById(R.id.tvDeviceNo);
                tvPublishTime = view.findViewById(R.id.tvPublishTime);
                tvLocation = view.findViewById(R.id.tvLocation);


            }

        }

    }


}
