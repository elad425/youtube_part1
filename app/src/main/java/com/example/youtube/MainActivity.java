package com.example.youtube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.youtube.adapters.VideoListAdapter;
import com.example.youtube.entities.video;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView lstVideos = findViewById(R.id.lstVideos);
        final VideoListAdapter adapter = new VideoListAdapter(this);
        lstVideos.setAdapter(adapter);
        lstVideos.setLayoutManager(new LinearLayoutManager(this));

        List<video> videos = new ArrayList<>();
        videos.add(new video("android","elad","13/01/1999", "video", R.drawable.test_thumbnail,"10:10"));
        videos.add(new video("android1","elad1","14/01/1999", "video1", R.drawable.test_thumbnail,"10:10"));
        videos.add(new video("android2","elad2","15/01/1999", "video2", R.drawable.test_thumbnail,"10:10"));
        videos.add(new video("android3","elad3","16/01/1999", "video3", R.drawable.test_thumbnail,"10:10"));


        adapter.setVideos(videos);
    }
}