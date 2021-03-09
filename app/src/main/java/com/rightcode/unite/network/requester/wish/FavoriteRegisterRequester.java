package com.rightcode.unite.network.requester.wish;

import android.content.Context;

import com.rightcode.unite.network.requester.AbstractRequester;

import java.util.HashMap;

import lombok.Setter;
import retrofit2.Call;

public class FavoriteRegisterRequester extends AbstractRequester {

    @Setter
    Long productId;

    public FavoriteRegisterRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        HashMap<String, Long> map = new HashMap<>();
        map.put("productId", productId);
        Call call = networkManager.getApi().favoriteRegist(map);

        return call;
    }
}
