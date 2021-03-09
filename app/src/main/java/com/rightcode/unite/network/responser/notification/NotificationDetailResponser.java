package com.rightcode.unite.network.responser.notification;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.unite.network.model.CommonResult;
import com.rightcode.unite.network.model.response.notification.NotificationDetail;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class NotificationDetailResponser extends CommonResult {

    @JsonField
    NotificationDetail data;
}
