package reagodjj.example.com.expandablelistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import reagodjj.example.com.expandablelistview.Adapter.ChapterAdapter;
import reagodjj.example.com.expandablelistview.bean.Chapter;
import reagodjj.example.com.expandablelistview.bean.ChapterLab;

public class MainActivity extends AppCompatActivity {
    private ExpandableListView elvLanguage;
    private ChapterAdapter chapterAdapter;
    private List<Chapter> chapters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        elvLanguage = findViewById(R.id.elv_language);
        chapters.addAll(ChapterLab.generateDatas());

        chapterAdapter = new ChapterAdapter(this, chapters);
        elvLanguage.setAdapter(chapterAdapter);
    }
}
