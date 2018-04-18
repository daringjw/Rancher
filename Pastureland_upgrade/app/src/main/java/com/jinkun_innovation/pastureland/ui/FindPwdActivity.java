package com.jinkun_innovation.pastureland.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.common.Constants;
import com.jinkun_innovation.pastureland.utils.PhoneFormatCheckUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by Guan on 2018/3/19.
 */

public class FindPwdActivity extends Activity {

    @BindView(R.id.tiePhone)
    TextInputEditText mTiePhone;
    @BindView(R.id.etVerifyCode)
    EditText mEtVerifyCode;
    @BindView(R.id.btnSendVerifyCode)
    Button mBtnSendVerifyCode;
    @BindView(R.id.btnNext)
    Button mBtnNext;
    @BindView(R.id.ivBack)
    ImageView mIvBack;


    public static final String TAG = FindPwdActivity.class.getSimpleName();
    private String mPhone;
    private String mVerifyCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_findpwd);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.btnSendVerifyCode, R.id.btnNext, R.id.ivBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ivBack:

                finish();
                break;


            case R.id.btnSendVerifyCode:

                mPhone = mTiePhone.getText().toString();
                mVerifyCode = mEtVerifyCode.getText().toString();
                if (TextUtils.isEmpty(mPhone)) {

                    Toast.makeText(getApplicationContext(),
                            "号码不能为空",
                            Toast.LENGTH_SHORT).show();
                } else {


                    /**
                     * 判断是否为中国大陆合法号码
                     */
                    if (PhoneFormatCheckUtils.isMobile(mPhone)) {

                        //合法
                        new SweetAlertDialog(this)
                                .setTitleText("提示")
                                .setContentText("请确认" + mTiePhone.getText().toString()
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

                                        OkGo.<String>get(Constants.VERIFY_CODE)
                                                .tag(this)
                                                .params("cellPhoneNum", mTiePhone.getText().toString())
                                                .execute(new StringCallback() {
                                                    @Override
                                                    public void onSuccess(Response<String> response) {

                                                        String result = response.body().toString();
                                                        Log.d(TAG, result);

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


                break;

            case R.id.btnNext:

                mPhone = mTiePhone.getText().toString().trim();
                mVerifyCode = mEtVerifyCode.getText().toString().trim();

                //号码不为空，并且是大陆号码
                if (!TextUtils.isEmpty(mPhone)) {

                    if (PhoneFormatCheckUtils.isMobile(mPhone)) {
                        if (TextUtils.isEmpty(mVerifyCode)) {
                            Toast.makeText(getApplicationContext(), "验证码不能为空", Toast.LENGTH_SHORT).show();
                        } else {

                            Intent intent = new Intent(getApplicationContext(), ResetPwdActivity.class);
                            intent.putExtra("phone", mPhone);
                            intent.putExtra("verifyCode", mVerifyCode);
                            startActivity(intent);
                            finish();

                        }
                    } else {

                        Toast.makeText(getApplicationContext(), "请输入正确的号码",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(getApplicationContext(), "号码不能为空",
                            Toast.LENGTH_SHORT).show();

                }


                break;

        }


    }


}
