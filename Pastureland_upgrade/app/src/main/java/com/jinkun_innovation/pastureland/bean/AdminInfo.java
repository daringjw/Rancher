package com.jinkun_innovation.pastureland.bean;

/**
 * Created by Guan on 2018/3/21.
 */

public class AdminInfo {


    /**
     * adminInfo : {"adminType":"5","peopleName":"官珺伟","sex":"1","operateTime":"2018-03-21 15:49:31","updateTime":"2018-03-21 15:49:31","peopleNamePinyin":"gjw","workAddress":"深圳APP","token":"939a006e-4982-400f-a02e-4fcbc8a2b5d1","password":"722f87f7fef3ef9ce0b8677ba672bc30","createTime":"2018-03-13 19:31:19","orgnizeType":"5","cellphone":"17610893073","duty":"管理员","id":"6","age":"31","username":"17610893073","homeAddress":"深圳"}
     */

    private AdminInfoBean adminInfo;

    public AdminInfoBean getAdminInfo() {
        return adminInfo;
    }

    public void setAdminInfo(AdminInfoBean adminInfo) {
        this.adminInfo = adminInfo;
    }

    public static class AdminInfoBean {
        /**
         * adminType : 5
         * peopleName : 官珺伟
         * sex : 1
         * operateTime : 2018-03-21 15:49:31
         * updateTime : 2018-03-21 15:49:31
         * peopleNamePinyin : gjw
         * workAddress : 深圳APP
         * token : 939a006e-4982-400f-a02e-4fcbc8a2b5d1
         * password : 722f87f7fef3ef9ce0b8677ba672bc30
         * createTime : 2018-03-13 19:31:19
         * orgnizeType : 5
         * cellphone : 17610893073
         * duty : 管理员
         * id : 6
         * age : 31
         * username : 17610893073
         * homeAddress : 深圳
         */

        public String headImgUrl;
        private String adminType;
        private String peopleName;
        private String sex;
        private String operateTime;
        private String updateTime;
        private String peopleNamePinyin;
        private String workAddress;
        private String token;
        private String password;
        private String createTime;
        private String orgnizeType;
        private String cellphone;
        private String duty;
        private String id;
        private String age;
        private String username;
        private String homeAddress;

        public String getAdminType() {
            return adminType;
        }

        public void setAdminType(String adminType) {
            this.adminType = adminType;
        }

        public String getPeopleName() {
            return peopleName;
        }

        public void setPeopleName(String peopleName) {
            this.peopleName = peopleName;
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

        public String getPeopleNamePinyin() {
            return peopleNamePinyin;
        }

        public void setPeopleNamePinyin(String peopleNamePinyin) {
            this.peopleNamePinyin = peopleNamePinyin;
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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public String getCellphone() {
            return cellphone;
        }

        public void setCellphone(String cellphone) {
            this.cellphone = cellphone;
        }

        public String getDuty() {
            return duty;
        }

        public void setDuty(String duty) {
            this.duty = duty;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
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
