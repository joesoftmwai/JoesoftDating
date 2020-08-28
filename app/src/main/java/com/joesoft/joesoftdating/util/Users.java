package com.joesoft.joesoftdating.util;

import android.net.Uri;

import com.joesoft.joesoftdating.R;
import com.joesoft.joesoftdating.models.User;

public class Users {
    public User[] USERS = {
            Hanna, Dustin, Charlie,
            Sandy, Clara, MK
    };

    // gents
    public static final User Charlie = new User(
            Uri.parse("android.resource://com.joesoft.joesoftdating/" + R.drawable.charlie).toString(),
            "Charlie", "Male", "Female", "Looking"
    );
    public static final User Dustin = new User(
            Uri.parse("android.resource://com.joesoft.joesoftdating/" + R.drawable.dustin).toString(),
            "Dustin", "Male", "Female", "Not Looking"
    );
    public static final User MK = new User(
            Uri.parse("android.resource://com.joesoft.joesoftdating/" + R.drawable.mk).toString(),
            "MK", "Male", "Female", "Looking"
    );

    // ladies
    public static final User Hanna = new User(
            Uri.parse("android.resource://com.joesoft.joesoftdating/" + R.drawable.hanna).toString(),
            "Hanna", "Female", "Male", "Not Looking"
    );
    public static final User Clara = new User(
            Uri.parse("android.resource://com.joesoft.joesoftdating/" + R.drawable.clara).toString(),
            "Clara", "Female", "Male", "Looking"
    );
    public static final User Sandy = new User(
            Uri.parse("android.resource://com.joesoft.joesoftdating/" + R.drawable.sandy).toString(),
            "Sandy", "Female", "Male", "Looking"
    );
}
