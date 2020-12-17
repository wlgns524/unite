package com.rightcode.unite.Activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.CommonUtil;
import com.rightcode.unite.Util.PreferenceUtil;
import com.rightcode.unite.Util.ToastUtil;
import com.rightcode.unite.network.requester.version.VersionRequester;
import com.rightcode.unite.network.requester.visitors.VisitorsRequester;
import com.rightcode.unite.network.responser.version.VersionResponser;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class FlashActivity extends BaseActivity {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    private Subscription subscription;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        ButterKnife.bind(this);
//        initialize();
        timer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }


    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    private void timer() {
        subscription = Observable.interval(3000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    public void call(Long aLong) {
                        Intent intent = new Intent(FlashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finishWithAnim();
                    }
                });
    }

    private void initialize() {
        String userId = PreferenceUtil.getInstance(FlashActivity.this).get(PreferenceUtil.PreferenceKey.UserId, null);
        String userPw = PreferenceUtil.getInstance(FlashActivity.this).get(PreferenceUtil.PreferenceKey.UserPw, null);

        if (!TextUtils.isEmpty(userId) && !TextUtils.isEmpty(userPw)) {
        }
    }

//    private void registerToken() {
//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w("Fetching FCM registration token failed", task.getException());
//                            return;
//                        }
//
//                        String token = task.getResult();
//                        notificationRegister(token);
//                        Log.d(token);
//                    }
//                });
//    }

//    private void version() {
//        showLoading();
//        VersionRequester versionRequester = new VersionRequester(FlashActivity.this);
//
//        request(versionRequester,
//                success -> {
//                    VersionResponser result = (VersionResponser) success;
//                    if (CommonUtil.getVersionCode(FlashActivity.this) <= result.getVersion().getAndroid()) {
//                        checkPermission();
////                        timer();
//                    } else {
//                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
//                        try {
//                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                        } catch (android.content.ActivityNotFoundException anfe) {
//                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//                        }
//                    }
//                    hideLoading();
//                }, fail -> {
//                    hideLoading();
//                    showServerErrorDialog(fail.getResultMsg());
//                });
//    }

//    private void visitor() {
//        VisitorsRequester visitorsRequester = new VisitorsRequester(FlashActivity.this);
//
//        request(visitorsRequester,
//                success -> {
//
//                }, fail -> {
//
//                });
//    }

    private void checkPermission() {
        Dexter.withActivity(FlashActivity.this)
                .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
//                        for (PermissionGrantedResponse response : report.getGrantedPermissionResponses()) {
//                        }
                        if (report.areAllPermissionsGranted()) {
                            timer();
                        } else {
                            ToastUtil.show(FlashActivity.this, "[유니트]이용을 위해서 권한을 허용해주세요");
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        Toast.makeText(FlashActivity.this, "[유니트]이용을 위해서 권한을 허용해주세요", Toast.LENGTH_SHORT).show();
                        Intent appDetail = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                        appDetail.addCategory(Intent.CATEGORY_DEFAULT);
                        appDetail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(appDetail);
                    }
                }).check();
    }

//    private void notificationRegister(String token) {
//        NotificationRegisterRequester requester = new NotificationRegisterRequester(getApplicationContext());
//        requester.setNotificationToken(token);
//
//        request(requester,
//                success -> {
//                    Log.e("Notification Register Success");
//                }, fail -> {
//                    Log.e("Notification Register Fail");
//                });
//    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
