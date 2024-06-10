package com.example.youtube.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Objects;

public class user implements Parcelable {
    private String name;
    private String email;
    private  String password;
    private String profile_pic;
    private ArrayList<video> likedVideos;
    private ArrayList<video> dislikedVideos;
    private ArrayList<creator> subs;
    public user(String name, String email, String password, String profile_pic) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profile_pic = profile_pic;
        likedVideos = new ArrayList<>();
        dislikedVideos = new ArrayList<>();
        subs = new ArrayList<>();
    }

    protected user(Parcel in) {
        name = in.readString();
        email = in.readString();
        password = in.readString();
        profile_pic = in.readString();
        likedVideos = in.createTypedArrayList(video.CREATOR);
        dislikedVideos = in.createTypedArrayList(video.CREATOR);
        subs = in.createTypedArrayList(creator.CREATOR);
    }

    public static final Creator<user> CREATOR = new Creator<user>() {
        @Override
        public user createFromParcel(Parcel in) {
            return new user(in);
        }

        @Override
        public user[] newArray(int size) {
            return new user[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public ArrayList<video> getLikedVideos() {
        return likedVideos;
    }

    public void setLikedVideos(ArrayList<video> likedVideos) {
        this.likedVideos = likedVideos;
    }

    public ArrayList<video> getDislikedVideos() {
        return dislikedVideos;
    }

    public void setDislikedVideos(ArrayList<video> dislikedVideos) {
        this.dislikedVideos = dislikedVideos;
    }

    public ArrayList<creator> getSubs() {
        return subs;
    }

    public void setSubs(ArrayList<creator> subs) {
        this.subs = subs;
    }

    public void addToLiked(video video){
        for (video v : likedVideos){
            if (Objects.equals(v.getVideo_name(), video.getVideo_name())){
                return;
            }
        }
        this.likedVideos.add(video);
    }

    public void removeFromLiked(video video){
        likedVideos.removeIf(v -> Objects.equals(v.getVideo_name(), video.getVideo_name()));
    }

    public void addToDisLiked(video video){
        for (video v : dislikedVideos){
            if (Objects.equals(v.getVideo_name(), video.getVideo_name())){
                return;
            }
        }
        this.dislikedVideos.add(video);
    }

    public void removeFromDisLiked(video video){
        dislikedVideos.removeIf(v -> Objects.equals(v.getVideo_name(), video.getVideo_name()));
    }

    public void addToSubs(creator creator){
        this.subs.add(creator);
    }

    public void removeFromSubs(creator creator){
        subs.removeIf(c -> Objects.equals(c.getName(), creator.getName()));
    }

    public boolean isLiked(video video){
        for (video v : likedVideos){
            if (Objects.equals(v.getVideo_name(), video.getVideo_name())){
                return true;
            }
        }
        return false;
    }

    public boolean isDisLiked(video video){
        for (video v : dislikedVideos){
            if (Objects.equals(v.getVideo_name(), video.getVideo_name())){
                return true;
            }
        }
        return false;
    }

    public boolean isSubs(creator creator){
        for (creator c : subs){
            if (Objects.equals(c.getName(), creator.getName())){
                return true;
            }
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(profile_pic);
        dest.writeTypedList(likedVideos);
        dest.writeTypedList(dislikedVideos);
        dest.writeTypedList(subs);
    }
}
