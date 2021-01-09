package com.rightcode.unite.Adapter.RecyclerViewAdapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.unite.Adapter.ViewHolder.CommonRecyclerViewHolder;


public abstract class CommonRecyclerViewAdapter<VH extends CommonRecyclerViewHolder> extends RecyclerView.Adapter<VH> {
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
