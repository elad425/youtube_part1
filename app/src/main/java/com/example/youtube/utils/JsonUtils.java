package com.example.youtube.utils;

import android.content.Context;

import com.example.youtube.R;
import com.example.youtube.entities.video;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class JsonUtils {

    public static List<video> loadVideosFromJson(Context context) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.videoinfo);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Gson gson = new Gson();
        Type videoListType = new TypeToken<List<video>>() {}.getType();
        return gson.fromJson(reader, videoListType);
    }
}
