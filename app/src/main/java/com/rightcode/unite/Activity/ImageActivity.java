package com.rightcode.unite.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.rightcode.unite.Activity.Basic.BaseActivity;
import com.rightcode.unite.Adapter.ViewPagerAdapter.ImageViewPagerAdapter;
import com.rightcode.unite.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.unite.Util.ExtraData.EXTRA_IMAGE;

public class ImageActivity extends BaseActivity {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.vp_image)
    ViewPager vp_image;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

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

    @OnClick({R.id.iv_close})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.iv_close: {
                finishWithAnim();
                break;
            }
        }
    }

    private void initialize() {
        String image = getIntent().getStringExtra(EXTRA_IMAGE);
        ArrayList<String> imageList = new ArrayList<>();
        imageList.add(image);
        ImageViewPagerAdapter mImageViewPagerAdapter = new ImageViewPagerAdapter(getSupportFragmentManager(), ImageActivity.this, imageList);
        vp_image.setAdapter(mImageViewPagerAdapter);
    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
