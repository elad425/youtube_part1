package com.example.youtube.screens;

import static com.example.youtube.utils.ArrayFunction.findVideoPlace;

import androidx.activity.OnBackPressedCallback;
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
import android.widget.Toast;
import android.widget.VideoView;

import com.example.youtube.MainActivity;
import com.example.youtube.R;
import com.example.youtube.adapters.CommentsAdapter;
import com.example.youtube.entities.comment;
import com.example.youtube.entities.user;
import com.example.youtube.entities.video;
import com.example.youtube.utils.ShowListOfVideos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class VideoPlayerActivity extends AppCompatActivity {

    private boolean isLiked = false;
    private boolean isDisliked = false;
    private boolean areCommentsVisible = false;
    private RecyclerView rvComments;
    private TextView tvComments;
    private ImageButton ivToggleComments;
    private FloatingActionButton fabAddComment;
    private CommentsAdapter commentsAdapter;
    private ArrayList<comment> commentList;
    private ArrayList<video> videos;
    private int videoNumber;
    private user user;
    private video videoItem;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        Intent Login = new Intent(this, LogIn.class);

        RecyclerView lstVideos = findViewById(R.id.lstVideos);

        Intent intent = getIntent();
        videoItem = intent.getParcelableExtra("video_item");
        videos = intent.getParcelableArrayListExtra("video_list");
        user = intent.getParcelableExtra("user");

        if (user != null) {
            isLiked = user.isLiked(videoItem);
            isDisliked = user.isDisLiked(videoItem);
        }

        if (videoItem != null && videos != null) {
            videoNumber = findVideoPlace(videos, videoItem);
            ShowListOfVideos.displayVideoList(this, lstVideos, videos, user);
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

            // Display info about the video
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
            commentsAdapter = new CommentsAdapter(commentList,this, user, this);
            rvComments.setAdapter(commentsAdapter);

            fabAddComment.setOnClickListener(v -> {
                if (user == null) {
                    Toast.makeText(this, "please login in order to add a comment",
                            Toast.LENGTH_SHORT).show();
                    startActivity(Login);
                } else{
                    showAddCommentDialog();
                }
            });
        }

        ImageButton btnShare = findViewById(R.id.tv_btn_share);
        ImageButton btnLike = findViewById(R.id.tv_btn_like);
        ImageButton btnDislike = findViewById(R.id.tv_btn_dislike);
        ImageButton btnBack = findViewById(R.id.tv_video_back);

        if (isLiked){btnLike.setImageResource(R.drawable.ic_like_fill);}
        if (isDisliked){btnDislike.setImageResource(R.drawable.ic_dislike_fill);}

        btnBack.setOnClickListener(v -> handleBackAction());

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                handleBackAction();
            }
        });


        btnLike.setOnClickListener(v -> {
            if (user == null) {
                Toast.makeText(this, "please login in order to like",
                        Toast.LENGTH_SHORT).show();
                startActivity(Login);
            } else if (!isLiked) {
                user.addToLiked(videoItem);
                user.removeFromDisLiked(videoItem);
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
            if (user == null) {
                startActivity(Login);
                Toast.makeText(this, "please login in order to dislike",
                        Toast.LENGTH_SHORT).show();
            } else if (!isDisliked) {
                user.addToDisLiked(videoItem);
                user.removeFromLiked(videoItem);
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
            if (videoItem != null) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this video");
                shareIntent.putExtra(Intent.EXTRA_TEXT, videoItem.getVideo_path());
                startActivity(Intent.createChooser(shareIntent, "Share Video"));
            }
        });
    }

    private void handleBackAction() {
        Intent i = new Intent(this, MainActivity.class);
        i.putParcelableArrayListExtra("video_list", videos);
        i.putExtra("user", user);
        startActivity(i);
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
                comment newComment = new comment(commentText, user.getName(), "now");
                commentList.add(newComment);
                videos.get(videoNumber).setComments(commentList);
                commentsAdapter.notifyDataSetChanged();
                tvComments.setText(String.format("Comments (%d)", commentList.size()));
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    @SuppressLint({"NotifyDataSetChanged", "DefaultLocale"})
    public void removeComment(int position) {
        commentList.remove(position);
        videos.get(videoNumber).setComments(commentList);
        commentsAdapter.notifyDataSetChanged();
        tvComments.setText(String.format("Comments (%d)", commentList.size()));
    }

    @SuppressLint("NotifyDataSetChanged")
    public void editComment(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit comment");

        final EditText input = new EditText(this);
        input.setText(commentList.get(position).getComment());
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String editedCommentText = input.getText().toString().trim();
            if (!editedCommentText.isEmpty()) {
                commentList.get(position).setComment(editedCommentText);
                commentList.get(position).setDate("now");
                commentList.get(position).setUser(user.getName());
                videos.get(videoNumber).setComments(commentList);
                commentsAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}
