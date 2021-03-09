package com.rightcode.unite.Activity.Setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.Nullable;

import com.google.firebase.messaging.FirebaseMessaging;
import com.rightcode.unite.Activity.Basic.BaseActivity;
import com.rightcode.unite.Fragment.TopFragment;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.FragmentUtil;
import com.rightcode.unite.Util.Log;
import com.rightcode.unite.Util.ToastUtil;
import com.rightcode.unite.network.requester.notification.NotificationDetailRequester;
import com.rightcode.unite.network.requester.notification.NotificationUpdateRequester;
import com.rightcode.unite.network.responser.notification.NotificationDetailResponser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends BaseActivity {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.swt_phone_notification)
    Switch swt_phone_notification;
    @BindView(R.id.swt_message_notification)
    Switch swt_message_notification;

    private TopFragment mTopFragment;
    private String token;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        ButterKnife.bind(this);
        initialize();
//        tokenCallback("");
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

    @OnClick({R.id.swt_phone_notification, R.id.swt_message_notification})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.swt_phone_notification:
            case R.id.swt_message_notification: {
                notificationUpdate();
                break;
            }
        }
    }

    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "푸시알림");
        mTopFragment.setTextColor(TopFragment.Menu.CENTER, R.color.black);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.ic_back);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, v -> finishWithAnim());

        registerToken();
    }

    private void registerToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    String token = task.getResult();
                    notificationDetail(token);
                    Log.d(token);
                });
    }

    private void notificationDetail(String token) {
        showLoading();
        NotificationDetailRequester requester = new NotificationDetailRequester(NotificationActivity.this);
        requester.setNotificationToken(token);

        request(requester,
                success -> {
                    NotificationDetailResponser result = (NotificationDetailResponser) success;
                    hideLoading();
                    if (result.getCode() == 200) {
                        this.token = result.getData().getNotificationToken();
                        swt_phone_notification.setChecked(result.getData().getActive());
                        swt_message_notification.setChecked(result.getData().getChatPush());
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void notificationUpdate() {
        showLoading();
        NotificationUpdateRequester requester = new NotificationUpdateRequester(NotificationActivity.this);
        requester.setNotificationToken(token);
        requester.setActive(swt_phone_notification.isChecked());
        requester.setChatPush(swt_message_notification.isChecked());

        request(requester,
                success -> {
                    hideLoading();
                    if (success.getCode() == 200) {
                        ToastUtil.show(NotificationActivity.this, "Has been changed");
                    } else {
                        showServerErrorDialog(success.getResultMsg());
                    }
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
