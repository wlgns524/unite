package com.rightcode.unite.network.responser.partner;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.unite.network.model.CommonResult;
import com.rightcode.unite.network.model.response.partner.Partner;
import com.rightcode.unite.network.model.response.product.Product;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class PartnerListResponser extends CommonResult {

    @JsonField
    ArrayList<Partner> list;
}
