package com.rightcode.unite.Activity.Setting;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.rightcode.unite.Activity.Basic.BaseActivity;
import com.rightcode.unite.Adapter.RecyclerViewAdapter.NoticeRecyclerViewAdapter;
import com.rightcode.unite.Fragment.TopFragment;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.FragmentUtil;
import com.rightcode.unite.network.requester.board.BoardListRequester;
import com.rightcode.unite.network.responser.board.BoardListResponser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeActivity extends BaseActivity {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.rv_notice)
    RecyclerView rv_notice;

    private TopFragment mTopFragment;
    private NoticeRecyclerViewAdapter mNoticeRecyclerViewAdapter;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        ButterKnife.bind(this);
        initialize();
        boardList();
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
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "공지사항");
        mTopFragment.setTextColor(TopFragment.Menu.CENTER, R.color.black);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.ic_back);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, v -> finishWithAnim());

        mNoticeRecyclerViewAdapter = new NoticeRecyclerViewAdapter(NoticeActivity.this);
        rv_notice.setLayoutManager(new LinearLayoutManager(NoticeActivity.this, LinearLayoutManager.VERTICAL, false));
        rv_notice.setAdapter(mNoticeRecyclerViewAdapter);
    }

    private void boardList() {
        showLoading();
        BoardListRequester requester = new BoardListRequester(NoticeActivity.this);
        requester.setDiff("공지사항");

        request(requester,
                success -> {
                    BoardListResponser result = (BoardListResponser) success;
                    hideLoading();
                    if (result.getCode() == 200) {
                        mNoticeRecyclerViewAdapter.setData(result.getList());
                        mNoticeRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }
    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
