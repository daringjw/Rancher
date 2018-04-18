package com.jinkun_innovation.pastureland.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.common.Constants;
import com.jinkun_innovation.pastureland.utilcode.SpUtil;
import com.jinkun_innovation.pastureland.utilcode.util.AppUtils;
import com.jinkun_innovation.pastureland.utilcode.util.ToastUtils;
import com.jinkun_innovation.pastureland.utils.PhoneFormatCheckUtils;
import com.jinkun_innovation.pastureland.utils.PrefUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Guan on 2018/3/19.
 */

public class LoginActivity1 extends AppCompatActivity {

    private static final String TAG1 = LoginActivity1.class.getSimpleName();

    @BindView(R.id.tieAccount)
    TextInputEditText mTieAccount;
    @BindView(R.id.tiePwd)
    TextInputEditText mTiePwd;
    @BindView(R.id.tvForgetPwd)
    TextView mTvForgetPwd;
    @BindView(R.id.btnLogin)
    ImageView mBtnLogin;

    private SweetAlertDialog mPDialog;
    private String mUsername;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login1);
        ButterKnife.bind(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkRuntimePermissions();
        }

        TextView tvVersion = (TextView) findViewById(R.id.tvVersion);
        tvVersion.setText("版本号：" + AppUtils.getAppVersionCode() + "，IP=" + Constants.IP);


        mUsername = PrefUtils.getString(getApplicationContext(), "username", null);
        if (!TextUtils.isEmpty(mUsername)) {
            mTieAccount.setText(mUsername);
        }


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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.RECORD_AUDIO);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }


        if (permissions.size() > 0) {
            ActivityCompat.requestPermissions(LoginActivity1.this, permissions.toArray(new String[permissions.size()]),
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
                    Toast.makeText(LoginActivity1.this, "请手动打开摄像头权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 3;//权限请求


    @OnClick({R.id.tvForgetPwd, R.id.btnLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {


            case R.id.tvForgetPwd:

                startActivity(new Intent(getApplicationContext(), FindPwdActivity.class));

                break;
            case R.id.btnLogin:

                final String account = mTieAccount.getText().toString().trim();
                String pwd = mTiePwd.getText().toString();


                if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(pwd)) {


                    if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(pwd)) {

                        if (PhoneFormatCheckUtils.isMobile(account)) {

                            //大陆号码，可以登录
                            mPDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                            mPDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            mPDialog.setTitleText("正在登录...");
                            mPDialog.setCancelable(true);
                            mPDialog.show();

                            OkGo.<String>post(Constants.LOGIN)
                                    .tag(this)
                                    .params("username", account)
                                    .params("password", pwd)
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(Response<String> response) {

                                            String result = response.body().toString();
                                            Log.d(TAG1, result);
                                            if (result.contains("error")) {
                                                Toast.makeText(getApplicationContext(),
                                                        "账号或者密码错误",
                                                        Toast.LENGTH_SHORT).show();
                                                mPDialog.cancel();
                                            } else if (result.contains("token")) {


                                                //登陆成功
                                                PrefUtils.setString(getApplicationContext(), "login_success", result);
                                                PrefUtils.setString(getApplicationContext(), "username", account);
                                                mPDialog.cancel();
                                                SpUtil.saveLoginState(true);
                                                Toast.makeText(getApplicationContext(), "登录成功",
                                                        Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                                finish();

                                            } else {
                                                Toast.makeText(getApplicationContext(),
                                                        "登录异常",
                                                        Toast.LENGTH_SHORT).show();
                                                mPDialog.cancel();

                                            }


                                        }

                                        @Override
                                        public void onError(Response<String> response) {
                                            super.onError(response);

                                            ToastUtils.showShort("没有网络，请检查网络");

                                        }
                                    });


                        } else {

                            //非法电话号码
                            ToastUtils.showShort("请输入正确的电话号码");

                        }

                    }


                    break;
                } else {


                    ToastUtils.showShort("账号和密码不能为空");

                }


        }
    }

}
