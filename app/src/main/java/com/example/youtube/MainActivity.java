package com.example.youtube;
import com.example.youtube.screens.SearchVideo;
import com.example.youtube.utils.ShowListOfVideos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView lstVideos = findViewById(R.id.lstVideos);
        ShowListOfVideos.displayVideoList(this, lstVideos);

        ImageButton btnBack = findViewById(R.id.search_button);
        btnBack.setOnClickListener(v -> {
            Intent i = new Intent(this, SearchVideo.class);
            startActivity(i);
        });
    }
}
