package com.jinkun_innovation.pastureland.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.zxing.client.android.CaptureActivity2;
import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.bean.LoginSuccess;
import com.jinkun_innovation.pastureland.bean.RenLing;
import com.jinkun_innovation.pastureland.bean.SelectLivestock;
import com.jinkun_innovation.pastureland.common.Constants;
import com.jinkun_innovation.pastureland.ui.PublishClaimActivity;
import com.jinkun_innovation.pastureland.ui.RenlingDetailActivity;
import com.jinkun_innovation.pastureland.ui.activity.PayConfirmActivity;
import com.jinkun_innovation.pastureland.utilcode.constant.TimeConstants;
import com.jinkun_innovation.pastureland.utilcode.util.TimeUtils;
import com.jinkun_innovation.pastureland.utilcode.util.ToastUtils;
import com.jinkun_innovation.pastureland.utils.PrefUtils;
import com.jinkun_innovation.pastureland.utils.StrLengthUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by Guan on 2018/3/23.
 */

public class RenlingFragment1 extends Fragment {

    private static final String TAG1 = RenlingFragment1.class.getSimpleName();

    String mLogin_success;
    LoginSuccess mLoginSuccess;
    String mUsername;

    private static final int SCAN_REQUEST_CODE = 100;
    private int checkedItem = 0;
    private String scanMessage;

    int index = 2;

    private List<RenLing.LivestockListBean> mLivestockList;
    private String mDeviceNo;


    private void startScanActivity() {
        Intent intent = new Intent(getActivity(), CaptureActivity2.class);
        intent.putExtra(CaptureActivity2.USE_DEFUALT_ISBN_ACTIVITY, true);
        intent.putExtra("inputUnable", 0);
        intent.putExtra("lightUnable", 1);
        intent.putExtra("albumUnable", 1);
        intent.putExtra("cancleUnable", 1);
        startActivityForResult(intent, SCAN_REQUEST_CODE);

    }

    //  a为原字符串，b为要插入的字符串，t为插入位置
    public String Stringinsert(String a, String b, int t) {
        return a.substring(0, t) + b + a.substring(t, a.length());
    }

    String isbn;

    private String mState;

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case SCAN_REQUEST_CODE:

                    isbn = data.getStringExtra("CaptureIsbn");

