package com.rightcode.unite.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.unite.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.unite.Adapter.ViewHolder.LocationViewHolder;
import com.rightcode.unite.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import lombok.Getter;

import static java.util.stream.Collectors.toList;

public class LocationRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    private Context mContext;
    @Getter
    private ArrayList<String> data = new ArrayList<String>(Arrays.asList("전체", "방콕", "오사카", "다낭", "보라카이", "타이페이", "서울", "홍콩"));
    private ArrayList<Boolean> mSelectList = new ArrayList();
//    List<Integer> arr = Arrays.asList(new Integer[10]);
    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public LocationRecyclerViewAdapter(Context context) {
        this.mContext = context;
        for (int i = 0; i < data.size(); i++) {
            mSelectList.add(false);
        }
        mSelectList.set(0, true);
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_location_recyclerview, viewGroup, false);
        return new LocationViewHolder(view, mContext);
    }

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int position) {
        ((LocationViewHolder) viewHolder).onBind(data.get(position), mSelectList.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    public void setmSelectList(int position) {
        Collections.fill(mSelectList, false);
        mSelectList.set(position, true);
        notifyDataSetChanged();
    }

    public String getSelect() {
        return data.get(mSelectList.indexOf(true));
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
