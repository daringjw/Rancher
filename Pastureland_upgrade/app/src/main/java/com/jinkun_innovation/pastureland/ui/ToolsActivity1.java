package com.jinkun_innovation.pastureland.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.bean.GetToolList;
import com.jinkun_innovation.pastureland.bean.LoginSuccess;
import com.jinkun_innovation.pastureland.bean.ToolBean;
import com.jinkun_innovation.pastureland.common.Constants;
import com.jinkun_innovation.pastureland.ui.dialog.AddToolDialog1;
import com.jinkun_innovation.pastureland.utilcode.util.TimeUtils;
import com.jinkun_innovation.pastureland.utilcode.util.ToastUtils;
import com.jinkun_innovation.pastureland.utils.PrefUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.Collections;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Guan on 2018/5/11.
 */

public class ToolsActivity1 extends Activity {

    private static final String TAG1 = ToolsActivity1.class.getSimpleName();

    private AddToolDialog1 mAddToolDialog1;

    String mLogin_success;
    LoginSuccess mLoginSuccess;
    String mUsername;

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    MyAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tools1);

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


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
//创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
//如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);


        ImageView ivAdd = (ImageView) findViewById(R.id.ivAdd);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mAddToolDialog1 = new AddToolDialog1(ToolsActivity1.this,
                        new AddToolDialog1.PriorityListener() {
                            @Override
                            public void refreshPriorityUI(ToolBean toolBean) {

                                Log.d(TAG1, "tool_type=" + toolBean.tool_type);
                                Log.d(TAG1, "tool_sum=" + toolBean.tool_sum1);
                                Log.d(TAG1, "tool_fun=" + toolBean.tool_fun);

                                toolBean.time = TimeUtils.getNowString();

                                OkGo.<String>get(Constants.saveTool)
                                        .tag(this)
                                        .params("token", mLoginSuccess.getToken())
                                        .params("username", mUsername)
                                        .params("ranchID", mLoginSuccess.getRanchID())
                                        .params("deviceName", toolBean.tool_type)
                                        .params("deviceFunction", toolBean.tool_fun)
                                        .params("deviceCount", toolBean.tool_sum1)
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(Response<String> response) {

                                                String result = response.body().toString();
                                                if (result.contains("success")) {

                                                    ToastUtils.showShort("添加成功");
                                                    OkGo.<String>get(Constants.getToolList)
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
                                                                    GetToolList getToolList = gson1.fromJson(result, GetToolList.class);
                                                                    String msg = getToolList.getMsg();
                                                                    if (msg.contains("设备工具获取成功")) {


                                                                        List<GetToolList.ToolListBean> toolList = getToolList.getToolList();
                                                                        Collections.reverse(toolList);

                                                                        mAdapter = new MyAdapter(toolList);
                                                                        mRecyclerView.setAdapter(mAdapter);


                                                                    }


                                                                }
                                                            });


                                                } else {

                                                    ToastUtils.showShort("添加失败");

                                                }

                                            }
                                        });


                            }
                        });

                mAddToolDialog1.show();


            }
        });


        OkGo.<String>get(Constants.getToolList)
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
                        GetToolList getToolList = gson1.fromJson(result, GetToolList.class);
                        String msg = getToolList.getMsg();
                        if (msg.contains("设备工具获取成功")) {


                            List<GetToolList.ToolListBean> toolList = getToolList.getToolList();
                            Collections.reverse(toolList);
                            mAdapter = new MyAdapter(toolList);
                            mRecyclerView.setAdapter(mAdapter);


                        }


                    }
                });


    }

    List<GetToolList.ToolListBean> toolList;


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        public List<GetToolList.ToolListBean> datas = null;

        public MyAdapter(List<GetToolList.ToolListBean> datas) {
            this.datas = datas;
        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_get_tool,
                            viewGroup, false);

            ViewHolder vh = new ViewHolder(view);
            return vh;

        }

        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            String deviceName = datas.get(position).getDeviceName();
            if (deviceName.contains("割草机")) {
                viewHolder.ivIcon.setImageResource(R.mipmap.gecaoji2);
            } else if (deviceName.contains("播种机")) {
                viewHolder.ivIcon.setImageResource(R.mipmap.bozhongji1);
            }

            viewHolder.tvToolType.setText(deviceName);
            viewHolder.tvFunc.setText("作用：" + datas.get(position).getDeviceFunction());
            viewHolder.tvTime.setText("时间：" + datas.get(position).getCreateTime());
            viewHolder.tvNum.setText(datas.get(position).getDeviceCount() + "台");

            //删除item
            String id = datas.get(position).getId();
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    new SweetAlertDialog(ToolsActivity1.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("确定要删除些条目吗？")
                            .setContentText("删除后可能要手动进行录入")
                            .setConfirmText("确定")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();

                                    OkGo.<String>get(Constants.delTool)
                                            .tag(this)
                                            .params("token", mLoginSuccess.getToken())
                                            .params("username", mUsername)
                                            .params("id", datas.get(position).getId())
                                            .execute(new StringCallback() {
                                                @Override
                                                public void onSuccess(Response<String> response) {

                                                    String result = response.body().toString();
                                                    if (result.contains("success")) {
                                                        ToastUtils.showShort("删除成功");

                                                        //刷新数据
                                                        OkGo.<String>get(Constants.getToolList)
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
                                                                        GetToolList getToolList = gson1.fromJson(result, GetToolList.class);
                                                                        String msg = getToolList.getMsg();
                                                                        if (msg.contains("设备工具获取成功")) {


                                                                            List<GetToolList.ToolListBean> toolList = getToolList.getToolList();
                                                                            Collections.reverse(toolList);
                                                                            mAdapter = new MyAdapter(toolList);
                                                                            mRecyclerView.setAdapter(mAdapter);


                                                                        }


                                                                    }
                                                                });


                                                    } else {
                                                        ToastUtils.showShort("删除失败");

                                                    }


                                                }
                                            });


                                }
                            })
                            .setCancelText("取消")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();


                                }
                            })
                            .show();


                    return false;


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

            public TextView tvToolType, tvFunc, tvTime, tvNum;
            ImageView ivIcon;

            public ViewHolder(View view) {
                super(view);

                ivIcon = view.findViewById(R.id.ivIcon);
                tvToolType = view.findViewById(R.id.tvToolType);
                tvFunc = view.findViewById(R.id.tvFunc);
                tvTime = view.findViewById(R.id.tvTime);
                tvNum = view.findViewById(R.id.tvNum);


            }
        }

    }


}
