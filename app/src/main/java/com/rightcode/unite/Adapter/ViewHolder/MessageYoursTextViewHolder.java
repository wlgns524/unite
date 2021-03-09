package com.rightcode.unite.Adapter.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rightcode.unite.R;
import com.rightcode.unite.Util.ImageWrapper;
import com.rightcode.unite.Util.Log;
import com.rightcode.unite.network.model.response.partner.Partner;
import com.rightcode.unite.network.socket.model.ChatData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Setter;
import rx.functions.Action1;

public class MessageYoursTextViewHolder extends CommonRecyclerViewHolder {

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
    @BindView(R.id.tv_user)
    TextView tv_user;
    @BindView(R.id.tv_text)
    TextView tv_text;
    @BindView(R.id.tv_date)
    TextView tv_date;

    @Setter
    private Action1<ChatData> userKickAction;
    private Context mContext;
    private ChatData data;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public MessageYoursTextViewHolder(View viewHolder, Context context) {
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

    public void onBind(ChatData data) {
        this.data = data;
        ImageWrapper.loadCircle(mContext, data.getImage(), iv_profile, R.drawable.ic_profile);
        if (data.getIsLeader()) {
            iv_leader.setVisibility(View.VISIBLE);
        } else {
            iv_leader.setVisibility(View.GONE);
        }
        tv_user.setText(data.getName());
        tv_text.setText(data.getMessage());
        tv_date.setText(data.getCreatedAt());
    }

    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    @OnClick({R.id.iv_profile})
    void onClickMenu(View view){
        switch (view.getId()){
            case R.id.iv_profile:{
                if(userKickAction!=null){
                    userKickAction.call(data);
                }
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
