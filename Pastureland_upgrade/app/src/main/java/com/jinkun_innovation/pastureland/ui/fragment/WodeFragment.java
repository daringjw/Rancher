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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.ui.DeviceMsgActivity;
import com.jinkun_innovation.pastureland.ui.GeRenXinxiActivity;
import com.jinkun_innovation.pastureland.ui.HomeActivity;
import com.jinkun_innovation.pastureland.ui.view.CircleImageView;
import com.jinkun_innovation.pastureland.utilcode.util.AppUtils;
import com.jinkun_innovation.pastureland.utilcode.util.ImageUtils;
import com.jinkun_innovation.pastureland.utils.PrefUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Response;
import com.tencent.bugly.beta.Beta;

import java.io.File;

/**
 * Created by Guan on 2018/3/15.
 */

public class WodeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = View.inflate(getActivity(), R.layout.fragment_wode, null);

        TextView tvMyPhone = view.findViewById(R.id.tvMyPhone);

        String username = PrefUtils.getString(getActivity(), "username", null);
        if (!TextUtils.isEmpty(username)) {
            tvMyPhone.setText(username);
        }

        RelativeLayout rlGeRenXinxi = view.findViewById(R.id.rlGeRenXinxi);
        rlGeRenXinxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), GeRenXinxiActivity.class));

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





        final CircleImageView ivIcon = view.findViewById(R.id.ivIcon);
        String touxiang = PrefUtils.getString(getActivity(), "touxiang", null);
        if (!TextUtils.isEmpty(touxiang)) {
            OkGo.<File>get(touxiang)
                    .tag(this)
                    .execute(new FileCallback() {
                        @Override
                        public void onSuccess(Response<File> response) {

                            File file = response.body().getAbsoluteFile();
                            Bitmap bitmap = ImageUtils.getBitmap(file);
                            ivIcon.setImageBitmap(bitmap);

                        }
                    });
        }


        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }


}
