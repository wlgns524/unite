package com.rightcode.unite.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.rightcode.unite.Fragment.TopFragment;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.FragmentUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

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
        setContentView(R.layout.activity_setting);

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

    @OnClick({R.id.rl_login})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.rl_login: {
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.ic_profile);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, v -> {
            Intent intent = new Intent(SettingActivity.this, SettingActivity.class);
            startActivity(intent);
        });
        mTopFragment.setText(TopFragment.Menu.CENTER, "설정");
    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
