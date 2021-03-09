package com.rightcode.unite.Activity.Product;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rightcode.unite.Activity.Basic.BaseActivity;
import com.rightcode.unite.Activity.Login.LoginActivity;
import com.rightcode.unite.Component.MiddleRatingStarLayout;
import com.rightcode.unite.Fragment.Product.ProductInformationFragment;
import com.rightcode.unite.Fragment.Product.ProductPartnerFragment;
import com.rightcode.unite.Fragment.Product.ProductReviewFragment;
import com.rightcode.unite.Fragment.TopFragment;
import com.rightcode.unite.MemberManager;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.FragmentUtil;
import com.rightcode.unite.Util.ImageWrapper;
import com.rightcode.unite.Util.Log;
import com.rightcode.unite.Util.MoneyHelper;
import com.rightcode.unite.network.model.response.product.Product;
import com.rightcode.unite.network.model.response.product.ProductDetail;
import com.rightcode.unite.network.model.response.product.ProductLink;
import com.rightcode.unite.network.requester.product.ProductDetailRequester;
import com.rightcode.unite.network.requester.wish.FavoriteRegisterRequester;
import com.rightcode.unite.network.requester.wish.FavoriteRemoveRequester;
import com.rightcode.unite.network.responser.product.ProductDetailResponser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.unite.Util.ExtraData.EXTRA_ACTIVITY_ACTION;
import static com.rightcode.unite.Util.ExtraData.EXTRA_ACTIVITY_COMPLETE;
import static com.rightcode.unite.Util.ExtraData.EXTRA_PRODUCT;
import static com.rightcode.unite.Util.ExtraData.EXTRA_PRODUCT_ID;
import static com.rightcode.unite.Util.ExtraData.EXTRA_PRODUCT_IMAGES;

