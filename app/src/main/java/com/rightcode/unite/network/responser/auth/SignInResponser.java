package com.rightcode.unite.network.responser.auth;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.unite.network.model.CommonResult;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class SignInResponser extends CommonResult {

    @JsonField
    String token;
    @JsonField
    String role;
}