
package com.rightcode.unite.Adapter.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rightcode.unite.Component.MiddleRatingStarLayout;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.ImageWrapper;
import com.rightcode.unite.network.model.response.review.Review;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductReviewViewHolder extends CommonRecyclerViewHolder {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.iv_profile)
    ImageView iv_profile;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.rating)
    MiddleRatingStarLayout rating;
    @BindView(R.id.tv_content)
    TextView tv_content;

    private Context mContext;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public ProductReviewViewHolder(View viewHolder, Context context) {
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

    public void onBind(Review data) {
        switch (data.getPlatform()) {
            case "yanolja":
                ImageWrapper.load(mContext, R.drawable.ic_yanolja, iv_profile);
                break;
            case "myrealtrip":
                ImageWrapper.load(mContext, R.drawable.ic_myrealtrip, iv_profile);
                break;
            case "klook":
                ImageWrapper.load(mContext, R.drawable.ic_klook, iv_profile);
                break;
        }

        tv_name.setText(data.getName());
        tv_date.setText(data.getCreatedAt());
        rating.setRating(data.getRate());
        tv_content.setText(data.getContent());
    }

    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
