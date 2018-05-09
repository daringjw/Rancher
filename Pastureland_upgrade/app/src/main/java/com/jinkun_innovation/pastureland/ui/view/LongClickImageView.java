package com.jinkun_innovation.pastureland.ui.view;

/**
 * Created by Guan on 2018/5/9.
 */

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 该控件用以实现长按触发多次点击事件的效果
 *
 * @author Sanji.Shen
 */
public class LongClickImageView extends SimpleDraweeView {

    /**
     * 长按事件触发频率
     */
    private long mDelayMillis = 5000;

    private boolean isMotionEventUp = true;

    public LongClickImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        intiListener();
    }

    public LongClickImageView(Context context) {
        super(context);
        intiListener();
    }

    public LongClickImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        intiListener();
    }

    public void intiListener() {
        this.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                isMotionEventUp = false;
                mHandler.sendEmptyMessage(0);
                return false;
            }
        });
        this.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    isMotionEventUp = true;
                }
                return false;
            }
        });
    }

    /**
     * 用以处理click事件
     */
    Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(android.os.Message msg) {
            if (!isMotionEventUp && isEnabled()) {
                // 调用click事件
                performClick();
                mHandler.sendEmptyMessageDelayed(0, mDelayMillis);

            }
        }


    };





    /**
     * 设置触发时间间隔
     *
     * @param delayMillis
     */
    public void setmDelayMillis(long delayMillis) {
        this.mDelayMillis = delayMillis;
    }

}
