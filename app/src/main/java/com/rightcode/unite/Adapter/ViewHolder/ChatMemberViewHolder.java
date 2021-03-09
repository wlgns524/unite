
package com.rightcode.unite.Adapter.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rightcode.unite.R;
import com.rightcode.unite.Util.ImageWrapper;
import com.rightcode.unite.Util.Log;
import com.rightcode.unite.network.model.response.board.Board;
import com.rightcode.unite.network.socket.model.UserList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatMemberViewHolder extends CommonRecyclerViewHolder {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.iv_profile)
    ImageView iv_profile;
    @BindView(R.id.iv_leader)
    ImageView iv_leader;
    @BindView(R.id.tv_name)
    TextView tv_name;

    private Context mContext;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public ChatMemberViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    public void onBind(UserList data) {
        if (data.getIsLeader()) {
            iv_leader.setVisibility(View.VISIBLE);
        } else {
            iv_leader.setVisibility(View.GONE);
        }
        ImageWrapper.loadCircle(mContext, data.getImage(), iv_profile, R.drawable.ic_profile);
        tv_name.setText(data.getUserName());
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
