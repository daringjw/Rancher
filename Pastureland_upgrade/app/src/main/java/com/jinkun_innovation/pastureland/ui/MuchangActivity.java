package com.jinkun_innovation.pastureland.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.ui.activity.Muchang2Activity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Guan on 2018/3/17.
 */

public class MuchangActivity extends AppCompatActivity {

    @BindView(R.id.idBack)
    ImageView mIdBack;
    @BindView(R.id.ivLianjiangmuchang)
    ImageView mIvLianjiangmuchang;
    @BindView(R.id.ivHeishuimuchang)
    ImageView mIvHeishuimuchang;
    @BindView(R.id.ivHeiyuanmuchang)
    ImageView mIvHeiyuanmuchang;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_muchang);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.idBack, R.id.ivLianjiangmuchang, R.id.ivHeishuimuchang, R.id.ivHeiyuanmuchang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.idBack:
                finish();
                break;
            case R.id.ivLianjiangmuchang:

                startActivity(new Intent(getApplicationContext(),LianJiangPasturelandActivity.class));
                break;
            case R.id.ivHeishuimuchang:
                break;
            case R.id.ivHeiyuanmuchang:
                break;
        }
    }
}
