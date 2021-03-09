package com.rightcode.unite.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.unite.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.unite.Adapter.ViewHolder.FilterCategoryViewHolder;
import com.rightcode.unite.R;
import com.rightcode.unite.network.model.Filter;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FilterCatogoryRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    private Context mContext;
    private ArrayList<Filter> data = new ArrayList<Filter>() {{
        add(new Filter(R.drawable.selector_ticket, "어트랙션/공연"));
        add(new Filter(R.drawable.selector_running, "액티비티/체험"));
        add(new Filter(R.drawable.selector_airplane, "교통수단/여행서비스"));
    }};


    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public FilterCatogoryRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_filter_category_recyclerview, viewGroup, false);
        return new FilterCategoryViewHolder(view, mContext);
    }

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int position) {
        ((FilterCategoryViewHolder) viewHolder).onBind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    public String getSelectList() {
        return data.stream().filter(interestMenu -> interestMenu.getIsSelect()).map(interestMenu -> interestMenu.getTitle()).collect(Collectors.joining(","));
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
