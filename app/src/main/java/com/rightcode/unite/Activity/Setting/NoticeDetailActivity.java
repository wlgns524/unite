package com.rightcode.unite.Activity.Setting;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.rightcode.unite.Activity.Basic.BaseActivity;
import com.rightcode.unite.Fragment.TopFragment;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.FragmentUtil;
import com.rightcode.unite.network.model.response.board.BoardDetail;
import com.rightcode.unite.network.requester.board.BoardDetailRequester;
import com.rightcode.unite.network.responser.board.BoardDetailResponser;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.unite.Util.ExtraData.EXTRA_NOTICE_ID;


public class NoticeDetailActivity extends BaseActivity {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_content)
    TextView tv_content;

    private TopFragment mTopFragment;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

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
        if (getIntent() != null) {
            boardDetail(getIntent().getLongExtra(EXTRA_NOTICE_ID, 0L));
        }
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "공지사항");
        mTopFragment.setTextColor(TopFragment.Menu.CENTER, R.color.black);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.ic_back);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });
    }

    private void boardDetail(Long boardId) {
        showLoading();
        BoardDetailRequester requester = new BoardDetailRequester(NoticeDetailActivity.this);
        requester.setBoardId(boardId);

        request(requester,
                success -> {
                    BoardDetailResponser result = (BoardDetailResponser) success;
                    hideLoading();
                    if (result.getCode() == 200) {
                        initLayout(result.getData());
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void initLayout(BoardDetail data) {
        tv_title.setText(data.getTitle());
        tv_date.setText(data.getCreatedAt());
        tv_content.setText(data.getContent());
    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
