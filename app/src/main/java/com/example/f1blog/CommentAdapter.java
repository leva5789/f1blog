package com.example.f1blog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> commentList;
    private Context context;
    private ArticleDetailActivity activity;

    public CommentAdapter(List<Comment> commentList, ArticleDetailActivity activity) {
        this.commentList = commentList;
        this.context = activity;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.textViewComment.setText(comment.getText());
        holder.textViewUsername.setText(comment.getUsername());

        holder.buttonDelete.setOnClickListener(v -> {
            activity.deleteComment(comment.getId());
        });

        holder.buttonEdit.setOnClickListener(v -> {
            // Egyszerűsített szerkesztés: felugró ablak helyett itt csak a szöveg szerkesztése
            String newText = "Új szöveg"; // Helyettesítsd egy EditText-tel később
            activity.editComment(comment.getId(), newText);
        });
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView textViewComment, textViewUsername;
        Button buttonDelete, buttonEdit;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewComment = itemView.findViewById(R.id.textViewComment);
            textViewUsername = itemView.findViewById(R.id.textViewUsername);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
        }
    }
}