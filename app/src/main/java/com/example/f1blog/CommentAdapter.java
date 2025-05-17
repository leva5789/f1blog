package com.example.f1blog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
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

        // Animáció alkalmazása, ha ez az első elem (új komment)
        if (position == 0) {
            Animation slideInTop = AnimationUtils.loadAnimation(context, R.anim.slide_in_top);
            holder.itemView.startAnimation(slideInTop);
        }

        String currentUsername = activity.getCurrentUsername();
        if (currentUsername != null && currentUsername.equals(comment.getUsername())) {
            holder.imageViewEdit.setVisibility(View.VISIBLE);
            holder.imageViewDelete.setVisibility(View.VISIBLE);

            holder.imageViewDelete.setOnClickListener(v -> {
                activity.deleteComment(comment.getId());
            });

            holder.imageViewEdit.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Komment szerkesztése");

                final EditText input = new EditText(context);
                input.setText(comment.getText());
                builder.setView(input);

                builder.setPositiveButton("Mentés", (dialog, which) -> {
                    String newText = input.getText().toString().trim();
                    if (!newText.isEmpty()) {
                        activity.editComment(comment.getId(), newText);
                    } else {
                        activity.editComment(comment.getId(), comment.getText());
                    }
                });
                builder.setNegativeButton("Mégse", (dialog, which) -> dialog.cancel());

                builder.show();
            });
        } else {
            holder.imageViewEdit.setVisibility(View.GONE);
            holder.imageViewDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView textViewComment, textViewUsername;
        ImageView imageViewDelete, imageViewEdit;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewComment = itemView.findViewById(R.id.textViewComment);
            textViewUsername = itemView.findViewById(R.id.textViewUsername);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
            imageViewEdit = itemView.findViewById(R.id.imageViewEdit);
        }
    }
}