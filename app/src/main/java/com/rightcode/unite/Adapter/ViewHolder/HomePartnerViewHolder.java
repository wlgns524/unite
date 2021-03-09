
package com.rightcode.unite.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rightcode.unite.Activity.Partner.PartnerDetailActivity;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.ImageWrapper;
import com.rightcode.unite.network.model.response.partner.Partner;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.unite.Util.ExtraData.EXTRA_PARTNER_ID;

public class HomePartnerViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.iv_partner)
    ImageView iv_partner;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_join)
    TextView tv_join;
    @BindView(R.id.tv_people)
    TextView tv_people;
    @BindView(R.id.tv_view)
    TextView tv_view;

    private Context mContext;
    private Partner data;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public HomePartnerViewHolder(View viewHolder, Context context) {
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
        Intent intent = new Intent(mContext, PartnerDetailActivity.class);
        intent.putExtra(EXTRA_PARTNER_ID, data.getId());
        startActivity(intent);
    }


    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    public void onBind(Partner data) {
        this.data = data;
        ImageWrapper.loadRound(mContext, data.getImage(), iv_partner, R.dimen.image_corner_radius_16);
        tv_title.setText(data.getTitle());
        tv_join.setText(String.format("%d", data.getJoin()));
        tv_people.setText(String.format("%d/%d명", data.getJoin(), data.getPeople()));
        tv_view.setText(String.format("%d", data.getViewCount()));
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
