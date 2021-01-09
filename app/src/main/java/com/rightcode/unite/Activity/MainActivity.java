package com.rightcode.unite.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rightcode.unite.Fragment.AccompanyFragment;
import com.rightcode.unite.Fragment.ActivityFragment;
import com.rightcode.unite.Fragment.TopFragment;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.FragmentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.unite.Activity.BaseActivity.EXIT_BACK_PRESSED_TIME;

public class MainActivity extends BaseActivity {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.tv_activity)
    TextView tv_activity;
    @BindView(R.id.tv_accompany)
    TextView tv_accompany;

    private TopFragment mTopFragment;
    private Fragment currentFragment;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initialize();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        currentFragment = null;
    }

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        if (currentFragment instanceof ActivityFragment) {
            if (System.currentTimeMillis() > backKeyPressedTime + EXIT_BACK_PRESSED_TIME) {
                backKeyPressedTime = System.currentTimeMillis();
                toast = Toast.makeText(getApplicationContext(), getString(R.string.exit_message), Toast.LENGTH_SHORT);
                toast.show();
            } else {
                finishAffinity();
                toast.cancel();
            }
        } else {
            setFragment(AccompanyFragment.newInstance());
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

    @OnClick({R.id.tv_activity, R.id.tv_accompany})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_activity: {
                setFragment(ActivityFragment.newInstance());
                tv_activity.setSelected(true);
                tv_accompany.setSelected(false);
                break;
            }
            case R.id.tv_accompany: {
                setFragment(AccompanyFragment.newInstance());
                tv_activity.setSelected(false);
                tv_accompany.setSelected(true);
                break;
            }
        }
    }


    private void setFragment(Fragment f) {
        if (f == null)
            return;
        FragmentUtil.startFragment(getSupportFragmentManager(), R.id.main_fl_container, f);
        FragmentUtil.removeFragment(getSupportFragmentManager(), currentFragment);
        currentFragment = f;
    }

    private void initialize() {
        setFragment(ActivityFragment.newInstance());
        tv_activity.setSelected(true);
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.ic_profile);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, v -> {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        });
        mTopFragment.setImage(TopFragment.Menu.CENTER, R.drawable.ic_logo);
        mTopFragment.setImagePadding(TopFragment.Menu.RIGHT, 5);
//        mTopFragment.setListener(TopFragment.Menu.RIGHT, v -> {
//            Intent intent = new Intent(MainActivity.this, StudentClassHistoryActivity.class);
//            startActivity(intent);
//        });


    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------

}