package com.rightcode.unite.Fragment.Main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.unite.Activity.Login.LoginActivity;
import com.rightcode.unite.Activity.Setting.FavoriteActivity;
import com.rightcode.unite.Adapter.RecyclerViewAdapter.ProductRecyclerViewAdapter;
import com.rightcode.unite.Fragment.BaseFragment;
import com.rightcode.unite.Fragment.SearchFragment;
import com.rightcode.unite.MemberManager;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.FragmentUtil;
import com.rightcode.unite.Util.Log;
import com.rightcode.unite.Util.ToastUtil;
import com.rightcode.unite.network.requester.product.ProductListRequester;
import com.rightcode.unite.network.responser.product.ProductListResponser;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.unite.Fragment.SearchFragment.Menu.LIKE;
import static com.rightcode.unite.Fragment.SearchFragment.Menu.SEARCH;
import static com.rightcode.unite.Util.ExtraData.EXTRA_SEARCH_KEYWORD;

public class ProductFragment extends BaseFragment {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.rv_product)
    RecyclerView rv_product;
    @BindView(R.id.view_not_exist)
    View view_not_exist;

    private View root;
    private SearchFragment mSearchFragment;
    private ProductRecyclerViewAdapter mProductActivityRecyclerViewAdapter;


    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public static ProductFragment newInstance() {
        ProductFragment f = new ProductFragment();
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
        root = inflater.inflate(R.layout.fragment_product, container, false);

        ButterKnife.bind(this, root);
        initialize();

//        initializeSdk(getContext());
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

    private void initialize() {
        mSearchFragment = (SearchFragment) FragmentUtil.findFragmentByTag(getChildFragmentManager(), "SearchFragment");
        mSearchFragment.setVisible(SearchFragment.Menu.LIKE, true);
        mSearchFragment.setListener(SearchFragment.Menu.LIKE, v -> {
            Log.d("LIKE");
            if (MemberManager.getInstance(getContext()).isLogin()) {
                Intent intent = new Intent(getContext(), FavoriteActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        mSearchFragment.setVisible(SearchFragment.Menu.FILTER, true);
        mSearchFragment.setListener(SearchFragment.Menu.FILTER, v -> {
            if (mSearchFragment.isShowFilter()) {
                mSearchFragment.filterHide();
                productList(mSearchFragment.getArea(), mSearchFragment.getCategory(), mSearchFragment.getStartPrice(), mSearchFragment.getEndPrice());
            } else {
                mSearchFragment.filterShow();
            }
        });
        mSearchFragment.setListener(SEARCH, view -> {
            if (TextUtils.isEmpty(mSearchFragment.getSearchText())) {
                ToastUtil.show(getContext(), "검색어를 입력해주세요");
                return;
            }
            productList(mSearchFragment.getSearchText());
        });

        String search = getArguments().getString(EXTRA_SEARCH_KEYWORD);
        if (TextUtils.isEmpty(search)) {
            productList();
        } else {
            mSearchFragment.setSearchText(search);
            productList(search);
        }

        mProductActivityRecyclerViewAdapter = new ProductRecyclerViewAdapter(getContext());
        rv_product.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv_product.setAdapter(mProductActivityRecyclerViewAdapter);
        rv_product.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if (mSearchFragment.isShowFilter()) {
                    mSearchFragment.filterHide();
                }
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

    private void productList() {
        ProductListRequester requester = new ProductListRequester(getContext());
        showLoading();
        request(requester,
                success -> {
                    ProductListResponser result = (ProductListResponser) success;
                    hideLoading();
                    if (result.getCode() == 200) {
                        if (result.getList().size() > 0) {
                            mProductActivityRecyclerViewAdapter.setData(result.getList());
                            mProductActivityRecyclerViewAdapter.notifyDataSetChanged();
                            view_not_exist.setVisibility(View.GONE);
                            rv_product.setVisibility(View.VISIBLE);
                        } else {
                            view_not_exist.setVisibility(View.VISIBLE);
                            rv_product.setVisibility(View.GONE);
                        }
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void productList(String search) {
        ProductListRequester requester = new ProductListRequester(getContext());
        if (!TextUtils.isEmpty(search))
            requester.setSearch(search);
        showLoading();
        request(requester,
                success -> {
                    ProductListResponser result = (ProductListResponser) success;
                    hideLoading();
                    if (result.getCode() == 200) {
                        if (result.getList().size() > 0) {
                            mProductActivityRecyclerViewAdapter.setData(result.getList());
                            mProductActivityRecyclerViewAdapter.notifyDataSetChanged();
                            view_not_exist.setVisibility(View.GONE);
                            rv_product.setVisibility(View.VISIBLE);
                        } else {
                            view_not_exist.setVisibility(View.VISIBLE);
                            rv_product.setVisibility(View.GONE);
                        }
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }


    private void productList(String area, String category, Float startPrice, Float endPrice) {
        ProductListRequester requester = new ProductListRequester(getContext());
        if (!TextUtils.isEmpty(area))
            requester.setArea(area);
        if (!TextUtils.isEmpty(category))
            requester.setCategory(category);
        requester.setStartPrice(startPrice.intValue());
        requester.setEndPrice(endPrice.intValue());
        showLoading();
        request(requester,
                success -> {
                    ProductListResponser result = (ProductListResponser) success;
                    hideLoading();
                    if (result.getCode() == 200) {
                        if (result.getList().size() > 0) {
                            mProductActivityRecyclerViewAdapter.setData(result.getList());
                            mProductActivityRecyclerViewAdapter.notifyDataSetChanged();
                            view_not_exist.setVisibility(View.GONE);
                            rv_product.setVisibility(View.VISIBLE);
                        } else {
                            view_not_exist.setVisibility(View.VISIBLE);
                            rv_product.setVisibility(View.GONE);
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
