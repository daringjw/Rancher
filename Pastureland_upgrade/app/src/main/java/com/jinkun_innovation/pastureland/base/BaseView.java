package com.jinkun_innovation.pastureland.base;

import android.content.Context;

/**
 * Created by yangxing on 2017/10/25.
 */

public interface BaseView {
    /**
     * 显示正在加载view
     */
    void showLoading();
    /**
     * 关闭正在加载view
     */
    void hideLoading();
    /**
     * 显示提示
     * @param msg
     */
    void showToast(String msg);
    /**
     * 显示请求错误提示
     */
    void showErr(Throwable e);
    /**
     * 获取上下文
     * @return 上下文
     */
    Context getContext();
}
