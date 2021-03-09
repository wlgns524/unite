package com.rightcode.unite.Fragment.Main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.unite.Activity.Setting.SettingActivity;
import com.rightcode.unite.Adapter.RecyclerViewAdapter.HomePartnerRecyclerViewAdapter;
import com.rightcode.unite.Adapter.RecyclerViewAdapter.HomeProductRecyclerViewAdapter;
import com.rightcode.unite.Fragment.BaseFragment;
import com.rightcode.unite.Fragment.BottomFragment;
import com.rightcode.unite.Fragment.SearchFragment;
import com.rightcode.unite.Fragment.TopFragment;
import com.rightcode.unite.R;
import com.rightcode.unite.RxJava.RxBus;
import com.rightcode.unite.RxJava.RxEvent.SearchEvent;
import com.rightcode.unite.Util.FragmentUtil;
import com.rightcode.unite.network.requester.partner.PartnerListRequester;
import com.rightcode.unite.network.requester.product.ProductListRequester;
import com.rightcode.unite.network.responser.partner.PartnerListResponser;
import com.rightcode.unite.network.responser.product.ProductListResponser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.unite.Fragment.SearchFragment.Menu.SEARCH;

public class HomeFragment extends BaseFragment {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.rv_activity)
    RecyclerView rv_activity;
    @BindView(R.id.rv_partner)
    RecyclerView rv_partner;

    private View root;
    private TopFragment mTopFragment;
    private SearchFragment mSearchFragment;
    private HomeProductRecyclerViewAdapter mHomeProductRecyclerViewAdapter;
    private HomePartnerRecyclerViewAdapter mHomePartnerRecyclerViewAdapter;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public static HomeFragment newInstance() {
        HomeFragment f = new HomeFragment();
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
        root = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, root);
        initialize();
        productList("");
        partnerList();

        return root;
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

    @OnClick({R.id.tv_jeju, R.id.tv_busan, R.id.tv_seoul, R.id.tv_gangwon, R.id.tv_bangkok, R.id.tv_danang, R.id.tv_taiwan, R.id.tv_macao,
            R.id.tv_product_more, R.id.tv_partner_more})
    void onClickMenu(View view) {
        String search = null;
        switch (view.getId()) {
            case R.id.tv_jeju: {
                search = "제주";
                break;
            }
            case R.id.tv_busan: {
                search = "부산";
                break;
            }
            case R.id.tv_seoul: {
                search = "서울";
                break;
            }
            case R.id.tv_gangwon: {
                search = "강원";
                break;
            }
            case R.id.tv_bangkok: {
                search = "방콕";
                break;
            }
            case R.id.tv_danang: {
                search = "다낭";
                break;
            }
            case R.id.tv_taiwan: {
                search = "대만";
                break;
            }
            case R.id.tv_macao: {
                search = "마카오";
                break;
            }
            case R.id.tv_product_more: {
                RxBus.send(new SearchEvent(BottomFragment.Menu.PRODUCT, ""));
                break;
            }
            case R.id.tv_partner_more: {
                RxBus.send(new SearchEvent(BottomFragment.Menu.PARTNER, ""));
                break;
            }
        }
        if (!TextUtils.isEmpty(search)) {
            RxBus.send(new SearchEvent(BottomFragment.Menu.PRODUCT, search));
        }
    }

    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getChildFragmentManager(), "TopFragment");
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.ic_menu);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, v -> {
            Intent intent = new Intent(getContext(), SettingActivity.class);
            startActivity(intent);
        });
        mSearchFragment = (SearchFragment) FragmentUtil.findFragmentByTag(getChildFragmentManager(), "SearchFragment");
        mSearchFragment.setListener(SEARCH, view -> {
            RxBus.send(new SearchEvent(BottomFragment.Menu.PRODUCT, mSearchFragment.getSearchText()));
        });

        mHomeProductRecyclerViewAdapter = new HomeProductRecyclerViewAdapter(getContext());
        rv_activity.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_activity.setAdapter(mHomeProductRecyclerViewAdapter);

        mHomePartnerRecyclerViewAdapter = new HomePartnerRecyclerViewAdapter(getContext());
        rv_partner.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_partner.setAdapter(mHomePartnerRecyclerViewAdapter);
    }


    private void productList(String search) {
        ProductListRequester requester = new ProductListRequester(getContext());
        if (!TextUtils.isEmpty(search))
            requester.setSearch(search);
        requester.setLimit(5);
        requester.setRandom(true);
        showLoading();
        request(requester,
                success -> {
                    ProductListResponser result = (ProductListResponser) success;
                    hideLoading();
                    if (result.getCode() == 200) {
                        mHomeProductRecyclerViewAdapter.setData(result.getList());
                        mHomeProductRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void partnerList() {
        PartnerListRequester requester = new PartnerListRequester(getContext());
        requester.setLimit(5);
        showLoading();
        request(requester,
                success -> {
                    PartnerListResponser result = (PartnerListResponser) success;
                    hideLoading();
                    if (result.getCode() == 200) {
                        mHomePartnerRecyclerViewAdapter.setData(result.getList());
                        mHomePartnerRecyclerViewAdapter.notifyDataSetChanged();
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
