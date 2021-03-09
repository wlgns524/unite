package com.rightcode.unite.Activity.Setting;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.rightcode.unite.Activity.Basic.BaseActivity;
import com.rightcode.unite.Fragment.TopFragment;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.CommonUtil;
import com.rightcode.unite.Util.FragmentUtil;
import com.rightcode.unite.Util.ToastUtil;
import com.rightcode.unite.network.requester.inquiry.InquiryRegisterRequester;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InquiryActivity extends BaseActivity {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_title)
    EditText et_title;
    @BindView(R.id.et_message)
    EditText et_message;

    private TopFragment mTopFragment;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);

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

    @OnClick({R.id.tv_inquiry})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_inquiry: {
                if (!CommonUtil.isEmailValid(et_email.getText().toString())) {
                    ToastUtil.show(InquiryActivity.this, "이메일 형식을 확인해주세요");
                    return;
                }

                if (TextUtils.isEmpty(et_email.getText())) {
                    ToastUtil.show(InquiryActivity.this, "제목을 입력해주세요");
                    return;
                }
                if (TextUtils.isEmpty(et_title.getText())) {
                    ToastUtil.show(InquiryActivity.this, "제목을 입력해주세요");
                    return;
                }
                if (TextUtils.isEmpty(et_message.getText())) {
                    ToastUtil.show(InquiryActivity.this, "내용을 입력해주세요");
                    return;
                }

                inquiryRegister();
                break;
            }
        }
    }

    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "문의하기");
        mTopFragment.setTextColor(TopFragment.Menu.CENTER, R.color.black);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.ic_back);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, v -> finishWithAnim());
    }

    private void inquiryRegister() {
        InquiryRegisterRequester requester = new InquiryRegisterRequester(InquiryActivity.this);
        requester.setEmail(et_email.getText().toString());
        requester.setTitle(et_title.getText().toString());
        requester.setContent(et_message.getText().toString());
        showLoading();

        request(requester,
                success -> {
                    hideLoading();
                    if (success.getCode() == 200) {
                        ToastUtil.show(InquiryActivity.this, "등록되었습니다");
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
