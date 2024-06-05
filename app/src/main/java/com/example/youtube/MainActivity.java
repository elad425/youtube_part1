package com.example.youtube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.youtube.adapters.VideoListAdapter;
import com.example.youtube.entities.comment;
import com.example.youtube.entities.video;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView lstVideos = findViewById(R.id.lstVideos);
        final VideoListAdapter adapter = new VideoListAdapter(this);
        lstVideos.setAdapter(adapter);
        lstVideos.setLayoutManager(new LinearLayoutManager(this));

        List<comment> comments = new ArrayList<>();
        comment comment1 = new comment("what an amazing video!!","mark zuckerberg","one month ago");
        comment comment2 = new comment("this comment is to check if the limits off a single comment are ok and to blah blah blah","ilan brownfield", "1 minute ago");
        comment comment3 = new comment("what a bad quality video:(","moshe rabino","3 days ago");
        comment comment4 = new comment("what a bad quality video:(","moshe rabino","3 days ago");
        comment comment5 = new comment("what a bad quality video:(","moshe rabino","3 days ago");
        comment comment6 = new comment("what a bad quality video:(","moshe rabino","3 days ago");
        comment comment7 = new comment("what a bad quality video:(","moshe rabino","3 days ago");
        comment comment8 = new comment("what a bad quality video:(","moshe rabino","3 days ago");

        comments.add(comment1);
        comments.add(comment2);
        comments.add(comment3);
        comments.add(comment4);
        comments.add(comment5);
        comments.add(comment6);
        comments.add(comment7);
        comments.add(comment8);


        List<video> videos = new ArrayList<>();

        video v = new video("video player test","elad cohen","20 hours ago", "android.resource://"+getPackageName()+"/raw/record", R.drawable.test_thumbnail,"10:10", "1M views", "333k");
        v.setComments(comments);
        videos.add(v);
        videos.add(new video("video player test to see if each video play different file","yossef israeli","3 weeks ago", "android.resource://"+getPackageName()+"/raw/video2", R.drawable.thumbnail2,"12:45", "245K views", "2M"));
        videos.add(new video("video player test to see the flow of the videos when scrolling","Elon musk","2 day ago", "android.resource://"+getPackageName()+"/raw/video1", R.drawable.thumbnail3,"20:21", "6.5M views", "1M"));
        adapter.setVideos(videos);
    }
}