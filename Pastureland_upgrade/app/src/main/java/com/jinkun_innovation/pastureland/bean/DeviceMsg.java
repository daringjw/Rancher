package com.jinkun_innovation.pastureland.bean;

import java.util.List;

/**
 * Created by Guan on 2018/3/22.
 */

public class DeviceMsg {


    private List<LivestockClaimListBean> livestockClaimList;
    private List<BatteryListBean> batteryList;

    public List<LivestockClaimListBean> getLivestockClaimList() {
        return livestockClaimList;
    }

    public void setLivestockClaimList(List<LivestockClaimListBean> livestockClaimList) {
        this.livestockClaimList = livestockClaimList;
    }

    public List<BatteryListBean> getBatteryList() {
        return batteryList;
    }

    public void setBatteryList(List<BatteryListBean> batteryList) {
        this.batteryList = batteryList;
    }

    public static class LivestockClaimListBean {
        /**
         * livestockId : 2
         * color : 2
         * birthTime : 2018-03-06 09:59:27
         * livestockName : 100
         * businesstype : 1
         * variety : 100
         * price : 300.0
         * livestockType : 1
         * livestockDetails : 状态良好
         * cellphone : 18210819925
         * lifeTime : 3
         * videoTime : 2018-03-05 10:57:27
         * isClaimed : 1
         * photographicTime : 2018-03-04 10:57:27
         * memberId : 46
         * characteristics : 吃的好睡的好
         * finishTime : 2018-03-06 09:57:27
         * sumuId : 1
         * qiId : 1
         * weight : 18.0
         * health : 3
         * isVideo : 0
         * deviceNo : 2180100000005
         * claimTime : 2018-03-05 10:57:27
         * imgUrl : http://222.249.165.94:10100/jkimg/20180321/401ee4a868520a66d6ef29493090d9f9.jpg
         * isPhotographic : 0
         * createTime : 2018-03-04 10:57:27
         * ranchId : 1
         */

        private String livestockId;
        private String color;
        private String birthTime;
        private String livestockName;
        private String businesstype;
        private String variety;
        private String price;
        private String livestockType;
        private String livestockDetails;
        private String cellphone;
        private String lifeTime;
        private String videoTime;
        private String isClaimed;
        private String photographicTime;
        private String memberId;
        private String characteristics;
        private String finishTime;
        private String sumuId;
        private String qiId;
        private String weight;
        private String health;
        private String isVideo;
        private String deviceNo;
        private String claimTime;
        private String imgUrl;
        private String isPhotographic;
        private String createTime;
        private String ranchId;

        public String getLivestockId() {
            return livestockId;
        }

        public void setLivestockId(String livestockId) {
            this.livestockId = livestockId;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getBirthTime() {
            return birthTime;
        }

        public void setBirthTime(String birthTime) {
            this.birthTime = birthTime;
        }

        public String getLivestockName() {
            return livestockName;
        }

        public void setLivestockName(String livestockName) {
            this.livestockName = livestockName;
        }

        public String getBusinesstype() {
            return businesstype;
        }

        public void setBusinesstype(String businesstype) {
            this.businesstype = businesstype;
        }

        public String getVariety() {
            return variety;
        }

        public void setVariety(String variety) {
            this.variety = variety;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getLivestockType() {
            return livestockType;
        }

        public void setLivestockType(String livestockType) {
            this.livestockType = livestockType;
        }

        public String getLivestockDetails() {
            return livestockDetails;
        }

        public void setLivestockDetails(String livestockDetails) {
            this.livestockDetails = livestockDetails;
        }

        public String getCellphone() {
            return cellphone;
        }

        public void setCellphone(String cellphone) {
            this.cellphone = cellphone;
        }

        public String getLifeTime() {
            return lifeTime;
        }

        public void setLifeTime(String lifeTime) {
            this.lifeTime = lifeTime;
        }

        public String getVideoTime() {
            return videoTime;
        }

        public void setVideoTime(String videoTime) {
            this.videoTime = videoTime;
        }

        public String getIsClaimed() {
            return isClaimed;
        }

        public void setIsClaimed(String isClaimed) {
            this.isClaimed = isClaimed;
        }

        public String getPhotographicTime() {
            return photographicTime;
        }

        public void setPhotographicTime(String photographicTime) {
            this.photographicTime = photographicTime;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getCharacteristics() {
            return characteristics;
        }

        public void setCharacteristics(String characteristics) {
            this.characteristics = characteristics;
        }

        public String getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(String finishTime) {
            this.finishTime = finishTime;
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

        public String getIsVideo() {
            return isVideo;
        }

        public void setIsVideo(String isVideo) {
            this.isVideo = isVideo;
        }

        public String getDeviceNo() {
            return deviceNo;
        }

        public void setDeviceNo(String deviceNo) {
            this.deviceNo = deviceNo;
        }

        public String getClaimTime() {
            return claimTime;
        }

        public void setClaimTime(String claimTime) {
            this.claimTime = claimTime;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getIsPhotographic() {
            return isPhotographic;
        }

        public void setIsPhotographic(String isPhotographic) {
            this.isPhotographic = isPhotographic;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getRanchId() {
            return ranchId;
        }

        public void setRanchId(String ranchId) {
            this.ranchId = ranchId;
        }
    }

    public static class BatteryListBean {
        /**
         * livestockId : 264
         * lastBindTime : 2018-03-20 21:07:48
         * sumuId : 1
         * variety : 201
         * bindNum : 1
         * createTime : 2018-03-20 21:07:48
         * ranchId : 1
         * qiId : 1
         * livestockType : 4
         * deviceNo : 999999
         * battery : 0.0
         * bindStatus : 1
         */

        private String livestockId;
        private String lastBindTime;
        private String sumuId;
        private String variety;
        private String bindNum;
        private String createTime;
        private String ranchId;
        private String qiId;
        private String livestockType;
        private String deviceNo;
        private String battery;
        private String bindStatus;

        public String getLivestockId() {
            return livestockId;
        }

        public void setLivestockId(String livestockId) {
            this.livestockId = livestockId;
        }

        public String getLastBindTime() {
            return lastBindTime;
        }

        public void setLastBindTime(String lastBindTime) {
            this.lastBindTime = lastBindTime;
        }

        public String getSumuId() {
            return sumuId;
        }

        public void setSumuId(String sumuId) {
            this.sumuId = sumuId;
        }

        public String getVariety() {
            return variety;
        }

        public void setVariety(String variety) {
            this.variety = variety;
        }

        public String getBindNum() {
            return bindNum;
        }

        public void setBindNum(String bindNum) {
            this.bindNum = bindNum;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getRanchId() {
            return ranchId;
        }

        public void setRanchId(String ranchId) {
            this.ranchId = ranchId;
        }

        public String getQiId() {
            return qiId;
        }

        public void setQiId(String qiId) {
            this.qiId = qiId;
        }

        public String getLivestockType() {
            return livestockType;
        }

        public void setLivestockType(String livestockType) {
            this.livestockType = livestockType;
        }

        public String getDeviceNo() {
            return deviceNo;
        }

        public void setDeviceNo(String deviceNo) {
            this.deviceNo = deviceNo;
        }

        public String getBattery() {
            return battery;
        }

        public void setBattery(String battery) {
            this.battery = battery;
        }

        public String getBindStatus() {
            return bindStatus;
        }

        public void setBindStatus(String bindStatus) {
            this.bindStatus = bindStatus;
        }
    }
}
