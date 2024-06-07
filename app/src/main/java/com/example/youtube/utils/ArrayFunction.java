package com.example.youtube.utils;

import com.example.youtube.entities.video;

import java.util.ArrayList;
import java.util.Objects;

public class ArrayFunction {
    public static int findVideoPlace (ArrayList<video> videos, video video){
        int counter = 0;
        for (video v : videos){
            if (Objects.equals(v.getVideo_name(), video.getVideo_name())){
                return counter;
            }
            counter++;
        }
        return counter;
    }
}
