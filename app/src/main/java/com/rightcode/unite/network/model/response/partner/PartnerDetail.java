package com.rightcode.unite.network.model.response.partner;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.unite.network.model.response.product.ProductDetail;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class PartnerDetail {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @JsonField
    Long id;
    @JsonField
    Long productId;
    @JsonField
    String userName;
    @JsonField
    String userImage;
    @JsonField
    String image;
    @JsonField
    String diff;
    @JsonField
    String address;
    @JsonField
    String age;
    @JsonField
    String addressDetail;
    @JsonField
    String latitude;
    @JsonField
    String longitude;
    @JsonField
    String startDate;
    @JsonField
    String endDate;
    @JsonField
    String gender;
    @JsonField
    Integer people;
    @JsonField
    Integer join;
    @JsonField
    String title;
    @JsonField
    String content;
    @JsonField
    Integer viewCount;
    @JsonField
    String createdAt;
    @JsonField
    ProductDetail product;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
