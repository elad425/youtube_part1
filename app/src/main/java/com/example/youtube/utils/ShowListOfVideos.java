package com.example.youtube.utils;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.youtube.adapters.VideoListAdapter;
import com.example.youtube.entities.video;

import java.util.ArrayList;

public class ShowListOfVideos {
    public static void displayVideoList(Context context, RecyclerView lstVideos, ArrayList<video> videos) {
        final VideoListAdapter adapter = new VideoListAdapter(context);
        lstVideos.setAdapter(adapter);
        lstVideos.setLayoutManager(new LinearLayoutManager(context));
        adapter.setVideos(videos);
    }
}
