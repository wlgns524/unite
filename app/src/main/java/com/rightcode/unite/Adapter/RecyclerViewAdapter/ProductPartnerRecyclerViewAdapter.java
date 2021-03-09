package com.rightcode.unite.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.unite.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.unite.Adapter.ViewHolder.ProductPartnerHeaderViewHolder;
import com.rightcode.unite.Adapter.ViewHolder.ProductPartnerViewHolder;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.Log;
import com.rightcode.unite.network.model.response.partner.Partner;
import com.rightcode.unite.network.model.response.product.Product;

import java.util.ArrayList;

import lombok.Setter;

import static com.rightcode.unite.Adapter.ViewHolder.CommonRecyclerViewHolder.ITEM_VIEW;

public class ProductPartnerRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @Setter
    private Product header;
    @Setter
    private ArrayList<Partner> data;
    private Context mContext;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public ProductPartnerRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_partner_header_recyclerview, viewGroup, false);
            return new ProductPartnerHeaderViewHolder(view, mContext);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_partner_recyclerview, viewGroup, false);
            return new ProductPartnerViewHolder(view, mContext);
        }
    }


    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int position) {
        if (viewHolder instanceof ProductPartnerHeaderViewHolder) {
            ((ProductPartnerHeaderViewHolder) viewHolder).onBind(header);
        } else {
            ((ProductPartnerViewHolder) viewHolder).onBind(data.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == 0) {
//            return HEADER_VIEW;
//        } else {
        return ITEM_VIEW;
//        }
    }

    @Override
    public int getItemCount() {
        Log.d(data);
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

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
