package com.rightcode.unite.network.requester.wish;

import android.content.Context;

import com.rightcode.unite.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class FavoriteRemoveRequester extends AbstractRequester {

    @Setter
    Long productId;

    public FavoriteRemoveRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().favoriteRemove(productId);

        return call;
    }
}
