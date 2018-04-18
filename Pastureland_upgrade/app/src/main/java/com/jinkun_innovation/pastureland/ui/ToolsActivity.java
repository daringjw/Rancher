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
import com.jinkun_innovation.pastureland.bean.ToolBean;
import com.jinkun_innovation.pastureland.manager.ToolsManager;
import com.jinkun_innovation.pastureland.ui.dialog.AddToolDialog;
import com.jinkun_innovation.pastureland.utilcode.util.TimeUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * Created by Guan on 2018/3/19.
 */

public class ToolsActivity extends Activity {

    private static final String TAG1 = ToolsActivity.class.getSimpleName();
    private List<ToolBean> mToolBeanList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_tools);

        mToolBeanList = ToolsManager.getInstance().getToolBeanList();


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

        if (mToolBeanList != null) {
            mAdapter = new MyAdapter(mToolBeanList);
            mRecyclerView.setAdapter(mAdapter);
        }


        ImageView ivAdd = (ImageView) findViewById(R.id.ivAdd);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AddToolDialog dialog = new AddToolDialog(ToolsActivity.this, new AddToolDialog.PriorityListener() {
                    @Override
                    public void refreshPriorityUI(ToolBean toolBean) {

//                        ToastUtils.showShort(string);
                        Log.d(TAG1, "tool_type=" + toolBean.tool_type);
                        Log.d(TAG1, "tool_sum=" + toolBean.tool_sum);

                        toolBean.time = TimeUtils.getNowString();

                        mToolBeanList.add(0, toolBean);

                        //数据存储
                        ToolsManager.getInstance().setToolBeanList(mToolBeanList);

                        mAdapter.notifyDataSetChanged();


                    }
                });

                dialog.show();


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
        void onItemClick(View view, String[] data);


    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;


    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {

        public List<ToolBean> datas = null;

        public MyAdapter(List<ToolBean> datas) {
            this.datas = datas;
        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tool, viewGroup, false);
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

            String tool_type = datas.get(position).tool_type;
            if (tool_type.equals("割草机")) {
                viewHolder.ivIcon.setImageResource(R.mipmap.gecaoji2);
            } else {
                viewHolder.ivIcon.setImageResource(R.mipmap.bozhongji1);
            }

            viewHolder.tvToolType.setText(datas.get(position).tool_type);
            viewHolder.tvTime.setText("日期：" + datas.get(position).time);
            viewHolder.tvNum.setText(datas.get(position).tool_sum);


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

            public TextView tvToolType, tvTime, tvNum;
            public ImageView ivIcon;

            public ViewHolder(View view) {
                super(view);
//                mTextView = view.findViewById(R.id.tvClaim);

                ivIcon = view.findViewById(R.id.ivIcon);


                tvToolType = (TextView) view.findViewById(R.id.tvToolType);
                tvTime = (TextView) view.findViewById(R.id.tvTime);
                tvNum = (TextView) view.findViewById(R.id.tvNum);


            }

        }

    }


}
