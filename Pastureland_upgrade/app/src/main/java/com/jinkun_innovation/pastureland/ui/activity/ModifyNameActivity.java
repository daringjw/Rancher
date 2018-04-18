package com.jinkun_innovation.pastureland.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinkun_innovation.pastureland.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Guan on 2018/4/2.
 */

public class ModifyNameActivity extends Activity {

    @BindView(R.id.ivBack)
    ImageView mIvBack;
    @BindView(R.id.tvConfirm)
    TextView mTvConfirm;
    @BindView(R.id.etNewName)
    EditText mEtNewName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_modify_name);
        ButterKnife.bind(this);


    }


    @OnClick({R.id.ivBack, R.id.tvConfirm, R.id.etNewName})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:

                finish();

                break;
            case R.id.tvConfirm:

                Intent data = new Intent();
                data.putExtra("name", mEtNewName.getText().toString());
                setResult(RESULT_OK, data);
                finish();

                break;


            case R.id.etNewName:


                break;


        }
    }
}