public class ProductDetailActivity extends BaseActivity {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.iv_klook)
    ImageView iv_klook;
    @BindView(R.id.iv_myrealtrip)
    ImageView iv_myrealtrip;
    @BindView(R.id.iv_yanolja)
    ImageView iv_yanolja;

    @BindView(R.id.iv_image)
    ImageView iv_image;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.rating)
    MiddleRatingStarLayout rating;
    @BindView(R.id.tv_review_count)
    TextView tv_review_count;
    @BindView(R.id.iv_favorite)
    ImageView iv_favorite;

    @BindView(R.id.tv_information)
    TextView tv_information;
    @BindView(R.id.tv_review)
    TextView tv_review;
    @BindView(R.id.tv_accompany)
    TextView tv_accompany;

    @BindView(R.id.ll_price1)
    LinearLayout ll_price1;
    @BindView(R.id.tv_platform1)
    TextView tv_platform1;
    @BindView(R.id.tv_price1)
    TextView tv_price1;
    @BindView(R.id.tv_link1)
    TextView tv_link1;
    @BindView(R.id.ll_price2)
    LinearLayout ll_price2;
    @BindView(R.id.tv_platform2)
    TextView tv_platform2;
    @BindView(R.id.tv_price2)
    TextView tv_price2;
    @BindView(R.id.tv_link2)
    TextView tv_link2;
    @BindView(R.id.ll_price3)
    LinearLayout ll_price3;
    @BindView(R.id.tv_platform3)
    TextView tv_platform3;
    @BindView(R.id.tv_price3)
    TextView tv_price3;
    @BindView(R.id.tv_link3)
    TextView tv_link3;

    private TopFragment mTopFragment;
    private Fragment currentFragment;
    private Long productId;
    private ProductDetail productDetail;
    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ButterKnife.bind(this);
        initialize();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        currentFragment = null;
    }

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case EXTRA_ACTIVITY_ACTION: {
                    productDetail();
                    break;
                }
                case EXTRA_ACTIVITY_COMPLETE: {
                }
                default:
                    break;
            }
        }
    }


    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    @OnClick({R.id.tv_information, R.id.tv_review, R.id.tv_accompany, R.id.iv_favorite,
            R.id.tv_link1, R.id.tv_link2, R.id.tv_link3})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_information: {
                setFragment(ProductInformationFragment.newInstance());
                tv_information.setSelected(true);
                tv_review.setSelected(false);
                tv_accompany.setSelected(false);
                break;
            }
            case R.id.tv_review: {
                setFragment(ProductReviewFragment.newInstance());
                tv_information.setSelected(false);
                tv_review.setSelected(true);
                tv_accompany.setSelected(false);
                break;
            }
            case R.id.tv_accompany: {
                setFragment(ProductPartnerFragment.newInstance());
                tv_information.setSelected(false);
                tv_review.setSelected(false);
                tv_accompany.setSelected(true);
                break;
            }
            case R.id.iv_favorite: {
                if (MemberManager.getInstance(ProductDetailActivity.this).isLogin()) {
                    if (productDetail.getIsWishMine()) {
                        favoriteRemove();
                    } else {
                        favoriteRegister();
                    }
                } else {
                    Intent intent = new Intent(ProductDetailActivity.this, LoginActivity.class);
                    startActivityForResult(intent, EXTRA_ACTIVITY_ACTION);
                }
                break;
            }
            case R.id.tv_link1: {
                if (productDetail.getProducts() != null && productDetail.getProducts().size() > 0) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(productDetail.getProducts().get(0).getLink()));
                    startActivity(intent);
                }
                break;
            }
            case R.id.tv_link2: {
                if (productDetail.getProducts() != null && productDetail.getProducts().size() > 1) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(productDetail.getProducts().get(1).getLink()));
                    startActivity(intent);
                }
                break;
            }
            case R.id.tv_link3: {
                if (productDetail.getProducts() != null && productDetail.getProducts().size() > 2) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(productDetail.getProducts().get(2).getLink()));
                    startActivity(intent);
                }
                break;
            }
        }
    }


    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.ic_back);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, v -> {
            finishWithAnim();
        });

        if (getIntent() != null) {
            productId = getIntent().getLongExtra(EXTRA_PRODUCT_ID, 0L);
            productDetail();
        }
    }

    private void productDetail() {
        showLoading();
        ProductDetailRequester requester = new ProductDetailRequester(ProductDetailActivity.this);
        requester.setId(productId);

        request(requester,
                success -> {
                    ProductDetailResponser result = (ProductDetailResponser) success;
                    hideLoading();
                    if (result.getCode() == 200) {
                        productDetail = result.getData();
                        initLayout();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void initLayout() {
        if (mTopFragment != null)
            mTopFragment.setText(TopFragment.Menu.CENTER, productDetail.getArea());
        ImageWrapper.loadFitCenter(ProductDetailActivity.this, productDetail.getThumbnail(), iv_image);
        tv_name.setText(productDetail.getName());
        rating.setRating(productDetail.getAverageRate());
        tv_review_count.setText(String.format("(%d)", productDetail.getReviewCount()));
        setFragment(ProductInformationFragment.newInstance());
        tv_information.setSelected(true);
        iv_favorite.setSelected(productDetail.getIsWishMine());



        if (productDetail.getProducts() != null) {
            if (productDetail.getProducts().size() > 2) {
                ll_price1.setVisibility(TextUtils.isEmpty(productDetail.getProducts().get(0).getLink()) ? View.GONE : View.VISIBLE);
                ll_price2.setVisibility(TextUtils.isEmpty(productDetail.getProducts().get(1).getLink()) ? View.GONE : View.VISIBLE);
                ll_price3.setVisibility(TextUtils.isEmpty(productDetail.getProducts().get(2).getLink()) ? View.GONE : View.VISIBLE);

                tv_link1.setVisibility(TextUtils.isEmpty(productDetail.getProducts().get(0).getLink()) ? View.GONE : View.VISIBLE);
                tv_link2.setVisibility(TextUtils.isEmpty(productDetail.getProducts().get(1).getLink()) ? View.GONE : View.VISIBLE);
                tv_link3.setVisibility(TextUtils.isEmpty(productDetail.getProducts().get(2).getLink()) ? View.GONE : View.VISIBLE);

                tv_platform1.setText(productDetail.getProducts().get(0).getPlatform());
                tv_platform2.setText(productDetail.getProducts().get(1).getPlatform());
                tv_platform3.setText(productDetail.getProducts().get(2).getPlatform());

                tv_price1.setText(MoneyHelper.getKor(productDetail.getProducts().get(0).getPrice()));
                tv_price2.setText(MoneyHelper.getKor(productDetail.getProducts().get(1).getPrice()));
                tv_price3.setText(MoneyHelper.getKor(productDetail.getProducts().get(2).getPrice()));
            } else if (productDetail.getProducts().size() > 1) {
                ll_price1.setVisibility(TextUtils.isEmpty(productDetail.getProducts().get(0).getLink()) ? View.GONE : View.VISIBLE);
                ll_price2.setVisibility(TextUtils.isEmpty(productDetail.getProducts().get(1).getLink()) ? View.GONE : View.VISIBLE);

                tv_link1.setVisibility(TextUtils.isEmpty(productDetail.getProducts().get(0).getLink()) ? View.GONE : View.VISIBLE);
                tv_link2.setVisibility(TextUtils.isEmpty(productDetail.getProducts().get(1).getLink()) ? View.GONE : View.VISIBLE);

                tv_platform1.setText(productDetail.getProducts().get(0).getPlatform());
                tv_platform2.setText(productDetail.getProducts().get(1).getPlatform());

                tv_price1.setText(MoneyHelper.getKor(productDetail.getProducts().get(0).getPrice()));
                tv_price2.setText(MoneyHelper.getKor(productDetail.getProducts().get(1).getPrice()));
            } else if (productDetail.getProducts().size() > 0) {
                ll_price1.setVisibility(TextUtils.isEmpty(productDetail.getProducts().get(0).getLink()) ? View.GONE : View.VISIBLE);
                tv_platform1.setText(productDetail.getProducts().get(0).getPlatform());
                tv_price1.setText(MoneyHelper.getKor(productDetail.getProducts().get(0).getPrice()));
                tv_link1.setVisibility(TextUtils.isEmpty(productDetail.getProducts().get(0).getLink()) ? View.GONE : View.VISIBLE);
            }

            for (ProductLink productLink : productDetail.getProducts()) {
                if (productLink.getPlatform().equals("yanolja")) {
                    iv_yanolja.setVisibility(View.VISIBLE);
                } else if (productLink.getPlatform().equals("myrealtrip")) {
                    iv_myrealtrip.setVisibility(View.VISIBLE);
                } else if (productLink.getPlatform().equals("klook")) {
                    iv_klook.setVisibility(View.VISIBLE);
                }
            }
        }

//        if (productDetail.getPlatform().equals("yanolja")) {

//            if (productDetail.getMyrealtripPrice() > -1) {
//                tv_platform2.setText("마이리얼트립");
//                tv_price2.setText(MoneyHelper.getKor(productDetail.getMyrealtripPrice()));
//                ll_price2.setVisibility(View.VISIBLE);
//                iv_myrealtrip.setVisibility(View.VISIBLE);
//            } else {
//                ll_price2.setVisibility(View.GONE);
//                iv_myrealtrip.setVisibility(View.GONE);
//            }
//            if (productDetail.getKlookPrice() > -1) {
//                tv_platform3.setText("클룩");
//                tv_price3.setText(MoneyHelper.getKor(productDetail.getKlookPrice()));
//                ll_price3.setVisibility(View.GONE);
//                iv_klook.setVisibility(View.VISIBLE);
//            } else {
//                ll_price3.setVisibility(View.GONE);
//                iv_klook.setVisibility(View.GONE);
//            }
//        } else if (productDetail.getPlatform().equals("myrealtrip")) {
//            tv_platform1.setText("마이리얼트립");
//            iv_myrealtrip.setVisibility(View.VISIBLE);
//            tv_price1.setText(MoneyHelper.getKor(productDetail.getMyrealtripPrice()));
//            if (productDetail.getYanoljaPrice() > -1) {
//                tv_platform2.setText("야놀자");
//                tv_price2.setText(MoneyHelper.getKor(productDetail.getYanoljaPrice()));
//                ll_price2.setVisibility(View.VISIBLE);
//                iv_yanolja.setVisibility(View.VISIBLE);
//            } else {
//                ll_price2.setVisibility(View.GONE);
//                iv_yanolja.setVisibility(View.GONE);
//            }
//            if (productDetail.getKlookPrice() > -1) {
//                tv_platform3.setText("클룩");
//                tv_price3.setText(MoneyHelper.getKor(productDetail.getKlookPrice()));
//                ll_price3.setVisibility(View.VISIBLE);
//                iv_klook.setVisibility(View.VISIBLE);
//            } else {
//                ll_price3.setVisibility(View.GONE);
//                iv_klook.setVisibility(View.GONE);
//            }
//        } else if (productDetail.getPlatform().equals("klook")) {
//            tv_platform1.setText("클룩");
//            iv_klook.setVisibility(View.VISIBLE);
//            tv_price1.setText(MoneyHelper.getKor(productDetail.getKlookPrice()));
//            if (productDetail.getMyrealtripPrice() > -1) {
//                tv_platform2.setText("마이리얼트립");
//                tv_price2.setText(MoneyHelper.getKor(productDetail.getMyrealtripPrice()));
//                ll_price2.setVisibility(View.GONE);
//                iv_myrealtrip.setVisibility(View.VISIBLE);
//            } else {
//                ll_price2.setVisibility(View.GONE);
//                iv_myrealtrip.setVisibility(View.GONE);
//            }
//            if (productDetail.getYanoljaPrice() > -1) {
//                tv_platform3.setText("야놀자");
//                tv_price3.setText(MoneyHelper.getKor(productDetail.getYanoljaPrice()));
//                ll_price3.setVisibility(View.VISIBLE);
//                iv_yanolja.setVisibility(View.VISIBLE);
//            } else {
//                ll_price3.setVisibility(View.GONE);
//                iv_yanolja.setVisibility(View.GONE);
//            }
//        }
    }

    private void setFragment(Fragment f) {
        if (f == null)
            return;
        if (f instanceof ProductReviewFragment) {
            Bundle b = new Bundle();
            b.putLong(EXTRA_PRODUCT_ID, productId);
            f.setArguments(b);
        } else if (f instanceof ProductInformationFragment && productDetail != null) {
            Bundle b = new Bundle();
            b.putStringArrayList(EXTRA_PRODUCT_IMAGES, productDetail.getProductImages());
            f.setArguments(b);
        } else if (f instanceof ProductPartnerFragment) {
            Product product = new Product();
            product.setId(productDetail.getId());
            product.setNation(productDetail.getNation());
            product.setArea(productDetail.getArea());
            product.setCategory(productDetail.getCategory());
            product.setName(productDetail.getName());
            product.setPrice(productDetail.getPrice());
            product.setThumbnail(productDetail.getThumbnail());
            product.setPlatform(productDetail.getPlatform());
            product.setLink(productDetail.getLink());
            product.setAverageRate(productDetail.getAverageRate());
            product.setKlookPrice(product.getKlookPrice());
            product.setMyrealtripPrice(productDetail.getMyrealtripPrice());
            product.setYanoljaPrice(productDetail.getYanoljaPrice());

            Bundle b = new Bundle();
            b.putLong(EXTRA_PRODUCT_ID, productId);
            b.putSerializable(EXTRA_PRODUCT, product);
            f.setArguments(b);
        }
        FragmentUtil.startFragment(getSupportFragmentManager(), R.id.fl_container, f);
        FragmentUtil.removeFragment(getSupportFragmentManager(), currentFragment);
        currentFragment = f;
    }

    private void favoriteRegister() {
        FavoriteRegisterRequester requester = new FavoriteRegisterRequester(ProductDetailActivity.this);
        requester.setProductId(productId);

        request(requester,
                success -> {
                    if (success.getCode() == 200) {
                        iv_favorite.setSelected(true);
                    } else {
                        showServerErrorDialog(success.getResultMsg());
                    }
                }, fail -> {
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void favoriteRemove() {
        FavoriteRemoveRequester requester = new FavoriteRemoveRequester(ProductDetailActivity.this);
        requester.setProductId(productId);

        request(requester,
                success -> {
                    if (success.getCode() == 200) {
                        iv_favorite.setSelected(false);
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
