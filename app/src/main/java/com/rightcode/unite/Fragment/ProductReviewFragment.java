package com.rightcode.unite.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.unite.Adapter.RecyclerViewAdapter.ProductReviewRecyclerViewAdapter;
import com.rightcode.unite.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductReviewFragment extends BaseFragment {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.rv_review)
    RecyclerView rv_review;

    private View root;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public static ProductReviewFragment newInstance() {
        return new ProductReviewFragment();
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_product_review, container, false);

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
        ProductReviewRecyclerViewAdapter mProductReviewRecyclerViewAdapter = new ProductReviewRecyclerViewAdapter(getContext());
        rv_review.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv_review.setAdapter(mProductReviewRecyclerViewAdapter);
    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
