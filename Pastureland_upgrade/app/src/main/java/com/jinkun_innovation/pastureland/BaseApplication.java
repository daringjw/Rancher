package com.jinkun_innovation.pastureland;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.jinkun_innovation.pastureland.utilcode.util.CrashUtils;
import com.jinkun_innovation.pastureland.utilcode.util.LogUtils;
import com.jinkun_innovation.pastureland.utilcode.util.Utils;

import java.io.File;


/**
 * Created by yangxing on 2017/10/26.
 */

public class BaseApplication extends Application {

    private static int mMainThreadId;
    private static Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        context =getApplicationContext();
        mHandler=new Handler();

        Utils.init(this);
        initLog();

        Fresco.initialize(this);

        //崩溃日志保存在本地，测试
        CrashUtils.init(Environment.getExternalStorageDirectory() + "/Pastureland/crash/");

    }

    /**
     * 返回主线程的pid
     * @return
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }
    /**
     * 返回Handler
     * @return
     */
    public static Handler getHandler() {

        return mHandler;

    }

    private void initLog() {
        String rootDir = "/Pastureland/log";
        File file = new File(Environment.getExternalStorageDirectory(), rootDir);
        LogUtils.Config config = LogUtils.getConfig()
                .setLogSwitch(true)// 设置 log 总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(true)// 设置是否输出到控制台开关，默认开
                .setGlobalTag(null)// 设置 log 全局标签，默认为空
                // 当全局标签不为空时，我们输出的 log 全部为该 tag，
                // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
                .setLogHeadSwitch(true)// 设置 log 头信息开关，默认为开
                .setLog2FileSwitch(false)// 打印 log 时是否存到文件的开关，默认关
                .setDir(file.getAbsolutePath())// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setFilePrefix("")// 当文件前缀为空时，默认为"util"，即写入文件为"util-MM-dd.txt"
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setConsoleFilter(LogUtils.V)// log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
                .setFileFilter(LogUtils.V)// log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
                .setStackDeep(1);// log 栈深度，默认为 1
        LogUtils.d(config.toString());
    }

    private static Context context;

    public static Context getContext() {

        return context;

    }


}
