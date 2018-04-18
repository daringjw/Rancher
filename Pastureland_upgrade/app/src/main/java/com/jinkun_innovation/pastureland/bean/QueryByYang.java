package com.jinkun_innovation.pastureland.bean;

import java.util.List;

/**
 * Created by Guan on 2018/3/20.
 */

public class QueryByYang {


    private List<LivestockVarietyListBean> livestockVarietyList;

    public List<LivestockVarietyListBean> getLivestockVarietyList() {
        return livestockVarietyList;
    }

    public void setLivestockVarietyList(List<LivestockVarietyListBean> livestockVarietyList) {
        this.livestockVarietyList = livestockVarietyList;
    }

    public static class LivestockVarietyListBean {
        /**
         * deviceType : 2
         * phase : 2
         * color : 2
         * sumuId : 1
         * qiId : 1
         * weight : 20.0
         * health : 1
         * updateTime : 2018-03-23 10:57:54
         * deviceNo : 999979499467887
         * imgUrl : /jkimg/20180323/f2bf0f42ae85333abe4a891049dfa65d.jpg
         * variety : 100
         * createTime : 2018-01-23 10:57:54
         * imgUrlTime : 2018-03-23 10:57:54
         * ranchId : 1
         * livestockType : 1
         * id : 306
         * isClaimed : 0
         * bindStatus : 1
         */

        public String address;

        private String deviceType;
        private String phase;
        private String color;
        private String sumuId;
        private String qiId;
        private String weight;
        private String health;
        private String updateTime;
        private String deviceNo;
        private String imgUrl;
        private String variety;
        private String createTime;
        private String imgUrlTime;
        private String ranchId;
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
