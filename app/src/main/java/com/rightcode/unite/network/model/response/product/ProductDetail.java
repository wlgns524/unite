package com.rightcode.unite.network.model.response.product;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductDetail {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @JsonField
    Long id;
    @JsonField
    String nation;
    @JsonField
    String area;
    @JsonField
    String category;
    @JsonField
    String name;
    @JsonField
    Long price;
    @JsonField
    String thumbnail;
    @JsonField
    String platform;
    @JsonField
    String link;
    @JsonField
    Float averageRate;
    @JsonField
    Boolean isWishMine;
    @JsonField
    ArrayList<String> productImages;
    @JsonField
    Integer klookPrice;
    @JsonField
    Integer myrealtripPrice;
    @JsonField
    Integer yanoljaPrice;
    @JsonField
    Integer reviewCount;
    @JsonField
    List<ProductLink> products;

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
