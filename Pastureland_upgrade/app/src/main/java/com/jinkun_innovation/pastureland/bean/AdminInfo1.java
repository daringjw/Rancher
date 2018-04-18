package com.jinkun_innovation.pastureland.bean;

/**
 * Created by Guan on 2018/3/26.
 */

public class AdminInfo1 {


    /**
     * msg : 获取个人信息成功
     * code : success
     * adminInfo : {"adminType":"5","sex":"1","operateTime":"2018-03-26 18:41:11","updateTime":"2018-03-26 18:41:11","orgnizeId":"4","workAddress":"测试1","token":"14a4d8983d360390bac33c159eb440da","tokenMd5":"b30c11c192a52fd3741950a1ca50b293","password":"c5efa2ad1015513da730760e754bd7a4","headImgUrl":"","createTime":"2018-03-26 15:18:35","orgnizeType":"5","id":"9","username":"18126177726","homeAddress":"测试1"}
     */

    private String msg;
    private String code;
    private AdminInfoBean adminInfo;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AdminInfoBean getAdminInfo() {
        return adminInfo;
    }

    public void setAdminInfo(AdminInfoBean adminInfo) {
        this.adminInfo = adminInfo;
    }

    public static class AdminInfoBean {
        /**
         * adminType : 5
         * sex : 1
         * operateTime : 2018-03-26 18:41:11
         * updateTime : 2018-03-26 18:41:11
         * orgnizeId : 4
         * workAddress : 测试1
         * token : 14a4d8983d360390bac33c159eb440da
         * tokenMd5 : b30c11c192a52fd3741950a1ca50b293
         * password : c5efa2ad1015513da730760e754bd7a4
         * headImgUrl :
         * createTime : 2018-03-26 15:18:35
         * orgnizeType : 5
         * id : 9
         * username : 18126177726
         * homeAddress : 测试1
         */

        private String adminType;
        private String sex;
        private String operateTime;
        private String updateTime;
        private String orgnizeId;
        private String workAddress;
        private String token;
        private String tokenMd5;
        private String password;
        private String headImgUrl;
        private String createTime;
        private String orgnizeType;
        private String id;
        private String username;
        private String homeAddress;

        public String getAdminType() {
            return adminType;
        }

        public void setAdminType(String adminType) {
            this.adminType = adminType;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getOperateTime() {
            return operateTime;
        }

        public void setOperateTime(String operateTime) {
            this.operateTime = operateTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getOrgnizeId() {
            return orgnizeId;
        }

        public void setOrgnizeId(String orgnizeId) {
            this.orgnizeId = orgnizeId;
        }

        public String getWorkAddress() {
            return workAddress;
        }

        public void setWorkAddress(String workAddress) {
            this.workAddress = workAddress;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTokenMd5() {
            return tokenMd5;
        }

        public void setTokenMd5(String tokenMd5) {
            this.tokenMd5 = tokenMd5;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getHeadImgUrl() {
            return headImgUrl;
        }

        public void setHeadImgUrl(String headImgUrl) {
            this.headImgUrl = headImgUrl;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getOrgnizeType() {
            return orgnizeType;
        }

        public void setOrgnizeType(String orgnizeType) {
            this.orgnizeType = orgnizeType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getHomeAddress() {
            return homeAddress;
        }

        public void setHomeAddress(String homeAddress) {
            this.homeAddress = homeAddress;
        }
    }
}
