package com.example.youtube;
import com.example.youtube.entities.video;
import com.example.youtube.entities.user;
import com.example.youtube.screens.SearchVideo;
import com.example.youtube.utils.JsonUtils;
import com.example.youtube.utils.ShowListOfVideos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<video> videos;

    private user user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.transparent));

        Intent intent = getIntent();
        ArrayList<video> temp = intent.getParcelableArrayListExtra("video_list");
        if (temp != null){
            videos = temp;
        }else {
            videos = JsonUtils.loadVideosFromJson(this);
        }

        user tempUser = intent.getParcelableExtra("user");
        if (tempUser != null){
            user = tempUser;
        }else {
            user = new user("e", "e", "e", "thumbnail5");
        }

        RecyclerView lstVideos = findViewById(R.id.lstVideos);
        ShowListOfVideos.displayVideoList(this, lstVideos, videos, user);

        ImageButton btnSearch = findViewById(R.id.search_button);
        btnSearch.setOnClickListener(v -> {
            Intent i = new Intent(this, SearchVideo.class);
            i.putParcelableArrayListExtra("video_list", videos);
            i.putExtra("user",user);
            startActivity(i);
        });

        ImageButton btnCast = findViewById(R.id.cast_button);
        btnCast.setOnClickListener(v -> Toast.makeText(MainActivity.this,
                "The app doesn't support Chromecast yet", Toast.LENGTH_SHORT).show());
    }

}

