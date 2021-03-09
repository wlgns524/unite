package com.rightcode.unite.network.requester.user;

import android.content.Context;

import com.rightcode.unite.network.model.request.user.UserUpdate;
import com.rightcode.unite.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class UserUpdateRequester extends AbstractRequester {

    @Setter
    private UserUpdate userUpdate;

    public UserUpdateRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().userUpdate(userUpdate);

        return call;
    }
}
