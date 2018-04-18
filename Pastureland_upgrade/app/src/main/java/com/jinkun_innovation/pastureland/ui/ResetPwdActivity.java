package com.jinkun_innovation.pastureland.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
 * Created by Guan on 2018/3/19.
 */

public class ResetPwdActivity extends Activity {

    private static final String TAG1 = ResetPwdActivity.class.getSimpleName();

    @BindView(R.id.ivBack)
    ImageView mIvBack;
    @BindView(R.id.tieNewPwd)
    TextInputEditText mTieNewPwd;
    @BindView(R.id.tieConfirmNewPwd)
    TextInputEditText mTieConfirmNewPwd;
    @BindView(R.id.btnConfirm)
    Button mBtnConfirm;

    private String mPhone;
    private String mVerifyCode;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_resetpwd);

        ButterKnife.bind(this);

        mTieNewPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start,
                                      int before, int count) {

                String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
                String str = mTieNewPwd.getText().toString();
                boolean matches = str.matches(regex);
                TextView tvTips = (TextView) findViewById(R.id.tvTips);

                if (matches) {
                    tvTips.setVisibility(View.GONE);

                } else {

//                    ToastUtils.showShort("密码必须是由8-16位数字和字母的组合");
                    tvTips.setVisibility(View.VISIBLE);
                    tvTips.setText("密码必须是由8-16位数字和字母的组合");

                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }


    @OnClick({R.id.ivBack, R.id.btnConfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:

                finish();

                break;

            case R.id.btnConfirm:

                String newPwd = mTieNewPwd.getText().toString();
                String confirmPwd = mTieConfirmNewPwd.getText().toString();

                Intent intent = getIntent();
                mPhone = intent.getStringExtra("phone");
                mVerifyCode = intent.getStringExtra("verifyCode");
                Log.d(TAG1, mPhone);
                Log.d(TAG1, mVerifyCode);

                if (!TextUtils.isEmpty(newPwd) && !TextUtils.isEmpty(confirmPwd)) {

                    if (newPwd.equals(confirmPwd)) {
                        //密码一致
                        OkGo.<String>post(Constants.MODIFY_PASSWORD)
                                .tag(this)
                                .params("cellPhoneNum", mPhone)
                                .params("verificationCode", mVerifyCode)
                                .params("password", newPwd)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {

                                        String result = response.body().toString();
                                        Log.d(TAG1, result);
                                        if (result.contains("success")) {

                                            //密码修改成功
                                            Toast.makeText(getApplicationContext(), "密码修改成功"
                                                    , Toast.LENGTH_SHORT).show();

                                            startActivity(new Intent(getApplicationContext(),
                                                    LoginActivity1.class));
                                            finish();

                                        }


                                    }
                                });


                    } else {

                        Toast.makeText(getApplicationContext(), "密码不一致",
                                Toast.LENGTH_SHORT).show();

                    }


                } else {

                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();

                }


                break;
        }
    }


}
