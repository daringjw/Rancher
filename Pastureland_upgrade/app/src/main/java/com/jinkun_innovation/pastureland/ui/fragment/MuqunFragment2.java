package com.jinkun_innovation.pastureland.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.bean.LoginSuccess;
import com.jinkun_innovation.pastureland.bean.MuqunSum;
import com.jinkun_innovation.pastureland.common.Constants;
import com.jinkun_innovation.pastureland.ui.LoginActivity1;
import com.jinkun_innovation.pastureland.ui.YangListActivity;
import com.jinkun_innovation.pastureland.ui.activity.CamelListActivity;
import com.jinkun_innovation.pastureland.ui.activity.DonkeyListActivity;
import com.jinkun_innovation.pastureland.ui.activity.MaListActivity;
import com.jinkun_innovation.pastureland.ui.activity.MuqunLocActivity;
import com.jinkun_innovation.pastureland.ui.activity.NiuListActivity;
import com.jinkun_innovation.pastureland.ui.activity.PigListActivity;
import com.jinkun_innovation.pastureland.utilcode.AppManager;
import com.jinkun_innovation.pastureland.utilcode.SpUtil;
import com.jinkun_innovation.pastureland.utils.PrefUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by Guan on 2018/3/15.
 */

public class MuqunFragment2 extends Fragment {


    private static final String TAG1 = MuqunFragment2.class.getSimpleName();

    @BindView(R.id.ivYang)
    ImageView mIvYang;
    @BindView(R.id.ivNiu)
    ImageView mIvNiu;
    @BindView(R.id.ivMa)
    ImageView mIvMa;
    @BindView(R.id.ivPig)
    ImageView mIvPig;
    @BindView(R.id.ivCamel)
    ImageView ivCamel;

    @BindView(R.id.tvYangNo)
    TextView tvYangNo;

    Unbinder unbinder;

    String mLogin_success;
    LoginSuccess mLoginSuccess;
    String mUsername;

    private TextView mTvNiuNo;
    private TextView mTvMaNo;
    private TextView mTvDeerNo, tvCamelNo;

    public static String registrationId;
    private TextView mTvDonkey;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        registrationId = JPushInterface.getRegistrationID(getActivity());
        Log.d(TAG1, "registrationId1=" + registrationId);

        View view = View.inflate(getActivity(), R.layout.fragment_muqun, null);
        unbinder = ButterKnife.bind(this, view);

        mTvNiuNo = view.findViewById(R.id.tvNiuNo);
        mTvMaNo = view.findViewById(R.id.tvMaNo);
        mTvDeerNo = view.findViewById(R.id.tvDeerNo);
        tvCamelNo = view.findViewById(R.id.tvCamelNo);

        mLogin_success = PrefUtils.getString(getActivity(), "login_success", null);
        Gson gson = new Gson();
        mLoginSuccess = gson.fromJson(mLogin_success, LoginSuccess.class);
        mUsername = PrefUtils.getString(getActivity(), "username", null);


        ImageView ivMuqunLoc = view.findViewById(R.id.ivMuqunLoc);
        ivMuqunLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MuqunLocActivity.class);
                startActivity(intent);

            }
        });


        initData();

        ImageView ivDonkey = view.findViewById(R.id.ivDonkey);
        ivDonkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), DonkeyListActivity.class);
                startActivity(intent);

            }
        });

        mTvDonkey = view.findViewById(R.id.tvDonkey);



        return view;


    }

    private void initData() {

        OkGo.<String>get(Constants.QUERYTYPEANDSUM)
                .tag(this)
                .params("token", mLoginSuccess.getToken())
                .params("username", mUsername)
                .params("ranchID", mLoginSuccess.getRanchID())
                .execute(new StringCallback() {


                    @Override
                    public void onSuccess(Response<String> response) {

                        String s = response.body().toString();
                        Log.d(TAG1, s);
                        if (s.contains("获取牲畜类型和数量成功")) {

                            Gson gson1 = new Gson();

                            MuqunSum muqunSum = gson1.fromJson(s, MuqunSum.class);
                            MuqunSum.TypeMapBean typeMap = muqunSum.getTypeMap();

                            String s1 = typeMap.get_$1() + "";
                            String s2 = typeMap.get_$2() + "";
                            String s3 = typeMap.get_$3() + "";
                            String s4 = typeMap.get_$4() + "";
                            String s7 = typeMap.get_$7() + "";
                            String s8 = typeMap.get_$8() + "";


                            try {
//羊
                                if (!s1.equals("0") && !TextUtils.isEmpty(s1)) {
                                    tvYangNo.setText(s1 + "只");
                                }

                                //牛
                                if (!s2.equals("0") && !TextUtils.isEmpty(s2)) {
                                    mTvNiuNo.setText(s2 + "头");

                                }

                                //马
                                if (!s3.equals("0") && !TextUtils.isEmpty(s3)) {
                                    mTvMaNo.setText(s3 + "匹");

                                }

                                //猪
                                if (!s4.equals("0") && !TextUtils.isEmpty(s4)) {
                                    mTvDeerNo.setText(s4 + "头");

                                }

                                //骆驼
                                if (!s7.equals("0") && !TextUtils.isEmpty(s7)) {
                                    tvCamelNo.setText(s7 + "头");

                                }

                                //驴子
                                if (!s8.equals("0") && !TextUtils.isEmpty(s8)) {
                                    mTvDonkey.setText(s8 + "头");

                                }



                            } catch (Exception e) {

                                //退出到登录界面
                                SpUtil.saveLoginState(false);
                                startActivity(new Intent(getActivity(), LoginActivity1.class));
                                //停止极光推送服务
                                JPushInterface.stopPush(getActivity());
                                AppManager.getAppManager().finishAllActivity();

                            }


                        } else if (s.contains("认证失败")) {

                            //退出到登录界面
                            SpUtil.saveLoginState(false);
                            startActivity(new Intent(getActivity(), LoginActivity1.class));
                            //停止极光推送服务
                            JPushInterface.stopPush(getActivity());
                            AppManager.getAppManager().finishAllActivity();


                        }


                    }


                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                        //退出到登录界面
                        SpUtil.saveLoginState(false);
                        startActivity(new Intent(getActivity(), LoginActivity1.class));
                        //停止极光推送服务
                        JPushInterface.stopPush(getActivity());
                        AppManager.getAppManager().finishAllActivity();

                    }


                });


    }


    private boolean isGetData = false;

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            //   这里可以做网络请求或者需要的数据刷新操作
            initData();

        } else {

            isGetData = false;

        }

        return super.onCreateAnimation(transit, enter, nextAnim);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isGetData) {
            //   这里可以做网络请求或者需要的数据刷新操作
            initData();

            isGetData = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isGetData = false;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ivYang, R.id.ivNiu, R.id.ivMa, R.id.ivPig, R.id.ivCamel})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ivYang:

                startActivity(new Intent(getActivity(), YangListActivity.class));

                break;

            case R.id.ivNiu:

                startActivity(new Intent(getActivity(), NiuListActivity.class));

                break;

            case R.id.ivMa:

                startActivity(new Intent(getActivity(), MaListActivity.class));

                break;

            case R.id.ivPig:

                startActivity(new Intent(getActivity(), PigListActivity.class));

                break;

            case R.id.ivCamel:

                startActivity(new Intent(getActivity(), CamelListActivity.class));

                break;


        }
    }

}
