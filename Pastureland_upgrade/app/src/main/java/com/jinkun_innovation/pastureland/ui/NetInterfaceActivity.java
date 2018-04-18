package com.jinkun_innovation.pastureland.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.common.Constants;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Guan on 2018/3/14.
 */

public class NetInterfaceActivity extends AppCompatActivity {

    private static final String TAG = NetInterfaceActivity.class.getSimpleName();

    @BindView(R.id.btnVerifyCode)
    Button mBtnVerifyCode;
    @BindView(R.id.btnModifyCode)
    Button mBtnModifyCode;
    @BindView(R.id.btnSelectLivestock)
    Button mBtnSelectLivestock;
    @BindView(R.id.btnRelease)
    Button mBtnRelease;
    @BindView(R.id.btnIsClaimed)
    Button mBtnIsClaimed;
    @BindView(R.id.btnLivestockClaimList)
    Button mBtnLivestockClaimList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_netinterace);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.btnVerifyCode, R.id.btnModifyCode, R.id.btnSelectLivestock, R.id.btnRelease, R.id.btnIsClaimed, R.id.btnLivestockClaimList})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnVerifyCode:

                OkGo.<String>get(Constants.BASE_URL + Constants.VERIFY_CODE)
                        .tag(this)
                        .params("cellPhoneNum","1761089307")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {

                                Log.d(TAG,response.body().toString());

                                if (response.body().toString().contains("success")){
                                    Toast.makeText(getApplicationContext(),"发送成功",
                                            Toast.LENGTH_SHORT).show();

                                }

                            }
                        });

                break;


            case R.id.btnModifyCode:





                break;
            case R.id.btnSelectLivestock:

                break;
            case R.id.btnRelease:

                break;
            case R.id.btnIsClaimed:

                break;
            case R.id.btnLivestockClaimList:

                break;
        }
    }
}
