package com.example.youtube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.youtube.adapters.VideoListAdapter;
import com.example.youtube.entities.video;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
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

        List<video> videos = loadVideosFromJson();
        adapter.setVideos(videos);
    }

    private List<video> loadVideosFromJson() {
        InputStream inputStream = getResources().openRawResource(R.raw.videoinfo);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Gson gson = new Gson();
        Type videoListType = new TypeToken<List<video>>() {}.getType();
        return gson.fromJson(reader, videoListType);
    }
}
