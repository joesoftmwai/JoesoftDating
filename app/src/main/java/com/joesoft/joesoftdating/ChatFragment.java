package com.joesoft.joesoftdating;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joesoft.joesoftdating.models.Message;
import com.joesoft.joesoftdating.models.User;
import com.joesoft.joesoftdating.util.PreferenceKeys;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChatFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ChatFragment";
    // vars
    private Message mMessage;
    private User mCurrentUser = new User();
    private IMainActivity mInterface;

    // widgets
    private RecyclerView mRecyclerChats;
    private ImageView mChatToolbarBackButton;
    private CircleImageView mChatToolbarImage;
    private TextView mChatToolbarText;
    private AutoCompleteTextView mChatInputMessage;
    private ImageView mChatSendMessage;
    private LinearLayoutManager mLinearLayoutManager;
    private ChatRecyclerAdapter mChatRecyclerAdapter;
    private ArrayList<Message> mMessages = new ArrayList<>();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mMessage = bundle.getParcelable(getString(R.string.intent_message));
            mMessages.add(mMessage);
        }
        getSavedPreferences();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view =  inflater.inflate(R.layout.fragment_chat, container, false);
         mRecyclerChats = view.findViewById(R.id.rvChats);
         mChatToolbarBackButton = view.findViewById(R.id.chatToolbarBackButton);
         mChatToolbarImage = view.findViewById(R.id.chatToolbarImage);
         mChatToolbarText = view.findViewById(R.id.chatToolbarText);
         mChatInputMessage = view.findViewById(R.id.chatInputMessage);
         mChatSendMessage = view.findViewById(R.id.chatSendMessage);


         mChatToolbarBackButton.setOnClickListener(this);
         mChatSendMessage.setOnClickListener(this);
         mChatToolbarImage.setOnClickListener(this);

         setBackground(view);
         initToolbar();
         initRecyclerView();

         setAutoCompleteTextView();
         return view;
    }

    private void setAutoCompleteTextView() {
        String[] messages = getResources().getStringArray(R.array.message_suggestions_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, messages);
        mChatInputMessage.setAdapter(adapter);
    }


    private void initToolbar() {
        Glide.with(getActivity())
                .load(mMessage.getUser().getProfile_image())
                .into(mChatToolbarImage);
        mChatToolbarText.setText(mMessage.getUser().getName());
    }

    private void setBackground(View view) {
        ImageView backGroundImage = view.findViewById(R.id.chatBackground);
        Glide.with(getActivity())
                .load(R.drawable.heart_background)
                .into(backGroundImage);
    }

    private void initRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mChatRecyclerAdapter = new ChatRecyclerAdapter(getActivity(), mMessages);
        mRecyclerChats.setLayoutManager(mLinearLayoutManager);
        mRecyclerChats.setAdapter(mChatRecyclerAdapter);

    }

    private void getSavedPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String name = preferences.getString(PreferenceKeys.NAME, "");
        mCurrentUser.setName(name);

        String gender = preferences.getString(PreferenceKeys.GENDER, "");
        mCurrentUser.setGender(gender);

        String profileImage = preferences.getString(PreferenceKeys.PROFILE_IMAGE, "");
        mCurrentUser.setProfile_image(profileImage);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: clicked");

        if (v.getId() == R.id.chatToolbarBackButton) {
            mInterface.onBackPressed();
        }

        if (v.getId() == R.id.chatToolbarImage) {
            mInterface.inflateViewProfileFragment(mMessage.getUser());
        }

        if (v.getId() == R.id.chatSendMessage) {
            Log.d(TAG, "onClick: posting new message");
            mMessages.add(new Message(mCurrentUser, mChatInputMessage.getText().toString()));
            mChatRecyclerAdapter.notifyDataSetChanged();
            mChatInputMessage.setText("");
            mRecyclerChats.smoothScrollToPosition(mMessages.size() -1);
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mInterface = (IMainActivity) getActivity();
    }
}
