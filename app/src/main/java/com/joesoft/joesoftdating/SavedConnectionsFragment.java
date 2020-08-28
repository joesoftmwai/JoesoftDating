package com.joesoft.joesoftdating;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joesoft.joesoftdating.models.User;
import com.joesoft.joesoftdating.util.PreferenceKeys;
import com.joesoft.joesoftdating.util.Users;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SavedConnectionsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "SavedConnectionsFragment";
    private static final int COLUMN_COUNT = 2;
    // widgets
    private RecyclerView mRecyclerUsers;
    // vars
    private UsersRecyclerAdapter mUsersRecyclerAdapter;
    private ArrayList<User> mMatches = new ArrayList<>();
    private GridLayoutManager mGridLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_saved_connections, container, false);
        mRecyclerUsers = view.findViewById(R.id.rvUsers);
        mSwipeRefreshLayout = view.findViewById(R.id.saved_conn_swipe_ref_layout);

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

        mGridLayoutManager = new GridLayoutManager(getContext(), COLUMN_COUNT);
        mUsersRecyclerAdapter = new UsersRecyclerAdapter(mMatches, getActivity());
        mRecyclerUsers.setLayoutManager(mGridLayoutManager);
        mRecyclerUsers.setAdapter(mUsersRecyclerAdapter);

    }

    @Override
    public void onRefresh() {
        getConnections();
        onItemsLoadComplete();
    }

    private void onItemsLoadComplete() {
        mUsersRecyclerAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}

