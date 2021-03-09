package com.rightcode.unite.network.responser.review;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.unite.network.model.CommonResult;
import com.rightcode.unite.network.model.response.product.Product;
import com.rightcode.unite.network.model.response.review.Review;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class ReviewListResponser extends CommonResult {

    @JsonField
    ArrayList<Review> list;
}
