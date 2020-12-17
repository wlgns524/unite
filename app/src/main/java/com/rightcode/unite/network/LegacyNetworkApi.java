package com.rightcode.unite.network;

import com.rightcode.unite.network.model.CommonResult;
import com.rightcode.unite.network.responser.version.VersionResponser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LegacyNetworkApi {

    //----------------------------------------------------------------------------------------------
    // auth
    //----------------------------------------------------------------------------------------------

    // 0.1.0 - 2020.12.07
    @GET("/v1/auth/existLoginId")
    Call<CommonResult> existLoginId(
            @Query("loginId") String loginId
    );

    // 0.1.0 - 2020.12.07
    @GET("/v1/auth/certificationNumberSMS")
    Call<CommonResult> certificationNumberSMS(
            @Query("tel") String tel
    );

    // 0.1.0 - 2020.12.07
    @GET("/v1/auth/confirm")
    Call<CommonResult> confirm(
            @Query("tel") String tel,
            @Query("confirm") String confirm
    );

    // 0.1.1 - 2020.12.14
    @POST("/v1/auth/join")
    Call<CommonResult> signUp(
    );


    // 0.1.0 - 2020.12.09
    @GET("/v1/visitors")
    Call<CommonResult> visitors(
    );

    // 0.1.0 - 2020.12.09
    @GET("/v1/version")
    Call<VersionResponser> version(
    );

}