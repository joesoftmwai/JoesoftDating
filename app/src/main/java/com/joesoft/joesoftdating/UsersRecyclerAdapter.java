package com.joesoft.joesoftdating;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.joesoft.joesoftdating.models.User;

import java.util.ArrayList;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder> {
        private static final String TAG = "UsersRecyclerAdapter";
        private ArrayList<User> mUsers;
        private Context mContext;
        private IMainActivity mInterface;

    public UsersRecyclerAdapter(ArrayList<User> users, Context context) {
        mUsers = users;
        mContext = context;
    }

    @NonNull
    @Override
    public UsersRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsersRecyclerAdapter.ViewHolder holder, int position) {
        User user = mUsers.get(position);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        Glide.with(mContext)
                .load(user.getProfile_image())
                .apply(requestOptions)
                .into(holder.mProfileImage);
        holder.mTextName.setText(user.getName());
        holder.mTextInterestedIn.setText(user.getInterested_in());
        holder.mTextStatus.setText(user.getStatus());
        holder.mCurrentPosition = position;
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mInterface = (IMainActivity) mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mProfileImage;
        private TextView mTextName;
        private TextView mTextStatus;
        private TextView mTextInterestedIn;
        private int mCurrentPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mProfileImage = itemView.findViewById(R.id.profileImage);
            mTextName = itemView.findViewById(R.id.tvName);
            mTextInterestedIn = itemView.findViewById(R.id.tvInterestedIn);
            mTextStatus = itemView.findViewById(R.id.tvStatus);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: user item selected");
            mInterface.inflateViewProfileFragment(mUsers.get(mCurrentPosition));
        }
    }
}
