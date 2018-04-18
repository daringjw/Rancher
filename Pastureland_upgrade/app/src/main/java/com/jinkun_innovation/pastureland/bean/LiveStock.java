package com.jinkun_innovation.pastureland.bean;

/**
 * Created by Guan on 2018/4/1.
 */

public class LiveStock {

    /**
     * code : success
     * livestock : {"acreage":"12000.0","address":"内蒙古自治区兴安盟科右前旗满族屯乡哈日白辛村","areaAddress":"内蒙古","bindStatus":"1","color":"2","createTime":"2018-03-06 09:54:45","deviceNo":"5652565646464657","deviceType":"2","health":"1","id":"409","imgUrl":"/jkimg/20180331/3604dcbe9e50d6130c06b56c3ac56ec9.jpg","imgUrlTime":"2018-03-31 21:34:06","introduce":"连江牧场位于内蒙古自治区兴安盟科右前旗满族屯乡绿水哈日白辛嘎查。牧场面积13000亩；该牧场距离乌兰浩特市区160公里；距离阿尔山市区100公里；距离外蒙边境线40公里；它毗邻乌布林草原和《狼图腾》中所描述的额伦草原；是科尔沁草原最美的牧场之一。该牧场属于丘陵地貌，四周群山环绕，山上长满各种中草药，像甘草、黄芪、桔梗、芍药漫山都是。而且，春、夏、秋三季山上开满山丹花、黄花、兰花等各种各样的野花，像花海一样美丽。而且草的种类繁多，比如阔叶草、尖叶草漫山遍野。而牧场的水属于山泉水，清澈甘甜，一年四季滋润着美丽的牧场。蓝蓝的天空像用牛乳洗过一样清新；洁白的云朵似新棉初弹。春天的空气充满清新；夏天的空气充满花香；秋天的空气满是芬芳；即使冬天的空气也令人神清气爽。一年四季阳光明媚、星光灿烂。从上世纪70年代以来，该牧场以牧为主，全 面发展。目前共饲养有各类牲畜2300余头（只）。其中，乌珠穆沁黑头羊1500只；西门塔尔牛70余头；蒙古马8匹；草原黑毛猪130余头；草原绿鸟鸡600多只；还有几百只野鸽子；喜鹊和麻雀更数不胜数。雄鹰、野鸡、飞龙、野猪更是牧场白桦林中的常客。而年轻的牧场主连江同志，更是蒙古族热情的小伙子，他正张开热情的双臂，手捧洁白的哈达，欢迎全国和全球各地的朋友来牧场观光旅游，探访牧场和谐的生态环境和几千年蒙古族古老的习俗。远方的客人可以在网上认领您们喜欢的牲畜；他们可作为一种感情和友谊的纽带；为您的小孩及家人认领一款草原上的小精灵。","introduceAnimalCount":"12","introduceAnimalType":"羊牛马猪鸡","introduceMeadow":"茂盛","introduceNativeProduct":"斑马","introduceRiver":"归流河","isClaimed":"0","isFenceClose":"0","lantitudeBaidu":"22.584501794103367","lifeTime":"96","livestockType":"3","longtitudeBaidu":"113.86693470767109","name":"连江牧场","numSort":"9999","phase":"2","qiId":"1","ranchId":"1","ranchImgUrl":"/jkimg/20180331/3604dcbe9e50d6130c06b56c3ac56ec9.jpg","rancherAccount":"17610893073","rancherName":"李连江","rancherPhone":"15024832451","status":"1","sumuId":"1","updateTime":"2018-04-01 09:53:06","variety":"301","videoUrl":"/jkimg/20180309/666.mp4","weight":"100.0"}
     * msg : 获取牲畜详情成功
     */

    private String code;
    private LivestockBean livestock;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LivestockBean getLivestock() {
        return livestock;
    }

