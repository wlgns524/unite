package com.rightcode.unite.Activity.Setting;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.kakao.sdk.user.UserApiClient;
import com.nhn.android.naverlogin.OAuthLogin;
import com.rightcode.unite.Activity.Basic.BaseActivity;
import com.rightcode.unite.Activity.CustomGalleryActivity;
import com.rightcode.unite.Activity.Login.LoginActivity;
import com.rightcode.unite.Activity.Login.UserInformationActivity;
import com.rightcode.unite.Fragment.TopFragment;
import com.rightcode.unite.MemberManager;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.FragmentUtil;
import com.rightcode.unite.Util.ImageWrapper;
import com.rightcode.unite.Util.Log;
import com.rightcode.unite.Util.ToastUtil;
import com.rightcode.unite.network.requester.user.UserProfileImageDeleteRequester;
import com.rightcode.unite.network.requester.user.UserProfileImageRequester;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.unite.Util.ExtraData.EXTRA_ACTIVITY_ACTION;
import static com.rightcode.unite.Util.ExtraData.EXTRA_IMAGE_COUNT;
import static com.rightcode.unite.Util.ExtraData.EXTRA_SELECT_IMAGE;
import static com.rightcode.unite.Util.ExtraData.EXTRA_SINGLE_SELECT;
import static com.rightcode.unite.Util.ExtraData.REQUEST_CODE_GALLERY;

public class SettingActivity extends BaseActivity {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.iv_profile)
    ImageView iv_profile;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_login)
    TextView tv_login;

    private TopFragment mTopFragment;
    private ArrayList<String> selectedPhotos;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

//        RxBus.register(this);
        ButterKnife.bind(this);
        initialize();
        initLayout();
    }

    @Override
    public void finish() {
//        RxBus.unregister(this);
        super.finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_out_activity_to_left);
    }

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_GALLERY: {
                    ArrayList<String> photos = data.getStringArrayListExtra(EXTRA_SELECT_IMAGE);
                    selectedPhotos = photos;
                    userProfileImageRegist();
                    break;
                }
                case EXTRA_ACTIVITY_ACTION: {
                    initLayout();
                    break;
                }
                default:
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

    @OnClick({R.id.iv_profile, R.id.tv_login, R.id.tv_user_information, R.id.tv_post_mine, R.id.tv_notice, R.id.tv_inquiry, R.id.tv_notification})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.iv_profile: {
                if (MemberManager.getInstance(SettingActivity.this).isLogin()) {
                    checkPermission();
                }
                break;
            }
            case R.id.tv_login: {
                if (MemberManager.getInstance(SettingActivity.this).isLogin()) {
                    logout();
                } else {
                    Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                    startActivityForResult(intent, EXTRA_ACTIVITY_ACTION);
                }
                break;
            }
            case R.id.tv_user_information: {
                if (MemberManager.getInstance(SettingActivity.this).isLogin()) {
                    Intent intent = new Intent(SettingActivity.this, UserInformationActivity.class);
                    startActivityForResult(intent, EXTRA_ACTIVITY_ACTION);
                } else {
                    Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                    startActivityForResult(intent, EXTRA_ACTIVITY_ACTION);
                }
                break;
            }
            case R.id.tv_post_mine: {
                if (MemberManager.getInstance(SettingActivity.this).isLogin()) {
                    Intent intent = new Intent(SettingActivity.this, PostMineActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                    startActivityForResult(intent, EXTRA_ACTIVITY_ACTION);
                }
                break;
            }
            case R.id.tv_notice: {
                Intent intent = new Intent(SettingActivity.this, NoticeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.tv_inquiry: {
                Intent intent = new Intent(SettingActivity.this, InquiryActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.tv_notification: {
                Intent intent = new Intent(SettingActivity.this, NotificationActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private void logout() {
        switch (MemberManager.getInstance(SettingActivity.this).getUserInfo().getProvider()) {
            case kakao: {
                UserApiClient.getInstance().logout(error -> {
                    if (error != null) {
                        Log.e("로그아웃 실패. SDK에서 토큰 삭제됨");
                    } else {
                        Log.i("로그아웃 성공. SDK에서 토큰 삭제됨");
                    }
                    return null;
                });
            }
            case naver: {
                OAuthLogin mOAuthLoginInstance = OAuthLogin.getInstance();
                mOAuthLoginInstance.logout(SettingActivity.this);
            }
            case facebook: {
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                if (isLoggedIn) {
                    LoginManager.getInstance().logOut();
                }
            }
            case google: {
                GoogleSignInOptions gso = new GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        build();
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(SettingActivity.this, gso);
                googleSignInClient.signOut();
            }
        }
        MemberManager.getInstance(SettingActivity.this).userLogout();
        initLayout();
    }

    private void initialize() {
        overridePendingTransition(R.anim.slide_in_activity_from_left, R.anim.stay);
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.ic_back);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, v -> {
            finish();
        });
        mTopFragment.setText(TopFragment.Menu.CENTER, "설정");
    }

    private void initLayout() {
        if (MemberManager.getInstance(SettingActivity.this).isLogin()) {
            ImageWrapper.loadCircle(SettingActivity.this, MemberManager.getInstance(SettingActivity.this).getUserInfo().getImage(), iv_profile, R.drawable.ic_profile);
            tv_name.setText(MemberManager.getInstance(SettingActivity.this).getUserInfo().getName());
            tv_login.setText("로그아웃");
        } else {
            ImageWrapper.loadCircle(SettingActivity.this, R.drawable.ic_profile, iv_profile);
            tv_name.setText("로그인을 해주세요");
            tv_login.setText("로그인");
        }
    }

    private void checkPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent(SettingActivity.this, CustomGalleryActivity.class);
                intent.putExtra(EXTRA_IMAGE_COUNT, 1);
                intent.putExtra(EXTRA_SINGLE_SELECT, true);
                if (selectedPhotos != null) {
                    intent.putStringArrayListExtra(EXTRA_SELECT_IMAGE, selectedPhotos);
                }
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                ToastUtil.show(SettingActivity.this, "앨범 접근권한을 허용해주세요");
            }
        };

        TedPermission.with(SettingActivity.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("앨범 접근권한을 허용해주세요")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    private void userProfileImageRegist() {
        showLoading();
        UserProfileImageRequester requester = new UserProfileImageRequester(SettingActivity.this);
        requester.setPath(selectedPhotos);

        request(requester,
                success -> {
                    hideLoading();
                    if (success.getCode() == 200) {
                        ImageWrapper.loadCircle(SettingActivity.this, selectedPhotos.get(0), iv_profile, R.drawable.ic_profile);
                    } else {
                        showServerErrorDialog(success.getResultMsg());
                    }
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void userProfileImageDelete() {
        UserProfileImageDeleteRequester requester = new UserProfileImageDeleteRequester(SettingActivity.this);
        showLoading();
        request(requester,
                success -> {
                    hideLoading();
                    if (success.getCode() == 200) {
                        ImageWrapper.loadCircle(SettingActivity.this, R.drawable.ic_profile, iv_profile);
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
