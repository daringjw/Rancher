package com.jinkun_innovation.pastureland.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.jinkun_innovation.pastureland.R;

/**
 * Created by Guan on 2018/5/11.
 */

public class ToolsActivity1 extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tools1);

        ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


        ImageView ivAdd = (ImageView) findViewById(R.id.ivAdd);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });


    }


}
