package com.rightcode.unite.Activity.Message;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.rightcode.unite.Activity.Basic.BaseActivity;
import com.rightcode.unite.Activity.CustomGalleryActivity;
import com.rightcode.unite.Activity.Partner.PartnerDetailActivity;
import com.rightcode.unite.Adapter.RecyclerViewAdapter.MessageDetailRecyclerViewAdapter;
import com.rightcode.unite.Dialog.UserKickOutDialog;
import com.rightcode.unite.Fragment.TopFragment;
import com.rightcode.unite.MemberManager;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.FragmentUtil;
import com.rightcode.unite.Util.ImageWrapper;
import com.rightcode.unite.Util.Log;
import com.rightcode.unite.Util.ToastUtil;
import com.rightcode.unite.network.model.response.user.UserInfo;
import com.rightcode.unite.network.requester.chatroom.ChatImageRequester;
import com.rightcode.unite.network.requester.chatroom.ChatRoomUserListRequester;
import com.rightcode.unite.network.responser.chatroom.ChatRoomUserListResponser;
import com.rightcode.unite.network.socket.SocketConnection;
import com.rightcode.unite.network.socket.model.ChatDataResponse;
import com.rightcode.unite.network.socket.model.ChatRoom;
import com.rightcode.unite.network.socket.model.RoomJoinResponse;
import com.rightcode.unite.network.socket.model.UserList;
import com.rightcode.unite.network.socket.model.UserListResponse;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.unite.Util.ExtraData.EXTRA_ACTIVITY_ACTION;
import static com.rightcode.unite.Util.ExtraData.EXTRA_ACTIVITY_COMPLETE;
import static com.rightcode.unite.Util.ExtraData.EXTRA_CHATROOM;
import static com.rightcode.unite.Util.ExtraData.EXTRA_IMAGE_COUNT;
import static com.rightcode.unite.Util.ExtraData.EXTRA_PARTNER_ID;
import static com.rightcode.unite.Util.ExtraData.EXTRA_SELECT_IMAGE;
import static com.rightcode.unite.Util.ExtraData.EXTRA_SINGLE_SELECT;
import static com.rightcode.unite.Util.ExtraData.REQUEST_CODE_GALLERY;
import static com.rightcode.unite.network.socket.SocketEventEnums.SocketEvent.kickOut;
import static com.rightcode.unite.network.socket.SocketEventEnums.SocketEvent.newMessage;
import static com.rightcode.unite.network.socket.SocketEventEnums.SocketEvent.roomExit;
import static com.rightcode.unite.network.socket.SocketEventEnums.SocketEvent.roomJoin;
import static com.rightcode.unite.network.socket.SocketEventEnums.SocketEvent.roomLeave;
import static com.rightcode.unite.network.socket.SocketEventEnums.SocketEvent.sendMessage;
import static com.rightcode.unite.network.socket.SocketEventEnums.SocketEvent.userList;

