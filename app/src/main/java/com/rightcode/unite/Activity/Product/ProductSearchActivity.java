package com.rightcode.unite.Activity.Product;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.unite.Activity.Basic.BaseActivity;
import com.rightcode.unite.Adapter.RecyclerViewAdapter.ProductSearchRecyclerViewAdapter;
import com.rightcode.unite.Component.RecyclerViewOnClickListener;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.Log;
import com.rightcode.unite.Util.ToastUtil;
import com.rightcode.unite.network.requester.product.ProductListRequester;
import com.rightcode.unite.network.responser.product.ProductListResponser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.unite.Util.ExtraData.EXTRA_PRODUCT;

public class ProductSearchActivity extends BaseActivity {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.et_product)
    EditText et_product;
    @BindView(R.id.rv_product_search)
    RecyclerView rv_product_search;

    private ProductSearchRecyclerViewAdapter mProductSearchRecyclerViewAdapter;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);

        ButterKnife.bind(this);
        initialize();
        productList("");
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

    @OnClick({R.id.iv_search})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.iv_search: {
                if (TextUtils.isEmpty(et_product.getText())) {
                    ToastUtil.show(ProductSearchActivity.this, "검색어를 입력해주세요");
                    return;
                }

                productList(et_product.getText().toString());
                break;
            }
        }
    }

    private void initialize() {
        mProductSearchRecyclerViewAdapter = new ProductSearchRecyclerViewAdapter(ProductSearchActivity.this);
        rv_product_search.setLayoutManager(new LinearLayoutManager(ProductSearchActivity.this, LinearLayoutManager.VERTICAL, false));
        rv_product_search.setAdapter(mProductSearchRecyclerViewAdapter);
        rv_product_search.addOnItemTouchListener(new RecyclerViewOnClickListener(ProductSearchActivity.this, (view, position) -> {
            Intent i = new Intent();
            i.putExtra(EXTRA_PRODUCT, mProductSearchRecyclerViewAdapter.getData().get(position));
            setResult(RESULT_OK, i);
            finishWithAnim();
        }));
    }

    private void productList(String search) {
        ProductListRequester requester = new ProductListRequester(ProductSearchActivity.this);
        if (!TextUtils.isEmpty(search))
            requester.setSearch(search);
        showLoading();
        request(requester,
                success -> {
                    ProductListResponser result = (ProductListResponser) success;
                    hideLoading();
                    if (result.getCode() == 200) {
                        mProductSearchRecyclerViewAdapter.setData(result.getList());
                        mProductSearchRecyclerViewAdapter.notifyDataSetChanged();
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
