package com.jinkun_innovation.pastureland.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.bean.ToolBean;
import com.jinkun_innovation.pastureland.ui.view.AmountViewAge;
import com.jinkun_innovation.pastureland.utilcode.util.ToastUtils;

/**
 * Created by Guan on 2018/5/14.
 */

public class AddToolDialog1 extends Dialog {

    int tool_type;
    private int tool_sum;

    Context mContext;
    private ToolBean mToolBean;
    private PriorityListener listener;
    private EditText mEtToolFunc;


    /**
     * 自定义Dialog监听器
     */
    public interface PriorityListener {
        /**
         * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
         */
        public void refreshPriorityUI(ToolBean toolBean);


    }


    public AddToolDialog1(@NonNull Context context, PriorityListener listener) {
        super(context);

        this.mContext = context;
        this.listener = listener;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_add_tool1);


        mEtToolFunc = (EditText) findViewById(R.id.etToolFunc);

        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cancel();

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


        AmountViewAge avAge = findViewById(R.id.avAge);
        avAge.setGoods_storage(10000);
        tool_sum = 1;
        avAge.setOnAmountChangeListener(new AmountViewAge.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {

                //年龄
                tool_sum = amount;


            }

        });

        mToolBean = new ToolBean();

        Button btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                switch (tool_type) {

                    case 0:
                        //割草机
                        mToolBean.tool_type = "割草机";

                        break;
                    case 1:
                        //播种机
                        mToolBean.tool_type = "播种机";

                        break;

                }


                mToolBean.tool_sum1 = tool_sum;
                mToolBean.tool_fun = mEtToolFunc.getText().toString().trim();
                if (TextUtils.isEmpty(mToolBean.tool_fun)) {

                    ToastUtils.showShort("工具功能不能为空");
                    return;

                }else {

                    listener.refreshPriorityUI(mToolBean);
                    cancel();

                }



            }
        });


    }


}
