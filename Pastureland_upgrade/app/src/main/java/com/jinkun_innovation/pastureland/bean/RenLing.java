package com.jinkun_innovation.pastureland.bean;

import java.util.List;

/**
 * Created by Guan on 2018/3/16.
 */

public class RenLing {


    /**
     * msg : 获取发布到认领表牲畜信息成功
     * livestockList : [{"livestockId":"10","color":"2","birthTime":"2018-04-11 16:14:31","livestockName":"蒙古马","businesstype":"3","variety":"301","price":"20000","livestockType":"3","livestockDetails":"","lifeTime":"96","isClaimed":"0","characteristics":"头大颈短、体魄强健、胸宽鬂长、皮厚毛粗","orderNo":"20180411160107591002","sumuId":"1","qiId":"1","weight":"10.0","health":"1","isVideo":"1","deviceNo":"2345234524653262","imgUrl":"/jkimg/20180411/2ba88eb27026be616c4502c3e33812fd.jpg","isPhotographic":"1","createTime":"2018-02-11 16:14:29","ranchId":"16","tradeStatus":"0","name":"内蒙古乌拉克斯加牧场"},{"livestockId":"9","color":"2","birthTime":"2018-04-11 15:54:49","livestockName":"蒙古马","businesstype":"3","variety":"301","price":"20000","nickname":"user1","livestockType":"3","livestockDetails":"","cellphone":"17610893073","lifeTime":"96","isClaimed":"1","memberId":"7","characteristics":"头大颈短、体魄强健、胸宽鬂长、皮厚毛粗","finishTime":"2018-04-01 00:00:00","sumuId":"1","qiId":"1","weight":"40.0","health":"1","isVideo":"1","deviceNo":"2345234524653261","claimTime":"2018-04-01 00:00:00","cellPhoneNum":"17610893073","imgUrl":"/jkimg/20180411/7ebd82f1895976cf1cff9dde96b943a2.jpg","isPhotographic":"1","createTime":"2017-11-11 15:54:48","ranchId":"16","name":"内蒙古乌拉克斯加牧场"},{"livestockId":"8","color":"2","birthTime":"2018-04-11 15:53:47","livestockName":"草原黑毛猪","businesstype":"3","variety":"401","price":"600","livestockType":"4","livestockDetails":"","lifeTime":"24","isClaimed":"0","characteristics":"体驱较长、体质健壮、耳大下垂、肉质细腻","orderNo":"20180410120438203008","sumuId":"1","qiId":"1","weight":"50.0","health":"1","isVideo":"1","deviceNo":"2345234524653265","imgUrl":"/jkimg/20180411/f60ffb11ce79f2b0885b50af44ca9f11.jpg","isPhotographic":"1","createTime":"2017-08-11 15:53:45","ranchId":"16","tradeStatus":"0","name":"内蒙古乌拉克斯加牧场"},{"livestockId":"6","color":"2","birthTime":"2018-04-10 10:57:52","livestockName":"山羊","businesstype":"3","variety":"101","price":"800","nickname":"user1","livestockType":"1","livestockDetails":"","cellphone":"17610893073","lifeTime":"24","isClaimed":"1","memberId":"7","characteristics":"山羊灵活，身体脂肪少，耐力较好","finishTime":"2018-04-01 00:00:00","orderNo":"20180410112937689007","sumuId":"1","qiId":"1","weight":"10.0","health":"1","isVideo":"1","deviceNo":"6012333555222656","claimTime":"2018-04-01 00:00:00","cellPhoneNum":"17610893073","imgUrl":"/jkimg/20180410/80175949574c1b5e6f64770dfe6352db.jpg","isPhotographic":"1","createTime":"2018-02-10 10:57:52","ranchId":"16","tradeStatus":"3","name":"内蒙古乌拉克斯加牧场"},{"livestockId":"5","color":"2","birthTime":"2018-04-10 10:55:58","livestockName":"西门塔尔牛","businesstype":"3","videoUrl":"/jkimg/20180411/c3a8d91234a9a51a1a3607c4f9c5b02d.mp4","variety":"201","price":"10000","nickname":"杨兴","livestockType":"2","livestockDetails":"","cellphone":"13501138453","lifeTime":"72","videoTime":"2018-04-11 00:41:25","isClaimed":"1","photographicTime":"2018-04-10 20:31:11","memberId":"8","characteristics":"体格大、生长快、肌肉多、脂肪少、产奶多","finishTime":"2018-04-01 00:00:00","sumuId":"1","qiId":"1","weight":"10.0","health":"1","isVideo":"0","deviceNo":"0012333555222655","claimTime":"2018-04-01 00:00:00","cellPhoneNum":"13501138453","imgUrl":"/jkimg/20180410/c088a43d9c1fd7c1547d29d1c43330d7.jpg","livestockImgUrl":"/jkimg/20180411/5dc4819d30ef857cc2cba3fa6615b321.jpg","isPhotographic":"1","createTime":"2018-02-10 10:55:56","ranchId":"16","name":"内蒙古乌拉克斯加牧场"}]
     * code : success
     */