                    if (!TextUtils.isEmpty(isbn)) {
                        if (StrLengthUtil.length(isbn) == 16) {

                            /*Intent intent = new Intent(getActivity(), PublishClaimActivity.class);
                            intent.putExtra("isbn", isbn);
                            startActivityForResult(intent, 1001);*/

                            OkGo.<String>get(Constants.SELECT_LIVE_STOCK)
                                    .tag(this)
                                    .params("token", mLoginSuccess.getToken())
                                    .params("username", mUsername)
                                    .params("deviceNO", isbn)
                                    .params("ranchID", mLoginSuccess.getRanchID())
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(Response<String> response) {

                                            String result = response.body().toString();
                                            Gson gson = new Gson();
                                            SelectLivestock selectLivestock = gson.fromJson(result, SelectLivestock.class);
                                            String msg = selectLivestock.getMsg();
                                            String msg1 = selectLivestock.msg1;

                                            boolean code = selectLivestock.isCode();
                                            if (code) {

                                                //已经发布过,是否重新发布认领
                                                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                                        .setTitleText("")
                                                        .setContentText("已经发布过,是否重新发布认领?")
                                                        .setCancelText("否")
                                                        .setConfirmText("是")
                                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                                                sweetAlertDialog.cancel();
                                                                Intent intent = new Intent(getActivity(), PublishClaimActivity.class);
                                                                intent.putExtra("isbn", isbn);
                                                                startActivityForResult(intent, 1001);

                                                            }
                                                        })
                                                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sDialog) {
                                                                sDialog.cancel();
                                                            }
                                                        })
                                                        .show();


                                            } else if (msg.equals("false") && msg1.contains("此设备已经被牧场主为")) {

                                                ToastUtils.showShort("此设备已经被别的牧场主所登记");
                                                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                                        .setTitleText("无法发布认领")
                                                        .setContentText("此设备已经被别的牧场主所登记")
                                                        .show();


                                            } else {

                                                //没有发布过

                                                Intent intent = new Intent(getActivity(), PublishClaimActivity.class);
                                                intent.putExtra("isbn", isbn);
                                                startActivityForResult(intent, 1001);


                                            }


                                        }
                                    });


                        } else if (StrLengthUtil.length(isbn) == 15) {

                            final String str = Stringinsert(isbn, "1", 7);
                            Log.d(TAG1, "15位isbn=" + str);
                            Log.d(TAG1, "新的长度" + StrLengthUtil.length(str));
                            /*Intent intent = new Intent(getActivity(), PublishClaimActivity.class);
                            intent.putExtra("isbn", str);
                            startActivityForResult(intent, 1001);*/

                            OkGo.<String>get(Constants.SELECT_LIVE_STOCK)
                                    .tag(this)
                                    .params("token", mLoginSuccess.getToken())
                                    .params("username", mUsername)
                                    .params("deviceNO", str)
                                    .params("ranchID", mLoginSuccess.getRanchID())
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(Response<String> response) {

                                            String result = response.body().toString();
                                            Gson gson = new Gson();
                                            SelectLivestock selectLivestock = gson.fromJson(result, SelectLivestock.class);
                                            String msg = selectLivestock.getMsg();
                                            boolean code = selectLivestock.isCode();
                                            if (code) {

                                                //已经发布过,是否重新发布认领
                                                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                                        .setTitleText("")
                                                        .setContentText("已经发布过,是否重新发布认领")
                                                        .setCancelText("否")
                                                        .setConfirmText("是")
                                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                                                sweetAlertDialog.cancel();

                                                                Intent intent = new Intent(getActivity(), PublishClaimActivity.class);
                                                                intent.putExtra("isbn", str);
                                                                startActivityForResult(intent, 1001);

                                                            }
                                                        })
                                                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sDialog) {
                                                                sDialog.cancel();
                                                            }
                                                        })
                                                        .show();


                                            } else {

                                                //没有发布过
                                                Intent intent = new Intent(getActivity(), PublishClaimActivity.class);
                                                intent.putExtra("isbn", str);
                                                startActivityForResult(intent, 1001);
                                            }


                                        }
                                    });


                        } else {

                            ToastUtils.showShort("设备号必须是16位数字");

                        }

                    }

                case 1001:


                    //刷新数据
                    OkGo.<String>post(Constants.LIVE_STOCK_CLAIM_LIST)
                            .tag(this)
                            .params("token", mLoginSuccess.getToken())
                            .params("username", mUsername)
                            .params("ranchID", mLoginSuccess.getRanchID())
                            .params("isClaimed", 0)
                            .params("current", 1)
                            .params("pagesize", 10)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {


                                    String s = response.body().toString();
//                                    Log.d(TAG1, s);
                                    if (s.contains("livestockId")) {

                                        index = 2;

                                        Gson gson1 = new Gson();
                                        RenLing renLing = gson1.fromJson(s, RenLing.class);
                                        mLivestockList = renLing.getLivestockList();

                                        //创建并设置Adapter
                                        mAdapter = new MyAdapter(mLivestockList);
                                        mRecyclerView.setAdapter(mAdapter);

                                        MoveToPosition(mLayoutManager, 0);


                                        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {

                                                RenLing.LivestockListBean livestockListBean = mLivestockList.get(position);
                                                Intent intent = new Intent(getActivity(), RenlingDetailActivity.class);
                                                intent.putExtra("getDeviceNo", livestockListBean.getDeviceNo());
                                                startActivity(intent);


                                            }
                                        });


                                    }


                                }
                            });


                    break;


                case 1002:
                    //支付成功，刷新界面
                    //刷新数据
                    OkGo.<String>post(Constants.LIVE_STOCK_CLAIM_LIST)
                            .tag(this)
                            .params("token", mLoginSuccess.getToken())
                            .params("username", mUsername)
                            .params("ranchID", mLoginSuccess.getRanchID())
