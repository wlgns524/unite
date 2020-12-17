package com.rightcode.unite.network.requester.visitors;

import android.content.Context;


import com.rightcode.unite.network.requester.AbstractRequester;

import retrofit2.Call;

public class VisitorsRequester extends AbstractRequester {

    public VisitorsRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().visitors();

        return call;
    }
}
