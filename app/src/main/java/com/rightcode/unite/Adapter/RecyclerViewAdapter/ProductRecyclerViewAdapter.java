package com.rightcode.unite.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.unite.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.unite.Adapter.ViewHolder.ProductHeaderViewHolder;
import com.rightcode.unite.Adapter.ViewHolder.ProductViewHolder;
import com.rightcode.unite.R;
import com.rightcode.unite.network.model.response.product.Product;

import java.util.ArrayList;

import lombok.Setter;

import static com.rightcode.unite.Adapter.ViewHolder.CommonRecyclerViewHolder.HEADER_VIEW;
import static com.rightcode.unite.Adapter.ViewHolder.CommonRecyclerViewHolder.ITEM_VIEW;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @Setter
    private ArrayList<Product> data;
    private Context mContext;


    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public ProductRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_header_recyclerview, viewGroup, false);
            return new ProductHeaderViewHolder(view, mContext);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_recyclerview, viewGroup, false);
            return new ProductViewHolder(view, mContext);
        }
    }


    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int position) {
        if (viewHolder instanceof ProductHeaderViewHolder) {
            ((ProductHeaderViewHolder) viewHolder).onBind();
        } else {
            ((ProductViewHolder) viewHolder).onBind(data.get(position - 1));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_VIEW;
        } else {
            return ITEM_VIEW;
        }
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size() + 1;
        } else {
            return 1;
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