//                .params("isClaimed",)
                            .params("current", 1)
                            .params("pagesize", 10)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {


                                    String s = response.body().toString();
//                                    Log.d(TAG1, s);
                                    if (s.contains("livestockId")) {

                                        index = 2;

                                        Gson gson1 = new Gson();
                                        RenLing renLing = gson1.fromJson(s, RenLing.class);
                                        mLivestockList = renLing.getLivestockList();

                                        //创建并设置Adapter
                                        mAdapter = new MyAdapter(mLivestockList);
                                        mRecyclerView.setAdapter(mAdapter);

                                        MoveToPosition(mLayoutManager, 0);


                                        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {

                                                RenLing.LivestockListBean livestockListBean = mLivestockList.get(position);
                                                Intent intent = new Intent(getActivity(), RenlingDetailActivity.class);
                                                intent.putExtra("getDeviceNo", livestockListBean.getDeviceNo());
                                                startActivity(intent);

                                            }
                                        });


                                    } else {


                                    }


                                }
                            });

                    break;

            }

        }
    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = View.inflate(getActivity(), R.layout.fragment_claim, null);

        ImageView ivAdd = view.findViewById(R.id.ivAdd);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //扫描
                checkedItem = 2;
                startScanActivity();


            }
        });


        RefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {


                if (mState.equals("全部")) {
                    OkGo.<String>post(Constants.LIVE_STOCK_CLAIM_LIST)
                            .tag(this)
                            .params("token", mLoginSuccess.getToken())
                            .params("username", mUsername)
                            .params("ranchID", mLoginSuccess.getRanchID())
//                .params("isClaimed",)
                            .params("current", 1)
                            .params("pagesize", 10)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {


                                    String s = response.body().toString();
//                                Log.d(TAG1, s);
                                    if (s.contains("livestockId")) {

                                        index = 2;
                                        Gson gson1 = new Gson();
                                        RenLing renLing = gson1.fromJson(s, RenLing.class);
                                        mLivestockList = renLing.getLivestockList();

                                        //创建并设置Adapter
                                        mAdapter = new MyAdapter(mLivestockList);
                                        mRecyclerView.setAdapter(mAdapter);


                                        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {

                                                Log.d(TAG1, position + "被点击了");
                                                RenLing.LivestockListBean livestockListBean = mLivestockList.get(position);

                                                Intent intent = new Intent(getActivity(), RenlingDetailActivity.class);
                                                intent.putExtra("getImgUrl", livestockListBean.getImgUrl());
                                                intent.putExtra("getLivestockName", livestockListBean.getLivestockName());
                                                intent.putExtra("getDeviceNo", livestockListBean.getDeviceNo());
                                                intent.putExtra("getCharacteristics", livestockListBean.getCharacteristics());
                                                intent.putExtra("getCellphone", livestockListBean.getCellphone());
                                                intent.putExtra("getCreateTime", livestockListBean.getCreateTime());
                                                intent.putExtra("getPrice", livestockListBean.getPrice());
                                                intent.putExtra("getIsClaimed", livestockListBean.getIsClaimed());
                                                intent.putExtra("getLifeTime", livestockListBean.getLifeTime());
                                                intent.putExtra("getBirthTime", livestockListBean.getBirthTime());
                                                intent.putExtra("getClaimTime", livestockListBean.getClaimTime());

                                                startActivity(intent);

                                            }
                                        });


                                    } else {


                                    }


                                }
                            });


                } else if (mState.equals("未认领")) {

                    OkGo.<String>post(Constants.LIVE_STOCK_CLAIM_LIST)
                            .tag(this)
                            .params("token", mLoginSuccess.getToken())
                            .params("username", mUsername)
                            .params("ranchID", mLoginSuccess.getRanchID())
                            .params("isClaimed", 0)
                            .params("current", 1)
                            .params("pagesize", 10)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {


                                    String s = response.body().toString();
//                                Log.d(TAG1, s);
                                    if (s.contains("livestockId")) {

                                        index = 2;
                                        Gson gson1 = new Gson();
                                        RenLing renLing = gson1.fromJson(s, RenLing.class);
                                        mLivestockList = renLing.getLivestockList();

                                        //创建并设置Adapter
                                        mAdapter = new MyAdapter(mLivestockList);
                                        mRecyclerView.setAdapter(mAdapter);


                                        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {

                                                Log.d(TAG1, position + "被点击了");
                                                RenLing.LivestockListBean livestockListBean = mLivestockList.get(position);

                                                Intent intent = new Intent(getActivity(), RenlingDetailActivity.class);
                                                intent.putExtra("getImgUrl", livestockListBean.getImgUrl());
                                                intent.putExtra("getLivestockName", livestockListBean.getLivestockName());
                                                intent.putExtra("getDeviceNo", livestockListBean.getDeviceNo());
                                                intent.putExtra("getCharacteristics", livestockListBean.getCharacteristics());
                                                intent.putExtra("getCellphone", livestockListBean.getCellphone());
                                                intent.putExtra("getCreateTime", livestockListBean.getCreateTime());
                                                intent.putExtra("getPrice", livestockListBean.getPrice());
                                                intent.putExtra("getIsClaimed", livestockListBean.getIsClaimed());
                                                intent.putExtra("getLifeTime", livestockListBean.getLifeTime());
                                                intent.putExtra("getBirthTime", livestockListBean.getBirthTime());
                                                intent.putExtra("getClaimTime", livestockListBean.getClaimTime());

                                                startActivity(intent);

                                            }
                                        });


                                    } else {


                                    }


                                }
                            });

                } else if (mState.equals("已认领")) {

                    OkGo.<String>post(Constants.LIVE_STOCK_CLAIM_LIST)
                            .tag(this)
                            .params("token", mLoginSuccess.getToken())
                            .params("username", mUsername)
                            .params("ranchID", mLoginSuccess.getRanchID())
                            .params("isClaimed", 1)
                            .params("current", 1)
                            .params("pagesize", 10)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {


                                    String s = response.body().toString();
//                                Log.d(TAG1, s);
                                    if (s.contains("livestockId")) {

                                        index = 2;
                                        Gson gson1 = new Gson();
                                        RenLing renLing = gson1.fromJson(s, RenLing.class);
                                        mLivestockList = renLing.getLivestockList();

                                        //创建并设置Adapter
                                        mAdapter = new MyAdapter(mLivestockList);
                                        mRecyclerView.setAdapter(mAdapter);


                                        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {

                                                Log.d(TAG1, position + "被点击了");
                                                RenLing.LivestockListBean livestockListBean = mLivestockList.get(position);

                                                Intent intent = new Intent(getActivity(), RenlingDetailActivity.class);
                                                intent.putExtra("getImgUrl", livestockListBean.getImgUrl());
                                                intent.putExtra("getLivestockName", livestockListBean.getLivestockName());
                                                intent.putExtra("getDeviceNo", livestockListBean.getDeviceNo());
                                                intent.putExtra("getCharacteristics", livestockListBean.getCharacteristics());
                                                intent.putExtra("getCellphone", livestockListBean.getCellphone());
                                                intent.putExtra("getCreateTime", livestockListBean.getCreateTime());
                                                intent.putExtra("getPrice", livestockListBean.getPrice());
                                                intent.putExtra("getIsClaimed", livestockListBean.getIsClaimed());
                                                intent.putExtra("getLifeTime", livestockListBean.getLifeTime());
                                                intent.putExtra("getBirthTime", livestockListBean.getBirthTime());
                                                intent.putExtra("getClaimTime", livestockListBean.getClaimTime());

                                                startActivity(intent);

                                            }
                                        });


                                    } else {


                                    }


                                }
                            });
                }


                refreshlayout.finishRefresh(1000);//传入false表示刷新失败


            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {


                if (mState.equals("全部")) {

                    OkGo.<String>post(Constants.LIVE_STOCK_CLAIM_LIST)
                            .tag(this)
                            .params("token", mLoginSuccess.getToken())
                            .params("username", mUsername)
                            .params("ranchID", mLoginSuccess.getRanchID())
                            .params("current", index)
                            .params("pagesize", 10)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {

                                    String s = response.body().toString();
//                                Log.d(TAG1, s);
                                    if (s.contains("livestockId")) {

                                        index++;

                                        Gson gson1 = new Gson();
                                        RenLing renLing = gson1.fromJson(s, RenLing.class);
                                        List<RenLing.LivestockListBean> mylist =
                                                renLing.getLivestockList();

                                        if (mylist.size() != 0) {
                                            for (int i = 0; i < mylist.size(); i++) {
                                                mLivestockList.add(mylist.get(i));
                                            }
                                            MoveToPosition(mLayoutManager, 10 * (index - 1));


                                        }

                                        //创建并设置Adapter
                                        mAdapter = new MyAdapter(mLivestockList);
                                        mRecyclerView.setAdapter(mAdapter);


                                        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {

                                                RenLing.LivestockListBean livestockListBean = mLivestockList.get(position);
                                                Intent intent = new Intent(getActivity(), RenlingDetailActivity.class);
                                                intent.putExtra("getDeviceNo", livestockListBean.getDeviceNo());
                                                startActivity(intent);


                                            }
                                        });


                                    } else {

                                        ToastUtils.showShort("没有更多数据了");

                                    }


                                }
                            });

                } else if (mState.equals("未认领")) {

                    OkGo.<String>post(Constants.LIVE_STOCK_CLAIM_LIST)
                            .tag(this)
                            .params("token", mLoginSuccess.getToken())
                            .params("username", mUsername)
                            .params("ranchID", mLoginSuccess.getRanchID())
                            .params("isClaimed", 0)
                            .params("current", index)
                            .params("pagesize", 10)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {

                                    String s = response.body().toString();
//                                Log.d(TAG1, s);
                                    if (s.contains("livestockId")) {

                                        index++;

                                        Gson gson1 = new Gson();
                                        RenLing renLing = gson1.fromJson(s, RenLing.class);
                                        List<RenLing.LivestockListBean> mylist =
                                                renLing.getLivestockList();

                                        if (mylist.size() != 0) {
                                            for (int i = 0; i < mylist.size(); i++) {
                                                mLivestockList.add(mylist.get(i));
                                            }
                                            MoveToPosition(mLayoutManager, 10 * (index - 1));


                                        }

                                        //创建并设置Adapter
                                        mAdapter = new MyAdapter(mLivestockList);
                                        mRecyclerView.setAdapter(mAdapter);


                                        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {

                                                RenLing.LivestockListBean livestockListBean = mLivestockList.get(position);
                                                Intent intent = new Intent(getActivity(), RenlingDetailActivity.class);
                                                intent.putExtra("getDeviceNo", livestockListBean.getDeviceNo());
                                                startActivity(intent);

                                            }
                                        });

                                    } else {

                                        ToastUtils.showShort("没有更多数据了");

                                    }


                                }
                            });

                } else if (mState.equals("已认领")) {


                    OkGo.<String>post(Constants.LIVE_STOCK_CLAIM_LIST)
                            .tag(this)
                            .params("token", mLoginSuccess.getToken())
                            .params("username", mUsername)
                            .params("ranchID", mLoginSuccess.getRanchID())
                            .params("current", index)
                            .params("isClaimed", 1)
                            .params("pagesize", 10)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {

                                    String s = response.body().toString();
//                                Log.d(TAG1, s);
                                    if (s.contains("livestockId")) {

                                        index++;

                                        Gson gson1 = new Gson();
                                        RenLing renLing = gson1.fromJson(s, RenLing.class);
                                        List<RenLing.LivestockListBean> mylist =
                                                renLing.getLivestockList();

                                        if (mylist.size() != 0) {
                                            for (int i = 0; i < mylist.size(); i++) {
                                                mLivestockList.add(mylist.get(i));
                                            }
                                            MoveToPosition(mLayoutManager, 10 * (index - 1));

                                        }

                                        //创建并设置Adapter
                                        mAdapter = new MyAdapter(mLivestockList);
                                        mRecyclerView.setAdapter(mAdapter);


                                        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {

                                                RenLing.LivestockListBean livestockListBean = mLivestockList.get(position);
                                                Intent intent = new Intent(getActivity(), RenlingDetailActivity.class);
                                                intent.putExtra("getDeviceNo", livestockListBean.getDeviceNo());
                                                startActivity(intent);


                                            }
                                        });


                                    } else {

                                        ToastUtils.showShort("没有更多数据了");

                                    }

                                }
                            });

                }


                refreshLayout.finishLoadMore();


            }

        });


        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);

        mLogin_success = PrefUtils.getString(getActivity(), "login_success", null);
        Gson gson = new Gson();
        mLoginSuccess = gson.fromJson(mLogin_success, LoginSuccess.class);
        mUsername = PrefUtils.getString(getActivity(), "username", null);

        OkGo.<String>post(Constants.LIVE_STOCK_CLAIM_LIST)
                .tag(this)
                .params("token", mLoginSuccess.getToken())
                .params("username", mUsername)
                .params("ranchID", mLoginSuccess.getRanchID())
