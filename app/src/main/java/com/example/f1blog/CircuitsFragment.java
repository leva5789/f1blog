package com.example.f1blog;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.ArrayList;
import java.util.List;

public class CircuitsFragment extends Fragment {

    private RecyclerView recyclerView;
    private CircuitAdapter circuitAdapter;
    private List<Circuit> circuitList;
    private TextView textViewNearestCircuit;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 101;
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 102;
    private static final String CHANNEL_ID = "F1BlogNotifications";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        createNotificationChannel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circuits, container, false);

        textViewNearestCircuit = view.findViewById(R.id.textViewNearestCircuit);
        recyclerView = view.findViewById(R.id.recyclerViewCircuits);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 2025-ös versenynaptár
        circuitList = new ArrayList<>();
        circuitList.add(new Circuit("Ausztrália", "Melbourne", "Március 14-16", -37.8497, 144.968));
        circuitList.add(new Circuit("Kína", "Sanghaj", "Március 21-23", 31.3389, 121.22));
        circuitList.add(new Circuit("Japán", "Szuzuka", "Április 4-6", 34.8431, 136.541));
        circuitList.add(new Circuit("Bahrein", "Szahír", "Április 11-13", 26.0325, 50.5106));
        circuitList.add(new Circuit("Szaúd-Arábia", "Dzsiddah", "Április 18-20", 21.6319, 39.1044));
        circuitList.add(new Circuit("Miami", "Miami", "Május 2-4", 25.9581, -80.2389));
        circuitList.add(new Circuit("Emilia-Romagna", "Imola", "Május 16-18", 44.3439, 11.7167));
        circuitList.add(new Circuit("Monaco", "Monte Carlo", "Május 23-25", 43.7347, 7.42056));
        circuitList.add(new Circuit("Spanyolország", "Barcelona", "Május 30 - Június 1", 41.57, 2.26111));
        circuitList.add(new Circuit("Kanada", "Montreal", "Június 13-15", 45.5, -73.5228));
        circuitList.add(new Circuit("Ausztria", "Spielberg", "Június 27-29", 47.2197, 14.7647));
        circuitList.add(new Circuit("Nagy-Britannia", "Silverstone", "Július 4-6", 52.0786, -1.01694));
        circuitList.add(new Circuit("Belgium", "Spa-Francorchamps", "Július 25-27", 50.4372, 5.97139));
        circuitList.add(new Circuit("Magyarország", "Hungaroring", "Augusztus 1-3", 47.5829, 19.2486));
        circuitList.add(new Circuit("Hollandia", "Zandvoort", "Augusztus 29-31", 52.3888, 4.54092));
        circuitList.add(new Circuit("Olaszország", "Monza", "Szeptember 5-7", 45.6156, 9.28111));
        circuitList.add(new Circuit("Azerbajdzsán", "Baku", "Szeptember 19-21", 40.3725, 49.8533));
        circuitList.add(new Circuit("Szingapúr", "Szingapúr", "Október 3-5", 1.2914, 103.864));
        circuitList.add(new Circuit("USA", "Austin", "Október 17-19", 30.1328, -97.6411));
        circuitList.add(new Circuit("Mexikó", "Mexikóváros", "Október 24-26", 19.4042, -99.0917));
        circuitList.add(new Circuit("Brazília", "Interlagos", "November 7-9", -23.7036, -46.6997));
        circuitList.add(new Circuit("Las Vegas", "Las Vegas", "November 20-22", 36.1162, -115.174));
        circuitList.add(new Circuit("Katar", "Losail", "November 28-30", 25.49, 51.4542));
        circuitList.add(new Circuit("Abu Dhabi", "Yas Marina", "December 5-7", 24.4672, 54.6031));

        circuitAdapter = new CircuitAdapter(circuitList);
        recyclerView.setAdapter(circuitAdapter);

        // Helymeghatározási engedély kérése
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getUserLocation();
        }

        // Értesítési engedély kérése (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_REQUEST_CODE);
            }
        }

        // Teszt értesítés gomb
        Button buttonTestNotification = view.findViewById(R.id.buttonTestNotification);
        buttonTestNotification.setOnClickListener(v -> sendTestNotification());

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUserLocation();
            } else {
                textViewNearestCircuit.setText("Helymeghatározási engedély megtagadva");
            }
        } else if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Értesítési engedély megtagadva", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getUserLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Circuit nearestCircuit = findNearestCircuit(location);
                    textViewNearestCircuit.setText("Legközelebbi pálya: " + nearestCircuit.getName() + " (" + nearestCircuit.getCity() + ")");
                } else {
                    textViewNearestCircuit.setText("Nem sikerült meghatározni a helyet");
                }
            }
        }).addOnFailureListener(e -> {
            textViewNearestCircuit.setText("Hiba a helymeghatározás során: " + e.getMessage());
        });
    }

    private Circuit findNearestCircuit(Location userLocation) {
        Circuit nearestCircuit = circuitList.get(0);
        float[] results = new float[1];
        Location.distanceBetween(userLocation.getLatitude(), userLocation.getLongitude(),
                nearestCircuit.getLatitude(), nearestCircuit.getLongitude(), results);
        float minDistance = results[0];

        for (Circuit circuit : circuitList) {
            Location.distanceBetween(userLocation.getLatitude(), userLocation.getLongitude(),
                    circuit.getLatitude(), circuit.getLongitude(), results);
            float distance = results[0];
            if (distance < minDistance) {
                minDistance = distance;
                nearestCircuit = circuit;
            }
        }
        return nearestCircuit;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "F1Blog Notifications";
            String description = "Értesítések az F1Blog alkalmazástól";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendTestNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("F1")
                .setContentText("TESZT ÉRTESÍTÉS")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(1001, builder.build());
    }
}

class Circuit {
    private String name;
    private String city;
    private String date;
    private double latitude;
    private double longitude;

    public Circuit(String name, String city, String date, double latitude, double longitude) {
        this.name = name;
        this.city = city;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

class CircuitAdapter extends RecyclerView.Adapter<CircuitAdapter.CircuitViewHolder> {

    private List<Circuit> circuitList;

    public CircuitAdapter(List<Circuit> circuitList) {
        this.circuitList = circuitList;
    }

    @NonNull
    @Override
    public CircuitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_circuit, parent, false);
        return new CircuitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CircuitViewHolder holder, int position) {
        Circuit circuit = circuitList.get(position);
        holder.textViewCircuitName.setText(circuit.getName());
        holder.textViewCity.setText(circuit.getCity());
        holder.textViewDate.setText(circuit.getDate());
    }

    @Override
    public int getItemCount() {
        return circuitList.size();
    }

    static class CircuitViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCircuitName, textViewCity, textViewDate;

        public CircuitViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCircuitName = itemView.findViewById(R.id.textViewCircuitName);
            textViewCity = itemView.findViewById(R.id.textViewCity);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }
}