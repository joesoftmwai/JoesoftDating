package com.joesoft.joesoftdating.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Message implements Parcelable {
    private User user;
    private String message;

    public Message() {
    }

    public Message(User user, String message) {
        this.user = user;
        this.message = message;
    }

    protected Message(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        message = in.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        dest.writeString(message);
    }
}
