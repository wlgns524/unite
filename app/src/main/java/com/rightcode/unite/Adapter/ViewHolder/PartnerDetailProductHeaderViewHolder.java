
package com.rightcode.unite.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rightcode.unite.Component.MiddleRatingStarLayout;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.ImageWrapper;
import com.rightcode.unite.Util.Log;
import com.rightcode.unite.Util.MoneyHelper;
import com.rightcode.unite.network.model.response.partner.PartnerDetail;
import com.rightcode.unite.network.model.response.product.ProductDetail;
import com.rightcode.unite.network.model.response.product.ProductLink;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PartnerDetailProductHeaderViewHolder extends CommonRecyclerViewHolder {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------


    @BindView(R.id.iv_image)
    ImageView iv_image;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.rating)
    MiddleRatingStarLayout rating;
    @BindView(R.id.tv_review_count)
    TextView tv_review_count;


    @BindView(R.id.iv_profile)
    ImageView iv_profile;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_join)
    TextView tv_join;
    @BindView(R.id.tv_people)
    TextView tv_people;
    @BindView(R.id.tv_view)
    TextView tv_view;
    @BindView(R.id.tv_want_gender)
    TextView tv_want_gender;
    @BindView(R.id.tv_want_age)
    TextView tv_want_age;
    @BindView(R.id.tv_want_people)
    TextView tv_want_people;


    @BindView(R.id.iv_klook)
    ImageView iv_klook;
    @BindView(R.id.iv_myrealtrip)
    ImageView iv_myrealtrip;
    @BindView(R.id.iv_yanolja)
    ImageView iv_yanolja;

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

    private Context mContext;
    private ProductDetail productDetail;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public PartnerDetailProductHeaderViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    public void onBind(PartnerDetail data, ProductDetail productDetail) {
        this.productDetail = productDetail;
        ImageWrapper.loadCircle(mContext, data.getUserImage(), iv_profile, R.drawable.ic_profile);
        tv_user_name.setText(data.getUserName());
        tv_date.setText(data.getCreatedAt());
        tv_title.setText(data.getTitle());
        tv_content.setText(data.getContent());
        tv_join.setText(String.format("%d", data.getJoin()));
        tv_people.setText(String.format("%d/%d명", data.getJoin(), data.getPeople()));
        tv_view.setText(String.format("%d", data.getViewCount()));
        tv_want_gender.setText(data.getGender());
        tv_want_age.setText(data.getAge());
        tv_want_people.setText(String.format("%d명", data.getPeople()));


        // 상품상세
        ImageWrapper.load(mContext, productDetail.getThumbnail(), iv_image);
        tv_name.setText(productDetail.getName());
        rating.setRating(productDetail.getAverageRate());
        tv_review_count.setText(String.format("(%d)", productDetail.getReviewCount()));

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
    }

    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    @OnClick({R.id.tv_link1, R.id.tv_link2, R.id.tv_link3})
    void onClickMenu(View view){
        switch (view.getId()){
            case R.id.tv_link1:{
                if (productDetail.getProducts() != null && productDetail.getProducts().size() > 0) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(productDetail.getProducts().get(0).getLink()));
                    startActivity(intent);
                }
                break;
            }
            case R.id.tv_link2:{
                if (productDetail.getProducts() != null && productDetail.getProducts().size() > 1) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(productDetail.getProducts().get(1).getLink()));
                    startActivity(intent);
                }
                break;
            }
            case R.id.tv_link3:{
                if (productDetail.getProducts() != null && productDetail.getProducts().size() > 2) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(productDetail.getProducts().get(2).getLink()));
                    startActivity(intent);
                }
                break;
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
