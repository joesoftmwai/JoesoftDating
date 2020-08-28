package com.joesoft.joesoftdating;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.joesoft.joesoftdating.models.Message;
import com.joesoft.joesoftdating.models.User;
import com.joesoft.joesoftdating.util.Messages;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesRecyclerAdapter extends RecyclerView.Adapter<MessagesRecyclerAdapter.ViewHolder> {
    private static final String TAG = "MessagesRecyclerAdapter";
    private ArrayList<User> mUsers;
    private Context mContext;
    private IMainActivity mInterface;

    public MessagesRecyclerAdapter(ArrayList<User> users, Context context) {
        mUsers = users;
        mContext = context;
    }

    @NonNull
    @Override
    public MessagesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_message, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesRecyclerAdapter.ViewHolder holder, int position) {
        User user = mUsers.get(position);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        Glide.with(mContext)
                .load(user.getProfile_image())
                .apply(requestOptions)
                .into(holder.mMsgImage);
        holder.mMsgName.setText(user.getName());
        // holder.mMsgMessage.setText(R.string.msg_default_Message);
        holder.mMsgMessage.setText(Messages.MESSAGES[position]);
        holder.mMsgReply.setText(R.string.msg_default_reply);
        holder.mCurrentPosition = position;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mInterface = (IMainActivity) mContext;
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView mMsgImage;
        private TextView mMsgName;
        private TextView mMsgMessage;
        private TextView mMsgReply;
        private int mCurrentPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mMsgImage = itemView.findViewById(R.id.msgImage);
            mMsgName = itemView.findViewById(R.id.msgName);
            mMsgMessage = itemView.findViewById(R.id.msgMessage);
            mMsgReply = itemView.findViewById(R.id.msgReply);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: user item selected");
//            Toast.makeText(mContext, "Hi!", Toast.LENGTH_SHORT).show();
            mInterface.onMessageSelected(new Message(mUsers.get(mCurrentPosition), Messages.MESSAGES[mCurrentPosition]));
        }
    }
}
