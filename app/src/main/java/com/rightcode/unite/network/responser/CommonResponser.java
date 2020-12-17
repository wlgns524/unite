package com.rightcode.unite.network.responser;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.unite.network.model.CommonResult;

import lombok.Getter;


@JsonObject
public class CommonResponser {
    @JsonField
    @Getter
    CommonResult commonResult;

}
