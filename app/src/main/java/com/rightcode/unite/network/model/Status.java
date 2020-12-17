package com.rightcode.unite.network.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.unite.network.error.ServerResultCode;

import lombok.Getter;
import lombok.ToString;

@JsonObject
@Getter @ToString
public class Status {
    @JsonField
    ServerResultCode code;
    @JsonField
    String title;
    @JsonField
    String msg;
}