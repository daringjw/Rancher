package com.jinkun_innovation.pastureland.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.bean.LoginSuccess;
import com.jinkun_innovation.pastureland.bean.ToolBean;
import com.jinkun_innovation.pastureland.common.Constants;
import com.jinkun_innovation.pastureland.ui.dialog.AddToolDialog1;
import com.jinkun_innovation.pastureland.utilcode.util.TimeUtils;
import com.jinkun_innovation.pastureland.utilcode.util.ToastUtils;
import com.jinkun_innovation.pastureland.utils.PrefUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/**
 * Created by Guan on 2018/5/11.
 */

public class ToolsActivity1 extends Activity {

    private static final String TAG1 = ToolsActivity1.class.getSimpleName();

    private AddToolDialog1 mAddToolDialog1;

    String mLogin_success;
    LoginSuccess mLoginSuccess;
    String mUsername;


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


        mLogin_success = PrefUtils.getString(this, "login_success", null);
        Gson gson = new Gson();
        mLoginSuccess = gson.fromJson(mLogin_success, LoginSuccess.class);
        mUsername = PrefUtils.getString(this, "username", null);

        ImageView ivAdd = (ImageView) findViewById(R.id.ivAdd);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mAddToolDialog1 = new AddToolDialog1(ToolsActivity1.this,
                        new AddToolDialog1.PriorityListener() {
                            @Override
                            public void refreshPriorityUI(ToolBean toolBean) {

                                Log.d(TAG1, "tool_type=" + toolBean.tool_type);
                                Log.d(TAG1, "tool_sum=" + toolBean.tool_sum1);
                                Log.d(TAG1, "tool_fun=" + toolBean.tool_fun);

                                toolBean.time = TimeUtils.getNowString();

                                OkGo.<String>get(Constants.saveTool)
                                        .tag(this)
                                        .params("token", mLoginSuccess.getToken())
                                        .params("username", mUsername)
                                        .params("ranchID", mLoginSuccess.getRanchID())
                                        .params("deviceName", toolBean.tool_type)
                                        .params("deviceFunction", toolBean.tool_fun)
                                        .params("deviceCount", toolBean.tool_sum1)
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(Response<String> response) {

                                                String result = response.body().toString();
                                                if (result.contains("success")) {

                                                    ToastUtils.showShort("添加成功");

                                                } else {

                                                    ToastUtils.showShort("添加失败");

                                                }

                                            }
                                        });


                            }
                        });

                mAddToolDialog1.show();


            }
        });


        OkGo.<String>get(Constants.getToolList)
                .tag(this)
                .params("token", mLoginSuccess.getToken())
                .params("username", mUsername)
                .params("ranchID", mLoginSuccess.getRanchID())
                .params("current", 1)
                .params("pagesize", 299)

                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        String result = response.body().toString();



                    }
                });




    }


}
