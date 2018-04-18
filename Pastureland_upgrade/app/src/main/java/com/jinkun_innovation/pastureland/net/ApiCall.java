package com.jinkun_innovation.pastureland.net;

import com.jinkun_innovation.pastureland.bean.ClaimListBean;
import com.jinkun_innovation.pastureland.bean.ConfirmClaimBean;
import com.jinkun_innovation.pastureland.bean.LoginBean;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by yangxing on 2017/11/10.
 */

public interface ApiCall {

    /**
     * 1登录
     *
     * @param account
     * @param pwd
     * @return
     */
//    @POST("/wisdomRanch/rancher/login.do")
    @POST("/rancher/login.do")
    @FormUrlEncoded
    Observable<LoginBean> login(@Field("account") String account, @Field("pwd") String pwd);

    /**
     * 2获取认领列表
     *
     * @param token
     * @param userId
     * @param userName
     * @return
     */
//    @POST("/wisdomRanch/mobile/rancher/ClaimList.do")
    @POST("/mobile/rancher/ClaimList.do")
    @FormUrlEncoded
    Observable<ClaimListBean> getClaimList(@Field("token") String token, @Field("userId") String userId
            , @Field("userName") String userName);


    /**
     * 3确认按钮
     *
     * @param claimId
     * @param token
     * @param userId
     * @return
     */
//    @POST("/wisdomRanch/mobile/rancher/confirmClaim.do")
    @POST("/mobile/rancher/confirmClaim.do")
    @FormUrlEncoded
    Observable<ConfirmClaimBean> confireClaimList(@Field("claimId") String claimId, @Field("token") String token,
                                                  @Field("userId") String userId);
//4
    @Multipart
//    @POST("/wisdomRanch/manager/livestockERecode/createERecode.do")
    @POST("/manager/livestockERecode/createERecode.do")
    Observable<ConfirmClaimBean> uploadPhoto(@Part MultipartBody.Part file
            , @Part("deviceNo") RequestBody deviceNo
            , @Part("variety") RequestBody variety
            , @Part("phase") RequestBody phase
            , @Part("token") RequestBody token
            , @Part("userId") RequestBody userId
            , @Part("userName") RequestBody userName
            , @Part("companyId") RequestBody companyId);

//5
    @Multipart
//    @POST("/wisdomRanch/manager/livestockERecode/createERecode.do")
    @POST("/manager/livestockERecode/livephoto.do")
    Observable<ConfirmClaimBean> livephoto(@Part MultipartBody.Part file, @Part("userName") RequestBody userName
    );
}
