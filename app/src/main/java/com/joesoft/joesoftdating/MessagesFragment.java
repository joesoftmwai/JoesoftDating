package com.joesoft.joesoftdating;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joesoft.joesoftdating.models.User;
import com.joesoft.joesoftdating.util.PreferenceKeys;
import com.joesoft.joesoftdating.util.Users;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class MessagesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "MessagesFragment";
    // widgets
    private RecyclerView mRecyclerUsers;
    // vars
    private MessagesRecyclerAdapter mUsersRecyclerAdapter;
    private ArrayList<User> mMatches = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view =  inflater.inflate(R.layout.fragment_messages, container, false);
        mRecyclerUsers = view.findViewById(R.id.rvUsers);
        mSwipeRefreshLayout = view.findViewById(R.id.msgs_swipe_ref_layout);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        getConnections();
        return view;
    }

    private void getConnections() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> savedNames = preferences.getStringSet(PreferenceKeys.SAVED_CONNECTION, new HashSet<String>());
        Users users = new Users();
        if (mMatches != null) {
            mMatches.clear();
        }
        for (User user : users.USERS) {
            if (savedNames.contains(user.getName())) {
                mMatches.add(user);
            }
        }
        if (mUsersRecyclerAdapter == null) {
            initRecyclerView();
        }
    }


    private void initRecyclerView() {

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mUsersRecyclerAdapter = new MessagesRecyclerAdapter(mMatches, getActivity());
        mRecyclerUsers.setLayoutManager(mLinearLayoutManager);
        mRecyclerUsers.setAdapter(mUsersRecyclerAdapter);

    }

    @Override
    public void onRefresh() {
        getConnections();
        onItemsLoadFinished();
    }

    private void onItemsLoadFinished() {
        mUsersRecyclerAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
