package com.jinkun_innovation.pastureland.bean;

import java.util.List;

/**
 * Created by Guan on 2018/5/11.
 */

public class Dianziweilan {


    /**
     * msg : 获取设备消息成功
     * code : success
     * livestockClaimList : []
     * deviceAlarmMsgList : [{"deviceType":"1","livestockId":"1315","address":"广东省深圳市宝安区金海路10附近，汇潮科技大厦","msgType":"2","sumuId":"1","qiId":"1","latitudeGps":"22.581779833333332","deviceNo":"0001180100000020","longtitudeBaidu":"113.86622913023584","longtitudeGps":"113.85480000000001","msgContent":"头羊离开电子围栏区域报警！头羊定位网关编号：0001180100000020, 经度：113.86622913023584, 纬度：22.584942586987648。","recordTime":"2018-05-11 14:16:20","variety":"100","latitudeBaidu":"22.584942586987648","msgTitle":"头羊离开电子围栏区域报警！头羊定位网关编号：0001180100000020","ranchId":"13","livestockType":"1","id":"1","reportTime":"2018-05-11 11:57:10"}]
     * adminMsgList : []
     * batteryList : [{"deviceType":"1","livestockId":"199","sumuId":"1","bindNum":"1","qiId":"1","deviceNo":"0001180100000004","battery":"0.0","lastBindTime":"2018-04-04 10:37:18","variety":"100","createTime":"2018-04-04 10:37:18","ranchId":"13","livestockType":"1","bindStatus":"1"},{"deviceType":"3","livestockId":"201","sumuId":"1","bindNum":"1","qiId":"1","deviceNo":"0003180100000005","battery":"0.0","lastBindTime":"2018-04-04 10:40:22","variety":"301","createTime":"2018-04-04 10:40:22","ranchId":"13","livestockType":"3","bindStatus":"1"},{"deviceType":"3","livestockId":"202","sumuId":"1","bindNum":"1","qiId":"1","deviceNo":"0003180100000006","battery":"0.0","lastBindTime":"2018-04-04 11:24:22","variety":"100","createTime":"2018-04-04 11:24:22","ranchId":"13","livestockType":"1","bindStatus":"1"},{"deviceType":"3","livestockId":"204","sumuId":"1","bindNum":"1","qiId":"1","deviceNo":"0003180100000009","battery":"0.0","lastBindTime":"2018-04-04 11:54:06","variety":"301","createTime":"2018-04-04 11:54:06","ranchId":"13","livestockType":"3","bindStatus":"1"}]
     */

    private String msg;
    private String code;
    private List<?> livestockClaimList;
    private List<DeviceAlarmMsgListBean> deviceAlarmMsgList;
    private List<?> adminMsgList;
    private List<BatteryListBean> batteryList;

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

    public List<?> getLivestockClaimList() {
        return livestockClaimList;
    }

    public void setLivestockClaimList(List<?> livestockClaimList) {
        this.livestockClaimList = livestockClaimList;
    }

    public List<DeviceAlarmMsgListBean> getDeviceAlarmMsgList() {
        return deviceAlarmMsgList;
    }

    public void setDeviceAlarmMsgList(List<DeviceAlarmMsgListBean> deviceAlarmMsgList) {
        this.deviceAlarmMsgList = deviceAlarmMsgList;
    }

    public List<?> getAdminMsgList() {
        return adminMsgList;
    }

    public void setAdminMsgList(List<?> adminMsgList) {
        this.adminMsgList = adminMsgList;
    }

    public List<BatteryListBean> getBatteryList() {
        return batteryList;
    }

    public void setBatteryList(List<BatteryListBean> batteryList) {
        this.batteryList = batteryList;
    }

