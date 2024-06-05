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
        videos.add(new video("video player test","elad cohen","20 hours ago", "android.resource://"+getPackageName()+"/raw/record", R.drawable.test_thumbnail,"10:10", "1M views", "333k"));
        videos.add(new video("video player test to see if each video play different file","yossef israeli","3 weeks ago", "android.resource://"+getPackageName()+"/raw/video3", R.drawable.thumbnail2,"12:45", "245K views", "2M"));
        videos.add(new video("video player test to see the flow of the videos when scrolling","Elon musk","2 day ago", "android.resource://"+getPackageName()+"/raw/video1", R.drawable.thumbnail3,"20:21", "6.5M views", "1M"));
        adapter.setVideos(videos);
    }
}