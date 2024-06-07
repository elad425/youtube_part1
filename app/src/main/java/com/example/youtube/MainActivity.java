package com.example.youtube;
import com.example.youtube.entities.video;
import com.example.youtube.screens.SearchVideo;
import com.example.youtube.utils.JsonUtils;
import com.example.youtube.utils.ShowListOfVideos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<video> videos;

        Intent intent = getIntent();
        ArrayList<video> temp = intent.getParcelableArrayListExtra("video_list");
        if (temp != null){
            videos = temp;
        }else {
            videos = JsonUtils.loadVideosFromJson(this);
        }

        RecyclerView lstVideos = findViewById(R.id.lstVideos);
        ShowListOfVideos.displayVideoList(this, lstVideos, videos);

        ImageButton btnBack = findViewById(R.id.search_button);
        btnBack.setOnClickListener(v -> {
            Intent i = new Intent(this, SearchVideo.class);
            i.putParcelableArrayListExtra("video_list", videos);
            startActivity(i);
        });
    }
}
