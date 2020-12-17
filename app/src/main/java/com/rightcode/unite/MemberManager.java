package com.rightcode.unite;

import android.content.Context;
import android.location.Location;

import com.gun0912.tedpermission.util.ObjectUtils;
import com.rightcode.unite.RxJava.RxBus;
import com.rightcode.unite.RxJava.RxEvent.LoginEvent;
import com.rightcode.unite.Util.GPSUtil;
import com.rightcode.unite.Util.PreferenceUtil;

import java.util.ArrayList;

public class MemberManager {
    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------
    private volatile static MemberManager sMemberManager;

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------
    private Context context;


    //----------------------------------------------------------------------------------------------
    // get / set
    //----------------------------------------------------------------------------------------------
    public boolean isLogin() {
        return !ObjectUtils.isEmpty("");
    }

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    private MemberManager(Context context) {
        if (context == null) {
            return;
        }
        this.context = context.getApplicationContext();
    }

    public static MemberManager getInstance(Context context) {
        if (sMemberManager == null) {
            synchronized (MemberManager.class) {
                if (sMemberManager == null) {
                    sMemberManager = new MemberManager(context);
                }
            }
        }
        return sMemberManager;
    }

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

//    public void updateLogInInfo(UserInfo userInfo) {
//        this.userInfo = userInfo;
//    }

    public void userLogin(String userId, String userPw) {
        PreferenceUtil.getInstance(context).put(PreferenceUtil.PreferenceKey.UserId, userId);
        PreferenceUtil.getInstance(context).put(PreferenceUtil.PreferenceKey.UserPw, userPw);
        RxBus.send(new LoginEvent(true));
    }

    public void userLogout() {
        PreferenceUtil.getInstance(context).put(PreferenceUtil.PreferenceKey.UserId, "");
        PreferenceUtil.getInstance(context).put(PreferenceUtil.PreferenceKey.UserPw, "");
        RxBus.send(new LoginEvent(false));
    }


//    public UserInfo getUserInfo() {
//        return userInfo;
//    }

    public Location getLocation() {
        GPSUtil gpsUtil = new GPSUtil(context);
        return gpsUtil.getLocation();
    }


    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------


}

