package com.jinkun_innovation.pastureland.bean;

/**
 * Created by Guan on 2018/4/7.
 */

public class IsBinded {


    /**
     * msg : true
     * code : success
     * msg1 : 设备绑定状态
     */

    private boolean msg;
    private String code;
    private String msg1;

    public boolean isMsg() {
        return msg;
    }

    public void setMsg(boolean msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg1() {
        return msg1;
    }

    public void setMsg1(String msg1) {
        this.msg1 = msg1;
    }
}
