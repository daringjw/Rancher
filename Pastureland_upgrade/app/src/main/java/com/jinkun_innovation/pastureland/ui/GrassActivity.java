package com.jinkun_innovation.pastureland.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.bean.GrassBean;
import com.jinkun_innovation.pastureland.manager.GrassManager;
import com.jinkun_innovation.pastureland.ui.dialog.AddGrassDialog;
import com.jinkun_innovation.pastureland.utilcode.util.TimeUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * Created by Guan on 2018/3/19.
 */

public class GrassActivity extends Activity {


    private static final String TAG1 = GrassActivity.class.getSimpleName();
    private List<GrassBean> mGrassBeanList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_grass);

        mGrassBeanList = GrassManager.getInstance().getGrassBeanList();


        ImageView ivAdd = (ImageView) findViewById(R.id.ivAdd);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddGrassDialog dialog = new AddGrassDialog(GrassActivity.this, new AddGrassDialog.PriorityListener() {
                    @Override
                    public void refreshPriorityUI(GrassBean grassBean) {

//                        ToastUtils.showShort(string);
                        Log.d(TAG1, "grass_type=" + grassBean.grass_type);
                        Log.d(TAG1, "grass_weight=" + grassBean.grass_weight);

                        grassBean.grass_time = TimeUtils.getNowString();

                        mGrassBeanList.add(0, grassBean);

                        //数据存储
                        GrassManager.getInstance().setGrassBeanList(mGrassBeanList);

                        mAdapter.notifyDataSetChanged();


                    }
                });

                dialog.show();


            }
        });


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
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {


                refreshLayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败

            }
        });

        /*FunGameHitBlockHeader funGameHitBlockHeader = new FunGameHitBlockHeader(this);
        //设置 Header 为 Material样式
        refreshLayout.setRefreshHeader(funGameHitBlockHeader);
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(this)
                .setSpinnerStyle(SpinnerStyle.Scale));*/


        mRecyclerView = findViewById(R.id.my_recycler_view);
//创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
//如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
//创建并设置Adapter
        mAdapter = new MyAdapter(mGrassBeanList);
        mRecyclerView.setAdapter(mAdapter);

    }


    private String[] getDummyDatas() {

        String[] arr = {"北京", "上海", "广州", "深圳"};

        return arr;

    }

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter mAdapter;

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String[] data);


    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;


    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {

        public List<GrassBean> datas = null;

        public MyAdapter(List<GrassBean> datas) {
            this.datas = datas;
        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grass, viewGroup, false);
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
//            viewHolder.itemView.setTag(datas[position]);

            String grass_type = datas.get(position).grass_type;
            if (grass_type.equals("干草")) {
                viewHolder.ivIcon.setImageResource(R.mipmap.gancao);
                viewHolder.tvWhatGancao.setText("晒干的牧场青草");
            } else {
                viewHolder.ivIcon.setImageResource(R.mipmap.jiegan);
                viewHolder.tvWhatGancao.setText("玉米秸秆粉碎料");
            }

            viewHolder.tvGancao.setText(grass_type);
            viewHolder.tvTime.setText(datas.get(position).grass_time);
            viewHolder.tvWeight.setText(datas.get(position).grass_weight);


        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                //注意这里使用getTag方法获取数据
//                mOnItemClickListener.onItemClick(v,(String[])v.getTag());

            }
        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return datas.size();
        }


        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView ivIcon;
            public TextView tvGancao, tvWhatGancao, tvTime, tvWeight;

            public ViewHolder(View view) {
                super(view);

//                mTextView = view.findViewById(R.id.tvClaim);
                ivIcon = view.findViewById(R.id.ivIcon);
                tvGancao = view.findViewById(R.id.tvGancao);
                tvWhatGancao = view.findViewById(R.id.tvWhatGancao);
                tvTime = view.findViewById(R.id.tvTime);
                tvWeight = view.findViewById(R.id.tvWeight);


            }

        }

    }


}
