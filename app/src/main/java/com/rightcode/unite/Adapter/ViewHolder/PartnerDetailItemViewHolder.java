
package com.rightcode.unite.Adapter.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rightcode.unite.Component.MiddleRatingStarLayout;
import com.rightcode.unite.R;
import com.rightcode.unite.network.model.response.partner.PartnerDetail;
import com.rightcode.unite.network.model.response.review.Review;
import com.rightcode.unite.network.socket.model.ChatRoom;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PartnerDetailItemViewHolder extends CommonRecyclerViewHolder {

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

    public PartnerDetailItemViewHolder(View viewHolder, Context context) {
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

    public void onBind(PartnerDetail data) {
//        tv_name.setText(data.getName());
//        tv_date.setText(data.getCreatedAt());
//        rating.setRating(data.getRate());
//        tv_content.setText(data.getContent());
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
