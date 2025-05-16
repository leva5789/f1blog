package com.example.f1blog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    public NewsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<News> newsList = getNewsList();
        recyclerView.setAdapter(new NewsAdapter(newsList));

        return view;
    }

    private List<News> getNewsList() {
        List<News> news = new ArrayList<>();
        // Hírek hozzáadása (2025. május 16-i állapot alapján)
        news.add(new News(
                "FIA Reduces Penalties for Driver Swearing",
                "Formula 1's governing body has reduced penalties for drivers swearing or criticizing officials after widespread outcry, following Max Verstappen's community service penalty last year.",
                "2025-05-15"
        ));
        news.add(new News(
                "McLaren Cleared After FIA Investigation",
                "Oscar Piastri's McLaren MCL39 was found compliant with 2025 F1 technical regulations after an investigation following the Miami GP.",
                "2025-05-15"
        ));
        news.add(new News(
                "Alpine Team in Turmoil",
                "Despite recent changes, Pierre Gasly insists there's 'no trouble' at Alpine, even after Jack Doohan's departure.",
                "2025-05-15"
        ));
        news.add(new News(
                "Piastri Leads McLaren 1-2 in Imola Practice",
                "Oscar Piastri led a McLaren 1-2 in the opening Emilia Romagna GP practice, with rivals close behind.",
                "2025-05-15"
        ));
        news.add(new News(
                "Verstappen on Red Bull Updates",
                "Max Verstappen says Red Bull's latest upgrades at Imola won't deliver 'magic' despite their efforts to catch McLaren.",
                "2025-05-15"
        ));
        return news;
    }
}