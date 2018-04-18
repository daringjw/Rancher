package com.jinkun_innovation.pastureland.net;

import android.util.Log;

import com.jinkun_innovation.pastureland.utilcode.util.NetworkUtils;
import com.jinkun_innovation.pastureland.utilcode.util.ToastUtils;

import rx.Subscriber;

/**
 * Created by yangxing on 2018/1/23.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    public BaseSubscriber() {
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i("tag", "MySubscriber.onStart()");
        //接下来可以检查网络连接等操作
        if (!NetworkUtils.isAvailableByPing()) {

            ToastUtils.showShort("当前网络不可用");
            // 一定好主动调用下面这一句,取消本次Subscriber订阅
            if (!isUnsubscribed()) {
                unsubscribe();
            }
            return;
        }
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof Exception) {
            //访问获得对应的Exception
            onError(ExceptionHandle.handleException(e));
        } else {
            //将Throwable 和 未知错误的status code返回
            onError(new ExceptionHandle.ResponseThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    public abstract void onError(ExceptionHandle.ResponseThrowable throwable);

    @Override
    public void onNext(T t) {

    }


}
