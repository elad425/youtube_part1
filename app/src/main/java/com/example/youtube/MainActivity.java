package com.example.youtube;
import com.example.youtube.entities.video;
import com.example.youtube.entities.user;
import com.example.youtube.screens.AddVideoActivity;
import com.example.youtube.screens.LogIn;
import com.example.youtube.screens.ProfilePage;
import com.example.youtube.screens.SearchVideo;
import com.example.youtube.utils.JsonUtils;
import com.example.youtube.utils.videoListUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
            user = new user("elad cohen", "eladcohen@gmail.com", "12345678", "thumbnail5");
        }

        RecyclerView lstVideos = findViewById(R.id.lstVideos);
        videoListUtils.displayVideoList(this, lstVideos, videos, user);

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

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.navigation_home);
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_profile){
                Intent i = new Intent(MainActivity.this, ProfilePage.class);
                i.putExtra("user",user);
                i.putExtra("videos",videos);
                startActivity(i);
                return true;
            } else if (item.getItemId() == R.id.navigation_add_video){
                if (user != null) {
                    Intent i = new Intent(MainActivity.this, AddVideoActivity.class);
                    i.putExtra("videos", videos);
                    i.putExtra("user",user);
                    startActivity(i);
                    return true;
                } else {
                    Toast.makeText(MainActivity.this,
                            "please log in in order to add a video", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, LogIn.class);
                    startActivity(i);
                }
            }
            return false;
        });
    }

}

