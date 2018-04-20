package com.jinkun_innovation.pastureland.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.bean.AdminInfo;
import com.jinkun_innovation.pastureland.bean.LoginSuccess;
import com.jinkun_innovation.pastureland.common.Constants;
import com.jinkun_innovation.pastureland.ui.DeviceMsgActivity;
import com.jinkun_innovation.pastureland.ui.GeRenXinxiActivity;
import com.jinkun_innovation.pastureland.ui.HomeActivity;
import com.jinkun_innovation.pastureland.ui.view.CircleImageView;
import com.jinkun_innovation.pastureland.utilcode.util.ImageUtils;
import com.jinkun_innovation.pastureland.utilcode.util.ToastUtils;
import com.jinkun_innovation.pastureland.utils.PrefUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Guan on 2018/3/15.
 */


public class WodeFragment extends Fragment {

    String mLogin_success;
    LoginSuccess mLoginSuccess;
    String mUsername;
    private TextView mTvMyPhone;
    private CircleImageView mIvIcon;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case 101:

                    OkGo.<String>post(Constants.ADMINLIST)
                            .tag(this)
                            .params("token", mLoginSuccess.getToken())
                            .params("username", mUsername)
                            .params("ranchID", mLoginSuccess.getRanchID())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {

                                    String s = response.body().toString();

                                    if (s.contains("获取个人信息异常")) {

                                        ToastUtils.showShort("获取个人信息异常");

                                    } else if (s.contains("获取个人信息成功")) {
                                        Gson gson1 = new Gson();
                                        AdminInfo adminInfo = gson1.fromJson(s, AdminInfo.class);

                                        String username = adminInfo.getAdminInfo().getUsername();
                                        mTvMyPhone.setText(username);

                                        String mHeadImgUrl = adminInfo.getAdminInfo().headImgUrl;
                                        if (!TextUtils.isEmpty(mHeadImgUrl)) {
                                            mHeadImgUrl = Constants.BASE_URL + mHeadImgUrl;

                                            OkGo.<File>get(mHeadImgUrl)
                                                    .tag(this)
                                                    .execute(new FileCallback() {
                                                        @Override
                                                        public void onSuccess(Response<File> response) {

                                                            File file = response.body().getAbsoluteFile();
                                                            Bitmap bitmap = ImageUtils.getBitmap(file);
                                                            mIvIcon.setImageBitmap(bitmap);

                                                        }
                                                    });

                                        }


                                    }


                                }
                            });

                    break;

            }
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = View.inflate(getActivity(), R.layout.fragment_wode, null);

        mTvMyPhone = view.findViewById(R.id.tvMyPhone);


        RelativeLayout rlGeRenXinxi = view.findViewById(R.id.rlGeRenXinxi);
        rlGeRenXinxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), GeRenXinxiActivity.class);
                startActivityForResult(intent, 101);


            }
        });

        LinearLayout llPastureLand = view.findViewById(R.id.llPastureLand);
        llPastureLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeActivity activity = (HomeActivity) getActivity();
                activity.viewPager.setCurrentItem(0);

            }
        });

        LinearLayout llMuqun = view.findViewById(R.id.llMuqun);
        llMuqun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeActivity activity = (HomeActivity) getActivity();
                activity.viewPager.setCurrentItem(1);

            }
        });


        LinearLayout llClaim = view.findViewById(R.id.llClaim);
        llClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeActivity activity = (HomeActivity) getActivity();
                activity.viewPager.setCurrentItem(2);

            }
        });


        LinearLayout llDeviceMsg = view.findViewById(R.id.llDeviceMsg);
        llDeviceMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), DeviceMsgActivity.class));

            }
        });


        mIvIcon = view.findViewById(R.id.ivIcon);

        mLogin_success = PrefUtils.getString(getActivity(), "login_success", null);
        Gson gson = new Gson();
        mLoginSuccess = gson.fromJson(mLogin_success, LoginSuccess.class);
        mUsername = PrefUtils.getString(getActivity(), "username", null);

        OkGo.<String>post(Constants.ADMINLIST)
                .tag(this)
                .params("token", mLoginSuccess.getToken())
                .params("username", mUsername)
                .params("ranchID", mLoginSuccess.getRanchID())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        String s = response.body().toString();

                        if (s.contains("获取个人信息异常")) {

                            ToastUtils.showShort("获取个人信息异常");

                        } else if (s.contains("获取个人信息成功")) {
                            Gson gson1 = new Gson();
                            AdminInfo adminInfo = gson1.fromJson(s, AdminInfo.class);

                            String username = adminInfo.getAdminInfo().getUsername();
                            mTvMyPhone.setText(username);

                            String mHeadImgUrl = adminInfo.getAdminInfo().headImgUrl;
                            if (!TextUtils.isEmpty(mHeadImgUrl)) {
                                mHeadImgUrl = Constants.BASE_URL + mHeadImgUrl;

                                OkGo.<File>get(mHeadImgUrl)
                                        .tag(this)
                                        .execute(new FileCallback() {
                                            @Override
                                            public void onSuccess(Response<File> response) {

                                                File file = response.body().getAbsoluteFile();
                                                Bitmap bitmap = ImageUtils.getBitmap(file);
                                                mIvIcon.setImageBitmap(bitmap);

                                            }
                                        });

                            }


                        }


                    }
                });


        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }


}