    private String msg;
    private String code;
    private List<LivestockListBean> livestockList;

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

    public List<LivestockListBean> getLivestockList() {
        return livestockList;
    }

    public void setLivestockList(List<LivestockListBean> livestockList) {
        this.livestockList = livestockList;
    }

    public static class LivestockListBean {
        /**
         * livestockId : 10
         * color : 2
         * birthTime : 2018-04-11 16:14:31
         * livestockName : 蒙古马
         * businesstype : 3
         * variety : 301
         * price : 20000
         * livestockType : 3
         * livestockDetails :
         * lifeTime : 96
         * isClaimed : 0
         * characteristics : 头大颈短、体魄强健、胸宽鬂长、皮厚毛粗
         * orderNo : 20180411160107591002
         * sumuId : 1
         * qiId : 1
         * weight : 10.0
         * health : 1
         * isVideo : 1
         * deviceNo : 2345234524653262
         * imgUrl : /jkimg/20180411/2ba88eb27026be616c4502c3e33812fd.jpg
         * isPhotographic : 1
         * createTime : 2018-02-11 16:14:29
         * ranchId : 16
         * tradeStatus : 0
         * name : 内蒙古乌拉克斯加牧场
         * nickname : user1
         * cellphone : 17610893073
         * memberId : 7
         * finishTime : 2018-04-01 00:00:00
         * claimTime : 2018-04-01 00:00:00
         * cellPhoneNum : 17610893073
         * videoUrl : /jkimg/20180411/c3a8d91234a9a51a1a3607c4f9c5b02d.mp4
         * videoTime : 2018-04-11 00:41:25
         * photographicTime : 2018-04-10 20:31:11
         * livestockImgUrl : /jkimg/20180411/5dc4819d30ef857cc2cba3fa6615b321.jpg
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
        private String lifeTime;
        private String isClaimed;
        private String characteristics;
        private String orderNo;
        private String sumuId;
        private String qiId;
        private String weight;
        private String health;
        private String isVideo;
        private String deviceNo;
        private String imgUrl;
        private String isPhotographic;
        private String createTime;
        private String ranchId;
        private String tradeStatus;
        private String name;
        private String nickname;
        private String cellphone;
        private String memberId;
        private String finishTime;
        private String claimTime;
        private String cellPhoneNum;
        private String videoUrl;
        private String videoTime;
        private String photographicTime;
        private String livestockImgUrl;

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

        public String getLifeTime() {
            return lifeTime;
        }

        public void setLifeTime(String lifeTime) {
            this.lifeTime = lifeTime;
        }

        public String getIsClaimed() {
            return isClaimed;
        }

        public void setIsClaimed(String isClaimed) {
            this.isClaimed = isClaimed;
        }

        public String getCharacteristics() {
            return characteristics;
        }

        public void setCharacteristics(String characteristics) {
            this.characteristics = characteristics;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
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

        public String getTradeStatus() {
            return tradeStatus;
        }

        public void setTradeStatus(String tradeStatus) {
            this.tradeStatus = tradeStatus;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getCellphone() {
            return cellphone;
        }

        public void setCellphone(String cellphone) {
            this.cellphone = cellphone;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(String finishTime) {
            this.finishTime = finishTime;
        }

        public String getClaimTime() {
            return claimTime;
        }

        public void setClaimTime(String claimTime) {
            this.claimTime = claimTime;
        }

        public String getCellPhoneNum() {
            return cellPhoneNum;
        }

        public void setCellPhoneNum(String cellPhoneNum) {
            this.cellPhoneNum = cellPhoneNum;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getVideoTime() {
            return videoTime;
        }

        public void setVideoTime(String videoTime) {
            this.videoTime = videoTime;
        }

        public String getPhotographicTime() {
            return photographicTime;
        }

        public void setPhotographicTime(String photographicTime) {
            this.photographicTime = photographicTime;
        }

        public String getLivestockImgUrl() {
            return livestockImgUrl;
        }

        public void setLivestockImgUrl(String livestockImgUrl) {
            this.livestockImgUrl = livestockImgUrl;
        }
    }
}
