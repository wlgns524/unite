package com.rightcode.unite.network.requester.partner;

import android.content.Context;
import android.text.TextUtils;

import com.rightcode.unite.network.requester.AbstractRequester;

import java.util.HashMap;

import lombok.Setter;
import retrofit2.Call;

public class PartnerRegisterRequester extends AbstractRequester {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @Setter
    private String diff;
    @Setter
    private Long productId;
    @Setter
    private String address;
    @Setter
    private String addressDetail;
    @Setter
    private String latitude;
    @Setter
    private String longitude;
    @Setter
    private String startDate;
    @Setter
    private String endDate;
    @Setter
    private String age;
    @Setter
    private String gender;
    @Setter
    private String people;
    @Setter
    private String title;
    @Setter
    private String content;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public PartnerRegisterRequester(Context context) {
        super(context);
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    @Override
    protected Call genApi() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("diff", diff);

        if (productId != null)
            map.put("productId", productId);
        if (!TextUtils.isEmpty(address))
            map.put("address", address);
        if (!TextUtils.isEmpty(addressDetail))
            map.put("addressDetail", addressDetail);
        if (!TextUtils.isEmpty(latitude))
            map.put("latitude", latitude);
        if (!TextUtils.isEmpty(longitude))
            map.put("longitude", longitude);

        map.put("age", age);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("gender", gender);
        map.put("people", people);
        map.put("title", title);
        map.put("content", content);

        return networkManager.getApi().partnerRegister(map);
    }

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

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
