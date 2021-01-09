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
import com.rightcode.unite.Adapter.RecyclerViewAdapter.ProductAccompanyRecyclerViewAdapter;
import com.rightcode.unite.Adapter.RecyclerViewAdapter.TourRecyclerViewAdapter;
import com.rightcode.unite.Component.GridSpacingItemDecoration;
import com.rightcode.unite.Component.RecyclerViewOnClickListener;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.unite.Adapter.ViewHolder.CommonRecyclerViewHolder.HEADER_VIEW;
import static com.rightcode.unite.Adapter.ViewHolder.CommonRecyclerViewHolder.ITEM_VIEW;

public class ProductAccompanyFragment extends BaseFragment {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.rv_accommpany)
    RecyclerView rv_accommpany;

    private View root;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public static ProductAccompanyFragment newInstance() {
        return new ProductAccompanyFragment();
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_product_accompany, container, false);

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
        ProductAccompanyRecyclerViewAdapter mProductAccompanyRecyclerViewAdapter = new ProductAccompanyRecyclerViewAdapter(getContext());
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(mProductAccompanyRecyclerViewAdapter.getItemViewType(position)){
                    case HEADER_VIEW:
                        return 2;
                    case ITEM_VIEW:
                    default:
                        return 1;
                }
            }
        });
        rv_accommpany.setLayoutManager(mLayoutManager);
        rv_accommpany.setAdapter(mProductAccompanyRecyclerViewAdapter);
//        rv_accommpany.addItemDecoration(new GridSpacingItemDecoration(2, 10, false));

    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
