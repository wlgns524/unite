package com.rightcode.unite.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.unite.Adapter.ViewHolder.AroundViewHolder;
import com.rightcode.unite.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.unite.Adapter.ViewHolder.ProductAccompanyHeaderViewHolder;
import com.rightcode.unite.Adapter.ViewHolder.ProductAccompanyViewHolder;
import com.rightcode.unite.R;

import static com.rightcode.unite.Adapter.ViewHolder.CommonRecyclerViewHolder.HEADER_VIEW;
import static com.rightcode.unite.Adapter.ViewHolder.CommonRecyclerViewHolder.ITEM_VIEW;

public class ProductAccompanyRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    private Context mContext;

//    List<Integer> arr = Arrays.asList(new Integer[10]);
    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public ProductAccompanyRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == HEADER_VIEW) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_accompany_header_recyclerview, viewGroup, false);
            return new ProductAccompanyHeaderViewHolder(view, mContext);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_accompany_recyclerview, viewGroup, false);
            return new ProductAccompanyViewHolder(view, mContext);
        }
    }

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int position) {
        if (viewHolder instanceof ProductAccompanyHeaderViewHolder) {
            ((ProductAccompanyHeaderViewHolder) viewHolder).onBind();
        } else {
            ((ProductAccompanyViewHolder) viewHolder).onBind();
        }
    }

    @Override
    public int getItemCount() {
        return 21;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_VIEW;
        } else {
            return ITEM_VIEW;
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
