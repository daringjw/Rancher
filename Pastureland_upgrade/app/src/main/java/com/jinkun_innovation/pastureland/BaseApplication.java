package com.jinkun_innovation.pastureland;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.jinkun_innovation.pastureland.utilcode.util.CrashUtils;
import com.jinkun_innovation.pastureland.utilcode.util.LogUtils;
import com.jinkun_innovation.pastureland.utilcode.util.Utils;

import java.io.File;


/**
 * Created by yangxing on 2017/10/26.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        initLog();

        Fresco.initialize(this);

        //崩溃日志保存在本地，测试
        CrashUtils.init(Environment.getExternalStorageDirectory() + "/Pastureland/crash/");




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

    public Context mContext;

    public Context getContext() {
        return getApplicationContext();
    }
}
