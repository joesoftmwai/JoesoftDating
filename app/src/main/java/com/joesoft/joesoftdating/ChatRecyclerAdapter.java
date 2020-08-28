package com.joesoft.joesoftdating;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.joesoft.joesoftdating.models.Message;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Message> mMessages;

    public ChatRecyclerAdapter(Context context, ArrayList<Message> messages) {
        mContext = context;
        mMessages = messages;
    }

    @NonNull
    @Override
    public ChatRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRecyclerAdapter.ViewHolder holder, int position) {
        Message message = mMessages.get(position);
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        Glide.with(mContext)
                .load(message.getUser().getProfile_image())
                .apply(requestOptions)
                .into(holder.mChatImage);

        holder.mChatText.setText(message.getMessage());



    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mChatImage;
        private TextView mChatText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mChatImage = itemView.findViewById(R.id.chatImage);
            mChatText = itemView.findViewById(R.id.chatText);
        }
    }
}
