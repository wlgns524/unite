package com.rightcode.unite.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommonRecyclerViewHolder extends RecyclerView.ViewHolder {
    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    public static final int HEADER_VIEW = 0;
    public static final int ITEM_VIEW = 1;

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    private long mLastClickTime = System.currentTimeMillis();
    private static final long CLICK_TIME_INTERVAL = 500;
    private Context mContext;

    //----------------------------------------------------------------------------------------------
    // action
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // get / set
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // abstract
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public CommonRecyclerViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        mContext = context;
    }


    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    public void startActivity(Intent intent) {
        // 더블 탭으로 인한 이중 액션 막기
//        long now = System.currentTimeMillis();
//        if (now - mLastClickTime < CLICK_TIME_INTERVAL) {
//            return;
//        }
//        mLastClickTime = now;
        mContext.startActivity(intent);
    }
    //----------------------------------------------------------------------------------------------
    // event
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------
}
