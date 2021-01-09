package com.rightcode.unite.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.unite.Adapter.RecyclerViewAdapter.AroundRecyclerViewAdapter;
import com.rightcode.unite.Adapter.RecyclerViewAdapter.LocationRecyclerViewAdapter;
import com.rightcode.unite.Adapter.RecyclerViewAdapter.PopularRecyclerViewAdapter;
import com.rightcode.unite.Adapter.RecyclerViewAdapter.TourRecyclerViewAdapter;
import com.rightcode.unite.Component.GridSpacingItemDecoration;
import com.rightcode.unite.Component.RecyclerViewOnClickListener;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityFragment extends BaseFragment {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.rv_location)
    RecyclerView rv_location;
    @BindView(R.id.rv_tour)
    RecyclerView rv_tour;
    @BindView(R.id.rv_popular)
    RecyclerView rv_popular;
    @BindView(R.id.rv_around)
    RecyclerView rv_around;

    private View root;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public static ActivityFragment newInstance() {
        return new ActivityFragment();
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_activity, container, false);

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
        LocationRecyclerViewAdapter mLocationRecyclerViewAdapter = new LocationRecyclerViewAdapter(getContext());
        rv_location.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_location.setAdapter(mLocationRecyclerViewAdapter);
        rv_location.addOnItemTouchListener(new RecyclerViewOnClickListener(getContext(), (view, position) -> {
            Log.d(mLocationRecyclerViewAdapter.getData().get(position));
            mLocationRecyclerViewAdapter.setmSelectList(position);
            rv_location.smoothScrollToPosition(position);
            Log.d(mLocationRecyclerViewAdapter.getSelect());
        }));
        TourRecyclerViewAdapter mTourRecyclerViewAdapter = new TourRecyclerViewAdapter(getContext());
        rv_tour.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_tour.setAdapter(mTourRecyclerViewAdapter);

        PopularRecyclerViewAdapter mPopularRecyclerViewAdapter = new PopularRecyclerViewAdapter(getContext());
        rv_popular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv_popular.setAdapter(mPopularRecyclerViewAdapter);

        AroundRecyclerViewAdapter mAroundRecyclerViewAdapter = new AroundRecyclerViewAdapter(getContext());
        rv_around.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv_around.setAdapter(mAroundRecyclerViewAdapter);
        rv_around.addItemDecoration(new GridSpacingItemDecoration(2, 10, false));
    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