    public static class DeviceAlarmMsgListBean {
        /**
         * deviceType : 1
         * livestockId : 1315
         * address : 广东省深圳市宝安区金海路10附近，汇潮科技大厦
         * msgType : 2
         * sumuId : 1
         * qiId : 1
         * latitudeGps : 22.581779833333332
         * deviceNo : 0001180100000020
         * longtitudeBaidu : 113.86622913023584
         * longtitudeGps : 113.85480000000001
         * msgContent : 头羊离开电子围栏区域报警！头羊定位网关编号：0001180100000020, 经度：113.86622913023584, 纬度：22.584942586987648。
         * recordTime : 2018-05-11 14:16:20
         * variety : 100
         * latitudeBaidu : 22.584942586987648
         * msgTitle : 头羊离开电子围栏区域报警！头羊定位网关编号：0001180100000020
         * ranchId : 13
         * livestockType : 1
         * id : 1
         * reportTime : 2018-05-11 11:57:10
         */

        private String deviceType;
        private String livestockId;
        private String address;
        private String msgType;
        private String sumuId;
        private String qiId;
        private String latitudeGps;
        private String deviceNo;
        private String longtitudeBaidu;
        private String longtitudeGps;
        private String msgContent;
        private String recordTime;
        private String variety;
        private String latitudeBaidu;
        private String msgTitle;
        private String ranchId;
        private String livestockType;
        private String id;
        private String reportTime;

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getLivestockId() {
            return livestockId;
        }

        public void setLivestockId(String livestockId) {
            this.livestockId = livestockId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMsgType() {
            return msgType;
        }

        public void setMsgType(String msgType) {
            this.msgType = msgType;
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

        public String getLatitudeGps() {
            return latitudeGps;
        }

        public void setLatitudeGps(String latitudeGps) {
            this.latitudeGps = latitudeGps;
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

        public String getLongtitudeGps() {
            return longtitudeGps;
        }

        public void setLongtitudeGps(String longtitudeGps) {
            this.longtitudeGps = longtitudeGps;
        }

        public String getMsgContent() {
            return msgContent;
        }

        public void setMsgContent(String msgContent) {
            this.msgContent = msgContent;
        }

        public String getRecordTime() {
            return recordTime;
        }

        public void setRecordTime(String recordTime) {
            this.recordTime = recordTime;
        }

        public String getVariety() {
            return variety;
        }

        public void setVariety(String variety) {
            this.variety = variety;
        }

        public String getLatitudeBaidu() {
            return latitudeBaidu;
        }

        public void setLatitudeBaidu(String latitudeBaidu) {
            this.latitudeBaidu = latitudeBaidu;
        }

        public String getMsgTitle() {
            return msgTitle;
        }

        public void setMsgTitle(String msgTitle) {
            this.msgTitle = msgTitle;
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

        public String getReportTime() {
            return reportTime;
        }

        public void setReportTime(String reportTime) {
            this.reportTime = reportTime;
        }
    }

    public static class BatteryListBean {
        /**
         * deviceType : 1
         * livestockId : 199
         * sumuId : 1
         * bindNum : 1
         * qiId : 1
         * deviceNo : 0001180100000004
         * battery : 0.0
         * lastBindTime : 2018-04-04 10:37:18
         * variety : 100
         * createTime : 2018-04-04 10:37:18
         * ranchId : 13
         * livestockType : 1
         * bindStatus : 1
         */

        private String deviceType;
        private String livestockId;
        private String sumuId;
        private String bindNum;
        private String qiId;
        private String deviceNo;
        private String battery;
        private String lastBindTime;
        private String variety;
        private String createTime;
        private String ranchId;
        private String livestockType;
        private String bindStatus;

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getLivestockId() {
            return livestockId;
        }

        public void setLivestockId(String livestockId) {
            this.livestockId = livestockId;
        }

        public String getSumuId() {
            return sumuId;
        }

        public void setSumuId(String sumuId) {
            this.sumuId = sumuId;
        }

        public String getBindNum() {
            return bindNum;
        }

        public void setBindNum(String bindNum) {
            this.bindNum = bindNum;
        }

        public String getQiId() {
            return qiId;
        }

        public void setQiId(String qiId) {
            this.qiId = qiId;
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

        public String getLastBindTime() {
            return lastBindTime;
        }

        public void setLastBindTime(String lastBindTime) {
            this.lastBindTime = lastBindTime;
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

        public String getBindStatus() {
            return bindStatus;
        }

        public void setBindStatus(String bindStatus) {
            this.bindStatus = bindStatus;
        }
    }
}
