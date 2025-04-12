package com.example.f1blog;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DriversAdapter extends RecyclerView.Adapter<DriversAdapter.DriverViewHolder> {

    private List<Driver> drivers;

    public DriversAdapter(List<Driver> drivers) {
        this.drivers = drivers;
    }

    @NonNull
    @Override
    public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_driver, parent, false);
        return new DriverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverViewHolder holder, int position) {
        Driver driver = drivers.get(position);
        holder.nameTextView.setText(driver.getName());
        holder.teamTextView.setText(driver.getTeam());

        // Kattintás kezelése
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DriverDetailsActivity.class);
            intent.putExtra("driver", driver);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return drivers.size();
    }

    static class DriverViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, teamTextView;

        DriverViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewDriverName);
            teamTextView = itemView.findViewById(R.id.textViewTeamName);
        }
    }
}