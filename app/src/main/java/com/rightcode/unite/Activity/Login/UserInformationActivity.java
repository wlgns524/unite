package com.rightcode.unite.Activity.Login;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.rightcode.unite.Activity.Basic.BaseActivity;
import com.rightcode.unite.Fragment.TopFragment;
import com.rightcode.unite.MemberManager;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.DataEnums;
import com.rightcode.unite.Util.DateUtil;
import com.rightcode.unite.Util.FragmentUtil;
import com.rightcode.unite.Util.PreferenceUtil;
import com.rightcode.unite.Util.ToastUtil;
import com.rightcode.unite.network.model.request.user.UserUpdate;
import com.rightcode.unite.network.model.response.user.UserInfo;
import com.rightcode.unite.network.requester.auth.SignInRequester;
import com.rightcode.unite.network.requester.user.UserInfoRequester;
import com.rightcode.unite.network.requester.user.UserUpdateRequester;
import com.rightcode.unite.network.responser.auth.SignInResponser;
import com.rightcode.unite.network.responser.user.UserInfoResponser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.unite.Util.ExtraData.EXTRA_PROVIDER;
import static com.rightcode.unite.Util.ExtraData.EXTRA_SNS_PK;

public class UserInformationActivity extends BaseActivity {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.et_nickname)
    EditText et_nickname;
    @BindView(R.id.tv_birthday)
    TextView tv_birthday;
    @BindView(R.id.tv_gender_man)
    TextView tv_gender_man;
    @BindView(R.id.tv_gender_woman)
    TextView tv_gender_woman;
    @BindView(R.id.tv_save)
    TextView tv_save;

    private TopFragment mTopFragment;
    private String gender;
    private String birthday;
    private DataEnums.Provider provider;
    private String socialPk;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        ButterKnife.bind(this);
        initialize();
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

    @OnClick({R.id.tv_birthday, R.id.tv_gender_man, R.id.tv_gender_woman, R.id.tv_save})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_birthday: {
                DatePickerDialog dialog = new DatePickerDialog(UserInformationActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        (view1, year, monthOfYear, dayOfMonth) -> {
                            birthday = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                            tv_birthday.setText(birthday);
                        }
                        , DateUtil.getYearToInt(), DateUtil.getMonthToInt() - 1, DateUtil.getDayToInt());
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                break;
            }
            case R.id.tv_gender_man: {
                tv_gender_man.setSelected(true);
                tv_gender_woman.setSelected(false);
                gender = "남";
                break;
            }
            case R.id.tv_gender_woman: {
                tv_gender_man.setSelected(false);
                tv_gender_woman.setSelected(true);
                gender = "여";
                break;
            }
            case R.id.tv_save: {
                if (MemberManager.getInstance(UserInformationActivity.this).isLogin()) {
                    if (TextUtils.isEmpty(et_nickname.getText())) {
                        ToastUtil.show(UserInformationActivity.this, "닉네임을 입력해주세요");
                        return;
                    }

                    if (TextUtils.isEmpty(tv_birthday.getText().toString())) {
                        ToastUtil.show(UserInformationActivity.this, "생년월일을 입력해주세요");
                        return;
                    }

                    userUpdate();
                } else {
                    if (isValid())
                        signIn();
                }
                break;
            }
        }
    }

    private void initialize() {
        if (getIntent() != null) {
            provider = (DataEnums.Provider) getIntent().getSerializableExtra(EXTRA_PROVIDER);
            socialPk = getIntent().getStringExtra(EXTRA_SNS_PK);
        }
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.ic_back);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, v -> {
            finishWithAnim();
        });

        if (MemberManager.getInstance(UserInformationActivity.this).isLogin()) {
            et_nickname.setText(MemberManager.getInstance(UserInformationActivity.this).getUserInfo().getName());
            birthday = MemberManager.getInstance(UserInformationActivity.this).getUserInfo().getBirthDay();
            tv_birthday.setText(birthday);
            if (MemberManager.getInstance(UserInformationActivity.this).getUserInfo().getGender().equals("남")) {
                tv_gender_man.setSelected(true);
                tv_gender_woman.setSelected(false);
            } else {
                tv_gender_man.setSelected(false);
                tv_gender_woman.setSelected(true);
            }
            tv_save.setText("저장하기");
        }
    }

    private Boolean isValid() {
        if (TextUtils.isEmpty(et_nickname.getText())) {
            ToastUtil.show(UserInformationActivity.this, "닉네임을 입력해주세요");
            return false;
        }

        if (TextUtils.isEmpty(birthday)) {
            ToastUtil.show(UserInformationActivity.this, "생년월일을 입력해주세요");
            return false;
        }

        if (TextUtils.isEmpty(gender)) {
            ToastUtil.show(UserInformationActivity.this, "성별을 선택해주세요");
            return false;
        }

        return true;
    }

    private void signIn() {
        showLoading();
        SignInRequester requester = new SignInRequester(UserInformationActivity.this);
        requester.setName(et_nickname.getText().toString());
        requester.setPassword("rightcode1234");
        requester.setProvider(provider);
        requester.setSocialPk(socialPk);
        requester.setBirthDay(birthday);
        requester.setGender(gender);

        request(requester,
                success -> {
                    SignInResponser result = (SignInResponser) success;
                    if (result.getCode() == 200) {
                        PreferenceUtil.getInstance(UserInformationActivity.this).put(PreferenceUtil.PreferenceKey.ServiceToken, result.getToken());
                        userInfo();
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
        UserInfoRequester requester = new UserInfoRequester(UserInformationActivity.this);
        request(requester,
                success -> {
                    UserInfoResponser result = (UserInfoResponser) success;
                    hideLoading();
                    if (result.getCode() == 200) {
                        MemberManager.getInstance(UserInformationActivity.this).updateLogInInfo(result.getData());
                        MemberManager.getInstance(UserInformationActivity.this).userLogin();
                        setResult(RESULT_OK);
                        finishWithAnim();
                    } else {
                        PreferenceUtil.getInstance(UserInformationActivity.this).put(PreferenceUtil.PreferenceKey.ServiceToken, "");
                        showServerErrorDialog(result.getResultMsg());
                    }
                }, fail -> {
                    hideLoading();
                    PreferenceUtil.getInstance(UserInformationActivity.this).put(PreferenceUtil.PreferenceKey.ServiceToken, "");
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void userUpdate() {
        showLoading();
        UserUpdateRequester requester = new UserUpdateRequester(UserInformationActivity.this);
        UserUpdate userUpdate = new UserUpdate();
        userUpdate.setName(et_nickname.getText().toString());
        userUpdate.setBirthDay(tv_birthday.getText().toString());
        userUpdate.setGender(gender);
        requester.setUserUpdate(userUpdate);

        request(requester,
                success -> {
                    hideLoading();
                    if (success.getCode() == 200) {
                        UserInfo userInfo = MemberManager.getInstance(UserInformationActivity.this).getUserInfo();
                        userInfo.setBirthDay(tv_birthday.getText().toString());
                        userInfo.setGender(gender);
                        userInfo.setName(et_nickname.getText().toString());
                        MemberManager.getInstance(UserInformationActivity.this).updateLogInInfo(userInfo);
                        ToastUtil.show(UserInformationActivity.this, "변경되었습니다");
                        finishWithAnim();
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
