package com.example.f1blog;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.TeamViewHolder> {
    private static final String TAG = "TeamsAdapter";
    private List<Team> teams;

    public TeamsAdapter(List<Team> teams) {
        this.teams = teams;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent, false);
        return new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        Team team = teams.get(position);
        if (holder.nameTextView != null) {
            holder.nameTextView.setText(team.getName());
        } else {
            Log.e(TAG, "nameTextView is null");
        }
        if (holder.driversTextView != null) {
            holder.driversTextView.setText(team.getDrivers());
        } else {
            Log.e(TAG, "driversTextView is null");
        }
        if (holder.colorBar != null) {
            int colorRes = TeamColorUtils.getTeamColor(team.getName());
            holder.colorBar.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), colorRes));
        } else {
            Log.e(TAG, "colorBar is null");
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), TeamDetailsActivity.class);
            intent.putExtra("team", team);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    static class TeamViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, driversTextView;
        View colorBar;

        TeamViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewTeamName);
            driversTextView = itemView.findViewById(R.id.textViewDrivers);
            colorBar = itemView.findViewById(R.id.teamColorBar);
            if (nameTextView == null) {
                Log.e(TAG, "textViewTeamName not found in item_team.xml");
            }
            if (driversTextView == null) {
                Log.e(TAG, "textViewDrivers not found in item_team.xml");
            }
            if (colorBar == null) {
                Log.e(TAG, "teamColorBar not found in item_team.xml");
            }
        }
    }
}