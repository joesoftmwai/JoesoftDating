package com.joesoft.joesoftdating;

import com.joesoft.joesoftdating.models.Message;
import com.joesoft.joesoftdating.models.User;

public interface IMainActivity {
    void inflateViewProfileFragment(User user);
    void onMessageSelected(Message message);
    void onBackPressed();
    void hideKeyboard();
}
