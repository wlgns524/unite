package com.rightcode.unite.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rightcode.unite.Fragment.ProductAccompanyFragment;
import com.rightcode.unite.Fragment.ProductInformationFragment;
import com.rightcode.unite.Fragment.ProductReviewFragment;
import com.rightcode.unite.Fragment.TopFragment;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.FragmentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailActivity extends BaseActivity {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.tv_information)
    TextView tv_information;
    @BindView(R.id.tv_review)
    TextView tv_review;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

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

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    @OnClick({R.id.tv_information, R.id.tv_review, R.id.tv_accompany})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_information: {
                setFragment(ProductInformationFragment.newInstance());
                tv_information.setSelected(true);
                tv_review.setSelected(false);
                tv_accompany.setSelected(false);
                break;
            }
            case R.id.tv_review: {
                setFragment(ProductReviewFragment.newInstance());
                tv_information.setSelected(false);
                tv_review.setSelected(true);
                tv_accompany.setSelected(false);
                break;
            }
            case R.id.tv_accompany: {
                setFragment(ProductAccompanyFragment.newInstance());
                tv_information.setSelected(false);
                tv_review.setSelected(false);
                tv_accompany.setSelected(true);
                break;
            }
        }
    }


    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.ic_profile);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setText(TopFragment.Menu.CENTER, "다낭");
        mTopFragment.setListener(TopFragment.Menu.LEFT, v -> {
            finishWithAnim();
        });

        setFragment(ProductInformationFragment.newInstance());
        tv_information.setSelected(true);

    }

    private void setFragment(Fragment f) {
        if (f == null)
            return;
        FragmentUtil.startFragment(getSupportFragmentManager(), R.id.fl_container, f);
        FragmentUtil.removeFragment(getSupportFragmentManager(), currentFragment);
        currentFragment = f;
    }
    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
