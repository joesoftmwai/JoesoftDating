package com.joesoft.joesoftdating;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joesoft.joesoftdating.models.User;
import com.joesoft.joesoftdating.util.PreferenceKeys;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.HashSet;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;


public class ViewProfileFragment extends Fragment implements OnLikeListener, View.OnClickListener {
    private static final String TAG = "ViewProfileFragment";
    // widgets
    private TextView mProfileName, mProfileInterestedIn, mProfileGender, mProfileStatus;
    private LikeButton mLikeButton;
    private CircleImageView mProfileImage;
    private ImageView mBackButton;
    // vars
    private User mUser;
    private IMainActivity mInterface;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mUser = bundle.getParcelable(getString(R.string.intent_user));
            Log.d(TAG, "onCreate: got incoming bundle: user " + mUser.getName());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_view_profile, container, false);
        mProfileName = view.findViewById(R.id.pName);
        mProfileInterestedIn = view.findViewById(R.id.pInterestedIn);
        mProfileGender = view.findViewById(R.id.pGender);
        mProfileStatus = view.findViewById(R.id.pStatus);
        mLikeButton = view.findViewById(R.id.pLikeButton);
        mProfileImage = view.findViewById(R.id.pImage);
        mBackButton = view.findViewById(R.id.pBackButton);

        mLikeButton.setOnLikeListener(this);
        mBackButton.setOnClickListener(this);

        checkIfConnected();
        setBackgroundImage(view);
        init();

        return view;
    }

    private void init() {
        if (mUser != null) {
            Glide.with(getActivity())
                    .load(mUser.getProfile_image())
                    .into(mProfileImage);
            mProfileName.setText(mUser.getName());
            mProfileInterestedIn.setText(mUser.getInterested_in());
            mProfileGender.setText(mUser.getGender());
            mProfileStatus.setText(mUser.getStatus());

        }
    }

    private void checkIfConnected() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> savedNames = preferences.getStringSet(PreferenceKeys.SAVED_CONNECTION, new HashSet<String>());

        if (savedNames.contains(mUser.getName())) {
            mLikeButton.setLiked(true);
        } else {
            mLikeButton.setLiked(false);
        }
    }

    private void setBackgroundImage(View  view) {
        ImageView backgroundImage = view.findViewById(R.id.pBackground);
        Glide.with(getActivity())
                .load(R.drawable.heart_background)
                .into(backgroundImage);
    }

    @Override
    public void liked(LikeButton likeButton) {
        Log.d(TAG, "liked: liked");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        Set<String> savedNames = preferences.getStringSet(PreferenceKeys.SAVED_CONNECTION, new HashSet<String>());
        savedNames.add(mUser.getName());

        editor.putStringSet(PreferenceKeys.SAVED_CONNECTION, savedNames);
        editor.commit();
    }

    @Override
    public void unLiked(LikeButton likeButton) {
        Log.d(TAG, "unLiked: unLiked");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        Set<String> savedNames = preferences.getStringSet(PreferenceKeys.SAVED_CONNECTION, new HashSet<String>());
        savedNames.remove(mUser.getName());
        editor.remove(PreferenceKeys.SAVED_CONNECTION);
        editor.commit();

        editor.putStringSet(PreferenceKeys.SAVED_CONNECTION, savedNames);
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pBackButton) {
            mInterface.onBackPressed();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mInterface = (IMainActivity) getActivity();
    }
}
