package com.rightcode.unite.network.requester.chatroom;

import android.content.Context;

import com.rightcode.unite.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class ChatRoomUserListRequester extends AbstractRequester {

    @Setter
    private Long partnerId;

    public ChatRoomUserListRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().chatRoomUserList(partnerId);

        return call;
    }
}
