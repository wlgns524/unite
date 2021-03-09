package com.rightcode.unite.Activity.Login;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.user.UserApiClient;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.rightcode.unite.Activity.Basic.BaseActivity;
import com.rightcode.unite.MemberManager;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.DataEnums;
import com.rightcode.unite.Util.Log;
import com.rightcode.unite.Util.PreferenceUtil;
import com.rightcode.unite.network.requester.auth.SignInRequester;
import com.rightcode.unite.network.requester.user.UserInfoRequester;
import com.rightcode.unite.network.responser.auth.SignInResponser;
import com.rightcode.unite.network.responser.user.UserInfoResponser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.unite.Util.ExtraData.EXTRA_ACTIVITY_ACTION;
import static com.rightcode.unite.Util.ExtraData.EXTRA_ACTIVITY_COMPLETE;
import static com.rightcode.unite.Util.ExtraData.EXTRA_PROVIDER;
import static com.rightcode.unite.Util.ExtraData.EXTRA_SNS_PK;

public class LoginActivity extends BaseActivity {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    private static final int GOOGLE_REQUEST = 9001;
    private static OAuthLogin mOAuthLoginInstance;
    private static Context mContext;
    private CallbackManager mCallbackManager;

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    private GoogleSignInOptions gso;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        mContext = LoginActivity.this;
    }


    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mCallbackManager != null)
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        switch (requestCode) {
            case GOOGLE_REQUEST: {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    signIn(account.getId(), DataEnums.Provider.google);
                } catch (ApiException e) {
                    showServerErrorDialog(e.getMessage());
                }
                break;
            }
            case EXTRA_ACTIVITY_ACTION: {
                break;
            }
            case EXTRA_ACTIVITY_COMPLETE: {
                setResult(RESULT_OK);
                finishWithAnim();
                break;
            }
        }
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

    @OnClick({R.id.ll_naver, R.id.ll_kakao, R.id.ll_google, R.id.ll_facebook, R.id.iv_close})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.ll_naver: {
                mOAuthLoginInstance = OAuthLogin.getInstance();
                mOAuthLoginInstance.enableWebViewLoginOnly();
                mOAuthLoginInstance.init(
                        LoginActivity.this
                        , getString(R.string.naver_client_id)
                        , getString(R.string.naver_client_secret)
                        , getString(R.string.app_name));
                mOAuthLoginInstance.startOauthLoginActivity(LoginActivity.this, mOAuthLoginHandler);
                break;
            }
            case R.id.ll_kakao: {
                LoginClient.getInstance().loginWithKakaoTalk(LoginActivity.this, (token, loginError) -> {
                    Log.d(token.getAccessToken());
                    if (loginError != null) {
                        showServerErrorDialog("로그인 실패");
                    } else {
                        Log.d("로그인 성공");

                        // 사용자 정보 요청
                        UserApiClient.getInstance().me((user, meError) -> {
                            if (meError != null) {
                                showServerErrorDialog("사용자 정보 요청 실패");
                            } else {
                                signIn(String.valueOf(user.getId()), DataEnums.Provider.kakao);
                            }
                            return null;
                        });
                    }
                    return null;
                });
                break;
            }
            case R.id.ll_google: {
                gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.google_web_client_id))
                        .requestEmail()
                        .build();
                Intent signInIntent = GoogleSignIn.getClient(this, gso).getSignInIntent();
                startActivityForResult(signInIntent, GOOGLE_REQUEST);
                break;
            }
            case R.id.ll_facebook: {
                mCallbackManager = CallbackManager.Factory.create();
                LoginManager loginManager = LoginManager.getInstance();
                loginManager.logInWithReadPermissions(LoginActivity.this,
//                        Arrays.asList("public_profile", "email"));
                Arrays.asList("public_profile"));
                loginManager.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("facebook:onSuccess:" + loginResult);
                        signIn(loginResult.getAccessToken().getUserId(), DataEnums.Provider.facebook);
                    }

                    @Override
                    public void onCancel() {
                        Log.d("facebook:onCancel");
                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(error);
                        Log.d(error.getMessage());
                        showServerErrorDialog("facebook:onError");
                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
                break;
            }
            case R.id.iv_close: {
                finishWithAnim();
                break;
            }
        }
    }

    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                String tokenType = mOAuthLoginInstance.getTokenType(mContext);
                new RequestApiTask().execute();
            } else {
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        }

    };

    private void userInfo(String token, DataEnums.Provider provider) {
        Intent intent = new Intent(LoginActivity.this, UserInformationActivity.class);
        intent.putExtra(EXTRA_PROVIDER, provider);
        intent.putExtra(EXTRA_SNS_PK, token);
        startActivityForResult(intent, EXTRA_ACTIVITY_COMPLETE);
    }

    private void signIn(String token, DataEnums.Provider provider) {
        showLoading();
        SignInRequester requester = new SignInRequester(LoginActivity.this);
        requester.setPassword("rightcode1234");
        requester.setProvider(provider);
        requester.setSocialPk(token);

        request(requester,
                success -> {
                    SignInResponser result = (SignInResponser) success;
                    if (result.getCode() == 200) {
                        PreferenceUtil.getInstance(LoginActivity.this).put(PreferenceUtil.PreferenceKey.ServiceToken, result.getToken());
                        userInfo();
                    } else if (result.getCode() == 203) {
                        userInfo(token, provider);
                    } else {
                        hideLoading();
                        showServerErrorDialog(result.getResultMsg());
                    }
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }


    private void userInfo() {
        UserInfoRequester requester = new UserInfoRequester(LoginActivity.this);
        request(requester,
                success -> {
                    UserInfoResponser result = (UserInfoResponser) success;
                    hideLoading();
                    if (result.getCode() == 200) {
                        MemberManager.getInstance(LoginActivity.this).updateLogInInfo(result.getData());
                        MemberManager.getInstance(LoginActivity.this).userLogin();
                        setResult(RESULT_OK);
                        finishWithAnim();
                    } else {
                        PreferenceUtil.getInstance(LoginActivity.this).put(PreferenceUtil.PreferenceKey.ServiceToken, "");
                        showServerErrorDialog(result.getResultMsg());
                    }
                }, fail -> {
                    hideLoading();
                    PreferenceUtil.getInstance(LoginActivity.this).put(PreferenceUtil.PreferenceKey.ServiceToken, "");
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------


    private class RequestApiTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = "https://openapi.naver.com/v1/nid/me";
            String at = mOAuthLoginInstance.getAccessToken(mContext);
            return mOAuthLoginInstance.requestApi(mContext, at, url);
        }

        protected void onPostExecute(String content) {
            try {
                String response = new JSONObject(content).getString("response");
                String id = new JSONObject(response).getString("id");
                signIn(id, DataEnums.Provider.naver);
            } catch (JSONException e) {
                showServerErrorDialog(e.getMessage());
            }
        }
    }
}
