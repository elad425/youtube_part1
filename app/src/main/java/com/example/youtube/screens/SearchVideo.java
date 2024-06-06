package com.example.youtube.screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtube.MainActivity;
import com.example.youtube.R;
import com.example.youtube.adapters.SearchAdapter;
import com.example.youtube.entities.video;
import com.example.youtube.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchVideo extends AppCompatActivity {

    private List<video> filteredList;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_video);

        SearchView searchView = findViewById(R.id.search_view);
        filteredList = new ArrayList<>();
        searchAdapter = new SearchAdapter(filteredList, this);

        RecyclerView rvSearch = findViewById(R.id.rv_search);
        rvSearch.setLayoutManager(new LinearLayoutManager(this));
        rvSearch.setAdapter(searchAdapter);

        ImageButton btnBack = findViewById(R.id.search_back);
        btnBack.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
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

    private void filterVideos(String query) {
        List<video> videoList = JsonUtils.loadVideosFromJson(this);
        filteredList.clear();
        for (video video : videoList){
            if (video.getVideo_name().toLowerCase().startsWith(query.toLowerCase())
                    && !query.equals("")){
                filteredList.add(video);
            }
        }
        searchAdapter.setFilteredList(filteredList);
    }
}
