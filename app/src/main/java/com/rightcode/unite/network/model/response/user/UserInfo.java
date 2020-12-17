package com.rightcode.unite.network.model.response.user;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.bluelinelabs.logansquare.annotation.OnJsonParseComplete;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfo {

    @JsonField
    Integer id;
    @JsonField
    String tel;
    @JsonField
    String loginId;
    @JsonField
    String firstName;
    @JsonField
    String lastName;
    @JsonField
    String nation;
    @JsonField
    String image;
    @JsonField
    Boolean active;
    @JsonField
    List<String> interest;

    @OnJsonParseComplete
    void OnParseComplete() {
    }

}