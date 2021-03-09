package com.rightcode.unite.network;

import com.rightcode.unite.network.model.CommonResult;
import com.rightcode.unite.network.model.request.user.UserUpdate;
import com.rightcode.unite.network.responser.auth.SignInResponser;
import com.rightcode.unite.network.responser.board.BoardDetailResponser;
import com.rightcode.unite.network.responser.board.BoardListResponser;
import com.rightcode.unite.network.responser.chatroom.ChatRoomUserListResponser;
import com.rightcode.unite.network.responser.notification.NotificationDetailResponser;
import com.rightcode.unite.network.responser.partner.PartnerDetailResponser;
import com.rightcode.unite.network.responser.partner.PartnerListResponser;
import com.rightcode.unite.network.responser.product.ProductDetailResponser;
import com.rightcode.unite.network.responser.product.ProductListResponser;
import com.rightcode.unite.network.responser.review.ReviewListResponser;
import com.rightcode.unite.network.responser.user.UserInfoResponser;
import com.rightcode.unite.network.responser.version.VersionResponser;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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


    // 0.1.0 - 2021.01.17
    @POST("/v1/auth/login")
    Call<SignInResponser> signIn(
            @Body Map<String, String> params
    );

    //----------------------------------------------------------------------------------------------
    // board
    //----------------------------------------------------------------------------------------------

    // 0.1.0 - 2021.01.18
    @GET("/v1/board/list")
    Call<BoardListResponser> boardList(
            @Query("diff") String diff
    );

    // 0.1.0 - 2021.01.18
    @GET("/v1/board/detail")
    Call<BoardDetailResponser> boardDetail(
            @Query("id") Long boardId
    );

    //----------------------------------------------------------------------------------------------
    // product
    //----------------------------------------------------------------------------------------------

    // 0.1.3 - 2021.01.27
    @GET("/v1/product/list")
    Call<ProductListResponser> productList(
            @Query("limit") Integer limit,
            @Query("startPrice") Integer startPrice,
            @Query("endPrice") Integer endPrice,
            @Query("area") String area,
            @Query("category") String category,
            @Query("search") String search,
            @Query("random") Boolean random,
            @Query("wishMine") Boolean wishMine
    );

    // 0.1.4 - 2021.02.23
    @GET("/v1/product/detail")
    Call<ProductDetailResponser> productDetail(
            @Query("id") Long id
    );

    //----------------------------------------------------------------------------------------------
    // review
    //----------------------------------------------------------------------------------------------

    // 0.1.0 - 2021.01.15
    @GET("/v1/review/list")
    Call<ReviewListResponser> reviewList(
            @Query("productId") Long productId
    );

    //----------------------------------------------------------------------------------------------
    // visitors
    //----------------------------------------------------------------------------------------------

    // 0.1.0 - 2020.12.09
    @GET("/v1/visitors")
    Call<CommonResult> visitors(
    );

    //----------------------------------------------------------------------------------------------
    // version
    //----------------------------------------------------------------------------------------------

    // 0.1.0 - 2020.12.09
    @GET("/v1/version")
    Call<VersionResponser> version(
    );

    //----------------------------------------------------------------------------------------------
    // user
    //----------------------------------------------------------------------------------------------

    // 0.1.2 - 2021.01.30
    @GET("v1/user/info")
    Call<UserInfoResponser> userInfo(
    );

    // 0.1.0 - 2021.01.17
    @Multipart
    @POST("/v1/user/file/register")
    Call<CommonResult> profileImage(
            @Part MultipartBody.Part... image
    );

    // 0.1.0 - 2021.01.17
    @DELETE("/v1/user/file/remove")
    Call<CommonResult> profileImageDelete(
    );

    // 0.1.0 - 2021.01.30
    @PUT("/v1/user/update")
    Call<CommonResult> userUpdate(
            @Body UserUpdate userUpdate
    );

    //----------------------------------------------------------------------------------------------
    // inquiry
    //----------------------------------------------------------------------------------------------

    // 0.1.0- 2021.01.30
    @POST("/v1/inquiry/register")
    Call<CommonResult> inquiryRegister(
            @Body Map<String, String> params
    );


    //----------------------------------------------------------------------------------------------
    // partner
    //----------------------------------------------------------------------------------------------

    // 0.1.1 - 2021.01.23
    @POST("/v1/partner/register")
    Call<CommonResult> partnerRegister(
            @Body Map<String, Object> params
    );

    // 0.1.0 - 2021.01.17
    @GET("/v1/partner/list")
    Call<PartnerListResponser> partnerList(
            @Query("productId") Long productId,
            @Query("limit") Integer limit,
            @Query("search") String search,
            @Query("sort") String sort
    );

    // 0.1.2 - 2021.01.30
    @GET("/v1/partner/detail")
    Call<PartnerDetailResponser> partnerDetail(
            @Query("id") Long id
    );

    // 0.1.0 - 2021.02.02
    @GET("/v1/partner/check")
    Call<CommonResult> partnerCheck(
            @Query("id") Long id
    );

    //----------------------------------------------------------------------------------------------
    // chatMessage
    //----------------------------------------------------------------------------------------------

    // 0.1.0 - 2021.02.03
    @Multipart
    @POST("/v1/chatMessage/file/register")
    Call<CommonResult> chatFileRegister(
            @Query("partnerId") Long partnerId,
            @Part MultipartBody.Part... image
    );

    //----------------------------------------------------------------------------------------------
    // chatRoom
    //----------------------------------------------------------------------------------------------

    // 0.1.0 - 2021.02.03
    @GET("/v1/chatRoom/user/list")
    Call<ChatRoomUserListResponser> chatRoomUserList(
            @Query("partnerId") Long partnerId
    );

    //----------------------------------------------------------------------------------------------
    // wish
    //----------------------------------------------------------------------------------------------

    // 0.1.0 - 2021.01.27
    @POST("/v1/wish/register")
    Call<CommonResult> favoriteRegist(
            @Body Map<String, Long> params
    );

    // 0.1.0 - 2021.01.27
    @DELETE("/v1/wish/remove")
    Call<CommonResult> favoriteRemove(
            @Query("productId") Long productId
    );


    //----------------------------------------------------------------------------------------------
    // notification
    //----------------------------------------------------------------------------------------------

    // 0.1.0- 2021.01.30
    @POST("/v1/notification/register")
    Call<CommonResult> notificationRegister(
            @Body Map<String, String> params
    );

    // 0.1.0- 2021.02.03
    @PUT("/v1/notification/update")
    Call<CommonResult> notificationUpdate(
            @Query("notificationToken") String notificationToken,
            @Query("active") Boolean active,
            @Query("chatPush") Boolean chatPush
    );

    // 0.1.0 - 2021.02.03
    @GET("/v1/notification/detail")
    Call<NotificationDetailResponser> notificationDetail(
            @Query("notificationToken") String notificationToken
    );

}