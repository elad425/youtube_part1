package com.example.youtube.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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

    private boolean isLiked = false;
    private boolean isDisliked = false;

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
                final VideoView videoView = findViewById(R.id.tv_video_view);
                videoView.setVideoURI(Uri.parse(videoPath));

                // Add media controller
                MediaController mediaController = new MediaController(this);
                mediaController.setAnchorView(videoView);
                videoView.setMediaController(mediaController);

                // Start playing the video
                videoView.start();

                // display info about the video
                TextView tvVideoName = findViewById(R.id.tv_video_name);
                TextView tvVideoViews = findViewById(R.id.tv_video_views);
                TextView tvCreator = findViewById(R.id.tv_creator);
                TextView tvPublishDate = findViewById(R.id.tv_publish_date);

                tvVideoName.setText(videoItem.getVideoName());
                tvVideoViews.setText(videoItem.getViews());
                tvCreator.setText(videoItem.getCreator());
                tvPublishDate.setText(videoItem.getDate_of_release());
            }
        }

        ImageButton btnBack = findViewById(R.id.tv_video_back);
        btnBack.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });

        ImageButton btnLike = findViewById(R.id.tv_btn_like);
        ImageButton btnDislike = findViewById(R.id.tv_btn_dislike);

        btnLike.setOnClickListener(v -> {
            if (!isLiked) {
                btnLike.setImageResource(R.drawable.ic_like_fill);
                isLiked = true;
                if (isDisliked) {
                    btnDislike.setImageResource(R.drawable.ic_dislike);
                    isDisliked = false;
                }
            } else {
                btnLike.setImageResource(R.drawable.ic_like);
                isLiked = false;
            }
        });

        btnDislike.setOnClickListener(v -> {
            if (!isDisliked) {
                btnDislike.setImageResource(R.drawable.ic_dislike_fill);
                isDisliked = true;
                if (isLiked) {
                    btnLike.setImageResource(R.drawable.ic_like);
                    isLiked = false;
                }
            } else {
                btnDislike.setImageResource(R.drawable.ic_dislike);
                isDisliked = false;
            }
        });

        ImageButton btnShare = findViewById(R.id.tv_btn_share);
        btnShare.setOnClickListener(v -> {
            if (intent != null && intent.getSerializableExtra("video_item") != null) {
                video videoItem = (video) intent.getSerializableExtra("video_item");
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this video");
                assert videoItem != null;
                shareIntent.putExtra(Intent.EXTRA_TEXT, videoItem.getVideoPath());
                startActivity(Intent.createChooser(shareIntent, "Share Video"));
            }
        });
    }
}