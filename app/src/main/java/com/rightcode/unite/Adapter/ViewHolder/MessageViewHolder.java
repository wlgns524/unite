
package com.rightcode.unite.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rightcode.unite.Activity.Message.MessageDetailActivity;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.ImageWrapper;
import com.rightcode.unite.network.socket.model.ChatRoom;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.unite.Util.ExtraData.EXTRA_CHATROOM;
import static com.rightcode.unite.Util.ExtraData.EXTRA_PARTNER_ID;

public class MessageViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.ll_post_info)
    LinearLayout ll_post_info;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_message)
    TextView tv_message;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.iv_info)
    ImageView iv_info;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_join)
    TextView tv_join;
    @BindView(R.id.tv_people)
    TextView tv_people;
    @BindView(R.id.tv_view)
    TextView tv_view;
    @BindView(R.id.tv_count)
    TextView tv_count;

    private Context mContext;
    private ChatRoom data;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public MessageViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(mContext, MessageDetailActivity.class);
        intent.putExtra(EXTRA_CHATROOM, data);
        intent.putExtra(EXTRA_PARTNER_ID, data.getId());
        startActivity(intent);
    }


    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    public void onBind(ChatRoom data) {
        this.data = data;
        tv_name.setText(data.getUserName());
        tv_message.setText(data.getMessage());
        tv_date.setText(data.getCreatedAt());
        ImageWrapper.loadRound(mContext, data.getImage(), iv_info, R.dimen.image_corner_radius_8);
        tv_title.setText(data.getTitle());
        tv_join.setText(String.format("%d", data.getJoin()));
        tv_people.setText(String.format("%d/%d명", data.getJoin(), data.getPeople()));
        tv_view.setText(String.format("%d", data.getViewCount()));
        if (data.getCount() > 0) {
            tv_count.setText(String.format("%d", data.getCount()));
            tv_count.setVisibility(View.VISIBLE);
        } else {
            tv_count.setVisibility(View.GONE);
        }
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
