package com.example.youtube.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtube.R;
import com.example.youtube.entities.video;
import com.example.youtube.screens.VideoPlayerActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.VideoViewHolder> {

    private ArrayList<video> videoList;

    private final LayoutInflater mInflater;

    public SearchAdapter(ArrayList<video> videoList, Context context) {
        this.videoList = videoList;
        this.mInflater = LayoutInflater.from(context);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setFilteredList(ArrayList<video> filteredList){
        this.videoList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        video video = videoList.get(position);
        holder.searchResultTextView.setText(video.getVideo_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video clickedVideoItem = videoList.get(holder.getAdapterPosition());
                Intent i = new Intent(mInflater.getContext(), VideoPlayerActivity.class);
                i.putExtra("video_item", clickedVideoItem);
                i.putExtra("video_list", videoList);
                mInflater.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView searchResultTextView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            searchResultTextView = itemView.findViewById(R.id.search_result);
        }
    }
}
