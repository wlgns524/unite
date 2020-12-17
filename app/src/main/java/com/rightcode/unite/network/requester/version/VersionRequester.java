package com.rightcode.unite.network.requester.version;

import android.content.Context;

import com.rightcode.unite.network.requester.AbstractRequester;

import retrofit2.Call;

public class VersionRequester extends AbstractRequester {

    public VersionRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().version();

        return call;
    }
}
