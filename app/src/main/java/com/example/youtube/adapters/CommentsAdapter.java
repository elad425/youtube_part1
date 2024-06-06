package com.example.youtube.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.youtube.R;
import com.example.youtube.entities.comment;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private final List<comment> commentList;

    public CommentsAdapter(List<comment> commentList) {
        this.commentList = commentList;
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
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView tvCommentUser;
        TextView tvCommentText;
        TextView tvCommentDate;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCommentUser = itemView.findViewById(R.id.tv_comment_user);
            tvCommentText = itemView.findViewById(R.id.tv_comment_text);
            tvCommentDate = itemView.findViewById(R.id.tv_comment_date);
        }
    }
}
