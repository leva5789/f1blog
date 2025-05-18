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
        // Hírek hozzáadása (2025. május 16-i állapot alapján) képekkel
        news.add(new News(
                "Győzelmet ért a bátor első körös előzés Verstappen számára",
                "Max Verstappen nyerte az Emilia-Romagna Nagydíjat, miután az első kanyarban egy váratlan, bátor manőverrel megelőzte Oscar Piastrit. A verseny során Lando Norris lett a második, Oscar Piastri pedig a harmadik.",
                "2025-05-18",
                R.drawable.news_1
        ));

        news.add(new News(
                "Váz- és motorcsere a Red Bullnál – Cunoda a boxutcából rajtol",
                "Yuki Tsunoda súlyos balesetet szenvedett az Emilia-Romagna Nagydíj időmérőjén, ami miatt váz- és motorcserére kényszerült, így csak a boxutcából indulhat. A japán versenyző elismerte, hogy túl korán próbált túl sokat kihozni az autóból.",
                "2025-05-18",
                R.drawable.news_2
        ));

        news.add(new News(
                "Leclerc: Átkozott jó fejlesztések kellenének – Nem hiszek ebben",
                "Charles Leclerc csalódottan nyilatkozott az Emilia-Romagna Nagydíj időmérője után, ahol a Ferrari versenyzői a 11. és 12. helyet szerezték meg. Leclerc szerint az autó teljesítménye nem elegendő, és nem hisz abban, hogy a közelgő fejlesztések jelentős fordulatot hoznának a szezonban.",
                "2025-05-18",
                R.drawable.news_3
        ));



        news.add(new News(
                "Bearman szerint igazságtalan volt a piros zászlós körtörlés – az FIA elmagyarázta",
                "Oliver Bearman szerint igazságtalanul törölték az időmérőn futott körét, mivel úgy véli, a piros zászló jelzés csak a célvonal átlépése után jelent meg. Az FIA azonban az adatok alapján úgy ítélte meg, hogy Bearman 3,3 másodperccel a piros zászló után ért célba, így a kör törlése indokolt volt.",
                "2025-05-17",
                R.drawable.news_4
        ));


        news.add(new News(
                "Sainz: Ha egy éve azt mondják, hogy Imolában a Ferrarik előtt végzek a Williamsszel…",
                "Carlos Sainz a Williams színeiben a hatodik helyről rajtol az Emilia-Romagna Nagydíjon, megelőzve volt csapatát, a Ferrarit. A spanyol pilóta szerint a csapat fejlődése és az autóhoz való alkalmazkodása kulcsfontosságú volt ebben az eredményben.",
                "2025-05-18",
                R.drawable.news_5
        ));



        return news;
    }
}