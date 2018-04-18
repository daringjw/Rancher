package com.jinkun_innovation.pastureland.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.base.BaseActivity;
import com.jinkun_innovation.pastureland.bean.LoginBean;
import com.jinkun_innovation.pastureland.common.Constants;
import com.jinkun_innovation.pastureland.net.ApiCall;
import com.jinkun_innovation.pastureland.net.RetrofitManger;
import com.jinkun_innovation.pastureland.utilcode.AppManager;
import com.jinkun_innovation.pastureland.utilcode.MD5;
import com.jinkun_innovation.pastureland.utilcode.SpUtil;
import com.jinkun_innovation.pastureland.utilcode.util.ActivityUtils;
import com.jinkun_innovation.pastureland.utilcode.util.AppUtils;
import com.jinkun_innovation.pastureland.utilcode.util.LogUtils;
import com.jinkun_innovation.pastureland.utilcode.util.ToastUtils;
import com.jinkun_innovation.pastureland.utils.PhoneFormatCheckUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tencent.bugly.beta.Beta;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yangxing on 2018/1/16.
 */

public class LoginActivity extends BaseActivity {

    private Subscription mSubscription;
    @BindView(R.id.edt_name)
    EditText mEdtName;
    @BindView(R.id.edt_pwd)
    EditText mEdtPwd;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.btn_setting_ip)
    Button mBtnSettingIp;

    @Override
    protected void initToolBar() {


    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkRuntimePermissions() {
        List<String> permissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CAMERA);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissions.size() > 0) {
            ActivityCompat.requestPermissions(LoginActivity.this, permissions.toArray(new String[permissions.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        } else {
//            startScanActivity();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(LoginActivity.this, "请手动打开摄像头权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 3;//权限请求



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mBtnSettingIp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ActivityUtils.startActivity(IpSettingActivity.class);
                return true;
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkRuntimePermissions();
        }

        /**
         * 升级
         */
        Button btnVersion = (Button) findViewById(R.id.btnVersion);
        boolean appDebug = AppUtils.isAppDebug();
        if (appDebug) {
            btnVersion.setText("测试版本 1.0." + AppUtils.getAppVersionCode());
        } else {
            btnVersion.setText("正式版本 1.0." + AppUtils.getAppVersionCode());
        }

        btnVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Beta.checkUpgrade();
            }
        });


        /**
         * 忘记密码
         */
        Button btnForgetPwd = (Button) findViewById(R.id.btnForgetPwd);
        btnForgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str = mEdtName.getText().toString();

                if (TextUtils.isEmpty(str)) {

                    Toast.makeText(getApplicationContext(),
                            "号码不能为空",
                            Toast.LENGTH_SHORT).show();
                } else {


                    /**
                     * 判断是否为中国大陆合法号码
                     */
                    if (PhoneFormatCheckUtils.isChinaPhoneLegal(str)) {

                        //合法
                        new SweetAlertDialog(LoginActivity.this)
                                .setTitleText("提示")
                                .setContentText("请确认" + mEdtName.getText().toString()
                                        + "是不是你本人号码，确定找回密码？")
                                .setCancelText("取消")
                                .setConfirmText("确定")
                                .showCancelButton(true)
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.cancel();
                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(final SweetAlertDialog sweetAlertDialog) {

                                        OkGo.<String>get(Constants.BASE_URL + Constants.VERIFY_CODE)
                                                .tag(this)
                                                .params("cellPhoneNum", mEdtName.getText().toString())
                                                .execute(new StringCallback() {
                                                    @Override
                                                    public void onSuccess(Response<String> response) {

                                                        String result = response.body().toString();
                                                        if (result.contains("success")) {

                                                            Toast.makeText(getApplicationContext(), "验证码已经发送",
                                                                    Toast.LENGTH_SHORT).show();
                                                            sweetAlertDialog.cancel();

                                                            //安全检测,重置密码
//                                                            startActivity(new Intent(getApplicationContext(),
//                                                                   ModifyPwdActivity.class ));


                                                        }

                                                    }
                                                });


                                    }
                                })
                                .show();

                    } else {
                        //不是中国大陆号码，请重新输入
                        Toast.makeText(getApplicationContext(), "不是中国大陆号码，请重新输入",
                                Toast.LENGTH_SHORT).show();
                    }


                }


            }
        });


        /**
         * 主页
         */
        Button btnHomeActivity = (Button) findViewById(R.id.btnHomeActivity);
        btnHomeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();


            }
        });

    }

    @OnClick({R.id.btn_login, R.id.btn_setting_ip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (TextUtils.isEmpty(mEdtName.getText().toString())) {
                    ToastUtils.showShort("用户名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(mEdtPwd.getText().toString())) {
                    ToastUtils.showShort("密码不能为空");
                    return;
                }
                toLogin();
                break;


        }

    }

    private void toLogin() {
        mSubscription = RetrofitManger.getInstance().createReq(ApiCall.class)
                .login(mEdtName.getText().toString(),
                        MD5.md5Encode(mEdtName.getText().toString(),
                                mEdtPwd.getText().toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginBean>() {
                    @Override
                    public void onStart() {
                        showProgress("正在登录");
                    }

                    @Override
                    public void onCompleted() {
                        hiddenProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hiddenProgress();
                        LogUtils.e(e.getMessage());
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (loginBean.getCode().equals("200")) {
                            SpUtil.saveLoginState(true);
                            SpUtil.saveAccount(mEdtName.getText().toString());
                            SpUtil.saveToken(loginBean.getData().getIdTooken());
                            SpUtil.saveUserId(loginBean.getData().getId());
                            SpUtil.saveCompanyId(String.valueOf(loginBean.getData().getCompanyId()));
                            ActivityUtils.startActivity(MainActivity.class);
                            AppManager.getAppManager().finishActivity();
                        } else {
                            ToastUtils.showShort(loginBean.getMsg());
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
