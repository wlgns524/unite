package com.rightcode.unite.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.unite.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.unite.Adapter.ViewHolder.FilterTravelAreaViewHolder;
import com.rightcode.unite.R;
import com.rightcode.unite.network.model.Filter;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FilterTravelAreaRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    private Context mContext;
    private ArrayList<Filter> data = new ArrayList<Filter>() {{
        add(new Filter(R.drawable.ic_jeju_white, "제주도"));
        add(new Filter(R.drawable.ic_busan_white, "부산"));
        add(new Filter(R.drawable.ic_seoul_white, "서울"));
        add(new Filter(R.drawable.ic_gangwon_white, "강원도"));
        add(new Filter(R.drawable.ic_bangkok_white, "방콕"));
        add(new Filter(R.drawable.ic_danang_white, "다낭"));
        add(new Filter(R.drawable.ic_taiwan_white, "대만"));
        add(new Filter(R.drawable.ic_macao_white, "마카오"));
    }};


    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public FilterTravelAreaRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_filter_travel_area_recyclerview, viewGroup, false);
        return new FilterTravelAreaViewHolder(view, mContext);
    }

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int position) {
        ((FilterTravelAreaViewHolder) viewHolder).onBind(data.get(position));
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
