package com.example.youtube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
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
        videos.add(new video("video player test","elad cohen","20 hours ago", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4", R.drawable.test_thumbnail,"10:10", "1M views"));
        videos.add(new video("video player test to see if each video play different file","yossef israeli","3 weeks ago", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4", R.drawable.thumbnail2,"12:45", "245K views"));

        adapter.setVideos(videos);
    }
}