package com.jinkun_innovation.pastureland.bean;

import java.util.List;

/**
 * Created by yangxing on 2018/1/20.
 */

public class ClaimListBean {
    /**
     * data : [{"animalNum":"Y20171220142045704395","animalTerm":"15","animalType":"羊","animalVariety":"乌珠穆沁黑头羊","claimAddr":"北京市海淀区硅谷小区23号楼302","claimMail":"","claimName":"刘玉柱","claimNum":1,"claimPhone":"13911062898","claimPrice":1500,"claimTerm":"3","claimTime":{"date":20,"day":3,"hours":16,"minutes":47,"month":11,"nanos":0,"seconds":43,"time":1513759663000,"timezoneOffset":-480,"year":117},"claimWebCat":"","colour":"白色","distributionTimes":1,"furNum":0,"isRefrigerate":1,"orderId":"ff8080816072a89d0160731b64aa001a","pastureId":"ff80808160594999016059510fc30003","pastureName":"连江牧场","processingMethod":"皮毛内脏","remark":"无","sendTimes":0,"slaughterNum":0,"slaugtherDate":"2017-12-21 11:20:08","state":0,"stateDesc":"已确认(待宰杀)","totalWeight":0},{"animalNum":"Y20171220150308625986","animalTerm":"15","animalType":"羊","animalVariety":"乌珠穆沁黑头羊","claimAddr":"北京市东城区坤泰小区801","claimMail":"","claimName":"李霞","claimNum":1,"claimPhone":"13911802867","claimPrice":1500,"claimTerm":"1","claimTime":{"date":20,"day":3,"hours":16,"minutes":50,"month":11,"nanos":0,"seconds":22,"time":1513759822000,"timezoneOffset":-480,"year":117},"claimWebCat":"","colour":"白色","distributionTimes":1,"furNum":0,"isRefrigerate":1,"orderId":"ff8080816072a89d0160731dd1f9001d","pastureId":"ff80808160594999016059510fc30003","pastureName":"连江牧场","processingMethod":"皮毛内脏","remark":"无","sendTimes":0,"slaughterNum":0,"slaugtherDate":"2017-12-21 11:20:47","state":0,"stateDesc":"已确认(待宰杀)","totalWeight":0},{"animalNum":"Y20171220150800796109","animalTerm":"15","animalType":"羊","animalVariety":"乌珠穆沁黑头羊","claimAddr":"北京市海淀区人名大学政治教研室","claimMail":"","claimName":"刘宇峰","claimNum":1,"claimPhone":"18510001367","claimPrice":1500,"claimTerm":"1","claimTime":{"date":20,"day":3,"hours":16,"minutes":51,"month":11,"nanos":0,"seconds":2,"time":1513759862000,"timezoneOffset":-480,"year":117},"claimWebCat":"","colour":"白色","distributionTimes":1,"furNum":0,"isRefrigerate":1,"orderId":"ff8080816072a89d0160731e6d1b001e","pastureId":"ff80808160594999016059510fc30003","pastureName":"连江牧场","processingMethod":"皮毛内脏","remark":"无","sendTimes":0,"slaughterNum":0,"slaugtherDate":"2017-12-21 11:21:09","state":0,"stateDesc":"已确认(待宰杀)","totalWeight":0},{"animalNum":"Y20171220142151700710","animalTerm":"15","animalType":"羊","animalVariety":"乌珠穆沁黑头羊","claimAddr":"北京市燕郊幸福小区6号楼503","claimMail":"","claimName":"朱之用","claimNum":1,"claimPhone":"13681542676","claimPrice":1500,"claimTerm":"1","claimTime":{"date":21,"day":4,"hours":12,"minutes":7,"month":11,"nanos":0,"seconds":49,"time":1513829269000,"timezoneOffset":-480,"year":117},"claimWebCat":"","colour":"黑白","distributionTimes":1,"furNum":0,"isRefrigerate":1,"orderId":"ff8080816076d4f6016077417e870000","pastureId":"ff80808160594999016059510fc30003","pastureName":"连江牧场","processingMethod":"皮毛内脏","remark":"无","sendTimes":0,"slaughterNum":0,"slaugtherDate":"2017-12-21 12:09:05","state":0,"stateDesc":"已确认(待宰杀)","totalWeight":0}]
     * msg : 查询认领列表成功!
     * code : 200
     */

    private String msg;
    private String code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * animalNum : Y20171220142045704395
         * animalTerm : 15
         * animalType : 羊
         * animalVariety : 乌珠穆沁黑头羊
         * claimAddr : 北京市海淀区硅谷小区23号楼302
         * claimMail :
         * claimName : 刘玉柱
         * claimNum : 1
         * claimPhone : 13911062898
         * claimPrice : 1500
         * claimTerm : 3
         * claimTime : {"date":20,"day":3,"hours":16,"minutes":47,"month":11,"nanos":0,"seconds":43,"time":1513759663000,"timezoneOffset":-480,"year":117}
         * claimWebCat :
         * colour : 白色
         * distributionTimes : 1
         * furNum : 0
         * isRefrigerate : 1
         * orderId : ff8080816072a89d0160731b64aa001a
         * pastureId : ff80808160594999016059510fc30003
         * pastureName : 连江牧场
         * processingMethod : 皮毛内脏
         * remark : 无
         * sendTimes : 0
         * slaughterNum : 0
         * slaugtherDate : 2017-12-21 11:20:08
         * state : 0
         * stateDesc : 已确认(待宰杀)
         * totalWeight : 0
         */

