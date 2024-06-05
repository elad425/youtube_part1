package com.example.youtube.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.youtube.R;
import com.example.youtube.entities.video;

import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoViewHolder> {

    static class VideoViewHolder extends RecyclerView.ViewHolder{
        private final TextView video_name;
        private final TextView creator;
        private final TextView views;
        private final TextView publish_date;
        private final ImageView thumbnail;
        private final TextView video_length;

        private VideoViewHolder(View itemView){
            super(itemView);
            video_name = itemView.findViewById(R.id.video_name);
            creator = itemView.findViewById(R.id.creator);
            views = itemView.findViewById(R.id.views);
            publish_date = itemView.findViewById(R.id.publish_date);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            video_length = itemView.findViewById(R.id.video_length);
        }
    }

    private final LayoutInflater mInflater;
    private List<video> videos;

    public VideoListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.video_layout_vertical, parent, false);
        return new VideoViewHolder(itemView);
    }
    public void onBindViewHolder(VideoViewHolder holder, int position){
        if (videos != null){
            final video current = videos.get(position);
            holder.video_name.setText(current.getVideoName());
            holder.creator.setText(current.getCreator());
            holder.views.setText(current.getViews());
            holder.publish_date.setText(current.getDate_of_release());
            holder.video_length.setText(current.getVideo_length());
            holder.thumbnail.setImageResource(current.getThumbnail());
        }
    }
     public void setVideos(List<video> v){
        videos = v;
        notifyDataSetChanged();
     }

    @Override
    public int getItemCount() {
        if (videos != null)
            return videos.size();
        else return 0;
    }

    public List<video> getVideos() {
        return videos;
    }
}