//                .params("isClaimed",)
                .params("current", 1)
                .params("pagesize", 10)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        String s = response.body().toString();
//                        Log.d(TAG1, s);
                        if (s.contains("livestockId")) {

                            Gson gson1 = new Gson();
                            RenLing renLing = gson1.fromJson(s, RenLing.class);
                            mLivestockList = renLing.getLivestockList();

                            //创建并设置Adapter
                            mAdapter = new MyAdapter(mLivestockList);
                            mRecyclerView.setAdapter(mAdapter);

                            mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {

                                    Log.d(TAG1, position + "被点击了");
                                    RenLing.LivestockListBean livestockListBean = mLivestockList.get(position);

                                    Intent intent = new Intent(getActivity(), RenlingDetailActivity.class);
                                    intent.putExtra("getDeviceNo", livestockListBean.getDeviceNo());
                                    intent.putExtra("getImgUrl", livestockListBean.getImgUrl());
                                    intent.putExtra("getLivestockName", livestockListBean.getLivestockName());
                                    intent.putExtra("getCharacteristics", livestockListBean.getCharacteristics());
                                    intent.putExtra("getCellphone", livestockListBean.getCellphone());
                                    intent.putExtra("getCreateTime", livestockListBean.getCreateTime());
                                    intent.putExtra("getPrice", livestockListBean.getPrice());
                                    intent.putExtra("getIsClaimed", livestockListBean.getIsClaimed());
                                    intent.putExtra("getLifeTime", livestockListBean.getLifeTime());
                                    intent.putExtra("getBirthTime", livestockListBean.getBirthTime());
                                    intent.putExtra("getClaimTime", livestockListBean.getClaimTime());
                                    startActivity(intent);

                                }
                            });

                        } else {

                        }

                    }
                });


        Spinner spinner = (Spinner) view.findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] claim = getResources().getStringArray(R.array.claim);

                mState = claim[pos];

                if (mState.equals("全部")) {

                    mLogin_success = PrefUtils.getString(getActivity(), "login_success", null);
                    Gson gson = new Gson();
                    mLoginSuccess = gson.fromJson(mLogin_success, LoginSuccess.class);
                    mUsername = PrefUtils.getString(getActivity(), "username", null);

                    OkGo.<String>post(Constants.LIVE_STOCK_CLAIM_LIST)
                            .tag(this)
                            .params("token", mLoginSuccess.getToken())
                            .params("username", mUsername)
                            .params("ranchID", mLoginSuccess.getRanchID())
//                .params("isClaimed",)
                            .params("current", 1)
                            .params("pagesize", 10)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {

                                    startIndex = 0;

                                    String s = response.body().toString();
                                    Log.d(TAG1, s);
                                    if (s.contains("error")) {

                                        return;

                                    } else if (s.contains("成功")) {

                                        Gson gson1 = new Gson();
                                        RenLing renLing = gson1.fromJson(s, RenLing.class);
                                        mLivestockList = renLing.getLivestockList();

                                        //创建并设置Adapter
                                        mAdapter = new MyAdapter(mLivestockList);
                                        mRecyclerView.setAdapter(mAdapter);

                                        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int data) {

                                                Log.d(TAG1, data + "被点击了");
                                                RenLing.LivestockListBean livestockListBean = mLivestockList.get(data);
                                                Intent intent = new Intent(getActivity(), RenlingDetailActivity.class);
                                                intent.putExtra("getDeviceNo", livestockListBean.getDeviceNo());
                                                startActivity(intent);

                                            }
                                        });


                                    } else {


                                        return;
                                    }


                                }
                            });

                } else if (claim[pos].equals("未认领")) {

                    mLogin_success = PrefUtils.getString(getActivity(), "login_success", null);
                    Gson gson = new Gson();
                    mLoginSuccess = gson.fromJson(mLogin_success, LoginSuccess.class);
                    mUsername = PrefUtils.getString(getActivity(), "username", null);

                    OkGo.<String>post(Constants.LIVE_STOCK_CLAIM_LIST)
                            .tag(this)
                            .params("token", mLoginSuccess.getToken())
                            .params("username", mUsername)
                            .params("ranchID", mLoginSuccess.getRanchID())
                            .params("isClaimed", 0)  //未认领
                            .params("current", 1)
                            .params("pagesize", 10)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {

                                    startIndex = 0;

                                    String s = response.body().toString();
                                    Log.d(TAG1, s);
                                    if (s.contains("未有发布牲畜") || s.contains("获取发布到认领输出信息异常")) {

                                        if (mLivestockList != null) {
                                            mLivestockList.clear();
                                            mAdapter.notifyDataSetChanged();
                                        }


                                    } else if (s.contains("成功")) {

                                        Gson gson1 = new Gson();
                                        RenLing renLing = gson1.fromJson(s, RenLing.class);
                                        mLivestockList = renLing.getLivestockList();

                                        //创建并设置Adapter
                                        mAdapter = new MyAdapter(mLivestockList);
                                        mRecyclerView.setAdapter(mAdapter);

                                        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int data) {

                                                Log.d(TAG1, data + "被点击了");
                                                RenLing.LivestockListBean livestockListBean = mLivestockList.get(data);
                                                Intent intent = new Intent(getActivity(), RenlingDetailActivity.class);
                                                intent.putExtra("getDeviceNo", livestockListBean.getDeviceNo());
                                                startActivity(intent);

                                            }
                                        });

                                    }


                                }
                            });

                } else if (claim[pos].equals("已认领")) {

                    mLogin_success = PrefUtils.getString(getActivity(), "login_success", null);
                    Gson gson = new Gson();
                    mLoginSuccess = gson.fromJson(mLogin_success, LoginSuccess.class);
                    mUsername = PrefUtils.getString(getActivity(), "username", null);

                    OkGo.<String>post(Constants.LIVE_STOCK_CLAIM_LIST)
                            .tag(this)
                            .params("token", mLoginSuccess.getToken())
                            .params("username", mUsername)
                            .params("ranchID", mLoginSuccess.getRanchID())
                            .params("isClaimed", 1)
                            .params("current", 1)
                            .params("pagesize", 10)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {

                                    startIndex = 0;

                                    String s = response.body().toString();
                                    Log.d(TAG1, s);
                                    if (s.contains("未有发布牲畜") || s.contains("获取发布到认领输出信息异常")) {

                                        if (mLivestockList != null) {
                                            mLivestockList.clear();
                                            mAdapter.notifyDataSetChanged();
                                        }

                                    } else if (s.contains("成功")) {

                                        Gson gson1 = new Gson();
                                        RenLing renLing = gson1.fromJson(s, RenLing.class);
                                        mLivestockList = renLing.getLivestockList();

                                        //创建并设置Adapter
                                        mAdapter = new MyAdapter(mLivestockList);
                                        mRecyclerView.setAdapter(mAdapter);


                                        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int data) {

                                                Log.d(TAG1, data + "被点击了");
                                                RenLing.LivestockListBean livestockListBean = mLivestockList.get(data);
                                                Intent intent = new Intent(getActivity(), RenlingDetailActivity.class);
                                                intent.putExtra("getDeviceNo", livestockListBean.getDeviceNo());
                                                startActivity(intent);


                                            }
                                        });


                                    }


                                }
                            });

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback


            }

        });


        return view;


    }

    int startIndex = -1;  // 起始页（从0开始）

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


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {


        private OnRecyclerViewItemClickListener mOnItemClickListener = null;


        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            this.mOnItemClickListener = listener;

        }

        public List<RenLing.LivestockListBean> datas = null;

        public MyAdapter(List<RenLing.LivestockListBean> datas) {

            this.datas = datas;
        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_claim, viewGroup, false);
            ViewHolder vh = new ViewHolder(view);

            //将创建的View注册点击事件
            view.setOnClickListener(this);

            return vh;
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

