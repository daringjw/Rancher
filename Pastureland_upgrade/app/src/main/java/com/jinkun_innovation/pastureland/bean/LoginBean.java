package com.jinkun_innovation.pastureland.bean;

/**
 * Created by yangxing on 2018/1/20.
 */

public class LoginBean {

    /**
     * msg : 登陆成功!
     * data : {"accountType":2,"companyId":2,"creatTime":{"date":15,"day":5,"hours":16,"minutes":34,"month":11,"nanos":0,"seconds":38,"time":1513326878000,"timezoneOffset":-480,"year":117},"id":"ff808081605949990160594f9b9a0002","idTooken":"3c31d7e7801544eea990a30a365baef5","idTookenMd5":"55f06583b06d3b13e0b42ed9a95f3772","identifyingCode":"","isOnLogin":1,"password":"970a302ef0628fef70963be486ab3792","roleId":0,"townsAccount":"","townsName":"","username":"bymc"}
     * code : 200
     */

    private String msg;
    private DataBean data;
    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * accountType : 2
         * companyId : 2
         * creatTime : {"date":15,"day":5,"hours":16,"minutes":34,"month":11,"nanos":0,"seconds":38,"time":1513326878000,"timezoneOffset":-480,"year":117}
         * id : ff808081605949990160594f9b9a0002
         * idTooken : 3c31d7e7801544eea990a30a365baef5
         * idTookenMd5 : 55f06583b06d3b13e0b42ed9a95f3772
         * identifyingCode :
         * isOnLogin : 1
         * password : 970a302ef0628fef70963be486ab3792
         * roleId : 0
         * townsAccount :
         * townsName :
         * username : bymc
         */

        private int accountType;
        private int companyId;
        private CreatTimeBean creatTime;
        private String id;
        private String idTooken;
        private String idTookenMd5;
        private String identifyingCode;
        private int isOnLogin;
        private String password;
        private int roleId;
        private String townsAccount;
        private String townsName;
        private String username;

        public int getAccountType() {
            return accountType;
        }

        public void setAccountType(int accountType) {
            this.accountType = accountType;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public CreatTimeBean getCreatTime() {
            return creatTime;
        }

        public void setCreatTime(CreatTimeBean creatTime) {
            this.creatTime = creatTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdTooken() {
            return idTooken;
        }

        public void setIdTooken(String idTooken) {
            this.idTooken = idTooken;
        }

        public String getIdTookenMd5() {
            return idTookenMd5;
        }

        public void setIdTookenMd5(String idTookenMd5) {
            this.idTookenMd5 = idTookenMd5;
        }

        public String getIdentifyingCode() {
            return identifyingCode;
        }

        public void setIdentifyingCode(String identifyingCode) {
            this.identifyingCode = identifyingCode;
        }

        public int getIsOnLogin() {
            return isOnLogin;
        }

        public void setIsOnLogin(int isOnLogin) {
            this.isOnLogin = isOnLogin;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public String getTownsAccount() {
            return townsAccount;
        }

        public void setTownsAccount(String townsAccount) {
            this.townsAccount = townsAccount;
        }

        public String getTownsName() {
            return townsName;
        }

        public void setTownsName(String townsName) {
            this.townsName = townsName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public static class CreatTimeBean {
            /**
             * date : 15
             * day : 5
             * hours : 16
             * minutes : 34
             * month : 11
             * nanos : 0
             * seconds : 38
             * time : 1513326878000
             * timezoneOffset : -480
             * year : 117
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int nanos;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getNanos() {
                return nanos;
            }

            public void setNanos(int nanos) {
                this.nanos = nanos;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }
    }
}
