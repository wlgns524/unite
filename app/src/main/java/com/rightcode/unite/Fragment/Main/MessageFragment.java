package com.rightcode.unite.Fragment.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.rightcode.unite.Activity.Login.LoginActivity;
import com.rightcode.unite.Adapter.RecyclerViewAdapter.MessageRecyclerViewAdapter;
import com.rightcode.unite.Fragment.BaseFragment;
import com.rightcode.unite.Fragment.TopFragment;
import com.rightcode.unite.MemberManager;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.FragmentUtil;
import com.rightcode.unite.Util.Log;
import com.rightcode.unite.Util.PreferenceUtil;
import com.rightcode.unite.Util.ToastUtil;
import com.rightcode.unite.network.socket.SocketConnection;
import com.rightcode.unite.network.socket.SocketEventEnums;
import com.rightcode.unite.network.socket.model.ChatRoomResponse;
import com.rightcode.unite.network.socket.model.SocketResult;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.rightcode.unite.Util.ExtraData.EXTRA_ACTIVITY_ACTION;
import static com.rightcode.unite.Util.ExtraData.EXTRA_ACTIVITY_COMPLETE;
import static com.rightcode.unite.network.socket.SocketEventEnums.SocketEvent.listUpdate;

public class MessageFragment extends BaseFragment {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.cl_need_login)
    ConstraintLayout cl_need_login;
    @BindView(R.id.cl_clear_message)
    ConstraintLayout cl_clear_message;
    @BindView(R.id.rv_message)
    RecyclerView rv_message;

    private View root;
    private TopFragment mTopFragment;
    private SocketConnection socketConnection;
    private MessageRecyclerViewAdapter mMessageRecyclerViewAdapter;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public static MessageFragment newInstance() {
        MessageFragment f = new MessageFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_message, container, false);

        ButterKnife.bind(this, root);
        initialize();

        return root;
    }


    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case EXTRA_ACTIVITY_ACTION: {
                    initialize();
                    break;
                }
                case EXTRA_ACTIVITY_COMPLETE: {
                }
                default:
                    break;
            }
        }
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

    @OnClick({R.id.tv_login})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_login: {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivityForResult(intent, EXTRA_ACTIVITY_ACTION);
                break;
            }
        }
    }

    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getChildFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "메세지");
        mTopFragment.setTextColor(TopFragment.Menu.CENTER, R.color.black);

        if (MemberManager.getInstance(getContext()).isLogin()) {
            mMessageRecyclerViewAdapter = new MessageRecyclerViewAdapter(getContext());
            rv_message.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            rv_message.setAdapter(mMessageRecyclerViewAdapter);
            initSocketConnection();
            cl_need_login.setVisibility(View.GONE);
        } else {
            rv_message.setVisibility(View.GONE);
            cl_clear_message.setVisibility(View.GONE);
            cl_need_login.setVisibility(View.VISIBLE);
        }
    }

    private void initSocketConnection() {
        try {
            socketConnection = SocketConnection.getInstance();
            if (!SocketConnection.isConnected()) {
                socketConnection.setOnConnect(args -> {
                    login();
                }).setOnDisconnect(args -> {
                    ((Activity) getContext()).runOnUiThread(() -> ToastUtil.show(getContext(), getString(R.string.network_error_message)));
                }).addEvent(listUpdate, args -> {
                    Gson gson = new Gson();
                    JSONObject object = (JSONObject) args[0];
                    ChatRoomResponse result = gson.fromJson(object.toString(), ChatRoomResponse.class);
                    update(result);
                }).on().connect();
            } else {
                socketConnection.addEvent(listUpdate, args -> {
                    Gson gson = new Gson();
                    JSONObject object = (JSONObject) args[0];
                    ChatRoomResponse result = gson.fromJson(object.toString(), ChatRoomResponse.class);
                    update(result);
                });
                login();
            }
        }catch (Exception e){
            ((Activity) getContext()).runOnUiThread(() -> ToastUtil.show(getContext(), getString(R.string.network_error_message)));
        }
    }

    private void login() {
        try {
            JSONObject data = new JSONObject();
            data.put("token", PreferenceUtil.getInstance(getContext()).get(PreferenceUtil.PreferenceKey.ServiceToken, ""));
            socketConnection.emit(SocketEventEnums.SocketEvent.login, data, args -> {
                Gson gson = new Gson();
                JSONObject object = (JSONObject) args[0];
                SocketResult result = gson.fromJson(object.toString(), SocketResult.class);
                if (result.getStatus() != 200) {
                    ((Activity) getContext()).runOnUiThread(() -> ToastUtil.show(getContext(), result.getMessage()));
                }
            });
        } catch (Exception e) {
            Log.e(e.getMessage());
        }
    }

    private void update(ChatRoomResponse response) {
        try {
            ((Activity) getContext()).runOnUiThread(() -> {
                if (response.getList().size() > 0) {
                    ((Activity) getContext()).runOnUiThread(() -> {
                        mMessageRecyclerViewAdapter.setData(response.getList());
                        mMessageRecyclerViewAdapter.notifyDataSetChanged();
                        rv_message.setVisibility(View.VISIBLE);
                        cl_clear_message.setVisibility(View.GONE);
                    });
                } else {
                    ((Activity) getContext()).runOnUiThread(() -> {
                        rv_message.setVisibility(View.GONE);
                        cl_clear_message.setVisibility(View.VISIBLE);
                    });
                }
            });
        }catch (Exception e){

        }
    }
    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
