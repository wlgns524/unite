package com.rightcode.unite.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.unite.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.unite.Adapter.ViewHolder.MessageMineFileViewHolder;
import com.rightcode.unite.Adapter.ViewHolder.MessageMineTextViewHolder;
import com.rightcode.unite.Adapter.ViewHolder.MessageSystemViewHolder;
import com.rightcode.unite.Adapter.ViewHolder.MessageYoursFileViewHolder;
import com.rightcode.unite.Adapter.ViewHolder.MessageYoursTextViewHolder;
import com.rightcode.unite.MemberManager;
import com.rightcode.unite.R;
import com.rightcode.unite.network.socket.model.ChatData;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import rx.functions.Action1;

public class MessageDetailRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    public final int MINE = 0;
    public final int MINE_FILE = 1;
    public final int YOURS = 2;
    public final int YOURS_FILE = 3;
    public final int SYSTEM = 4;


    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @Setter
    @Getter
    private ArrayList<ChatData> data;
    @Setter
    private Action1<ChatData> userKickAction;
    private Context mContext;


    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public MessageDetailRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == MINE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message_mine_text, viewGroup, false);
            return new MessageMineTextViewHolder(view, mContext);
        } else if (viewType == MINE_FILE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message_mine_file, viewGroup, false);
            return new MessageMineFileViewHolder(view, mContext);
        } else if (viewType == YOURS) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message_yours_text, viewGroup, false);
            return new MessageYoursTextViewHolder(view, mContext);
        } else if (viewType == YOURS_FILE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message_yours_file, viewGroup, false);
            return new MessageYoursFileViewHolder(view, mContext);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message_system, viewGroup, false);
            return new MessageSystemViewHolder(view, mContext);
        }
    }


    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int position) {
        if (viewHolder instanceof MessageMineTextViewHolder) {
            ((MessageMineTextViewHolder) viewHolder).onBind(data.get(position));
        } else if (viewHolder instanceof MessageMineFileViewHolder) {
            ((MessageMineFileViewHolder) viewHolder).onBind(data.get(position));
        } else if (viewHolder instanceof MessageYoursTextViewHolder) {
            ((MessageYoursTextViewHolder) viewHolder).onBind(data.get(position));
            ((MessageYoursTextViewHolder) viewHolder).setUserKickAction(userKickAction);
        } else if (viewHolder instanceof MessageYoursFileViewHolder) {
            ((MessageYoursFileViewHolder) viewHolder).onBind(data.get(position));
            ((MessageYoursFileViewHolder) viewHolder).setUserKickAction(userKickAction);
        } else {
            ((MessageSystemViewHolder) viewHolder).onBind(data.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).getType().equals("system")) {
            return SYSTEM;
        } else {
            if (data.get(position).getUserId().equals(MemberManager.getInstance(mContext).getUserInfo().getId())) {
                if (data.get(position).getType().equals("file")) {
                    return MINE_FILE;
                } else {
                    return MINE;
                }
            } else {
                if (data.get(position).getType().equals("file")) {
                    return YOURS_FILE;
                } else {
                    return YOURS;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    public void newMessage(ChatData chatData) {
        if (data == null)
            data = new ArrayList<>();
        data.add(chatData);
        notifyItemChanged(data.size() - 1);
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
