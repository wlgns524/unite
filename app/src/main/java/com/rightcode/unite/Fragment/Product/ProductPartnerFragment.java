package com.rightcode.unite.Fragment.Product;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.unite.Activity.Partner.PartnerRegisterActivity;
import com.rightcode.unite.Adapter.RecyclerViewAdapter.ProductPartnerRecyclerViewAdapter;
import com.rightcode.unite.Fragment.BaseFragment;
import com.rightcode.unite.R;
import com.rightcode.unite.network.model.response.product.Product;
import com.rightcode.unite.network.requester.partner.PartnerListRequester;
import com.rightcode.unite.network.responser.partner.PartnerListResponser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.unite.Util.ExtraData.EXTRA_PRODUCT;
import static com.rightcode.unite.Util.ExtraData.EXTRA_PRODUCT_ID;

public class ProductPartnerFragment extends BaseFragment {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.rv_product_partner)
    RecyclerView rv_product_partner;
    @BindView(R.id.view_not_exist)
    View view_not_exist;

    private View root;
    private ProductPartnerRecyclerViewAdapter mProductPartnerRecyclerViewAdapter;
    private Product data;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public static ProductPartnerFragment newInstance() {
        return new ProductPartnerFragment();
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_product_partner, container, false);

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

    @OnClick({R.id.header})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.header: {
                Intent intent = new Intent(getContext(), PartnerRegisterActivity.class);
                intent.putExtra(EXTRA_PRODUCT, data);
                startActivity(intent);
                break;
            }
        }
    }

    private void initialize() {
        partnerList(getArguments().getLong(EXTRA_PRODUCT_ID));
        data = (Product) getArguments().getSerializable(EXTRA_PRODUCT);
        mProductPartnerRecyclerViewAdapter = new ProductPartnerRecyclerViewAdapter(getContext());
        mProductPartnerRecyclerViewAdapter.setHeader((Product) getArguments().getSerializable(EXTRA_PRODUCT));
        rv_product_partner.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv_product_partner.setAdapter(mProductPartnerRecyclerViewAdapter);
    }

    private void partnerList(Long productId) {
        PartnerListRequester requester = new PartnerListRequester(getContext());
        requester.setProductId(productId);

        request(requester,
                success -> {
                    PartnerListResponser result = (PartnerListResponser) success;
                    if (result.getCode() == 200) {
                        if (result.getList().size() > 0) {
                            mProductPartnerRecyclerViewAdapter.setData(result.getList());
                            mProductPartnerRecyclerViewAdapter.notifyDataSetChanged();
                            view_not_exist.setVisibility(View.GONE);
                            rv_product_partner.setVisibility(View.VISIBLE);
                        } else {
                            view_not_exist.setVisibility(View.VISIBLE);
                            rv_product_partner.setVisibility(View.GONE);
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
