package com.rightcode.unite.Fragment.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.unite.Activity.Partner.PartnerRegisterActivity;
import com.rightcode.unite.Adapter.RecyclerViewAdapter.PartnerRecyclerViewAdapter;
import com.rightcode.unite.Component.GridSpacingItemDecoration;
import com.rightcode.unite.Fragment.BaseFragment;
import com.rightcode.unite.Fragment.SearchFragment;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.FragmentUtil;
import com.rightcode.unite.network.requester.partner.PartnerListRequester;
import com.rightcode.unite.network.responser.partner.PartnerListResponser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.rightcode.unite.Util.ExtraData.EXTRA_ACTIVITY_ACTION;
import static com.rightcode.unite.Util.ExtraData.EXTRA_ACTIVITY_COMPLETE;

public class PartnerFragment extends BaseFragment {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.rv_partner)
    RecyclerView rv_partner;
    @BindView(R.id.view_not_exist)
    View view_not_exist;
    @BindView(R.id.ll_partner_exist)
    LinearLayout ll_partner_exist;

    private View root;
    private SearchFragment mSearchFragment;
    private PartnerRecyclerViewAdapter mPartnerRecyclerViewAdapter;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public static PartnerFragment newInstance() {
        PartnerFragment f = new PartnerFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_partner, container, false);

        ButterKnife.bind(this, root);
        initialize();
        partnerList();

        return root;
    }


    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case EXTRA_ACTIVITY_ACTION: {
                    partnerList();
                    break;
                }
                case EXTRA_ACTIVITY_COMPLETE: {
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



    private void initialize() {
        mSearchFragment = (SearchFragment) FragmentUtil.findFragmentByTag(getChildFragmentManager(), "SearchFragment");
        mSearchFragment.setVisible(SearchFragment.Menu.WRITE, true);
        mSearchFragment.setListener(SearchFragment.Menu.WRITE, v -> {
            Intent intent = new Intent(getContext(), PartnerRegisterActivity.class);
            startActivityForResult(intent, EXTRA_ACTIVITY_ACTION);
        });
        mSearchFragment.setVisible(SearchFragment.Menu.FILTER, true);
        mSearchFragment.setListener(SearchFragment.Menu.FILTER, v -> {
            if (mSearchFragment.isShowFilter()) {
                mSearchFragment.filterHide();
            } else {
                mSearchFragment.filterShow();
            }
        });

        mPartnerRecyclerViewAdapter = new PartnerRecyclerViewAdapter(getContext());
        rv_partner.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
        rv_partner.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv_partner.setAdapter(mPartnerRecyclerViewAdapter);
        rv_partner.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if (mSearchFragment.isShowFilter())
                    mSearchFragment.filterHide();

                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }

    private void partnerList() {
        PartnerListRequester requester = new PartnerListRequester(getContext());
        requester.setSort("최신순");
        showLoading();
        request(requester,
                success -> {
                    PartnerListResponser result = (PartnerListResponser) success;
                    hideLoading();
                    if (result.getCode() == 200) {
                        if (result.getList().size() > 0) {
                            mPartnerRecyclerViewAdapter.setData(result.getList());
                            mPartnerRecyclerViewAdapter.notifyDataSetChanged();
                            view_not_exist.setVisibility(View.GONE);
                            ll_partner_exist.setVisibility(View.VISIBLE);
                        } else {
                            view_not_exist.setVisibility(View.VISIBLE);
                            ll_partner_exist.setVisibility(View.GONE);
                        }
                    } else {
                        showServerErrorDialog(result.getResultMsg());
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
