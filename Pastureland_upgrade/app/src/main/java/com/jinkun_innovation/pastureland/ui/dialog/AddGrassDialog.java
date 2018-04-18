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
import com.jinkun_innovation.pastureland.bean.GrassBean;

/**
 * Created by Guan on 2018/3/29.
 */

public class AddGrassDialog extends Dialog {


    Context mContext;

    private GrassBean mGrassBean;

    /**
     * 自定义Dialog监听器
     */
    public interface PriorityListener {
        /**
         * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
         */
        public void refreshPriorityUI(GrassBean grassBean);


    }

    private PriorityListener listener;

    public AddGrassDialog(@NonNull Context context, PriorityListener listener) {

        super(context);

        this.mContext = context;
        this.listener = listener;

    }

    int tool_type;
    int tool_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_add_grass);


        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cancel();

            }
        });

        mGrassBean = new GrassBean();

        Button btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cancel();
//                listener.refreshPriorityUI("数据来自：上");

                switch (tool_type) {

                    case 0:
                        //甘草
                        mGrassBean.grass_type = "干草";
                        switch (tool_num) {

                            case 0:
                                // 1 台
                                mGrassBean.grass_weight = "50千克";


                                break;
                            case 1:
                                // 2 台
                                mGrassBean.grass_weight = "100千克";

                                break;
                            case 2:
                                // 3 台
                                mGrassBean.grass_weight = "150千克";


                                break;
                            case 3:
                                // 4台
                                mGrassBean.grass_weight = "200千克";


                                break;
                            case 4:
                                // 5 台
                                mGrassBean.grass_weight = "250千克";

                                break;

                        }


                        break;

                    case 1:
                        //秸秆
                        mGrassBean.grass_type = "秸秆";

                        switch (tool_num) {

                            case 0:
                                // 1 台
                                mGrassBean.grass_weight = "50千克";


                                break;
                            case 1:
                                // 2 台
                                mGrassBean.grass_weight = "100千克";

                                break;
                            case 2:
                                // 3 台
                                mGrassBean.grass_weight = "150千克";


                                break;
                            case 3:
                                // 4台
                                mGrassBean.grass_weight = "200千克";

                                break;

                            case 4:
                                // 5 台
                                mGrassBean.grass_weight = "250千克";

                                break;

                        }

                        break;

                }

                listener.refreshPriorityUI(mGrassBean);


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
