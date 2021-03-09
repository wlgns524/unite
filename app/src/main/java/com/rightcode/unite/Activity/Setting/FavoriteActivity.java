package com.rightcode.unite.Activity.Setting;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.unite.Activity.Basic.BaseActivity;
import com.rightcode.unite.Adapter.RecyclerViewAdapter.FavoriteRecyclerViewAdapter;
import com.rightcode.unite.Dialog.CommonDialog;
import com.rightcode.unite.Fragment.TopFragment;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.FragmentUtil;
import com.rightcode.unite.network.requester.product.ProductListRequester;
import com.rightcode.unite.network.requester.wish.FavoriteRemoveRequester;
import com.rightcode.unite.network.responser.product.ProductListResponser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends BaseActivity {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.rv_favorite)
    RecyclerView rv_favorite;

    TopFragment mTopFragment;
    private FavoriteRecyclerViewAdapter mFavoriteRecyclerViewAdapter;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        ButterKnife.bind(this);
        initialize();
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
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "삼풍 찜 목록");
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.ic_back);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, v -> finishWithAnim());
        mTopFragment.setText(TopFragment.Menu.RIGHT, "모두삭제");
        mTopFragment.setTextColor(TopFragment.Menu.RIGHT, R.color.red);
        mTopFragment.setListener(TopFragment.Menu.RIGHT, v -> {
            CommonDialog commonDialog = new CommonDialog(FavoriteActivity.this);
            commonDialog.setMessage("찜 목록을 전체 삭제하시겠습니까 ?");
            commonDialog.setPositiveButton("확인", v1 -> {
                favoriteRemove();
                commonDialog.dismiss();
            });
            commonDialog.setNegativeButton("취소", v1 -> {
                commonDialog.dismiss();
            });
            commonDialog.show();
        });

        mFavoriteRecyclerViewAdapter = new FavoriteRecyclerViewAdapter(FavoriteActivity.this);
        rv_favorite.setLayoutManager(new LinearLayoutManager(FavoriteActivity.this, LinearLayoutManager.VERTICAL, false));
        rv_favorite.setAdapter(mFavoriteRecyclerViewAdapter);
        productList();
    }

    private void productList() {
        ProductListRequester requester = new ProductListRequester(FavoriteActivity.this);
        requester.setWishMine(true);
        showLoading();
        request(requester,
                success -> {
                    ProductListResponser result = (ProductListResponser) success;
                    hideLoading();
                    if (result.getCode() == 200) {
                        mFavoriteRecyclerViewAdapter.setData(result.getList());
                        mFavoriteRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void favoriteRemove() {
        FavoriteRemoveRequester requester = new FavoriteRemoveRequester(FavoriteActivity.this);
        requester.setProductId(0L);

        request(requester,
                success -> {
                    if (success.getCode() == 200) {
                        productList();
                    } else {
                        showServerErrorDialog(success.getResultMsg());
                    }
                }, fail -> {
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
