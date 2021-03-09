package com.rightcode.unite.network.socket.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonIgnore;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonObject
@ToString
public class SocketResult {

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @Getter
    @Setter
    @JsonField @JsonIgnore(ignorePolicy = JsonIgnore.IgnorePolicy.SERIALIZE_ONLY)
    Integer status;
    @Getter @Setter
    @JsonField @JsonIgnore(ignorePolicy = JsonIgnore.IgnorePolicy.SERIALIZE_ONLY)
    Boolean result;
    @Getter @Setter
    @JsonField @JsonIgnore(ignorePolicy = JsonIgnore.IgnorePolicy.SERIALIZE_ONLY)
    String message;

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    public boolean isSuccess() {
        return result;
    }

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
