package com.jinkun_innovation.pastureland.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jinkun_innovation.pastureland.bean.MessageEvent;
import com.jinkun_innovation.pastureland.ui.activity.ClaimMsgActivity;
import com.jinkun_innovation.pastureland.ui.activity.DianziweilanActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

import static cn.jpush.android.api.JPushInterface.ACTION_NOTIFICATION_RECEIVED;
import static cn.jpush.android.api.JPushInterface.EXTRA_ALERT;

/**
 * Created by Guan on 2018/5/3.
 */

public class M2Receiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "onReceive - " + intent.getAction());

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "收到了自定义消息。消息内容是：" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            String EXTRA_MESSAGE = bundle.getString(JPushInterface.EXTRA_MESSAGE);

            EventBus.getDefault().post(new MessageEvent(EXTRA_MESSAGE));

            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
        } else if (ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {


            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            Log.d(TAG, "通知标题=" + title);
            String EXTRA_ALERT = bundle.getString(JPushInterface.EXTRA_ALERT);
            Log.d(TAG, "通知内容=" + EXTRA_ALERT);


            // 在这里可以做些统计，或者做些其他工作
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "用户点击打开了通知");
            // 在这里可以自己写代码去定义用户点击后的行为
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            if (title.contains("电子围栏")){
                Intent i = new Intent(context, DianziweilanActivity.class);  //自定义打开的界面
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }else if (title.contains("低电量")){

                Intent i = new Intent(context, ClaimMsgActivity.class);  //自定义打开的界面
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }else {

            }




        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }


    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.d(TAG, " title : " + title);
        String message = bundle.getString(EXTRA_ALERT);
        Log.d(TAG, "message : " + message);

        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.d(TAG, "extras : " + extras);

    }

    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String myValue = "";
        try {
            JSONObject extrasJson = new JSONObject(extras);
            myValue = extrasJson.optString("myKey");
        } catch (Exception e) {
            Log.w(TAG, "Unexpected: extras is not a valid json", e);
            return;
        }
        /*if (TYPE_THIS.equals(myValue)) {
            Intent mIntent = new Intent(context, ThisActivity.class);
            mIntent.putExtras(bundle);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mIntent);
        } else if (TYPE_ANOTHER.equals(myValue)){
            Intent mIntent = new Intent(context, AnotherActivity.class);
            mIntent.putExtras(bundle);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mIntent);
        }*/
    }


}
