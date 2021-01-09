
package com.rightcode.unite.Adapter.ViewHolder;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.DeviceSizeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccompanyPopularAreaViewHolder extends CommonRecyclerViewHolder {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.cl_root)
    ConstraintLayout cl_root;
    @BindView(R.id.iv_test)
    ImageView iv_test;
    @BindView(R.id.cv_image)
    CardView cv_image;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_rating)
    TextView tv_rating;

    private Context mContext;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public AccompanyPopularAreaViewHolder(View viewHolder, Context context) {
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

    public void onBind(int position) {
        tv_title.bringToFront();
        tv_rating.bringToFront();
        if(position==0) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) iv_test.getLayoutParams();
            params.width = DeviceSizeUtil.dpToPx(mContext, 140);
            cl_root.setLayoutParams(params);
            cv_image.setVisibility(View.GONE);
            tv_title.setVisibility(View.GONE);
            tv_rating.setVisibility(View.GONE);
        }else{
            cv_image.setVisibility(View.VISIBLE);
            tv_title.setVisibility(View.VISIBLE);
            tv_rating.setVisibility(View.VISIBLE);
        }
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
