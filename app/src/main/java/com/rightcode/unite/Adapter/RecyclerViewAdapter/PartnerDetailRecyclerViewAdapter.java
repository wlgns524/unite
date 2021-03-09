package com.rightcode.unite.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gun0912.tedpermission.util.ObjectUtils;
import com.rightcode.unite.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.unite.Adapter.ViewHolder.PartnerDetailAreaHeaderViewHolder;
import com.rightcode.unite.Adapter.ViewHolder.PartnerDetailFooterViewHolder;
import com.rightcode.unite.Adapter.ViewHolder.PartnerDetailProductHeaderViewHolder;
import com.rightcode.unite.R;
import com.rightcode.unite.network.model.response.partner.Partner;
import com.rightcode.unite.network.model.response.partner.PartnerDetail;
import com.rightcode.unite.network.model.response.product.ProductDetail;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

import static com.rightcode.unite.Adapter.ViewHolder.CommonRecyclerViewHolder.FOOTER_VIEW;
import static com.rightcode.unite.Adapter.ViewHolder.CommonRecyclerViewHolder.HEADER_VIEW;
import static com.rightcode.unite.Adapter.ViewHolder.CommonRecyclerViewHolder.NONE;

public class PartnerDetailRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @Setter
    @Getter
    private PartnerDetail partnerDetail;
    @Setter
    private ProductDetail productDetail;
    @Setter
    private ArrayList<Partner> footer;
    private Context mContext;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public PartnerDetailRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == HEADER_VIEW) {
            if (partnerDetail != null && partnerDetail.getDiff().equals("지역")) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_partner_detail_area_recyclerview, viewGroup, false);
                return new PartnerDetailAreaHeaderViewHolder(view, mContext);
            } else {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_partner_detail_product_recyclerview, viewGroup, false);
                return new PartnerDetailProductHeaderViewHolder(view, mContext);
            }
        } else if (viewType == FOOTER_VIEW) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_partner_detail_footer_recyclerview, viewGroup, false);
            return new PartnerDetailFooterViewHolder(view, mContext);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_none, viewGroup, false);
            return new CommonRecyclerViewHolder(view, mContext);
        }
    }


    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int position) {
        if (viewHolder instanceof PartnerDetailAreaHeaderViewHolder) {
            ((PartnerDetailAreaHeaderViewHolder) viewHolder).onBind(partnerDetail);
        } else if (viewHolder instanceof PartnerDetailProductHeaderViewHolder) {
            ((PartnerDetailProductHeaderViewHolder) viewHolder).onBind(partnerDetail, productDetail);
        } else {
            ((PartnerDetailFooterViewHolder) viewHolder).onBind(footer);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            if (!ObjectUtils.isEmpty(partnerDetail)) {
                return HEADER_VIEW;
            } else {
                if (!ObjectUtils.isEmpty(footer)) {
                    return FOOTER_VIEW;
                } else {
                    return NONE;
                }
            }
        } else {
            if (!ObjectUtils.isEmpty(footer)) {
                return FOOTER_VIEW;
            } else {
                return NONE;
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (!ObjectUtils.isEmpty(partnerDetail))
            count++;
        if (!ObjectUtils.isEmpty(footer))
            count++;

        return count;
    }

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    public void notifiyDataChanged(int position) {
        if (position == 0) {
            if (getItemCount() > 0)
                notifyItemChanged(0);
        } else {
            notifyItemChanged(getItemCount() - 1);
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
