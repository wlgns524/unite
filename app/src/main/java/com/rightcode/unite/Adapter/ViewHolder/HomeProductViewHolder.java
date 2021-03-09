
package com.rightcode.unite.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rightcode.unite.Activity.Product.ProductDetailActivity;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.ImageWrapper;
import com.rightcode.unite.Util.MoneyHelper;
import com.rightcode.unite.network.model.response.product.Product;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.unite.Util.ExtraData.EXTRA_PRODUCT_ID;

public class HomeProductViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------


    @BindView(R.id.iv_product)
    ImageView iv_product;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_rate)
    TextView tv_rate;
    @BindView(R.id.tv_area)
    TextView tv_area;
    @BindView(R.id.tv_category)
    TextView tv_category;
    @BindView(R.id.tv_price)
    TextView tv_price;

    private Context mContext;
    private Product data;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public HomeProductViewHolder(View viewHolder, Context context) {
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
        tv_price.setText(MoneyHelper.getKor(data.getPrice()));
        ImageWrapper.loadRound(mContext, data.getThumbnail(), iv_product, R.dimen.image_corner_radius_8);
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
