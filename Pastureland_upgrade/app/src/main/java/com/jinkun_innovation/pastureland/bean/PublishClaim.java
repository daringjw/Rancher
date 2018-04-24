package com.jinkun_innovation.pastureland.bean;

/**
 * Created by Guan on 2018/4/24.
 */

public class PublishClaim {


    /**
     * msg2 : 发布牲畜到认领表成功
     * msg1 : 牲畜登记成功
     * code : success
     * data : 登记后在查询牲畜是否为空：com.jk.ranch.model.TLivestock@418ffe4a
     * data1 : com.jk.ranch.model.TLivestock@418ffe4a,com.jk.ranch.model.TLivestockPublicInfo@5916ab12
     */

    private String msg2;
    private String msg1;
    private String code;
    private String data;
    private String data1;

    public String getMsg2() {
        return msg2;
    }

    public void setMsg2(String msg2) {
        this.msg2 = msg2;
    }

    public String getMsg1() {
        return msg1;
    }

    public void setMsg1(String msg1) {
        this.msg1 = msg1;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }
}
