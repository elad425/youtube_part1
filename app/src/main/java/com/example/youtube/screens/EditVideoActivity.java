package com.example.youtube.screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.youtube.MainActivity;
import com.example.youtube.R;
import com.example.youtube.entities.video;

import java.util.ArrayList;

public class EditVideoActivity extends AppCompatActivity {

    private video videoItem;
    private EditText etVideoName, etCreator, etThumbnail, etVideoPath, etVideoLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_video);

        etVideoName = findViewById(R.id.et_video_name);
        etCreator = findViewById(R.id.et_creator);
        etThumbnail = findViewById(R.id.et_thumbnail);
        etVideoPath = findViewById(R.id.et_video_path);
        etVideoLength = findViewById(R.id.et_video_length);
        Button btnSave = findViewById(R.id.btn_save);

        Intent intent = getIntent();
        videoItem = intent.getParcelableExtra("video_item");

        if (videoItem != null) {
            etVideoName.setText(videoItem.getVideo_name());
            etCreator.setText(videoItem.getCreator());
            etThumbnail.setText(videoItem.getThumbnail());
            etVideoPath.setText(videoItem.getVideo_path());
            etVideoLength.setText(videoItem.getVideo_length());
        }

        btnSave.setOnClickListener(v -> saveVideoDetails());
    }

    private void saveVideoDetails() {
        videoItem.setVideo_name(etVideoName.getText().toString());
        videoItem.setCreator(etCreator.getText().toString());
        videoItem.setThumbnail(etThumbnail.getText().toString());
        videoItem.setVideo_path(etVideoPath.getText().toString());
        videoItem.setVideo_length(etVideoLength.getText().toString());
        videoItem.setComments(new ArrayList<>());
        videoItem.setViews("0 views");
        videoItem.setDate_of_release("now");

        Intent returnIntent = new Intent(this, MainActivity.class);
        returnIntent.putExtra("updated_video_item", videoItem);
        finish();
    }
}
