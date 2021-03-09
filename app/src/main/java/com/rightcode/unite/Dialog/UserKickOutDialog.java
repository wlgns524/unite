package com.rightcode.unite.Dialog;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rightcode.unite.Activity.ImageActivity;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.ImageWrapper;
import com.rightcode.unite.Util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Setter;
import rx.functions.Action1;

import static com.rightcode.unite.Util.ExtraData.EXTRA_IMAGE;

public class UserKickOutDialog extends BaseDialog {


    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.user_kick_iv_profile)
    ImageView user_kick_iv_profile;
    @BindView(R.id.user_kick_tv_name)
    TextView user_kick_tv_name;
    @BindView(R.id.user_kick_tv_out)
    TextView user_kick_tv_out;

    @Setter
    private Action1<Void> action;
    private Context context;
    private String image;
    private Boolean isLeader = false;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public UserKickOutDialog(Context context, Boolean isLeader) {
        super(context);
        setContentView(R.layout.dialog_user_kick_out);
        this.context = context;
        this.isLeader = isLeader;

        ButterKnife.bind(this);
        initialize();
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

    public void setImage(String image) {
        this.image = image;
        ImageWrapper.loadCircle(context, image, user_kick_iv_profile, R.drawable.ic_profile);
    }

    public void setName(String name) {
        user_kick_tv_name.setText(name);
    }

    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    @OnClick({R.id.user_kick_iv_profile, R.id.user_kick_tv_out, R.id.iv_close})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.user_kick_iv_profile: {
                if (!TextUtils.isEmpty(image)) {
                    Intent intent = new Intent(context, ImageActivity.class);
                    intent.putExtra(EXTRA_IMAGE, image);
                    startActivity(intent);
                }
                break;
            }
            case R.id.user_kick_tv_out: {
                if (action != null && isLeader) {
                    action.call(null);
                }
                break;
            }
            case R.id.iv_close: {
                dismiss();
                break;
            }
        }
    }

    private void initialize(){
        if(isLeader){
            user_kick_tv_out.setVisibility(View.VISIBLE);
        }else{
            user_kick_tv_out.setVisibility(View.INVISIBLE);
        }
    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
