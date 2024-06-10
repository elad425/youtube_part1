package com.example.youtube.screens;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.youtube.MainActivity;
import com.example.youtube.R;
import com.example.youtube.entities.creator;
import com.example.youtube.entities.user;
import com.example.youtube.entities.video;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;

public class AddVideoActivity extends AppCompatActivity {

    private static final int PICK_VIDEO_REQUEST = 1;
    private static final int PICK_THUMBNAIL_REQUEST  = 2;
    private EditText videoNameEditText;
    private ImageView thumbnailImageView;
    private TextView videoPathTextView;
    private Uri videoUri, thumbnailUri;
    private ArrayList<video> videos;
    private user user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);

        videoNameEditText = findViewById(R.id.video_name);
        thumbnailImageView = findViewById(R.id.thumbnail_image);
        videoPathTextView = findViewById(R.id.video_path);

        Button chooseVideoButton = findViewById(R.id.choose_video_button);
        chooseVideoButton.setOnClickListener(v -> selectVideo());

        Button chooseThumbnailButton = findViewById(R.id.choose_thumbnail_button);
        chooseThumbnailButton.setOnClickListener(v -> selectThumbnail());

        Button addVideoButton = findViewById(R.id.add_video_button);
        addVideoButton.setOnClickListener(v -> addVideo());

        videos = getIntent().getParcelableArrayListExtra("videos");
        user = getIntent().getParcelableExtra("user");
        if (videos == null) {
            videos = new ArrayList<>();
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.navigation_add_video);
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_profile){
                Intent i = new Intent(AddVideoActivity.this, ProfilePage.class);
                i.putExtra("user",user);
                i.putExtra("videos",videos);
                startActivity(i);
                return true;
            } else if (item.getItemId() == R.id.navigation_home){
                Intent i = new Intent(AddVideoActivity.this, MainActivity.class);
                i.putExtra("video_list", videos);
                i.putExtra("user",user);
                startActivity(i);
                return true;
            }
            return false;
        });
    }

    private void selectVideo() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");
        startActivityForResult(intent, PICK_VIDEO_REQUEST);
    }

    private void selectThumbnail() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_THUMBNAIL_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == PICK_VIDEO_REQUEST) {
                videoUri = data.getData();
                assert videoUri != null;
                videoPathTextView.setText(videoUri.getPath());
            } else if (requestCode == PICK_THUMBNAIL_REQUEST) {
                thumbnailUri = data.getData();
                try {
                    Bitmap thumbnailBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), thumbnailUri);
                    thumbnailImageView.setImageBitmap(thumbnailBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addVideo() {
        String videoName = videoNameEditText.getText().toString().trim();

        if (videoName.isEmpty() || thumbnailUri == null || videoUri == null) {
            Toast.makeText(this, "Please fill all fields and choose a video and a thumbnail", Toast.LENGTH_SHORT).show();
            return;
        }

        creator newCreator = new creator(user.getName(), "0", user.getProfile_pic());
        video newVideo = new video(videoName, newCreator, "today", videoUri.toString(), thumbnailUri.toString(), "0:12", "0", "0");

        videos.add(newVideo);
        Toast.makeText(this, "video added successfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(AddVideoActivity.this, MainActivity.class);
        intent.putParcelableArrayListExtra("video_list", videos);
        startActivity(intent);
        finish();
    }
}
