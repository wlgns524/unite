package com.rightcode.unite.Adapter.ViewPagerAdapter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.rightcode.unite.Adapter.ViewPagerFragment.ImageFragment;

import java.util.ArrayList;

import static com.rightcode.unite.Util.ExtraData.EXTRA_IMAGE;

public class ImageViewPagerAdapter extends CommonFragmentPagerAdapter {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    private ArrayList<String> data;
    private Context mContext;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public ImageViewPagerAdapter(FragmentManager fm, Context context, ArrayList<String> data) {
        super(fm, context);
        this.data = data;
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------


    @Override
    public Fragment getItem(int i) {
        Fragment f = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_IMAGE, data.get(i));
        f.setArguments(bundle);
        return f;
    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
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

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
