package com.jinkun_innovation.pastureland.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.bean.ToolBean;

/**
 * Created by Guan on 2018/3/29.
 */

public class AddToolDialog extends Dialog {


    Context mContext;
    private ToolBean mToolBean;

    /**
     * 自定义Dialog监听器
     */
    public interface PriorityListener {
        /**
         * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
         */
        public void refreshPriorityUI(ToolBean toolBean);


    }

    private PriorityListener listener;

    public AddToolDialog(@NonNull Context context, PriorityListener listener) {

        super(context);

        this.mContext = context;
        this.listener = listener;

    }

    int tool_type;
    int tool_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_add_tool);


        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cancel();

            }
        });

        mToolBean = new ToolBean();

        Button btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cancel();
//                listener.refreshPriorityUI("数据来自：上");

                switch (tool_type) {

                    case 0:
                        //割草机
                        mToolBean.tool_type = "割草机";
                        switch (tool_num) {

                            case 0:
                                // 1 台
                                mToolBean.tool_sum = "1台";
                                listener.refreshPriorityUI(mToolBean);


                                break;
                            case 1:
                                // 2 台
                                mToolBean.tool_sum = "2台";
                                listener.refreshPriorityUI(mToolBean);

                                break;
                            case 2:
                                // 3 台
                                mToolBean.tool_sum = "3台";

                                listener.refreshPriorityUI(mToolBean);


                                break;
                            case 3:
                                // 4台
                                mToolBean.tool_sum = "4台";

                                listener.refreshPriorityUI(mToolBean);


                                break;
                            case 4:
                                // 5 台
                                mToolBean.tool_sum = "5台";

                                listener.refreshPriorityUI(mToolBean);

                                break;


                        }


                        break;

                    case 1:
                        //播种机
                        mToolBean.tool_type = "播种机";
                        switch (tool_num) {

                            case 0:
                                // 1 台
                                mToolBean.tool_sum = "1台";
                                listener.refreshPriorityUI(mToolBean);


                                break;
                            case 1:
                                // 2 台
                                mToolBean.tool_sum = "2台";
                                listener.refreshPriorityUI(mToolBean);

                                break;
                            case 2:
                                // 3 台
                                mToolBean.tool_sum = "3台";

                                listener.refreshPriorityUI(mToolBean);


                                break;
                            case 3:
                                // 4台
                                mToolBean.tool_sum = "4台";

                                listener.refreshPriorityUI(mToolBean);


                                break;
                            case 4:
                                // 5 台
                                mToolBean.tool_sum = "5台";

                                listener.refreshPriorityUI(mToolBean);

                                break;

                        }


                        break;

                }


            }
        });

        Spinner spnType = (Spinner) findViewById(R.id.spnType);
        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

//                ToastUtils.showShort("spnType选择了第" + i + "个");
                tool_type = i;


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        Spinner spnWeight = (Spinner) findViewById(R.id.spnWeight);
        spnWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

//                ToastUtils.showShort("spnWeight选择了第" + i + "个");
                tool_num = i;


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });


    }


}
