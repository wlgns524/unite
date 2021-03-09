package com.rightcode.unite.Activity.Setting;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.unite.Activity.Basic.BaseActivity;
import com.rightcode.unite.Adapter.RecyclerViewAdapter.PostMineRecyclerViewAdapter;
import com.rightcode.unite.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostMineActivity extends BaseActivity {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.rv_post_mine)
    RecyclerView rv_post_mine;

    private PostMineRecyclerViewAdapter mPostMineRecyclerViewAdapter;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_mine);

        ButterKnife.bind(this);
        initialize();
    }


    //----------------------------------------------------------------------------------------------
    // override
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

    private void initialize() {
        mPostMineRecyclerViewAdapter = new PostMineRecyclerViewAdapter(PostMineActivity.this);
        rv_post_mine.setLayoutManager(new LinearLayoutManager(PostMineActivity.this, LinearLayoutManager.VERTICAL, false));
        rv_post_mine.setAdapter(mPostMineRecyclerViewAdapter);
    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