        private String animalNum;
        private String animalTerm;
        private String animalType;
        private String animalVariety;
        private String claimAddr;
        private String claimMail;
        private String claimName;
        private int claimNum;
        private String claimPhone;
        private int claimPrice;
        private String claimTerm;
        private ClaimTimeBean claimTime;
        private String claimWebCat;
        private String colour;
        private int distributionTimes;
        private int furNum;
        private int isRefrigerate;
        private String orderId;
        private String pastureId;
        private String pastureName;
        private String processingMethod;
        private String remark;
        private int sendTimes;
        private int slaughterNum;
        private String slaugtherDate;
        private int state; //1 未确认 2已确认
        private String stateDesc;
        private int totalWeight;

        public String getAnimalNum() {
            return animalNum;
        }

        public void setAnimalNum(String animalNum) {
            this.animalNum = animalNum;
        }

        public String getAnimalTerm() {
            return animalTerm;
        }

        public void setAnimalTerm(String animalTerm) {
            this.animalTerm = animalTerm;
        }

        public String getAnimalType() {
            return animalType;
        }

        public void setAnimalType(String animalType) {
            this.animalType = animalType;
        }

        public String getAnimalVariety() {
            return animalVariety;
        }

        public void setAnimalVariety(String animalVariety) {
            this.animalVariety = animalVariety;
        }

        public String getClaimAddr() {
            return claimAddr;
        }

        public void setClaimAddr(String claimAddr) {
            this.claimAddr = claimAddr;
        }

        public String getClaimMail() {
            return claimMail;
        }

        public void setClaimMail(String claimMail) {
            this.claimMail = claimMail;
        }

        public String getClaimName() {
            return claimName;
        }

        public void setClaimName(String claimName) {
            this.claimName = claimName;
        }

        public int getClaimNum() {
            return claimNum;
        }

        public void setClaimNum(int claimNum) {
            this.claimNum = claimNum;
        }

        public String getClaimPhone() {
            return claimPhone;
        }

        public void setClaimPhone(String claimPhone) {
            this.claimPhone = claimPhone;
        }

        public int getClaimPrice() {
            return claimPrice;
        }

        public void setClaimPrice(int claimPrice) {
            this.claimPrice = claimPrice;
        }

        public String getClaimTerm() {
            return claimTerm;
        }

        public void setClaimTerm(String claimTerm) {
            this.claimTerm = claimTerm;
        }

        public ClaimTimeBean getClaimTime() {
            return claimTime;
        }

        public void setClaimTime(ClaimTimeBean claimTime) {
            this.claimTime = claimTime;
        }

        public String getClaimWebCat() {
            return claimWebCat;
        }

        public void setClaimWebCat(String claimWebCat) {
            this.claimWebCat = claimWebCat;
        }

        public String getColour() {
            return colour;
        }

        public void setColour(String colour) {
            this.colour = colour;
        }

        public int getDistributionTimes() {
            return distributionTimes;
        }

        public void setDistributionTimes(int distributionTimes) {
            this.distributionTimes = distributionTimes;
        }

        public int getFurNum() {
            return furNum;
        }

        public void setFurNum(int furNum) {
            this.furNum = furNum;
        }

        public int getIsRefrigerate() {
            return isRefrigerate;
        }

        public void setIsRefrigerate(int isRefrigerate) {
            this.isRefrigerate = isRefrigerate;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getPastureId() {
            return pastureId;
        }

        public void setPastureId(String pastureId) {
            this.pastureId = pastureId;
        }

        public String getPastureName() {
            return pastureName;
        }

        public void setPastureName(String pastureName) {
            this.pastureName = pastureName;
        }

        public String getProcessingMethod() {
            return processingMethod;
        }

        public void setProcessingMethod(String processingMethod) {
            this.processingMethod = processingMethod;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getSendTimes() {
            return sendTimes;
        }

        public void setSendTimes(int sendTimes) {
            this.sendTimes = sendTimes;
        }

        public int getSlaughterNum() {
            return slaughterNum;
        }

        public void setSlaughterNum(int slaughterNum) {
            this.slaughterNum = slaughterNum;
        }

        public String getSlaugtherDate() {
            return slaugtherDate;
        }

        public void setSlaugtherDate(String slaugtherDate) {
            this.slaugtherDate = slaugtherDate;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getStateDesc() {
            return stateDesc;
        }

        public void setStateDesc(String stateDesc) {
            this.stateDesc = stateDesc;
        }

        public int getTotalWeight() {
            return totalWeight;
        }

        public void setTotalWeight(int totalWeight) {
            this.totalWeight = totalWeight;
        }

        public static class ClaimTimeBean {
            /**
             * date : 20
             * day : 3
             * hours : 16
             * minutes : 47
             * month : 11
             * nanos : 0
             * seconds : 43
             * time : 1513759663000
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
