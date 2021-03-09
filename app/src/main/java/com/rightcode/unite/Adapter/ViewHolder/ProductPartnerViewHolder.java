
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

public class ProductPartnerViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

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

    private Context mContext;
    private Partner data;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public ProductPartnerViewHolder(View viewHolder, Context context) {
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

        ImageWrapper.loadCircle(mContext, data.getUserImage(), iv_profile, R.drawable.ic_profile);
        tv_user_name.setText(data.getUserName());
        tv_date.setText(data.getCreatedAt());
        tv_title.setText(data.getTitle());
        tv_content.setText(data.getContent());
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
