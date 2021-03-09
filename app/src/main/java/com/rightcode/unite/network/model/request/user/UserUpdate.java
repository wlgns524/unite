package com.rightcode.unite.network.model.request.user;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

import lombok.Setter;

@JsonObject
@Setter
public class UserUpdate {

    @JsonField
    String gender;
    @JsonField
    String name;
    @JsonField
    String birthDay;
//    @JsonField
//    Boolean eventPush;
//    @JsonField
//    Boolean chatPush;
}