    public void setLivestock(LivestockBean livestock) {
        this.livestock = livestock;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class LivestockBean {
        /**
         * acreage : 12000.0
         * address : 内蒙古自治区兴安盟科右前旗满族屯乡哈日白辛村
         * areaAddress : 内蒙古
         * bindStatus : 1
         * color : 2
         * createTime : 2018-03-06 09:54:45
         * deviceNo : 5652565646464657
         * deviceType : 2
         * health : 1
         * id : 409
         * imgUrl : /jkimg/20180331/3604dcbe9e50d6130c06b56c3ac56ec9.jpg
         * imgUrlTime : 2018-03-31 21:34:06
         * introduce : 连江牧场位于内蒙古自治区兴安盟科右前旗满族屯乡绿水哈日白辛嘎查。牧场面积13000亩；该牧场距离乌兰浩特市区160公里；距离阿尔山市区100公里；距离外蒙边境线40公里；它毗邻乌布林草原和《狼图腾》中所描述的额伦草原；是科尔沁草原最美的牧场之一。该牧场属于丘陵地貌，四周群山环绕，山上长满各种中草药，像甘草、黄芪、桔梗、芍药漫山都是。而且，春、夏、秋三季山上开满山丹花、黄花、兰花等各种各样的野花，像花海一样美丽。而且草的种类繁多，比如阔叶草、尖叶草漫山遍野。而牧场的水属于山泉水，清澈甘甜，一年四季滋润着美丽的牧场。蓝蓝的天空像用牛乳洗过一样清新；洁白的云朵似新棉初弹。春天的空气充满清新；夏天的空气充满花香；秋天的空气满是芬芳；即使冬天的空气也令人神清气爽。一年四季阳光明媚、星光灿烂。从上世纪70年代以来，该牧场以牧为主，全 面发展。目前共饲养有各类牲畜2300余头（只）。其中，乌珠穆沁黑头羊1500只；西门塔尔牛70余头；蒙古马8匹；草原黑毛猪130余头；草原绿鸟鸡600多只；还有几百只野鸽子；喜鹊和麻雀更数不胜数。雄鹰、野鸡、飞龙、野猪更是牧场白桦林中的常客。而年轻的牧场主连江同志，更是蒙古族热情的小伙子，他正张开热情的双臂，手捧洁白的哈达，欢迎全国和全球各地的朋友来牧场观光旅游，探访牧场和谐的生态环境和几千年蒙古族古老的习俗。远方的客人可以在网上认领您们喜欢的牲畜；他们可作为一种感情和友谊的纽带；为您的小孩及家人认领一款草原上的小精灵。
         * introduceAnimalCount : 12
         * introduceAnimalType : 羊牛马猪鸡
         * introduceMeadow : 茂盛
         * introduceNativeProduct : 斑马
         * introduceRiver : 归流河
         * isClaimed : 0
         * isFenceClose : 0
         * lantitudeBaidu : 22.584501794103367
         * lifeTime : 96
         * livestockType : 3
         * longtitudeBaidu : 113.86693470767109
         * name : 连江牧场
         * numSort : 9999
         * phase : 2
         * qiId : 1
         * ranchId : 1
         * ranchImgUrl : /jkimg/20180331/3604dcbe9e50d6130c06b56c3ac56ec9.jpg
         * rancherAccount : 17610893073
         * rancherName : 李连江
         * rancherPhone : 15024832451
         * status : 1
         * sumuId : 1
         * updateTime : 2018-04-01 09:53:06
         * variety : 301
         * videoUrl : /jkimg/20180309/666.mp4
         * weight : 100.0
         */

        //请求拍照，照片放这里
        public String livestock_img_url;
        public String video_url;

        //homeImgUrl
        public String homeImgUrl;
        public String livestockDetails;

        private String acreage;
        private String address;
        private String areaAddress;
        private String bindStatus;
        private String color;
        private String createTime;
        private String deviceNo;
        private String deviceType;
        private String health;
        private String id;
        private String imgUrl;
        private String imgUrlTime;
        private String introduce;
        private String introduceAnimalCount;
        private String introduceAnimalType;
        private String introduceMeadow;
        private String introduceNativeProduct;
        private String introduceRiver;
        private String isClaimed;
        private String isFenceClose;
        private String lantitudeBaidu;
        private String lifeTime;
        private String livestockType;
        private String longtitudeBaidu;
        private String name;
        private String numSort;
        private String phase;
        private String qiId;
        private String ranchId;
        private String ranchImgUrl;
        private String rancherAccount;
        private String rancherName;
        private String rancherPhone;
        private String status;
        private String sumuId;
        private String updateTime;
        private String variety;
        private String videoUrl;
        private String weight;

        public String getAcreage() {
            return acreage;
        }

        public void setAcreage(String acreage) {
            this.acreage = acreage;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAreaAddress() {
            return areaAddress;
        }

        public void setAreaAddress(String areaAddress) {
            this.areaAddress = areaAddress;
        }

        public String getBindStatus() {
            return bindStatus;
        }

        public void setBindStatus(String bindStatus) {
            this.bindStatus = bindStatus;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDeviceNo() {
            return deviceNo;
        }

        public void setDeviceNo(String deviceNo) {
            this.deviceNo = deviceNo;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getHealth() {
            return health;
        }

        public void setHealth(String health) {
            this.health = health;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getImgUrlTime() {
            return imgUrlTime;
        }

        public void setImgUrlTime(String imgUrlTime) {
            this.imgUrlTime = imgUrlTime;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getIntroduceAnimalCount() {
            return introduceAnimalCount;
        }

        public void setIntroduceAnimalCount(String introduceAnimalCount) {
            this.introduceAnimalCount = introduceAnimalCount;
        }

        public String getIntroduceAnimalType() {
            return introduceAnimalType;
        }

        public void setIntroduceAnimalType(String introduceAnimalType) {
            this.introduceAnimalType = introduceAnimalType;
        }

        public String getIntroduceMeadow() {
            return introduceMeadow;
        }

        public void setIntroduceMeadow(String introduceMeadow) {
            this.introduceMeadow = introduceMeadow;
        }

        public String getIntroduceNativeProduct() {
            return introduceNativeProduct;
        }

        public void setIntroduceNativeProduct(String introduceNativeProduct) {
            this.introduceNativeProduct = introduceNativeProduct;
        }

        public String getIntroduceRiver() {
            return introduceRiver;
        }

        public void setIntroduceRiver(String introduceRiver) {
            this.introduceRiver = introduceRiver;
        }

        public String getIsClaimed() {
            return isClaimed;
        }

        public void setIsClaimed(String isClaimed) {
            this.isClaimed = isClaimed;
        }

        public String getIsFenceClose() {
            return isFenceClose;
        }

        public void setIsFenceClose(String isFenceClose) {
            this.isFenceClose = isFenceClose;
        }

        public String getLantitudeBaidu() {
            return lantitudeBaidu;
        }

        public void setLantitudeBaidu(String lantitudeBaidu) {
            this.lantitudeBaidu = lantitudeBaidu;
        }

        public String getLifeTime() {
            return lifeTime;
        }

        public void setLifeTime(String lifeTime) {
            this.lifeTime = lifeTime;
        }

        public String getLivestockType() {
            return livestockType;
        }

        public void setLivestockType(String livestockType) {
            this.livestockType = livestockType;
        }

        public String getLongtitudeBaidu() {
            return longtitudeBaidu;
        }

        public void setLongtitudeBaidu(String longtitudeBaidu) {
            this.longtitudeBaidu = longtitudeBaidu;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumSort() {
            return numSort;
        }

        public void setNumSort(String numSort) {
            this.numSort = numSort;
        }

        public String getPhase() {
            return phase;
        }

        public void setPhase(String phase) {
            this.phase = phase;
        }

        public String getQiId() {
            return qiId;
        }

        public void setQiId(String qiId) {
            this.qiId = qiId;
        }

        public String getRanchId() {
            return ranchId;
        }

        public void setRanchId(String ranchId) {
            this.ranchId = ranchId;
        }

        public String getRanchImgUrl() {
            return ranchImgUrl;
        }

        public void setRanchImgUrl(String ranchImgUrl) {
            this.ranchImgUrl = ranchImgUrl;
        }

        public String getRancherAccount() {
            return rancherAccount;
        }

        public void setRancherAccount(String rancherAccount) {
            this.rancherAccount = rancherAccount;
        }

        public String getRancherName() {
            return rancherName;
        }

        public void setRancherName(String rancherName) {
            this.rancherName = rancherName;
        }

        public String getRancherPhone() {
            return rancherPhone;
        }

        public void setRancherPhone(String rancherPhone) {
            this.rancherPhone = rancherPhone;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSumuId() {
            return sumuId;
        }

        public void setSumuId(String sumuId) {
            this.sumuId = sumuId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getVariety() {
            return variety;
        }

        public void setVariety(String variety) {
            this.variety = variety;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }
    }
}
