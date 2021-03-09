
package com.rightcode.unite.Adapter.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.unite.Adapter.RecyclerViewAdapter.PartnerRecyclerViewAdapter;
import com.rightcode.unite.Component.GridSpacingItemDecoration;
import com.rightcode.unite.Fragment.BottomFragment;
import com.rightcode.unite.R;
import com.rightcode.unite.RxJava.RxBus;
import com.rightcode.unite.RxJava.RxEvent.SearchEvent;
import com.rightcode.unite.network.model.response.partner.Partner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PartnerDetailFooterViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener{

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.rv_partner)
    RecyclerView rv_partner;

    private Context mContext;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public PartnerDetailFooterViewHolder(View viewHolder, Context context) {
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
        RxBus.send(new SearchEvent(BottomFragment.Menu.PARTNER, ""));
        ((Activity)mContext).finish();
    }

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    public void onBind(ArrayList<Partner> data) {
        PartnerRecyclerViewAdapter mPartnerRecyclerViewAdapter = new PartnerRecyclerViewAdapter(mContext);
        rv_partner.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
        rv_partner.setLayoutManager(new GridLayoutManager(mContext, 2));
        mPartnerRecyclerViewAdapter.setData(data);
        rv_partner.setAdapter(mPartnerRecyclerViewAdapter);
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
