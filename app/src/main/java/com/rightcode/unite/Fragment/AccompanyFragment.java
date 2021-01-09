package com.rightcode.unite.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.unite.Adapter.RecyclerViewAdapter.AccompanyPopularAreaRecyclerViewAdapter;
import com.rightcode.unite.Adapter.RecyclerViewAdapter.AccompanyPopularProductRecyclerViewAdapter;
import com.rightcode.unite.Adapter.RecyclerViewAdapter.AccompanyRecyclerViewAdapter;
import com.rightcode.unite.Adapter.RecyclerViewAdapter.AroundActivityRecyclerViewAdapter;
import com.rightcode.unite.Component.GridSpacingItemDecoration;
import com.rightcode.unite.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccompanyFragment extends BaseFragment {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.rv_around_activity)
    RecyclerView rv_around_activity;
    @BindView(R.id.rv_accommpany_popular_area)
    RecyclerView rv_accommpany_popular_area;
    @BindView(R.id.rv_accommpany_popular_product)
    RecyclerView rv_accommpany_popular_product;
    @BindView(R.id.rv_accommpany)
    RecyclerView rv_accommpany;
    @BindView(R.id.ll_text)
    LinearLayout ll_text;

    private View root;
    private int overallXScroll = 0;
    private float alpha = 0;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public static AccompanyFragment newInstance() {
        return new AccompanyFragment();
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_accompany, container, false);

        ButterKnife.bind(this, root);
        initialize();
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
        AroundActivityRecyclerViewAdapter mAroundActivityRecyclerViewAdapter = new AroundActivityRecyclerViewAdapter(getContext());
        rv_around_activity.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv_around_activity.setAdapter(mAroundActivityRecyclerViewAdapter);
        rv_around_activity.addItemDecoration(new GridSpacingItemDecoration(2, 10, false));

        rv_accommpany_popular_area.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        AccompanyPopularAreaRecyclerViewAdapter mAccompanyPopularRecyclerViewAdapter = new AccompanyPopularAreaRecyclerViewAdapter(getContext());
        rv_accommpany_popular_area.setAdapter(mAccompanyPopularRecyclerViewAdapter);
        rv_accommpany_popular_area.addOnScrollListener(onScrollListener);

        rv_accommpany_popular_product.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        AccompanyPopularProductRecyclerViewAdapter mAccompanyPopularProductRecyclerViewAdapter = new AccompanyPopularProductRecyclerViewAdapter(getContext());
        rv_accommpany_popular_product.setAdapter(mAccompanyPopularProductRecyclerViewAdapter);

        rv_accommpany.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        AccompanyRecyclerViewAdapter mAccompanyRecyclerViewAdapter = new AccompanyRecyclerViewAdapter(getContext());
        rv_accommpany.setAdapter(mAccompanyRecyclerViewAdapter);
    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------


    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            overallXScroll += dx;
            alpha = overallXScroll * 0.005f;
            alpha = -(alpha - 1);
            ll_text.setAlpha(alpha);
        }
    };
}
