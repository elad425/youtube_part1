package com.example.youtube.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.youtube.MainActivity;
import com.example.youtube.R;
import com.example.youtube.entities.video;

import org.jetbrains.annotations.Nullable;

public class VideoPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        Intent intent = getIntent();
        if (intent != null) {
            video videoItem = (video) intent.getSerializableExtra("video_item");
            if (videoItem != null) {
                String videoPath = videoItem.getVideoPath();
                // Initialize the VideoView
                final VideoView videoView = findViewById(R.id.video_view);
                videoView.setVideoPath(videoPath);

                // Add media controller
                MediaController mediaController = new MediaController(this);
                mediaController.setAnchorView(videoView);
                videoView.setMediaController(mediaController);

                // Start playing the video
                videoView.start();

                // display info about the video
                TextView tvVideoName = findViewById(R.id.tv_video_name);
                TextView tvVideoViews = findViewById(R.id.tv_video_views);

                tvVideoName.setText(videoItem.getVideoName());
                tvVideoViews.setText(videoItem.getViews());
            }
        }

        ImageButton btnBack = findViewById(R.id.video_back);
        btnBack.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });

    }
}