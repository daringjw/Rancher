package com.jinkun_innovation.pastureland.common;


/**
 * Created by Guan on 2018/3/13.
 */

public class Constants {

    //朱之用
//    public static final String BASE_URL = "http://192.168.50.215:8080";

//    public static final String IP = "222.249.165.94";
//    public static final String PORT = ":10100";


//    public static final String IP = "192.168.50.215";
//    public static final String PORT = ":8080";

    public static final String IP = "182.92.3.109";
    public static final String PORT = ":10100";

//    public static final String IP = "116.95.255.211";
//    public static final String PORT = ":10100";


    //测试服务器
    public static final String BASE_URL = "http://" + IP + PORT;


    //内部正式服务器
    //http://182.92.3.109:10100 testranch 12345678 这是109访问地址跟用户名密码
//    public static final String BASE_URL = "http://182.92.3.109:10100";

    //客户服务器
//    public static final String BASE_URL  ="http://116.95.255.211:10120";

    public static final int ranchID = 1;

    public static final String token = "ce5cc971-5904-4454-b639-cb8d48215963";


    //牧场主找回密码
    //1)点击发送验证码：
    public static final String VERIFY_CODE = BASE_URL + "/mobleClaim/txCode.do";

    //2)修改密码:
    public static final String MODIFY_PASSWORD = BASE_URL + "/ranchLogin/backPass.do";

    //登录:
    public static final String LOGIN = BASE_URL + "/ranchLogin/login.do";


    //发布认领
    //判断是被是否被绑定
    public static final String ISDEVICEBINDED = BASE_URL + "/releaseLivestocks/isDeviceBinded.do";


    //1、查看发布情况selectLivestock.do，未发布执行release.do
    public static final String SELECT_LIVE_STOCK = BASE_URL + "/releaseLivestocks/selectLivestock.do";


    //根据字典表类型选字典表品种(牲畜扫描时选类型时调用)
    public static final String SELECTVARIETY = BASE_URL + "/releaseLivestocks/selectVariety.do";

    //直接发布到认领表接口
    public static final String RELEASE = BASE_URL + "/releaseLivestocks/release.do";

    //打疫苗登记-新增牲畜
    public static final String SAVELIVESTOCK = BASE_URL + "/releaseLivestocks/saveLivestock.do";


    //重新发布到牲畜认领表
    public static final String IS_CLAIMED = BASE_URL + "/releaseLivestocks/isClaimed.do";

    //获取已发布牲畜信息
    public static final String LIVE_STOCK_CLAIM_LIST = BASE_URL + "/releaseLivestocks/livestockClaimList.do";

    //剪毛
    public static final String SHEARING = BASE_URL + "/releaseLivestocks/shearing.do";

    //牧场拍生活照和视频
    public static final String RANCHIMGVIDEO = BASE_URL + "/releaseLivestocks/ranchImgVideo.do";

    //牧场详情
    public static final String RANCH = BASE_URL + "/releaseLivestocks/ranch.do";

    //更新牧场
    public static final String UPDRANCH = BASE_URL + "/releaseLivestocks/updRanch.do";

    //牲畜详情
    public static final String LIVESTOCK = BASE_URL + "/releaseLivestocks/livestock.do";

    //设备消息
    public static final String DEVICEMSG = BASE_URL + "/releaseLivestocks/deviceMsg.do";

    //牧场主处理拍照和视频请求
    public static final String updLivestockClaim = BASE_URL + "/releaseLivestocks/updLivestockClaim.do";


    //设备解绑（解绑之前要弹出确认提示框）
    public static final String DOUNBINDDEVICE = BASE_URL + "/releaseLivestocks/doUnBindDevice.do";

    //个人信息
    //1）查询个人信息
    public static final String ADMINLIST = BASE_URL + "/adminMember/adminList.do";

    //2）更新个人信息
    public static final String UPDADMIN = BASE_URL + "/adminMember/updAdmin.do";

    //文件上传（图片、视频） /adminMember/headImgUrl.do
    public static final String HEADIMGURL = BASE_URL + "/adminMember/headImgUrl.do";

    //获取牧场牲畜类型和每种类型牲畜的数量
    public static final String QUERYTYPEANDSUM = BASE_URL + "/releaseLivestocks/queryTypeAndSum.do";

    //通过牲畜类型查询所有牲畜
    public static final String QUERYLIVESTOCKVARIETYLIST = BASE_URL + "/releaseLivestocks/queryLivestockVarietyList.do";

    //电子档案
    public static final String DianziDangan = BASE_URL + "/claimLivestock/getArchivesElectronicURL.do";

    //牧场主确认订单支付
    public static final String orderPay = BASE_URL + "/releaseLivestocks/orderPay.do";

    //牲畜删除
    public static final String delLivestock= BASE_URL + "/releaseLivestocks/delLivestock.do";



}
