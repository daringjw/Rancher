package com.jinkun_innovation.pastureland.utilcode;

import com.jinkun_innovation.pastureland.utilcode.util.SPUtils;

/**
 * Created by yangxing on 2018/1/21.
 */

public class SpUtil {
    public static void saveLoginState(boolean loginState) {
        SPUtils.getInstance().put("loginState", loginState);
    }

    public static boolean getLoginState() {
        return SPUtils.getInstance().getBoolean("loginState");
    }

    public static void saveUserId(String userId) {
        SPUtils.getInstance().put("userId", userId);
    }

    public static String getUserId() {
        return SPUtils.getInstance().getString("userId");
    }


    public static void saveToken(String token) {
        SPUtils.getInstance().put("token", token);
    }

    public static String getToken() {
        return SPUtils.getInstance().getString("token");
    }

    public static void saveAccount(String account) {
        SPUtils.getInstance().put("account", account);
    }

    public static String getAccount() {
        return SPUtils.getInstance().getString("account");
    }

    public static void saveCompanyId(String companyId) {
        SPUtils.getInstance().put("companyId", companyId);
    }

    public static String getCompanyId() {
        return SPUtils.getInstance().getString("companyId");
    }

    public static void saveIP(String ip) {
        SPUtils.getInstance().put("ip", ip);
    }

    public static String getIP() {
        return SPUtils.getInstance().getString("ip","222.249.165.94");
    }

    public static void savePort(String port) {
        SPUtils.getInstance().put("port", port);
    }

    public static String getPort() {
        return SPUtils.getInstance().getString("port","10100");
    }
}
