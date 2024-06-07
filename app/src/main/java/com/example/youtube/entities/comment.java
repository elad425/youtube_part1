package com.example.youtube.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class comment implements Parcelable {
    private String comment;
    private String user;
    private String date;
    public comment(String comment, String user, String date) {
        this.comment = comment;
        this.user = user;
        this.date = date;
    }

    protected comment(Parcel in) {
        comment = in.readString();
        user = in.readString();
        date = in.readString();
    }

    public static final Creator<comment> CREATOR = new Creator<comment>() {
        @Override
        public comment createFromParcel(Parcel in) {
            return new comment(in);
        }

        @Override
        public comment[] newArray(int size) {
            return new comment[size];
        }
    };

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(comment);
        dest.writeString(user);
        dest.writeString(date);
    }
}
