
package com.rightcode.unite.Adapter.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rightcode.unite.R;
import com.rightcode.unite.Util.ImageWrapper;
import com.rightcode.unite.network.model.Filter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterCategoryViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.tv_category)
    TextView tv_category;

    private Context mContext;
    private Filter data;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public FilterCategoryViewHolder(View viewHolder, Context context) {
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
        data.setIsSelect(!data.getIsSelect());
        onBind(data);
    }


    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    public void onBind(Filter data) {
        this.data = data;
        tv_category.setText(data.getTitle());
        tv_category.setCompoundDrawablesWithIntrinsicBounds(data.getImage(), 0, 0, 0);
        itemView.setSelected(data.getIsSelect());
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
