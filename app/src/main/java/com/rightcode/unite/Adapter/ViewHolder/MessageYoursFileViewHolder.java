package com.rightcode.unite.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rightcode.unite.Activity.ImageActivity;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.ImageWrapper;
import com.rightcode.unite.network.socket.model.ChatData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Setter;
import rx.functions.Action1;

import static com.rightcode.unite.Util.ExtraData.EXTRA_IMAGE;

public class MessageYoursFileViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener{

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
    @BindView(R.id.iv_image)
    ImageView iv_image;
    @BindView(R.id.tv_date)
    TextView tv_date;

    @Setter
    private Action1<ChatData> userKickAction;
    private Context mContext;
    private ChatData data;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public MessageYoursFileViewHolder(View viewHolder, Context context) {
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
        Intent intent = new Intent(mContext, ImageActivity.class);
        intent.putExtra(EXTRA_IMAGE, data.getImage());
        startActivity(intent);
    }

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    public void onBind(ChatData data) {
        this.data = data;
        //TODO - 이미지 리사이징
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_image.getLayoutParams();
//        params.height = DeviceSizeUtil.getRatioHeight(imageViewWidth, data.getImageWidth(), data.getImageHeight());
//        iv_image.setLayoutParams(params);
//        Bitmap bitmap = ImageUtil.drawableFromUrl(data.getImage());
//        if (bitmap != null) {
//            ImageWrapper.loadRound(mContext, data.getImage(), iv_image, bitmap.getWidth(), bitmap.getHeight());
//        }else{
        if (data.getIsLeader()) {
            iv_leader.setVisibility(View.VISIBLE);
        } else {
            iv_leader.setVisibility(View.GONE);
        }
        ImageWrapper.loadCircle(mContext, data.getMessage(), iv_profile, R.drawable.ic_profile);
//        }
    }


    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    @OnClick({R.id.iv_profile})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.iv_profile: {
                if (userKickAction != null) {
                    userKickAction.call(data);
                }
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
