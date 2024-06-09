package com.example.youtube.screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtube.MainActivity;
import com.example.youtube.R;
import com.example.youtube.adapters.SearchAdapter;
import com.example.youtube.entities.user;
import com.example.youtube.entities.video;
import com.example.youtube.utils.JsonUtils;

import java.util.ArrayList;

public class SearchVideo extends AppCompatActivity {

    private ArrayList<video> filteredList;
    private SearchAdapter searchAdapter;
    private ArrayList<video> videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_video);

        Intent intent = getIntent();
        ArrayList<video> temp = intent.getParcelableArrayListExtra("video_list");
        if (temp != null) {
            videos = temp;
        } else {
            videos = JsonUtils.loadVideosFromJson(this);
        }

        user user = intent.getParcelableExtra("user");
        SearchView searchView = findViewById(R.id.search_view);
        filteredList = new ArrayList<>();
        searchAdapter = new SearchAdapter(videos,filteredList, this, user);

        RecyclerView rvSearch = findViewById(R.id.rv_search);
        rvSearch.setLayoutManager(new LinearLayoutManager(this));
        rvSearch.setAdapter(searchAdapter);

        ImageButton btnBack = findViewById(R.id.search_back);
        btnBack.setOnClickListener(v -> handleBackAction());

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                handleBackAction();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterVideos(newText);
                return true;
            }
        });
    }

    private void handleBackAction() {
        Intent i = new Intent(this, MainActivity.class);
        i.putParcelableArrayListExtra("video_list", videos);
        startActivity(i);
    }

    private void filterVideos(String query) {
        filteredList.clear();
        for (video video : videos) {
            if (video.getVideo_name().toLowerCase().startsWith(query.toLowerCase())
                    && !query.equals("")) {
                filteredList.add(video);
            }
        }
        searchAdapter.setFilteredList(filteredList);
    }
}