public class MessageDetailActivity extends BaseActivity implements TextWatcher {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.rl_input)
    RelativeLayout rl_input;

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

    @BindView(R.id.iv_plus)
    ImageView iv_plus;
    @BindView(R.id.et_message)
    EditText et_message;
    @BindView(R.id.iv_send)
    ImageView iv_send;
    @BindView(R.id.tv_more)
    TextView tv_more;
    @BindView(R.id.rv_message)
    RecyclerView rv_message;

    private TopFragment mTopFragment;
    private MessageDetailRecyclerViewAdapter mMessageDetailRecyclerViewAdapter;
    private SocketConnection socketConnection;
    private Long partnerId;
    private ChatRoom chatRoom;
    private ArrayList<String> selectedPhotos;
    private Boolean isLeader = false;
    private UserInfo userInfo = MemberManager.getInstance(MessageDetailActivity.this).getUserInfo();

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        ButterKnife.bind(this);
        initialize();
    }

    @Override
    protected void onDestroy() {
        roomLeave();
        super.onDestroy();
    }

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_GALLERY: {
                    ArrayList<String> photos = data.getStringArrayListExtra(EXTRA_SELECT_IMAGE);
                    selectedPhotos = photos;
                    chatImageUpload();
                    break;
                }
                case EXTRA_ACTIVITY_ACTION: {
                    roomExit();
                    break;
                }
                case EXTRA_ACTIVITY_COMPLETE: {
                    break;
                }
                default:
                    break;
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        if (charSequence.length() > 0) {
            rl_input.setSelected(true);
            iv_plus.setSelected(true);
            iv_send.setSelected(true);
        } else {
            rl_input.setSelected(false);
            iv_plus.setSelected(false);
            iv_send.setSelected(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    @OnClick({R.id.iv_plus, R.id.iv_send, R.id.tv_more})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.iv_plus: {
                checkPermission();
                break;
            }
            case R.id.iv_send: {
                if (TextUtils.isEmpty(et_message.getText())) {
                    ToastUtil.show(MessageDetailActivity.this, "내용을 입력해주세요");
                    return;
                }
                sendMessage();
                break;
            }
            case R.id.tv_more: {
                Intent intent = new Intent(MessageDetailActivity.this, PartnerDetailActivity.class);
                intent.putExtra(EXTRA_PARTNER_ID, partnerId);
                startActivity(intent);
                break;
            }
        }
    }

    private void initialize() {
        if (getIntent() != null) {
            chatRoom = (ChatRoom) getIntent().getSerializableExtra(EXTRA_CHATROOM);
            partnerId = getIntent().getLongExtra(EXTRA_PARTNER_ID, 0L);
            initLayout(chatRoom);
            initSocketConnection();
            chatRoomUserList();
        }
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "메세지");
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.ic_back);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, v -> finishWithAnim());
        mTopFragment.setImage(TopFragment.Menu.RIGHT, R.drawable.ic_dot_menu);
        mTopFragment.setImagePadding(TopFragment.Menu.RIGHT, 5);
        mTopFragment.setListener(TopFragment.Menu.RIGHT, v -> {
//            showListPopupWindow(v);
            Intent intent = new Intent(MessageDetailActivity.this, MessageSettingActivity.class);
            intent.putExtra(EXTRA_PARTNER_ID, partnerId);
            startActivityForResult(intent, EXTRA_ACTIVITY_ACTION);
        });

        et_message.addTextChangedListener(this);
        tv_more.setVisibility(View.VISIBLE);

        mMessageDetailRecyclerViewAdapter = new MessageDetailRecyclerViewAdapter(MessageDetailActivity.this);
        rv_message.setLayoutManager(new LinearLayoutManager(MessageDetailActivity.this, LinearLayoutManager.VERTICAL, false));
        rv_message.setAdapter(mMessageDetailRecyclerViewAdapter);
        mMessageDetailRecyclerViewAdapter.setUserKickAction(chatData -> {
            if (isLeader) {
                UserKickOutDialog userKickOutDialog;
                if (chatData.getUserId() == userInfo.getId()) {
                    userKickOutDialog = new UserKickOutDialog(MessageDetailActivity.this, false);
                } else {
                    userKickOutDialog = new UserKickOutDialog(MessageDetailActivity.this, true);
                }
                userKickOutDialog.setImage(chatData.getImage());
                userKickOutDialog.setName(chatData.getName());
                userKickOutDialog.setAction(action -> {
                    userKickOut(chatData.getUserId());
                    userKickOutDialog.dismiss();
                });
                userKickOutDialog.show();
            } else {
                UserKickOutDialog userKickOutDialog = new UserKickOutDialog(MessageDetailActivity.this, false);
                userKickOutDialog.setImage(chatData.getImage());
                userKickOutDialog.setName(chatData.getName());
                userKickOutDialog.setAction(action -> {
                    userKickOut(chatData.getUserId());
                    userKickOutDialog.dismiss();
                });
                userKickOutDialog.show();
            }
        });
    }

    private void initLayout(ChatRoom data) {
        ImageWrapper.loadRound(MessageDetailActivity.this, data.getImage(), iv_info, R.dimen.image_corner_radius_8);
        tv_title.setText(data.getTitle());
        tv_join.setText(String.format("%d", data.getJoin()));
        tv_people.setText(String.format("%d/%d명", data.getJoin(), data.getPeople()));
        tv_view.setText(String.format("%d", data.getViewCount()));
    }

    private void showListPopupWindow(View view) {
        PopupMenu popup = new PopupMenu(MessageDetailActivity.this, view);

        popup.getMenu().add(0, 0, 0, "채팅방 나가기");

        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case 0:
                    roomExit();
                    break;
                default:
                    popup.dismiss();
                    break;
            }
            return true;
        });
        popup.show(); //showing popup menu
    }


    private void initSocketConnection() {
        try {
            socketConnection = SocketConnection.getInstance();
            if (!SocketConnection.isConnected()) {
                socketConnection.setOnConnect(args -> {
                    roomJoin();
                }).setOnDisconnect(args -> {
                    runOnUiThread(() -> ToastUtil.show(MessageDetailActivity.this, getString(R.string.network_error_message)));
                }).on().connect();
            } else {
                roomJoin();
            }
        } catch (Exception e) {
            runOnUiThread(() -> ToastUtil.show(MessageDetailActivity.this, getString(R.string.network_error_message)));
        }
    }


    private void roomJoin() {
        socketConnection = SocketConnection.getInstance();
        try {
            JSONObject data = new JSONObject();
            data.put("partnerId", partnerId);
            socketConnection.emit(roomJoin, data, args -> {
                Gson gson = new Gson();
                JSONObject object = (JSONObject) args[0];
                RoomJoinResponse result = gson.fromJson(object.toString(), RoomJoinResponse.class);
                if (result.getStatus() == 200) {
                    runOnUiThread(() -> {
                        mMessageDetailRecyclerViewAdapter.setData(result.getList());
                        mMessageDetailRecyclerViewAdapter.notifyDataSetChanged();
                        rv_message.post(() -> rv_message.smoothScrollToPosition(result.getList().size() - 1));
                    });
                    newMessage();
                    userList();
                } else {
                    runOnUiThread(() -> {
                        ToastUtil.show(MessageDetailActivity.this, result.getMessage());
                    });
                }
            });
        } catch (Exception e) {
            runOnUiThread(() -> {
                ToastUtil.show(MessageDetailActivity.this, e.getMessage());
            });
        }
    }

    private void newMessage() {
        socketConnection.addEvent(newMessage, args -> {
            try {
                Gson gson = new Gson();
                JSONObject object = (JSONObject) args[0];

                ChatDataResponse chatData = gson.fromJson(object.toString(), ChatDataResponse.class);
                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.postDelayed(() -> {
                    mMessageDetailRecyclerViewAdapter.newMessage(chatData.getData());
                    rv_message.scrollToPosition(mMessageDetailRecyclerViewAdapter.getItemCount() - 1);
                }, 0);
            } catch (Exception e) {
                Log.e(e.getMessage());
            }
        });
    }

    private void sendMessage() {
        JSONObject data = new JSONObject();
        try {
            data.put("message", et_message.getText().toString());
            socketConnection.emit(sendMessage, data);
            et_message.setText("");
        } catch (Exception e) {
            Log.e(e.getMessage());
        }
    }

    private void roomLeave() {
        socketConnection.emit(roomLeave, new JSONObject());
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

    private void roomExit() {
        try {
            runOnUiThread(() -> {
                socketConnection.emit(roomExit, new JSONObject());
                finishWithAnim();
            });
        } catch (Exception e) {
            Log.e(e.getMessage());
        }
    }

    private void userList() {
        socketConnection = SocketConnection.getInstance();
        socketConnection.addEvent(userList, args -> {
            try {
                Gson gson = new Gson();
                JSONObject object = (JSONObject) args[0];

                UserListResponse result = gson.fromJson(object.toString(), UserListResponse.class);
                Boolean Participation = true;

                for (UserList user : result.getList()) {
                    if (user.getUserId() == MemberManager.getInstance(MessageDetailActivity.this).getUserInfo().getId()) {
                        Participation = false;
                        break;
                    }
                }

                for (UserList user : result.getList()) {
                    if (user.getUserId() == userInfo.getId()) {
                        if (user.getIsLeader()) {
                            isLeader = true;
                            break;
                        }
                    }
                }

                if (Participation) {
                    Handler mHandler = new Handler(Looper.getMainLooper());
                    mHandler.postDelayed(() -> {
                        ToastUtil.show(MessageDetailActivity.this, "강퇴당했습니다");
                        finishWithAnim();
                    }, 0);
                }
            } catch (Exception e) {
            }
        });
    }

    private void checkPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent(MessageDetailActivity.this, CustomGalleryActivity.class);
                intent.putExtra(EXTRA_IMAGE_COUNT, 1);
                intent.putExtra(EXTRA_SINGLE_SELECT, true);
//                if (selectedPhotos != null) {
//                    intent.putStringArrayListExtra(EXTRA_SELECT_IMAGE, selectedPhotos);
//                }
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                ToastUtil.show(MessageDetailActivity.this, "앨범 접근권한을 허용해주세요");
            }
        };

        TedPermission.with(MessageDetailActivity.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("앨범 접근권한을 허용해주세요")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    private void chatImageUpload() {
        showLoading();
        ChatImageRequester requester = new ChatImageRequester(MessageDetailActivity.this);
        requester.setPartnerId(partnerId);
        requester.setPath(selectedPhotos);

        request(requester,
                success -> {
                    hideLoading();
                    if (success.getCode() == 200) {
                        selectedPhotos.clear();
                    } else {
                        showServerErrorDialog(success.getResultMsg());
                    }
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void chatRoomUserList() {
        showLoading();
        ChatRoomUserListRequester requester = new ChatRoomUserListRequester(MessageDetailActivity.this);
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