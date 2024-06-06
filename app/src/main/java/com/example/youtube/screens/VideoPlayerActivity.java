package com.example.youtube.screens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.youtube.MainActivity;
import com.example.youtube.R;
import com.example.youtube.adapters.CommentsAdapter;
import com.example.youtube.entities.comment;
import com.example.youtube.entities.video;
import com.example.youtube.utils.ShowListOfVideos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VideoPlayerActivity extends AppCompatActivity {

    private boolean isLiked = false;
    private boolean isDisliked = false;
    private boolean areCommentsVisible = false;
    private RecyclerView rvComments;
    private TextView tvComments;
    private ImageButton ivToggleComments;
    private FloatingActionButton fabAddComment;
    private CommentsAdapter commentsAdapter;
    private List<comment> commentList;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        RecyclerView lstVideos = findViewById(R.id.lstVideos);
        ShowListOfVideos.displayVideoList(this,lstVideos);

        Intent intent = getIntent();
        if (intent != null) {
            video videoItem = (video) intent.getSerializableExtra("video_item");
            if (videoItem != null) {
                String videoPath = videoItem.getVideo_path();
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

                tvVideoName.setText(videoItem.getVideo_name());
                tvVideoViews.setText(videoItem.getViews());
                tvCreator.setText(videoItem.getCreator());
                tvPublishDate.setText(videoItem.getDate_of_release());

                // Initialize comments section
                tvComments = findViewById(R.id.tv_comments);
                rvComments = findViewById(R.id.rv_comments);
                ivToggleComments = findViewById(R.id.iv_toggle_comments);
                fabAddComment = findViewById(R.id.fab_add_comment);

                commentList = videoItem.getComments();
                tvComments.setText(String.format("Comments (%d)", commentList.size()));
                ivToggleComments.setOnClickListener(v -> toggleComments());
                tvComments.setOnClickListener(v -> toggleComments());

                rvComments.setLayoutManager(new LinearLayoutManager(this));
                commentsAdapter = new CommentsAdapter(commentList);
                rvComments.setAdapter(commentsAdapter);

                fabAddComment.setOnClickListener(v -> showAddCommentDialog());
            }
        }

        ImageButton btnShare = findViewById(R.id.tv_btn_share);
        ImageButton btnLike = findViewById(R.id.tv_btn_like);
        ImageButton btnDislike = findViewById(R.id.tv_btn_dislike);
        ImageButton btnBack = findViewById(R.id.tv_video_back);

        btnBack.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });

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

        btnShare.setOnClickListener(v -> {
            if (intent != null && intent.getSerializableExtra("video_item") != null) {
                video videoItem = (video) intent.getSerializableExtra("video_item");
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this video");
                assert videoItem != null;
                shareIntent.putExtra(Intent.EXTRA_TEXT, videoItem.getVideo_path());
                startActivity(Intent.createChooser(shareIntent, "Share Video"));
            }
        });
    }

    private void toggleComments() {
        if (areCommentsVisible) {
            rvComments.setVisibility(View.GONE);
            fabAddComment.setVisibility(View.GONE);
            ivToggleComments.setImageResource(R.drawable.ic_arrow_down);
        } else {
            rvComments.setVisibility(View.VISIBLE);
            fabAddComment.setVisibility(View.VISIBLE);
            ivToggleComments.setImageResource(R.drawable.ic_arrow_up);
        }
        areCommentsVisible = !areCommentsVisible;
    }

    @SuppressLint({"DefaultLocale", "NotifyDataSetChanged"})
    private void showAddCommentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add a comment");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String commentText = input.getText().toString().trim();
            if (!commentText.isEmpty()) {
                comment newComment = new comment(commentText, "CurrentUser","now");
                commentList.add(newComment);
                commentsAdapter.notifyDataSetChanged();
                tvComments.setText(String.format("Comments (%d)", commentList.size()));
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}