//            viewHolder.tvId.setText(datas.get(position).getDeviceNo());
            //将数据保存在itemView的Tag中，以便点击时进行获取
            viewHolder.itemView.setTag(position);


            String imgUrl = datas.get(position).getImgUrl();
            imgUrl = Constants.BASE_URL + imgUrl;
            Log.d(TAG1, imgUrl);
            Uri uri = Uri.parse(imgUrl);
            viewHolder.ivGhoat.setImageURI(uri);

            String livestockName = datas.get(position).getLivestockName();
            viewHolder.tvAnimalName.setText(livestockName);

            mDeviceNo = datas.get(position).getDeviceNo();
            viewHolder.tvId.setText("设备号:" + mDeviceNo);

            String createTime = datas.get(position).getCreateTime();
            String nowString = TimeUtils.getNowString();
//            Log.d(TAG1, "createTime=" + createTime);
//            Log.d(TAG1, "nowString=" + nowString);
            long timeSpanByNow = TimeUtils.getTimeSpanByNow(createTime, TimeConstants.DAY);
            Log.d(TAG1, timeSpanByNow + "天=timeSpanByNow");
            int age = (int) timeSpanByNow / 30;
            Log.d(TAG1, age + "个月");
            if (age == 1) {
                age = 2;
            } else if (age == 2) {
                age = 3;
            } else if (age == 0) {
                age = 1;
            }
            viewHolder.tvAnimalAge.setText("年龄：" + age + "个月");

            String name = datas.get(position).getName();
            if (TextUtils.isEmpty(name)) {
                viewHolder.tvMuchangName.setText("牧场：无");
            } else {
                viewHolder.tvMuchangName.setText("牧场：" + name);
            }

            String isClaimed = datas.get(position).getIsClaimed();
            if (isClaimed.contains("1")) {

                viewHolder.tvDel.setText("");

                String claimTime = datas.get(position).getClaimTime();
                claimTime = claimTime.substring(0, 10);
                String finishTime = datas.get(position).getFinishTime();
                finishTime = finishTime.substring(0, 10);
                //已认领
                viewHolder.tvClaimTime.setText("认领时间：" + claimTime + "至" + finishTime);

                String tradeStatus = datas.get(position).getTradeStatus();

                String orderNo = datas.get(position).getOrderNo();
                viewHolder.tvOrderNo.setText("支付订单号：" + orderNo);

                if (!TextUtils.isEmpty(tradeStatus)) {
                    if (tradeStatus.contains("0")) {

                        //已认领未支付
                        viewHolder.btnPayConfirm.setVisibility(View.INVISIBLE);
                        viewHolder.tvState.setText("已认领未支付");



                    } else if (tradeStatus.contains("1")) {

                        //已认领未支付
                        viewHolder.btnPayConfirm.setVisibility(View.VISIBLE);
                        viewHolder.tvState.setText("已认领已支付");

                        viewHolder.btnPayConfirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(getActivity(), PayConfirmActivity.class);
                                mDeviceNo = datas.get(position).getDeviceNo();
                                intent.putExtra("mDeviceNo", mDeviceNo);
                                startActivityForResult(intent, 1002);



                            }
                        });


                    } else if (tradeStatus.contains("3")) {

                        //已认领已支付
                        viewHolder.btnPayConfirm.setVisibility(View.GONE);
                        viewHolder.tvState.setText("交易成功");


                    }
                }


                viewHolder.tvClaimPeople.setText("认领人：" + datas.get(position).getNickname());
                viewHolder.tvPhone.setText("电话：" + datas.get(position).getCellphone());


            } else if (isClaimed.contains("0")) {

                //未认领
                viewHolder.tvClaimTime.setText("发布时间：" + datas.get(position).getBirthTime());

                viewHolder.tvState.setText("未认领");

                viewHolder.tvOrderNo.setText("");
                viewHolder.tvClaimPeople.setText("");
                viewHolder.tvPhone.setText("");

                viewHolder.btnPayConfirm.setVisibility(View.GONE);

                viewHolder.tvDel.setText("删除");

                viewHolder.tvDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("删除?")
                                .setContentText("删除此条目")
                                .setConfirmText("确定")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {

                                        sDialog.cancel();
                                        OkGo.<String>get(Constants.delLivestock)
                                                .tag(this)
                                                .params("token", mLoginSuccess.getToken())
                                                .params("username", mUsername)
                                                .params("deviceNo", datas.get(position).getDeviceNo())
                                                .params("ranchID", mLoginSuccess.getRanchID())
                                                .execute(new StringCallback() {
                                                    @Override
                                                    public void onSuccess(Response<String> response) {

                                                        String result = response.body().toString();
                                                        if (result.contains("删除成功")) {

                                                            ToastUtils.showShort("删除成功");
                                                            //刷新数据
                                                            OkGo.<String>post(Constants.LIVE_STOCK_CLAIM_LIST)
                                                                    .tag(this)
                                                                    .params("token", mLoginSuccess.getToken())
                                                                    .params("username", mUsername)
                                                                    .params("ranchID", mLoginSuccess.getRanchID())
                                                             //.params("isClaimed",0)
                                                                    .params("current", 1)
                                                                    .params("pagesize", 10)
                                                                    .execute(new StringCallback() {
                                                                        @Override
                                                                        public void onSuccess(Response<String> response) {


                                                                            String s = response.body().toString();

                                                                            if (s.contains("livestockId")) {

                                                                                index = 2;
                                                                                Gson gson1 = new Gson();
                                                                                RenLing renLing = gson1.fromJson(s, RenLing.class);
                                                                                mLivestockList = renLing.getLivestockList();

                                                                                //创建并设置Adapter
                                                                                mAdapter = new MyAdapter(mLivestockList);
                                                                                mRecyclerView.setAdapter(mAdapter);

                                                                                MoveToPosition(mLayoutManager, 0);


                                                                                mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                                                                    @Override
                                                                                    public void onItemClick(View view, int position) {

                                                                                        RenLing.LivestockListBean livestockListBean = mLivestockList.get(position);
                                                                                        Intent intent = new Intent(getActivity(), RenlingDetailActivity.class);
                                                                                        intent.putExtra("getDeviceNo", livestockListBean.getDeviceNo());
                                                                                        startActivity(intent);


                                                                                    }
                                                                                });


                                                                            }


                                                                        }
                                                                    });


                                                        } else if (result.contains("删除失败")) {


                                                            ToastUtils.showShort("删除失败");


                                                        } else if (result.contains("已被认领不可删除")) {

                                                            ToastUtils.showShort("已被认领不可删除");

                                                        } else {

                                                            ToastUtils.showShort("删除异常");

                                                        }

                                                    }
                                                });


                                    }
                                })
                                .setCancelText("取消")
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.cancel();


                                    }
                                })
                                .show();


                    }
                });

            }


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

            SimpleDraweeView ivGhoat;

            TextView tvAnimalName, tvId, tvAnimalAge, tvMuchangName,
                    tvClaimTime, tvClaimPeople, tvState,
                    tvPhone, tvOrderNo, tvDel;

            Button btnPayConfirm;


            public ViewHolder(View view) {
                super(view);
//                mTextView = view.findViewById(R.id.tvClaim);
                ivGhoat = view.findViewById(R.id.ivGhoat);
                tvAnimalName = view.findViewById(R.id.tvAnimalName);
                tvId = view.findViewById(R.id.tvId);
                tvAnimalAge = view.findViewById(R.id.tvAnimalAge);
                tvMuchangName = view.findViewById(R.id.tvMuchangName);
                tvClaimTime = view.findViewById(R.id.tvClaimTime);
                tvClaimPeople = view.findViewById(R.id.tvClaimPeople);
                tvState = view.findViewById(R.id.tvState);
                tvPhone = view.findViewById(R.id.tvPhone);

                tvOrderNo = view.findViewById(R.id.tvOrderNo);
                btnPayConfirm = view.findViewById(R.id.btnPayConfirm);

                tvDel = view.findViewById(R.id.tvDel);


            }

        }

    }


}
