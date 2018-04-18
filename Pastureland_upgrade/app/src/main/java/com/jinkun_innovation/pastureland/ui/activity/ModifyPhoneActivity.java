package com.jinkun_innovation.pastureland.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.utilcode.util.ToastUtils;
import com.jinkun_innovation.pastureland.utils.PhoneFormatCheckUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Guan on 2018/4/3.
 */

public class ModifyPhoneActivity extends Activity {

    @BindView(R.id.ivBack)
    ImageView mIvBack;
    @BindView(R.id.tvConfirm)
    TextView mTvConfirm;
    @BindView(R.id.etNewName)
    EditText mEtNewName;
    @BindView(R.id.tvTips)
    TextView tvTips;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_modify_phone);
        ButterKnife.bind(this);

        mEtNewName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                boolean mobile = PhoneFormatCheckUtils.isMobile(mEtNewName.getText().toString());

                if (mobile) {
                    tvTips.setText("此号码是中国大陆号码");
                    tvTips.setTextColor(Color.BLACK);
                } else {
                    tvTips.setText("请输入中国大陆号码");
                    tvTips.setTextColor(Color.RED);
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }


    @OnClick({R.id.ivBack, R.id.tvConfirm, R.id.etNewName})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:

                finish();

                break;
            case R.id.tvConfirm:

                boolean mobile = PhoneFormatCheckUtils.isMobile(mEtNewName.getText().toString());
                if (mobile) {

                    Intent data = new Intent();
                    data.putExtra("phone", mEtNewName.getText().toString());
                    setResult(RESULT_OK, data);
                    finish();

                } else {
                    ToastUtils.showShort("请输入中国大陆号码");
                    tvTips.setText("请输入中国大陆号码");
                    tvTips.setTextColor(Color.RED);

                }


                break;

            case R.id.etNewName:


                break;
        }
    }
}
