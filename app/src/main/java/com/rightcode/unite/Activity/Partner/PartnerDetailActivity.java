package com.rightcode.unite.Activity.Partner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.rightcode.unite.Activity.Basic.BaseActivity;
import com.rightcode.unite.Activity.Login.LoginActivity;
import com.rightcode.unite.Activity.Message.MessageDetailActivity;
import com.rightcode.unite.Adapter.RecyclerViewAdapter.PartnerDetailRecyclerViewAdapter;
import com.rightcode.unite.Fragment.TopFragment;
import com.rightcode.unite.MemberManager;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.FragmentUtil;
import com.rightcode.unite.Util.Log;
import com.rightcode.unite.Util.PreferenceUtil;
import com.rightcode.unite.Util.ToastUtil;
import com.rightcode.unite.network.requester.partner.PartnerCheckRequester;
import com.rightcode.unite.network.requester.partner.PartnerDetailRequester;
import com.rightcode.unite.network.requester.partner.PartnerListRequester;
import com.rightcode.unite.network.responser.partner.PartnerDetailResponser;
import com.rightcode.unite.network.responser.partner.PartnerListResponser;
import com.rightcode.unite.network.socket.SocketConnection;
import com.rightcode.unite.network.socket.SocketEventEnums;
import com.rightcode.unite.network.socket.model.ChatRoom;
import com.rightcode.unite.network.socket.model.SocketResult;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.unite.Util.ExtraData.EXTRA_CHATROOM;
import static com.rightcode.unite.Util.ExtraData.EXTRA_PARTNER_ID;

public class PartnerDetailActivity extends BaseActivity {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.rv_partner_detail)
    RecyclerView rv_partner_detail;

    private TopFragment mTopFragment;
    private SocketConnection socketConnection;
    private PartnerDetailRecyclerViewAdapter mPartnerDetailRecyclerViewAdapter;
    private Long partnerId;
    private ChatRoom chatRoom;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_detail);

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

    @OnClick({R.id.tv_partner})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_partner: {
                if (MemberManager.getInstance(PartnerDetailActivity.this).isLogin()) {
                    partnerCheck();
                } else {
                    Intent intent = new Intent(PartnerDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            }
        }
    }

    private void initialize() {
        if (getIntent() != null) {
            partnerId = getIntent().getLongExtra(EXTRA_PARTNER_ID, 0L);
            partnerDetail();
            partnerList();
        }

        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "동행");
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.ic_back);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, v -> finishWithAnim());

        mPartnerDetailRecyclerViewAdapter = new PartnerDetailRecyclerViewAdapter(PartnerDetailActivity.this);
        rv_partner_detail.setLayoutManager(new LinearLayoutManager(PartnerDetailActivity.this, LinearLayoutManager.VERTICAL, false));
        rv_partner_detail.setAdapter(mPartnerDetailRecyclerViewAdapter);
    }

    private void partnerDetail() {
        PartnerDetailRequester requester = new PartnerDetailRequester(PartnerDetailActivity.this);
        showLoading();
        requester.setId(partnerId);

        request(requester,
                success -> {
                    PartnerDetailResponser result = (PartnerDetailResponser) success;
                    hideLoading();
                    if (result.getCode() == 200) {
                        chatRoom = new ChatRoom();
                        chatRoom.setImage(result.getData().getImage());
                        chatRoom.setTitle(result.getData().getTitle());
                        chatRoom.setViewCount(result.getData().getViewCount());
                        chatRoom.setPeople(result.getData().getPeople());
                        chatRoom.setJoin(result.getData().getJoin());
                        mPartnerDetailRecyclerViewAdapter.setPartnerDetail(result.getData());
                        mPartnerDetailRecyclerViewAdapter.setProductDetail(result.getData().getProduct());
                        mPartnerDetailRecyclerViewAdapter.notifiyDataChanged(0);
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void partnerList() {
        PartnerListRequester requester = new PartnerListRequester(PartnerDetailActivity.this);
        requester.setLimit(8);
        showLoading();
        request(requester,
                success -> {
                    PartnerListResponser result = (PartnerListResponser) success;
                    hideLoading();
                    if (result.getCode() == 200) {
                        mPartnerDetailRecyclerViewAdapter.setFooter(result.getList());
                        mPartnerDetailRecyclerViewAdapter.notifiyDataChanged(2);
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void initSocketConnection() {
        socketConnection = SocketConnection.getInstance();

        if (!SocketConnection.isConnected()) {
            socketConnection.setOnConnect(args -> {
                login();
            }).setOnDisconnect(args -> {
                runOnUiThread(() -> ToastUtil.show(PartnerDetailActivity.this, getString(R.string.network_error_message)));
            }).on().connect();
        } else {
            login();
        }
    }

    private void login() {
        JSONObject data = new JSONObject();
        try {
            data.put("token", PreferenceUtil.getInstance(PartnerDetailActivity.this).get(PreferenceUtil.PreferenceKey.ServiceToken, ""));
            socketConnection.emit(SocketEventEnums.SocketEvent.login, data, args -> {
                Gson gson = new Gson();
                JSONObject object = (JSONObject) args[0];
                SocketResult result = gson.fromJson(object.toString(), SocketResult.class);
                if (result.getStatus() == 200) {
                    Intent intent = new Intent(PartnerDetailActivity.this, MessageDetailActivity.class);
                    intent.putExtra(EXTRA_CHATROOM, chatRoom);
                    intent.putExtra(EXTRA_PARTNER_ID, partnerId);
                    startActivity(intent);
                } else {
                    runOnUiThread(() -> ToastUtil.show(PartnerDetailActivity.this, result.getMessage()));
                }
            });
        } catch (Exception e) {
            Log.e(e.getMessage());
        }
    }

    private void partnerCheck() {
        PartnerCheckRequester requester = new PartnerCheckRequester(PartnerDetailActivity.this);
        requester.setPartnerId(partnerId);

        request(requester,
                success -> {
                    if (success.getCode() == 200) {
                        initSocketConnection();
                    } else {
                        showServerErrorDialog(success.getResultMsg());
                    }
                }, fail -> {
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
