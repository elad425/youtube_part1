package com.example.youtube.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.youtube.R;
import com.example.youtube.entities.video;
import com.example.youtube.screens.VideoPlayerActivity;

import java.util.ArrayList;
import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoViewHolder> {

    private final LayoutInflater mInflater;
    private ArrayList<video> videos;
    static class VideoViewHolder extends RecyclerView.ViewHolder {
        private final TextView video_name;
        private final TextView creator;
        private final TextView views;
        private final TextView publish_date;
        private final ImageView thumbnail;
        private final TextView video_length;

        private VideoViewHolder(View itemView) {
            super(itemView);
            video_name = itemView.findViewById(R.id.video_name);
            creator = itemView.findViewById(R.id.creator);
            views = itemView.findViewById(R.id.views);
            publish_date = itemView.findViewById(R.id.publish_date);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            video_length = itemView.findViewById(R.id.video_length);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setVideos(ArrayList<video> v) {
        videos = v;
        notifyDataSetChanged();
    }

    public List<video> getVideos() {
        return videos;
    }

    @Override
    public int getItemCount() {
        if (videos != null)
            return videos.size();
        else return 0;
    }

    public VideoListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_video_vertical, parent, false);
        return new VideoViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        if (videos != null) {
            final video current = videos.get(position);
            holder.video_name.setText(current.getVideo_name());
            holder.creator.setText(current.getCreator());
            holder.views.setText(current.getViews());
            holder.publish_date.setText(current.getDate_of_release());
            holder.video_length.setText(current.getVideo_length());

            // Load thumbnail using resource identifier
            String thumbnailName = current.getThumbnail();
            int thumbnailId = mInflater.getContext().getResources().getIdentifier(thumbnailName, "drawable", mInflater.getContext().getPackageName());
            holder.thumbnail.setImageResource(thumbnailId);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video clickedVideoItem = videos.get(holder.getAdapterPosition());
                Intent i = new Intent(mInflater.getContext(), VideoPlayerActivity.class);
                i.putExtra("video_item", clickedVideoItem);
                i.putParcelableArrayListExtra("video_list", videos);
                mInflater.getContext().startActivity(i);
            }
        });
    }

}
