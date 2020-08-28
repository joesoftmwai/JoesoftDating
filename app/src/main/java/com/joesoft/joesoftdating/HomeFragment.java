package com.joesoft.joesoftdating;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joesoft.joesoftdating.models.User;
import com.joesoft.joesoftdating.util.Users;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "HomeFragment";
    public static final int COLUMN_COUNT = 2;
    // widgets
    private RecyclerView mRecyclerUsers;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    // vars
    private UsersRecyclerAdapter mUsersRecyclerAdapter;
    private ArrayList<User> mMatches = new ArrayList<>();
    private GridLayoutManager mGridLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerUsers = view.findViewById(R.id.rvUsers);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        findMatches();

        return view;
    }

    private void findMatches() {
        Users users = new Users();
        if (mMatches != null) {
            mMatches.clear();
        }
        for (User user : users.USERS) {
            mMatches.add(user);
        }
        if (mUsersRecyclerAdapter == null) {
            initRecyclerView();
        }
    }

    
    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: initialize recycler view");

        mGridLayoutManager = new GridLayoutManager(getContext(), COLUMN_COUNT);
        mUsersRecyclerAdapter = new UsersRecyclerAdapter(mMatches, getActivity());
        mRecyclerUsers.setLayoutManager(mGridLayoutManager);
        mRecyclerUsers.setAdapter(mUsersRecyclerAdapter);

    }

    public void scrollToTop() {
        mRecyclerUsers.smoothScrollToPosition(0);
    }

    @Override
    public void onRefresh() {
        findMatches();
        onItemsLoadComplete();
    }

    private void onItemsLoadComplete() {
        mUsersRecyclerAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
