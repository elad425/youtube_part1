package com.example.youtube.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class video implements Serializable {
    private String video_name;
    private String creator;
    private String date_of_release;
    private String views;
    private String likes;
    private List<comment> comments;
    private String video_path;
    private String thumbnail; // Change this to String
    private String video_length;

    public video(String video_name, String creator, String date_of_release, String video_path, String thumbnail, String video_length, String views, String likes) {
        this.video_name = video_name;
        this.creator = creator;
        this.date_of_release = date_of_release;
        this.video_path = video_path;
        this.thumbnail = thumbnail;
        this.views = views;
        this.video_length = video_length;
        this.comments = new ArrayList<>();
        this.likes = likes;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDate_of_release() {
        return date_of_release;
    }

    public void setDate_of_release(String date_of_release) {
        this.date_of_release = date_of_release;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public List<comment> getComments() {
        return comments;
    }

    public void setComments(List<comment> comments) {
        this.comments = comments;
    }

    public String getVideo_path() {
        return video_path;
    }

    public void setVideo_path(String video_path) {
        this.video_path = video_path;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideo_length() {
        return video_length;
    }

    public void setVideo_length(String video_length) {
        this.video_length = video_length;
    }
}
