package com.jinkun_innovation.pastureland.bean;

import java.util.List;

/**
 * Created by Guan on 2018/5/10.
 */

public class MuqunLoc {


    /**
     * msg : 按类型获取牲畜成功
     * code : success
     * livestockVarietyList : [{"deviceType":"1","phase":"2","address":"深圳","color":"2","sumuId":"9","qiId":"8","weight":"10.0","health":"1","updateTime":"2018-04-28 15:13:03","deviceNo":"0001241522122331","longtitudeBaidu":"null","imgUrl":"/jkimg/20180428/1c2ded32795d36385fa9e55d8c3790d3.jpg","variety":"201","createTime":"2018-02-28 15:13:03","latitudeBaidu":"null","imgUrlTime":"2018-04-28 15:13:03","ranchId":"25","name":"深圳牧场","livestockType":"2","id":"46","isClaimed":"0","bindStatus":"1"}]
     */

    private String msg;
    private String code;
    private List<LivestockVarietyListBean> livestockVarietyList;

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

    public List<LivestockVarietyListBean> getLivestockVarietyList() {
        return livestockVarietyList;
    }

    public void setLivestockVarietyList(List<LivestockVarietyListBean> livestockVarietyList) {
        this.livestockVarietyList = livestockVarietyList;
    }

    public static class LivestockVarietyListBean {
        /**
         * deviceType : 1
         * phase : 2
         * address : 深圳
         * color : 2
         * sumuId : 9
         * qiId : 8
         * weight : 10.0
         * health : 1
         * updateTime : 2018-04-28 15:13:03
         * deviceNo : 0001241522122331
         * longtitudeBaidu : null
         * imgUrl : /jkimg/20180428/1c2ded32795d36385fa9e55d8c3790d3.jpg
         * variety : 201
         * createTime : 2018-02-28 15:13:03
         * latitudeBaidu : null
         * imgUrlTime : 2018-04-28 15:13:03
         * ranchId : 25
         * name : 深圳牧场
         * livestockType : 2
         * id : 46
         * isClaimed : 0
         * bindStatus : 1
         */

        private String deviceType;
        private String phase;
        private String address;
        private String color;
        private String sumuId;
        private String qiId;
        private String weight;
        private String health;
        private String updateTime;
        private String deviceNo;
        private String longtitudeBaidu;
        private String imgUrl;
        private String variety;
        private String createTime;
        private String latitudeBaidu;
        private String imgUrlTime;
        private String ranchId;
        private String name;
        private String livestockType;
        private String id;
        private String isClaimed;
        private String bindStatus;

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getPhase() {
            return phase;
        }

        public void setPhase(String phase) {
            this.phase = phase;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getSumuId() {
            return sumuId;
        }

        public void setSumuId(String sumuId) {
            this.sumuId = sumuId;
        }

        public String getQiId() {
            return qiId;
        }

        public void setQiId(String qiId) {
            this.qiId = qiId;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getHealth() {
            return health;
        }

        public void setHealth(String health) {
            this.health = health;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getDeviceNo() {
            return deviceNo;
        }

        public void setDeviceNo(String deviceNo) {
            this.deviceNo = deviceNo;
        }

        public String getLongtitudeBaidu() {
            return longtitudeBaidu;
        }

        public void setLongtitudeBaidu(String longtitudeBaidu) {
            this.longtitudeBaidu = longtitudeBaidu;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getVariety() {
            return variety;
        }

        public void setVariety(String variety) {
            this.variety = variety;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getLatitudeBaidu() {
            return latitudeBaidu;
        }

        public void setLatitudeBaidu(String latitudeBaidu) {
            this.latitudeBaidu = latitudeBaidu;
        }

        public String getImgUrlTime() {
            return imgUrlTime;
        }

        public void setImgUrlTime(String imgUrlTime) {
            this.imgUrlTime = imgUrlTime;
        }

        public String getRanchId() {
            return ranchId;
        }

        public void setRanchId(String ranchId) {
            this.ranchId = ranchId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLivestockType() {
            return livestockType;
        }

        public void setLivestockType(String livestockType) {
            this.livestockType = livestockType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsClaimed() {
            return isClaimed;
        }

        public void setIsClaimed(String isClaimed) {
            this.isClaimed = isClaimed;
        }

        public String getBindStatus() {
            return bindStatus;
        }

        public void setBindStatus(String bindStatus) {
            this.bindStatus = bindStatus;
        }
    }
}
