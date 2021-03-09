package com.rightcode.unite.network.requester.notification;

import android.content.Context;

import com.rightcode.unite.network.requester.AbstractRequester;

import java.util.HashMap;

import lombok.Setter;
import retrofit2.Call;

public class NotificationRegisterRequester extends AbstractRequester {

    @Setter
    private String notificationToken;

    public NotificationRegisterRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        HashMap<String, String> map = new HashMap<>();
        map.put("notificationToken", notificationToken);

        return networkManager.getApi().notificationRegister(map);
    }
}
