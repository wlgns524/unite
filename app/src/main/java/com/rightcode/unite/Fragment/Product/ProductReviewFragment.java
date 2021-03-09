package com.rightcode.unite.Fragment.Product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.unite.Adapter.RecyclerViewAdapter.ProductReviewRecyclerViewAdapter;
import com.rightcode.unite.Fragment.BaseFragment;
import com.rightcode.unite.R;
import com.rightcode.unite.network.requester.review.ReviewListRequester;
import com.rightcode.unite.network.responser.review.ReviewListResponser;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.unite.Util.ExtraData.EXTRA_PRODUCT_ID;

public class ProductReviewFragment extends BaseFragment {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.rv_review)
    RecyclerView rv_review;
    @BindView(R.id.view_not_exist)
    View view_not_exist;

    private View root;
    private ProductReviewRecyclerViewAdapter mProductReviewRecyclerViewAdapter;

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
        reviewList(getArguments().getLong(EXTRA_PRODUCT_ID));
        mProductReviewRecyclerViewAdapter = new ProductReviewRecyclerViewAdapter(getContext());
        rv_review.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv_review.setAdapter(mProductReviewRecyclerViewAdapter);
    }

    private void reviewList(Long productId) {
        ReviewListRequester requester = new ReviewListRequester(getContext());
        requester.setProductId(productId);

        request(requester,
                success -> {
                    ReviewListResponser result = (ReviewListResponser) success;
                    if (result.getCode() == 200) {
                        if (result.getList().size() > 0) {
                            mProductReviewRecyclerViewAdapter.setData(result.getList());
                            mProductReviewRecyclerViewAdapter.notifyDataSetChanged();
                            view_not_exist.setVisibility(View.GONE);
                            rv_review.setVisibility(View.VISIBLE);
                        } else {
                            view_not_exist.setVisibility(View.VISIBLE);
                            rv_review.setVisibility(View.GONE);
                        }
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                }, fail -> {
                    showServerErrorDialog(fail.getResultMsg());
                });
    }


    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
