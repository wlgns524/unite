package com.rightcode.unite.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.unite.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.unite.Adapter.ViewHolder.PopularViewHolder;
import com.rightcode.unite.Adapter.ViewHolder.ProductReviewViewHolder;
import com.rightcode.unite.R;

public class ProductReviewRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {

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

    public ProductReviewRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_review_recyclerview, viewGroup, false);
        return new ProductReviewViewHolder(view, mContext);
    }

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int position) {
        ((ProductReviewViewHolder) viewHolder).onBind();
    }

    @Override
    public int getItemCount() {
        return 10;
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
