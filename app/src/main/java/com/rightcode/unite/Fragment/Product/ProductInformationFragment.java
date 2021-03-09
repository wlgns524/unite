package com.rightcode.unite.Fragment.Product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.unite.Adapter.RecyclerViewAdapter.ProductInformationRecyclerViewAdapter;
import com.rightcode.unite.Fragment.BaseFragment;
import com.rightcode.unite.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.unite.Util.ExtraData.EXTRA_PRODUCT_IMAGES;

public class ProductInformationFragment extends BaseFragment {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.rv_product_information)
    RecyclerView rv_product_information;

    private View root;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public static ProductInformationFragment newInstance() {
        return new ProductInformationFragment();
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_product_information, container, false);

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
        ProductInformationRecyclerViewAdapter mProductInformationRecyclerViewAdapter = new ProductInformationRecyclerViewAdapter(getContext());
        rv_product_information.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mProductInformationRecyclerViewAdapter.setData(getArguments().getStringArrayList(EXTRA_PRODUCT_IMAGES));
        rv_product_information.setAdapter(mProductInformationRecyclerViewAdapter);
    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
