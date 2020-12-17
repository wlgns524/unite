package com.rightcode.unite.network.model.request.user;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

import lombok.Setter;

@JsonObject
public class UserUpdate {

    @JsonField
    @Setter
    String password;
    @JsonField
    @Setter
    String firstName;
    @JsonField
    @Setter
    String lastName;
    @JsonField
    @Setter
    String nation;
    @JsonField
    @Setter
    String image;
    @JsonField
    @Setter
    List<String> interest;
}
