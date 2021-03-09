package com.rightcode.unite.network.responser.user;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.unite.network.model.CommonResult;
import com.rightcode.unite.network.model.response.review.Review;
import com.rightcode.unite.network.model.response.user.UserInfo;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfoResponser extends CommonResult {

    @JsonField
    UserInfo data;
}
