package com.jinkun_innovation.pastureland.bean;

import java.util.List;

/**
 * Created by Guan on 2018/4/1.
 */

public class SelectVariety {


    /**
     * code : success
     * msg : 获取品种成功
     * variety : [100,101]
     */

    private String code;
    private String msg;
    private List<Integer> variety;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Integer> getVariety() {
        return variety;
    }

    public void setVariety(List<Integer> variety) {
        this.variety = variety;
    }
}
