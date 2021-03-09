package com.rightcode.unite.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.rightcode.unite.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomFragment extends BaseFragment {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    public interface Interaction {
        void onChangeMenu(BottomFragment.Menu menu);
    }

    public enum Menu {
        HOME, PRODUCT, PARTNER, MESSAGE
    }

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.layoutTab1)
    LinearLayout mLayoutTab1;
    @BindView(R.id.layoutTab2)
    LinearLayout mLayoutTab2;
    @BindView(R.id.layoutTab3)
    LinearLayout mLayoutTab3;
    @BindView(R.id.layoutTab4)
    LinearLayout mLayoutTab4;

    @BindView(R.id.imgTab1)
    ImageView mImgTab1;
    @BindView(R.id.imgTab2)
    ImageView mImgTab2;
    @BindView(R.id.imgTab3)
    ImageView mImgTab3;
    @BindView(R.id.imgTab4)
    ImageView mImgTab4;

    @BindView(R.id.txtTab1)
    TextView mTxtTab1;
    @BindView(R.id.txtTab2)
    TextView mTxtTab2;
    @BindView(R.id.txtTab3)
    TextView mTxtTab3;
    @BindView(R.id.txtTab4)
    TextView mTxtTab4;


    private View root;
    private BottomFragment.Menu menu;
    private View currentLayout;
    private BottomFragment.Interaction mInteraction;


    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_bottom, container, false);
        ButterKnife.bind(this, root);

        return root;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BottomFragment.Interaction) {
            mInteraction = (BottomFragment.Interaction) context;
        }
    }
    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------


    public BottomFragment.Menu getMenu() {
        return menu;
    }

    public void setMenu(BottomFragment.Menu menu) {
        switch (menu) {
            case HOME:
                onClickMenu(mLayoutTab1);
                break;
            case PRODUCT:
                onClickMenu(mLayoutTab2);
                break;
            case PARTNER:
                onClickMenu(mLayoutTab3);
                break;
            case MESSAGE:
                onClickMenu(mLayoutTab4);
                break;
        }
    }

    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------


    @OnClick({R.id.layoutTab1, R.id.layoutTab2, R.id.layoutTab3, R.id.layoutTab4})
    void onClickMenu(View v) {
        if (v.isSelected()) {
            return;
        }

        BottomFragment.Menu menu = null;

        switch (v.getId()) {
            case R.id.layoutTab1:
                menu = Menu.HOME;
                break;
            case R.id.layoutTab2:
                menu = Menu.PRODUCT;
                break;
            case R.id.layoutTab3:
                menu = Menu.PARTNER;
                break;
            case R.id.layoutTab4:
                menu = Menu.MESSAGE;
                break;
        }
        if (this.menu == menu) {
            return;
        }

        this.menu = menu;
        updateLayout(v);

        if (mInteraction != null) {
            mInteraction.onChangeMenu(menu);
        }
    }

    private void updateLayout(View v) {
        if (currentLayout != null) {
            currentLayout.setSelected(false);
            currentLayout.setPressed(false);
        }

        currentLayout = v;

        if (currentLayout != null) {
            currentLayout.setSelected(true);
        }

        switch (this.menu) {
            case HOME: {
                mImgTab1.setSelected(true);
                mTxtTab1.setSelected(true);
                mImgTab2.setSelected(false);
                mTxtTab2.setSelected(false);
                mImgTab3.setSelected(false);
                mTxtTab3.setSelected(false);
                mImgTab4.setSelected(false);
                mTxtTab4.setSelected(false);
                break;
            }
            case PRODUCT: {
                mImgTab1.setSelected(false);
                mTxtTab1.setSelected(false);
                mImgTab2.setSelected(true);
                mTxtTab2.setSelected(true);
                mImgTab3.setSelected(false);
                mTxtTab3.setSelected(false);
                mImgTab4.setSelected(false);
                mTxtTab4.setSelected(false);
                break;
            }
            case PARTNER: {
                mImgTab1.setSelected(false);
                mTxtTab1.setSelected(false);
                mImgTab2.setSelected(false);
                mTxtTab2.setSelected(false);
                mImgTab3.setSelected(true);
                mTxtTab3.setSelected(true);
                mImgTab4.setSelected(false);
                mTxtTab4.setSelected(false);
                break;
            }
            case MESSAGE: {
                mImgTab1.setSelected(false);
                mTxtTab1.setSelected(false);
                mImgTab2.setSelected(false);
                mTxtTab2.setSelected(false);
                mImgTab3.setSelected(false);
                mTxtTab3.setSelected(false);
                mImgTab4.setSelected(true);
                mTxtTab4.setSelected(true);
                break;
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------

}