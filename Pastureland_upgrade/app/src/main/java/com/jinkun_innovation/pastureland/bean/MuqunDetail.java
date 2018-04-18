package com.jinkun_innovation.pastureland.bean;

/**
 * Created by Guan on 2018/4/3.
 */

public class MuqunDetail {


    /**
     * msg : 获取牧场详情成功
     * ranch : {"ranchImgUrl":"/jkimg/20180403/f951aabc03415a94c1617d5383ca3cff.jpg","introduceRiver":"归流河","address":"内蒙古呼和浩特市和林格尔县大红城乡大红城村","rancherPhone":"18126177726","sumuId":"1","introduce":"我们的牧场位于内蒙古呼和浩特市和林格尔县大红城乡大红城村，成立于2013年，为了响应政府的政策，现在是农民合作社，不过我还是喜欢称它为牧场。和林格尔在蒙语中是\u201c二十户人家\u201d的意思，自古就是人烟稀少，环境优质，适合发展养殖业。为了坚持传统放牧，自然生长，我们将大红城村南山作为草场，该座山属于草甸草原和灌木草原，面积广阔，山上植被丰富，非常适合放牧。","qiId":"1","rancherName":"常盼盼","acreage":"200.0","updateTime":"2018-04-04 17:44:58","rancherAccount":"18126177726","longtitudeBaidu":"113.876508","introduceMeadow":"茂盛","imgUrl":"/jkimg/20180404/b2143c1b1b45863ee04c5b07a4b49262.png","introduceAnimalCount":"232","introduceNativeProduct":"羊奶牛奶","createTime":"2018-03-29 14:38:45","ranchId":"11","introduceAnimalType":"羊牛马猪骆驼","name":"盼盼牧场","lantitudeBaidu":"22.60329","numSort":"9999","status":"1"}
     * code : success
     */

    private String msg;
    private RanchBean ranch;
    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RanchBean getRanch() {
        return ranch;
    }

    public void setRanch(RanchBean ranch) {
        this.ranch = ranch;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class RanchBean {
        /**
         * ranchImgUrl : /jkimg/20180403/f951aabc03415a94c1617d5383ca3cff.jpg
         * introduceRiver : 归流河
         * address : 内蒙古呼和浩特市和林格尔县大红城乡大红城村
         * rancherPhone : 18126177726
         * sumuId : 1
         * introduce : 我们的牧场位于内蒙古呼和浩特市和林格尔县大红城乡大红城村，成立于2013年，为了响应政府的政策，现在是农民合作社，不过我还是喜欢称它为牧场。和林格尔在蒙语中是“二十户人家”的意思，自古就是人烟稀少，环境优质，适合发展养殖业。为了坚持传统放牧，自然生长，我们将大红城村南山作为草场，该座山属于草甸草原和灌木草原，面积广阔，山上植被丰富，非常适合放牧。
         * qiId : 1
         * rancherName : 常盼盼
         * acreage : 200.0
         * updateTime : 2018-04-04 17:44:58
         * rancherAccount : 18126177726
         * longtitudeBaidu : 113.876508
         * introduceMeadow : 茂盛
         * imgUrl : /jkimg/20180404/b2143c1b1b45863ee04c5b07a4b49262.png
         * introduceAnimalCount : 232
         * introduceNativeProduct : 羊奶牛奶
         * createTime : 2018-03-29 14:38:45
         * ranchId : 11
         * introduceAnimalType : 羊牛马猪骆驼
         * name : 盼盼牧场
         * lantitudeBaidu : 22.60329
         * numSort : 9999
         * status : 1
         */

        private String ranchImgUrl;
        private String introduceRiver;
        private String address;
        private String rancherPhone;
        private String sumuId;
        private String introduce;
        private String qiId;
        private String rancherName;
        private String acreage;
        private String updateTime;
        private String rancherAccount;
        private String longtitudeBaidu;
        private String introduceMeadow;
        private String imgUrl;
        private String introduceAnimalCount;
        private String introduceNativeProduct;
        private String createTime;
        private String ranchId;
        private String introduceAnimalType;
        private String name;
        private String lantitudeBaidu;
        private String numSort;
        private String status;

        public String getRanchImgUrl() {
            return ranchImgUrl;
        }

        public void setRanchImgUrl(String ranchImgUrl) {
            this.ranchImgUrl = ranchImgUrl;
        }

        public String getIntroduceRiver() {
            return introduceRiver;
        }

        public void setIntroduceRiver(String introduceRiver) {
            this.introduceRiver = introduceRiver;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getRancherPhone() {
            return rancherPhone;
        }

        public void setRancherPhone(String rancherPhone) {
            this.rancherPhone = rancherPhone;
        }

        public String getSumuId() {
            return sumuId;
        }

        public void setSumuId(String sumuId) {
            this.sumuId = sumuId;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getQiId() {
            return qiId;
        }

        public void setQiId(String qiId) {
            this.qiId = qiId;
        }

        public String getRancherName() {
            return rancherName;
        }

        public void setRancherName(String rancherName) {
            this.rancherName = rancherName;
        }

        public String getAcreage() {
            return acreage;
        }

        public void setAcreage(String acreage) {
            this.acreage = acreage;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getRancherAccount() {
            return rancherAccount;
        }

        public void setRancherAccount(String rancherAccount) {
            this.rancherAccount = rancherAccount;
        }

        public String getLongtitudeBaidu() {
            return longtitudeBaidu;
        }

        public void setLongtitudeBaidu(String longtitudeBaidu) {
            this.longtitudeBaidu = longtitudeBaidu;
        }

        public String getIntroduceMeadow() {
            return introduceMeadow;
        }

        public void setIntroduceMeadow(String introduceMeadow) {
            this.introduceMeadow = introduceMeadow;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getIntroduceAnimalCount() {
            return introduceAnimalCount;
        }

        public void setIntroduceAnimalCount(String introduceAnimalCount) {
            this.introduceAnimalCount = introduceAnimalCount;
        }

        public String getIntroduceNativeProduct() {
            return introduceNativeProduct;
        }

        public void setIntroduceNativeProduct(String introduceNativeProduct) {
            this.introduceNativeProduct = introduceNativeProduct;
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

        public String getIntroduceAnimalType() {
            return introduceAnimalType;
        }

        public void setIntroduceAnimalType(String introduceAnimalType) {
            this.introduceAnimalType = introduceAnimalType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLantitudeBaidu() {
            return lantitudeBaidu;
        }

        public void setLantitudeBaidu(String lantitudeBaidu) {
            this.lantitudeBaidu = lantitudeBaidu;
        }

        public String getNumSort() {
            return numSort;
        }

        public void setNumSort(String numSort) {
            this.numSort = numSort;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
