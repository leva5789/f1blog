package com.example.f1blog;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<News> newsList;

    public NewsAdapter(List<News> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.textViewTitle.setText(news.getTitle());
        // Placeholder kép, később API-ból töltve
        // holder.imageViewNews.setImageResource(R.drawable.placeholder_image);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ArticleDetailActivity.class);
            intent.putExtra("articleTitle", news.getTitle());
            intent.putExtra("articleContent", news.getDescription()); // Feltételezem, hogy a description a teljes tartalom
            intent.putExtra("articleImage", "placeholder_image"); // Placeholder, később URL
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewNews;
        TextView textViewTitle;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewNews = itemView.findViewById(R.id.imageViewNews);
            textViewTitle = itemView.findViewById(R.id.textViewNewsTitle);
        }
    }
}