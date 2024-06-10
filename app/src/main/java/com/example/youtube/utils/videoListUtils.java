package com.example.youtube.utils;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.youtube.adapters.VideoListAdapter;
import com.example.youtube.entities.user;
import com.example.youtube.entities.video;

import java.util.ArrayList;
import java.util.Objects;

public class videoListUtils {

    public static void displayVideoList(Context context, RecyclerView lstVideos, ArrayList<video> videos, user user) {
        final VideoListAdapter adapter = new VideoListAdapter(context, user);
        lstVideos.setAdapter(adapter);
        lstVideos.setLayoutManager(new LinearLayoutManager(context));
        adapter.setVideos(videos);
    }

    public static int findVideoPlace (ArrayList<video> videos, video video){
        int counter = 0;
        for (video v : videos){
            if (Objects.equals(v.getVideo_name(), video.getVideo_name())){
                return counter;
            }
            counter++;
        }
        return counter;
    }

    public static ArrayList<video> filterVideoList(ArrayList<video> videos, ArrayList<video> filter){
        ArrayList<video> temp = new ArrayList<video>();
        if (filter != null) {
            for (video f : filter) {
                for (video v: videos){
                    if (!v.getVideo_name().equals(f.getVideo_name())){
                        temp.add(v);
                    }
                }
            }
            return temp;
        }
        return videos;
    }

}
