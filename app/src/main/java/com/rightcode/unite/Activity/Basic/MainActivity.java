package com.rightcode.unite.Activity.Basic;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.rightcode.unite.Fragment.BottomFragment;
import com.rightcode.unite.Fragment.Main.HomeFragment;
import com.rightcode.unite.Fragment.Main.MessageFragment;
import com.rightcode.unite.Fragment.Main.PartnerFragment;
import com.rightcode.unite.Fragment.Main.ProductFragment;
import com.rightcode.unite.R;
import com.rightcode.unite.RxJava.Event;
import com.rightcode.unite.RxJava.RxBus;
import com.rightcode.unite.RxJava.RxEvent.SearchEvent;
import com.rightcode.unite.Util.FragmentUtil;

import butterknife.ButterKnife;

import static com.rightcode.unite.Fragment.BottomFragment.Menu.HOME;
import static com.rightcode.unite.Util.ExtraData.EXTRA_SEARCH_KEYWORD;

public class MainActivity extends BaseActivity implements BottomFragment.Interaction {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    @Event(SearchEvent.class)
    public void onSearchEvent(SearchEvent event) {
        searchKeyword = event.getSearch();
        mBottomFragment.setMenu(event.getMenu());
    }


    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    private Fragment currentFragment;
    private BottomFragment mBottomFragment;
    private String searchKeyword;

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

        RxBus.register(this);
        ButterKnife.bind(this);
        initialize();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.unregister(this);
        FragmentUtil.removeFragment(getSupportFragmentManager(), mBottomFragment);
        mBottomFragment = null;
        currentFragment = null;
    }
    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        if (currentFragment instanceof HomeFragment) {
            if (System.currentTimeMillis() > backKeyPressedTime + EXIT_BACK_PRESSED_TIME) {
                backKeyPressedTime = System.currentTimeMillis();
                toast = Toast.makeText(getApplicationContext(), getString(R.string.exit_message), Toast.LENGTH_SHORT);
                toast.show();
            } else {
                finishAffinity();
                toast.cancel();
            }
        } else {
            setFragment(HomeFragment.newInstance());
            mBottomFragment.setMenu(HOME);
        }
    }

    @Override
    public void onChangeMenu(BottomFragment.Menu menu) {
        Fragment f = null;
        switch (menu) {
            case HOME:
                f = HomeFragment.newInstance();
                break;
            case PRODUCT:
                f = ProductFragment.newInstance();
                break;
            case PARTNER:
                f = PartnerFragment.newInstance();
                break;
            case MESSAGE:
                f = MessageFragment.newInstance();
                break;
        }
        if (searchKeyword != null) {
            Bundle b = new Bundle();
            b.putString(EXTRA_SEARCH_KEYWORD, searchKeyword);
            f.setArguments(b);
            searchKeyword = null;
        }
        setFragment(f);
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


    private void setFragment(Fragment f) {
        if (f == null)
            return;
        FragmentUtil.startFragment(getSupportFragmentManager(), R.id.main_fl_container, f);
        FragmentUtil.removeFragment(getSupportFragmentManager(), currentFragment);
        currentFragment = f;
    }

    private void initialize() {
        mBottomFragment = (BottomFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "BottomFragment");
        mBottomFragment.setMenu(HOME); // default
    }


    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------

}