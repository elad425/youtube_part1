package com.example.youtube.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtube.R;
import com.example.youtube.entities.comment;
import com.example.youtube.screens.VideoPlayerActivity;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private final ArrayList<comment> commentList;
    private final VideoPlayerActivity videoPlayerActivity;

    public CommentsAdapter(ArrayList<comment> commentList, VideoPlayerActivity videoPlayerActivity) {
        this.commentList = commentList;
        this.videoPlayerActivity = videoPlayerActivity;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        comment currentComment = commentList.get(position);
        holder.tvCommentUser.setText(currentComment.getUser());
        holder.tvCommentText.setText(currentComment.getComment());
        holder.tvCommentDate.setText(currentComment.getDate());

        holder.tvEditComment.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(v.getContext(), v);
            popup.getMenuInflater().inflate(R.menu.comment_options_menu, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_edit_comment) {
                    videoPlayerActivity.editComment(position);
                    return true;
                }
                else if (item.getItemId() == R.id.action_delete_comment) {
                    videoPlayerActivity.removeComment(position);
                    return true;
                    }
                return false;
            });
            popup.show();
        });
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView tvCommentUser;
        TextView tvCommentText;
        TextView tvCommentDate;
        ImageButton tvEditComment;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCommentUser = itemView.findViewById(R.id.tv_comment_user);
            tvCommentText = itemView.findViewById(R.id.tv_comment_text);
            tvCommentDate = itemView.findViewById(R.id.tv_comment_date);
            tvEditComment = itemView.findViewById(R.id.tv_comment_options);
        }
    }
}
