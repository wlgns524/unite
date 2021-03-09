package com.rightcode.unite.network.responser.chatroom;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.unite.network.model.CommonResult;
import com.rightcode.unite.network.model.response.partner.Partner;
import com.rightcode.unite.network.socket.model.UserList;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class ChatRoomUserListResponser extends CommonResult {

    @JsonField
    ArrayList<UserList> list;
}
