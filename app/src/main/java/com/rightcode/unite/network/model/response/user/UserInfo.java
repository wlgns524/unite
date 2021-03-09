package com.rightcode.unite.network.model.response.user;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.bluelinelabs.logansquare.annotation.OnJsonParseComplete;
import com.rightcode.unite.Util.DataEnums;
import com.rightcode.unite.network.typeConverter.ProviderConverter;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfo {

    @JsonField
    Long id;
    @JsonField(typeConverter = ProviderConverter.class)
    DataEnums.Provider provider;
    @JsonField
    String image;
    @JsonField
    String name;
    @JsonField
    String role;
    @JsonField
    String gender;
    @JsonField
    String birthDay;
    @JsonField
    Boolean active;
//    @JsonField
//    Boolean eventPush;
//    @JsonField
//    Boolean chatPush;

    @OnJsonParseComplete
    void OnParseComplete() {
    }

}