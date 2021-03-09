package com.rightcode.unite.Activity.Message;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.rightcode.unite.Activity.Basic.BaseActivity;
import com.rightcode.unite.Adapter.RecyclerViewAdapter.ChatMemberRecyclerViewAdapter;
import com.rightcode.unite.Component.RecyclerViewOnClickListener;
import com.rightcode.unite.Dialog.UserKickOutDialog;
import com.rightcode.unite.MemberManager;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.Log;
import com.rightcode.unite.network.model.response.user.UserInfo;
import com.rightcode.unite.network.requester.chatroom.ChatRoomUserListRequester;
import com.rightcode.unite.network.responser.chatroom.ChatRoomUserListResponser;
import com.rightcode.unite.network.socket.SocketConnection;
import com.rightcode.unite.network.socket.model.UserList;
import com.rightcode.unite.network.socket.model.UserListResponse;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.unite.Util.ExtraData.EXTRA_PARTNER_ID;
import static com.rightcode.unite.network.socket.SocketEventEnums.SocketEvent.kickOut;
import static com.rightcode.unite.network.socket.SocketEventEnums.SocketEvent.userList;

public class MessageSettingActivity extends BaseActivity {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.rv_chat_member)
    RecyclerView rv_chat_member;

    private ChatMemberRecyclerViewAdapter mChatMemberRecyclerViewAdapter;
    private SocketConnection socketConnection;
    private Long partnerId;
    private Boolean isLeader = false;
    private UserInfo userInfo = MemberManager.getInstance(MessageSettingActivity.this).getUserInfo();

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messgae_setting);

        ButterKnife.bind(this);
        initialize();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_out_activity_to_right);
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

    @OnClick({R.id.cl_root, R.id.tv_room_exit})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.cl_root: {
                finish();
                break;
            }
            case R.id.tv_room_exit: {
                setResult(RESULT_OK);
                finish();
                break;
            }
        }
    }

    private void initialize() {
        overridePendingTransition(R.anim.slide_in_activity_from_right, R.anim.stay);
        partnerId = getIntent().getLongExtra(EXTRA_PARTNER_ID, 0L);
        mChatMemberRecyclerViewAdapter = new ChatMemberRecyclerViewAdapter(MessageSettingActivity.this);
        rv_chat_member.setLayoutManager(new LinearLayoutManager(MessageSettingActivity.this, LinearLayoutManager.VERTICAL, false));
        rv_chat_member.setAdapter(mChatMemberRecyclerViewAdapter);
        rv_chat_member.addOnItemTouchListener(new RecyclerViewOnClickListener(MessageSettingActivity.this, (view, position) -> {
            UserList data = mChatMemberRecyclerViewAdapter.getData().get(position);

            if (isLeader) {
                UserKickOutDialog userKickOutDialog;
                if (data.getUserId() == userInfo.getId()) {
                    userKickOutDialog = new UserKickOutDialog(MessageSettingActivity.this, false);
                } else {
                    userKickOutDialog = new UserKickOutDialog(MessageSettingActivity.this, true);
                }
                userKickOutDialog.setImage(data.getImage());
                userKickOutDialog.setName(data.getUserName());
                userKickOutDialog.setAction(action -> {
                    userKickOut(data.getUserId());
                    userKickOutDialog.dismiss();
                });
                userKickOutDialog.show();
            } else {
                UserKickOutDialog userKickOutDialog = new UserKickOutDialog(MessageSettingActivity.this, false);
                userKickOutDialog.setImage(data.getImage());
                userKickOutDialog.setName(data.getUserName());
                userKickOutDialog.setAction(action -> {
                    userKickOut(data.getUserId());
                    userKickOutDialog.dismiss();
                });
                userKickOutDialog.show();
            }


        }));
        chatRoomUserList();
        userList();
    }

    private void userList() {
        socketConnection = SocketConnection.getInstance();
        socketConnection.addEvent(userList, args -> {
            try {
            Gson gson = new Gson();
            JSONObject object = (JSONObject) args[0];
                UserListResponse result = gson.fromJson(object.toString(), UserListResponse.class);
                for (UserList user : result.getList()) {
                    if (user.getUserId() == userInfo.getId()) {
                        isLeader = true;
                        break;
                    }
                }
                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.postDelayed(() -> {
                    mChatMemberRecyclerViewAdapter.setData(result.getList());
                    mChatMemberRecyclerViewAdapter.notifyDataSetChanged();
                }, 0);
            } catch (Exception e) {
                chatRoomUserList();
                Log.e(e.getMessage());
            }
        });
    }

    private void userKickOut(Long userId) {
        JSONObject data = new JSONObject();
        try {
            data.put("userId", userId);
            socketConnection.emit(kickOut, data);
        } catch (Exception e) {
            Log.e(e.getMessage());
        }
    }

    private void chatRoomUserList() {
        showLoading();
        ChatRoomUserListRequester requester = new ChatRoomUserListRequester(MessageSettingActivity.this);
        requester.setPartnerId(partnerId);

        request(requester,
                success -> {
                    ChatRoomUserListResponser result = (ChatRoomUserListResponser) success;
                    hideLoading();
                    if (result.getCode() == 200) {
                        for (UserList user : result.getList()) {
                            if (user.getUserId() == userInfo.getId()) {
                                if (user.getIsLeader()) {
                                    isLeader = true;
                                    break;
                                }
                            }
                        }
                        Handler mHandler = new Handler(Looper.getMainLooper());
                        mHandler.postDelayed(() -> {
                            mChatMemberRecyclerViewAdapter.setData(result.getList());
                            mChatMemberRecyclerViewAdapter.notifyDataSetChanged();
                        }, 0);
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
