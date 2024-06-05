package com.example.youtube.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class video implements Serializable {
    private String video_name;
    private String creator;
    private String date_of_release;
    private String views;
    private int likes;
    private List<comment> comments;
    private String video_path;
    private int thumbnail;
    private String video_length;

    public video(String video_name, String creator, String date_of_release, String video_path, int thumbnail, String video_length, String views) {
        this.video_name = video_name;
        this.creator = creator;
        this.date_of_release = date_of_release;
        this.video_path = video_path;
        this.thumbnail = thumbnail;
        this.views = views;
        this.video_length = video_length;
        this.comments = new ArrayList<comment>();
    }

    public String getVideoName() {
        return video_name;
    }

    public void setVideoName(String video_name) {
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getVideoPath() {
        return video_path;
    }

    public void setVideoPath(String video_path) {
        this.video_path = video_path;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<comment> getComments() {
        return comments;
    }

    public void setComments(List<comment> comments) {
        this.comments = comments;
    }

    public String getVideo_length() {
        return video_length;
    }

    public void setVideo_length(String video_length) {
        this.video_length = video_length;
    }
}
