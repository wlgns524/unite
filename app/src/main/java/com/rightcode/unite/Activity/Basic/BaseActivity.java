package com.rightcode.unite.Activity.Basic;


import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.rightcode.unite.Dialog.CommonDialog;
import com.rightcode.unite.Dialog.LoadingDialog;
import com.rightcode.unite.Interface.CommonViewInterface;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.AlertUtil;
import com.rightcode.unite.Util.CommonUtil;
import com.rightcode.unite.Util.Log;
import com.rightcode.unite.network.NetworkBridge;
import com.rightcode.unite.network.error.ErrorType;
import com.rightcode.unite.network.error.ServerResultCode;
import com.rightcode.unite.network.model.CommonResult;
import com.rightcode.unite.network.requester.CommonApiRequester;

import rx.functions.Action1;

public class BaseActivity extends AppCompatActivity implements CommonViewInterface {


//    private final int ERROR_LEVEL_1 = 1; // CHECKING_SERVICE
//    private final int ERROR_LEVEL_2 = 2; // NEED_TO_UPDATE
//    private final int ERROR_LEVEL_4 = 4; // 기타

    //----------------------------------------------------------------------------------------------
    // Static Field
    //----------------------------------------------------------------------------------------------

    private NetworkBridge networkBridge;
    private long mLastClickTime = System.currentTimeMillis();
    private static final long CLICK_TIME_INTERVAL = 500;
    protected String TAG = getClass().getSimpleName();
    protected static final int EXIT_BACK_PRESSED_TIME = 2000;
    protected long backKeyPressedTime = 0;
    protected Toast toast;

    private LoadingDialog mLoadingDialog;
    private CommonDialog errorDialog; // 서버 에러 팝업 관리


    //----------------------------------------------------------------------------------------------
    // Life Cycle
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkBridge = new NetworkBridge();
    }



    @Override
    protected void onDestroy() {
        if (networkBridge != null) {
            networkBridge.clear();
            networkBridge = null;
        }

        super.onDestroy();

        Glide.get(this).clearMemory();
        CommonUtil.unbindDrawables(getWindow().getDecorView());
        if (mLoadingDialog != null)
            mLoadingDialog = null;
    }


    //----------------------------------------------------------------------------------------------
    // Override
    //----------------------------------------------------------------------------------------------

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        // 더블 탭으로 인한 이중 액션 막기
        long now = System.currentTimeMillis();
        if (now - mLastClickTime < CLICK_TIME_INTERVAL) {
            return;
        }
        mLastClickTime = now;
        overridePendingTransition(R.anim.slide_in_activity_from_right, R.anim.slide_out_activity_to_left);
    }

    public void startActivityWithAnim(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_activity_from_right, R.anim.stay);
    }

    @Override
    public void finishWithAnim() {
        finish();
        overridePendingTransition(R.anim.slide_in_activity_from_left, R.anim.slide_out_activity_to_right);
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }

        if (!mLoadingDialog.isShowing() && !isFinishing()) {
            mLoadingDialog.setCanceledOnTouchOutside(false);
            mLoadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public boolean isLoading() {
        return mLoadingDialog != null && mLoadingDialog.isShowing();
    }

    @Override
    public void hideKeyboard(View v) {
        CommonUtil.hideKeyPad(v);
    }

    @Override
    public void showKeyboard(View v) {
        CommonUtil.showKeyPad(v);
    }

    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------


    protected void request(CommonApiRequester requester, Action1<? super CommonResult> successAction, Action1<? super CommonResult> failAction) {
        if (networkBridge != null) {
            networkBridge.request(requester, successAction, failAction);
        } else {
            Log.d("networkBridge null");
        }
    }

    public boolean handleServerError(CommonResult commonResult) {
        if (commonResult == null) {
            return false;
        }

        return handleServerError(commonResult.getCode(), commonResult.getResult(), commonResult.getResultMsg());
    }

    public boolean handleServerError(Integer type, Boolean code, String message) {
        if (type.equals(ErrorType.SERVER)) {
            if (code.equals(ServerResultCode.NEED_TO_UPDATE.toString())) {
                showNeedToUpdateServerErrorDialog(message);
                return true;
            } else if (code.equals(ServerResultCode.CHECKING_SERVICE.toString())) {
                showCheckingServiceServerErrorDialog(message);
                return true;
            }
        }
        return false;
    }

    public boolean handleServerError(ErrorType type, Integer code, String message) {
        if (type.equals(ErrorType.SERVER)) {
            if (code.equals(ServerResultCode.NEED_TO_UPDATE.toString())) {
                showNeedToUpdateServerErrorDialog(message);
                return true;
            } else if (code.equals(ServerResultCode.CHECKING_SERVICE.toString())) {
                showCheckingServiceServerErrorDialog(message);
                return true;
            }
        }
        return false;
    }

    public void showNeedToUpdateServerErrorDialog(String message) {
        if (errorDialog != null && errorDialog.isShowing()) {
            errorDialog.dismiss();
        }

        hideLoading();
        errorDialog = (CommonDialog) AlertUtil.show(this, message, action -> {
            startMarketActivity();
        });
        errorDialog.setOnCancelListener(cancel -> {
            finishAffinity();
        });
    }


    public void showServerErrorDialog(String message) {
        showServerErrorDialog(message, null);
    }


    public void showServerErrorDialog(String message, Action1 action) {
        if (errorDialog != null) {
        } else {
            errorDialog = new CommonDialog(this);
        }
        errorDialog.setMessage(message);
        if (errorDialog.isShowing()) {
            errorDialog.dismiss();
        }

        errorDialog = (CommonDialog) AlertUtil.show(this, message, action);
    }

    public void showCheckingServiceServerErrorDialog(String message) {
        if (errorDialog != null && errorDialog.isShowing()) {
            errorDialog.dismiss();
        }

        hideLoading();
        errorDialog = (CommonDialog) AlertUtil.show(this, message, action -> {
            finishAffinity();

        });

        errorDialog.setOnCancelListener(cancel -> {
            finishAffinity();
        });
    }

    public void startMarketActivity() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (CommonUtil.isGooglePlayStoreApp(this)) {
                intent.setData(Uri.parse("market://details?id=" + getPackageName()));
            } else {
                intent.setData(Uri.parse("market://details"));
            }
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Intent openMarket = new Intent(Intent.ACTION_MAIN)
                    .addCategory(Intent.CATEGORY_APP_MARKET)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName activityName = openMarket.resolveActivity(getPackageManager());
            if (activityName != null) {
                startActivity(openMarket);
            }
        }
    }
}
