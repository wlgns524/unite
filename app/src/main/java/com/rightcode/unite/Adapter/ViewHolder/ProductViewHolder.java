
package com.rightcode.unite.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rightcode.unite.Activity.Product.ProductDetailActivity;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.ImageWrapper;
import com.rightcode.unite.Util.Log;
import com.rightcode.unite.Util.MoneyHelper;
import com.rightcode.unite.network.model.response.product.Product;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.unite.Util.ExtraData.EXTRA_PRODUCT_ID;

public class ProductViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.iv_activity)
    ImageView iv_activity;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_rate)
    TextView tv_rate;
    @BindView(R.id.tv_area)
    TextView tv_area;
    @BindView(R.id.tv_category)
    TextView tv_category;
    @BindView(R.id.tv_price1)
    TextView tv_price1;
    @BindView(R.id.tv_price2)
    TextView tv_price2;
    @BindView(R.id.tv_price3)
    TextView tv_price3;

    private Context mContext;
    private Product data;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public ProductViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(mContext, ProductDetailActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, data.getId());
        startActivity(intent);
    }

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    public void onBind(Product data) {
        this.data = data;
        tv_name.setText(data.getName());
        tv_area.setText(data.getArea());
        tv_category.setText(data.getCategory());
        tv_rate.setText(String.format("%1.1f", data.getAverageRate()));
        Drawable img = null;
        if (data.getPlatform().equals("yanolja")) {
            img = mContext.getResources().getDrawable(R.drawable.ic_yanolja_small);
            tv_price1.setText(MoneyHelper.getKor(data.getYanoljaPrice()));
            if (data.getMyrealtripPrice() > -1) {
                tv_price2.setText(MoneyHelper.getKor(data.getMyrealtripPrice()));
                tv_price2.setVisibility(View.VISIBLE);
            } else {
                tv_price2.setVisibility(View.INVISIBLE);
            }
            if (data.getKlookPrice() > -1) {
                tv_price3.setText(MoneyHelper.getKor(data.getKlookPrice()));
                tv_price3.setVisibility(View.VISIBLE);
            } else {
                tv_price3.setVisibility(View.INVISIBLE);
            }
        } else if (data.getPlatform().equals("myrealtrip")) {
            img = mContext.getResources().getDrawable(R.drawable.ic_myrealtrip_small);
            tv_price1.setText(MoneyHelper.getKor(data.getMyrealtripPrice()));
            if (data.getYanoljaPrice() > -1) {
                tv_price2.setText(MoneyHelper.getKor(data.getYanoljaPrice()));
                tv_price2.setVisibility(View.VISIBLE);
            } else {
                tv_price2.setVisibility(View.INVISIBLE);
            }
            if (data.getKlookPrice() > -1) {
                tv_price3.setText(MoneyHelper.getKor(data.getKlookPrice()));
                tv_price3.setVisibility(View.VISIBLE);
            } else {
                tv_price3.setVisibility(View.INVISIBLE);
            }
        } else if (data.getPlatform().equals("klook")) {
            img = mContext.getResources().getDrawable(R.drawable.ic_klook_small);
            tv_price1.setText(MoneyHelper.getKor(data.getKlookPrice()));
            if (data.getMyrealtripPrice() > -1) {
                tv_price2.setText(MoneyHelper.getKor(data.getMyrealtripPrice()));
                tv_price2.setVisibility(View.VISIBLE);
            } else {
                tv_price2.setVisibility(View.INVISIBLE);
            }
            if (data.getYanoljaPrice() > -1) {
                tv_price3.setText(MoneyHelper.getKor(data.getYanoljaPrice()));
                tv_price3.setVisibility(View.VISIBLE);
            } else {
                tv_price3.setVisibility(View.INVISIBLE);
            }
        }

        tv_price1.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        ImageWrapper.loadRound(mContext, data.getThumbnail(), iv_activity, R.dimen.image_corner_radius_8);
    }

    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    @OnClick({R.id.tv_product_web})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_product_web: {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getLink()));
                startActivity(intent);
                break;
            }
        }
    }
    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
