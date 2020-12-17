package com.rightcode.unite.network.responser.version;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.unite.network.model.CommonResult;
import com.rightcode.unite.network.model.response.version.Versions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class VersionResponser extends CommonResult {

    @JsonField(name = "data")
    Versions version;
}
