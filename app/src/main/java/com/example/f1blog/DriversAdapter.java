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

public class DriversAdapter extends RecyclerView.Adapter<DriversAdapter.DriverViewHolder> {
    private static final String TAG = "DriversAdapter";
    private List<Driver> drivers;

    public DriversAdapter(List<Driver> drivers) {
        this.drivers = drivers;
    }

    @NonNull
    @Override
    public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_driver, parent, false);
        return new DriverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverViewHolder holder, int position) {
        Driver driver = drivers.get(position);
        if (holder.nameTextView != null) {
            holder.nameTextView.setText(driver.getName() != null ? driver.getName() : "N/A");
        } else {
            Log.e(TAG, "nameTextView is null");
        }
        if (holder.teamTextView != null) {
            holder.teamTextView.setText(driver.getTeam() != null ? driver.getTeam() : "N/A");
        } else {
            Log.e(TAG, "teamTextView is null");
        }
        if (holder.colorBar != null) {
            int colorRes = TeamColorUtils.getTeamColor(driver.getTeam() != null ? driver.getTeam() : "");
            holder.colorBar.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), colorRes));
        } else {
            Log.e(TAG, "colorBar is null");
        }
        // Helyezés beállítása
        if (holder.positionTextView != null) {
            holder.positionTextView.setText(String.valueOf(position + 1));
        } else {
            Log.e(TAG, "positionTextView is null");
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DriverDetailsActivity.class);
            intent.putExtra("driverName", driver.getName() != null ? driver.getName() : "N/A");
            intent.putExtra("team", driver.getTeam() != null ? driver.getTeam() : "N/A");
            intent.putExtra("wins", driver.getWins());
            intent.putExtra("poles", driver.getPoles());
            intent.putExtra("points", driver.getPoints());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return drivers.size();
    }

    static class DriverViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, teamTextView, positionTextView;
        View colorBar;

        public DriverViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewDriverName);
            teamTextView = itemView.findViewById(R.id.textViewTeam);
            colorBar = itemView.findViewById(R.id.teamColorBar);
            positionTextView = itemView.findViewById(R.id.textViewPosition);
            if (nameTextView == null) {
                Log.e(TAG, "textViewDriverName not found in item_driver.xml");
            }
            if (teamTextView == null) {
                Log.e(TAG, "textViewTeam not found in item_driver.xml");
            }
            if (colorBar == null) {
                Log.e(TAG, "teamColorBar not found in item_driver.xml");
            }
            if (positionTextView == null) {
                Log.e(TAG, "textViewPosition not found in item_driver.xml");
            }
        }
    }
}