package com.joesoft.joesoftdating;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.joesoft.joesoftdating.models.FragmentTag;
import com.joesoft.joesoftdating.models.Message;
import com.joesoft.joesoftdating.models.User;
import com.joesoft.joesoftdating.util.PreferenceKeys;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IMainActivity,
        BottomNavigationView.OnNavigationItemSelectedListener, KeyEvent.Callback,
        NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    // constants
    public static final int HOME_FRAGMENT = 0;
    public static final int CONNECTIONS_FRAGMENT = 1;
    public static final int MESSAGES_FRAGMENT = 2;

    // widgets
    private BottomNavigationViewEx mBottomNavigation;
    private ImageView mHeaderImage;
    private DrawerLayout mDrawerLayout;

    // fragments
    private HomeFragment mHomeFragment;
    private SavedConnectionsFragment mConnectionsFragment;
    private MessagesFragment mMsgFragment;
    private SettingsFragment mStsFragment;
    private ViewProfileFragment mProfileFragment;
    private ChatFragment mChatFragment;

    // vars
    private ArrayList<String> mFragmentTags = new ArrayList<>();
    private ArrayList<FragmentTag> mFragments = new ArrayList<>();
    private int mExitCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigation = findViewById(R.id.bottom_nav_view);
        mBottomNavigation.setOnNavigationItemSelectedListener(this);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        mHeaderImage = headerView.findViewById(R.id.headerImage);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        setHeaderImage();

        isFirstLogin();
        initBottomNavigation();
        setNavigationViewLister();
        init();
    }

    private void setHeaderImage() {
        Glide.with(this)
                .load(R.drawable.couple)
                .into(mHeaderImage);
    }

    private void hideBottomNavigation() {
        if (mBottomNavigation != null) {
            mBottomNavigation.setVisibility(View.GONE);
        }
    }

    private void showBottomNavigation() {
        if (mBottomNavigation != null) {
            mBottomNavigation.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.agreement:
                break;
            case R.id.ni_home:
                mFragmentTags.clear();
                mFragmentTags = new ArrayList<>();
                init();
                break;
            case R.id.bottom_nav_home:
                Log.d(TAG, "onNavigationItemSelected: Home fragment");
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    // fragment transaction
                    FragmentTransaction homeTransaction = getSupportFragmentManager().beginTransaction();
                    homeTransaction.add(R.id.main_content_frame, mHomeFragment, getString(R.string.tag_fragment_home));
                    // homeTransaction.addToBackStack(getString(R.string.tag_fragment_home));
                    homeTransaction.commit();
                    mFragmentTags.add(getString(R.string.tag_fragment_home));
                    mFragments.add(new FragmentTag(mHomeFragment, getString(R.string.tag_fragment_home)));
                } else {
                    mFragmentTags.remove(getString(R.string.tag_fragment_home));
                    mFragmentTags.add(getString(R.string.tag_fragment_home));
                }
                item.setChecked(true);
                setFragmentVisibilities(getString(R.string.tag_fragment_home));
                break;

            case R.id.bottom_nav_connections:
                Log.d(TAG, "onNavigationItemSelected: Connections fragment");
                if (mConnectionsFragment == null) {
                    mConnectionsFragment = new SavedConnectionsFragment();
                    // fragment transaction
                    FragmentTransaction connTransaction = getSupportFragmentManager().beginTransaction();
                    connTransaction.add(R.id.main_content_frame, mConnectionsFragment, getString(R.string.tag_fragment_conn));
                    // connTransaction.addToBackStack(getString(R.string.tag_fragment_conn));
                    connTransaction.commit();
                    mFragmentTags.add(getString(R.string.tag_fragment_conn));
                    mFragments.add(new FragmentTag(mConnectionsFragment, getString(R.string.tag_fragment_conn)));
                } else {
                    mFragmentTags.remove(getString(R.string.tag_fragment_conn));
                    mFragmentTags.add(getString(R.string.tag_fragment_conn));
                }
                item.setChecked(true);
                setFragmentVisibilities(getString(R.string.tag_fragment_conn));
                break;

            case R.id.bottom_nav_messages:
                Log.d(TAG, "onNavigationItemSelected: Messages fragment");
                if (mMsgFragment == null) {
                    mMsgFragment = new MessagesFragment();
                    // fragment transaction
                    FragmentTransaction msgTransaction = getSupportFragmentManager().beginTransaction();
                    msgTransaction.add(R.id.main_content_frame, mMsgFragment, getString(R.string.tag_fragment_msg));
                    // msgTransaction.addToBackStack(getString(R.string.tag_fragment_msg));
                    msgTransaction.commit();
                    mFragmentTags.add(getString(R.string.tag_fragment_msg));
                    mFragments.add(new FragmentTag(mMsgFragment, getString(R.string.tag_fragment_msg)));
                } else {
                    mFragmentTags.remove(getString(R.string.tag_fragment_msg));
                    mFragmentTags.add(getString(R.string.tag_fragment_msg));
                }
                item.setChecked(true);
                setFragmentVisibilities(getString(R.string.tag_fragment_msg));
                break;
            case R.id.settings:
                Log.d(TAG, "onNavigationItemSelected: Settings fragment");

                if (mStsFragment == null) {
                    mStsFragment = new SettingsFragment();
                    // fragment transaction
                    FragmentTransaction msgTransaction = getSupportFragmentManager().beginTransaction();
                    msgTransaction.add(R.id.main_content_frame, mStsFragment, getString(R.string.tag_fragment_sts));
                    // msgTransaction.addToBackStack(getString(R.string.tag_fragment_msg));
                    msgTransaction.commit();
                    mFragmentTags.add(getString(R.string.tag_fragment_sts));
                    mFragments.add(new FragmentTag(mStsFragment, getString(R.string.tag_fragment_sts)));
                } else {
                    mFragmentTags.remove(getString(R.string.tag_fragment_sts));
                    mFragmentTags.add(getString(R.string.tag_fragment_sts));
                }
                setFragmentVisibilities(getString(R.string.tag_fragment_sts));
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }

    private void initBottomNavigation() {
        //mBottomNavigation.enableAnimation(false);
    }

    private void setNavigationViewLister() {
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void init() {
        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
            // fragment transaction
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_frame, mHomeFragment, getString(R.string.tag_fragment_home));
            // transaction.addToBackStack(getString(R.string.tag_fragment_home));
            transaction.commit();
            mFragmentTags.add(getString(R.string.tag_fragment_home));
            mFragments.add(new FragmentTag(mHomeFragment, getString(R.string.tag_fragment_home)));
        } else {
            mFragmentTags.remove(getString(R.string.tag_fragment_home));
            mFragmentTags.add(getString(R.string.tag_fragment_home));
        }
        setFragmentVisibilities(getString(R.string.tag_fragment_home));
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        int backStackCount = mFragmentTags.size();
        if (backStackCount > 1) {
            String topFragmentTag = mFragmentTags.get(backStackCount - 1);
            String newTopFragmentTag = mFragmentTags.get(backStackCount - 2);

            setFragmentVisibilities(newTopFragmentTag);
            mFragmentTags.remove(topFragmentTag);

            mExitCount = 0;
        } else if (backStackCount == 1){
            String topFragmentTag = mFragmentTags.get(backStackCount - 1);
            if (topFragmentTag.equals(getString(R.string.tag_fragment_home))) {
                mHomeFragment.scrollToTop();
                mExitCount++;
                Toast.makeText(this, "One more click to exit", Toast.LENGTH_SHORT).show();
            } else {
                mExitCount++;
                Toast.makeText(this, "One more click to exit", Toast.LENGTH_SHORT).show();
            }
        }

        if (mExitCount >= 2) {
            super.onBackPressed();
        }

        hideKeyboard();
    }

    @Override
    public void hideKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            try {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    // hiding softInputKeyboard within a fragment

//    public static void hideKeyboard(Context context, View view) {
//        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
//    }

    private void setFragmentVisibilities(String tagName) {
        if (tagName.equals(getString(R.string.tag_fragment_home))) {
            showBottomNavigation();
        } else if (tagName.equals(getString(R.string.tag_fragment_conn))) {
            showBottomNavigation();
        } else if (tagName.equals(getString(R.string.tag_fragment_msg))) {
            showBottomNavigation();
        } else if (tagName.equals(getString(R.string.tag_fragment_sts))) {
            hideBottomNavigation();
        } else if (tagName.equals(getString(R.string.tag_fragment_chat))) {
            hideBottomNavigation();
        } else if (tagName.equals(getString(R.string.tag_fragment_profile))) {
            hideBottomNavigation();
        }

        for (int i=0; i<mFragments.size(); i++) {
            if (tagName.equals(mFragments.get(i).getTag())) {
                // show
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.show(mFragments.get(i).getFragment());
                transaction.commit();
            } else {
                // don't show
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.hide(mFragments.get(i).getFragment());
                transaction.commit();
            }
        }
        setNavigationIcon(tagName);
    }

    private void setNavigationIcon(String tagName) {
        Menu menu = mBottomNavigation.getMenu();
        MenuItem menuItem = null;

        if (tagName.equals(getString(R.string.tag_fragment_home))) {
            Log.d(TAG, "setNavigationIcon: Home fragment is visible");
            menuItem = menu.getItem(HOME_FRAGMENT);
            menuItem.setChecked(true);
        } else if (tagName.equals(getString(R.string.tag_fragment_conn))) {
            Log.d(TAG, "setNavigationIcon: Connections fragment is visible");
            menuItem = menu.getItem(CONNECTIONS_FRAGMENT);
            menuItem.setChecked(true);
        } else if (tagName.equals(getString(R.string.tag_fragment_msg))) {
            Log.d(TAG, "setNavigationIcon: Messages fragment is visible");
            menuItem = menu.getItem(MESSAGES_FRAGMENT);
            menuItem.setChecked(true);
        }

    }


    private void isFirstLogin() {
        Log.d(TAG, "isFirstLogin: checking if this is the first login");
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        boolean isFirstLogin = preferences.getBoolean(PreferenceKeys.FIRST_TIME_LOGIN, true);
        if (isFirstLogin) {
            Log.d(TAG, "isFirstLogin: launching alert dialog");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(getString(R.string.first_time_user_message));
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d(TAG, "onClick: closing the dialog");

                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean(PreferenceKeys.FIRST_TIME_LOGIN, false);
                            editor.commit();
                            dialog.dismiss();
                        }
                    });
            alertDialogBuilder.setIcon(R.drawable.heart_logo);
            alertDialogBuilder.setTitle(" ");

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }

    }

    @Override
    public void inflateViewProfileFragment(User user) {
        if (mProfileFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(mProfileFragment).commitAllowingStateLoss();
        }
        mProfileFragment = new ViewProfileFragment();
        // attach user obj as fragment arg
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.intent_user), user);
        // set arguments to fragment
        mProfileFragment.setArguments(args);
        // fragment transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_content_frame, mProfileFragment, getString(R.string.tag_fragment_profile));
        // transaction.addToBackStack(getString(R.string.tag_fragment_profile));
        transaction.commit();
        mFragmentTags.add(getString(R.string.tag_fragment_profile));
        mFragments.add(new FragmentTag(mProfileFragment, getString(R.string.tag_fragment_profile)));

        setFragmentVisibilities(getString(R.string.tag_fragment_profile));

    }

    @Override
    public void onMessageSelected(Message message) {
        if (mChatFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(mChatFragment).commitAllowingStateLoss();
        }
        mChatFragment = new ChatFragment();
        // attach user obj as fragment arg
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.intent_message), message);
        // set arguments to fragment
        mChatFragment.setArguments(args);
        // fragment transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_content_frame, mChatFragment, getString(R.string.tag_fragment_chat));
        // transaction.addToBackStack(getString(R.string.tag_fragment_chat));
        transaction.commit();
        mFragmentTags.add(getString(R.string.tag_fragment_chat));
        mFragments.add(new FragmentTag(mChatFragment, getString(R.string.tag_fragment_chat)));

        setFragmentVisibilities(getString(R.string.tag_fragment_chat));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isChildFragmentVisible()) {
            if (keyCode == KeyEvent.KEYCODE_TAB) {
                moveFocusInFragment();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void moveFocusInFragment() {

    }

    private boolean isChildFragmentVisible() {
        if (mProfileFragment != null) {
            if (mProfileFragment.isVisible()) {
                return true;
            }
        }
        if (mChatFragment != null) {
            if (mChatFragment.isVisible()) {
                return true;
            }
        }
        if (mStsFragment != null) {
            if (mStsFragment.isVisible()) {
                return true;
            }
        }
        return false;
    }
}
