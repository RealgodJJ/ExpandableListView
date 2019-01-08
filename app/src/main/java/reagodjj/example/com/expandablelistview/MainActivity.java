package reagodjj.example.com.expandablelistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import reagodjj.example.com.expandablelistview.adapter.ChapterAdapter;
import reagodjj.example.com.expandablelistview.bean.Chapter;
import reagodjj.example.com.expandablelistview.bean.ChapterLab;
import reagodjj.example.com.expandablelistview.biz.ChapterBiz;

public class MainActivity extends AppCompatActivity {
    private ExpandableListView elvLanguage;
    private ChapterAdapter chapterAdapter;
    private List<Chapter> chapters = new ArrayList<>();
    private ChapterBiz chapterBiz = new ChapterBiz();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        elvLanguage = findViewById(R.id.elv_language);
//        chapters.addAll(ChapterLab.generateDatas());

        chapterAdapter = new ChapterAdapter(this, chapters);
        elvLanguage.setAdapter(chapterAdapter);
        initEvents();

        chapterBiz.loadDatas(this, new ChapterBiz.CallBack() {
            @Override
            public void loadSuccess(List<Chapter> chapterList) {
                chapters.addAll(chapterList);
            }

            @Override
            public void loadFailed(Exception ex) {
                ex.printStackTrace();
            }
        }, false);
    }

    private void initEvents() {
        elvLanguage.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        elvLanguage.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });

        elvLanguage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        elvLanguage.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        elvLanguage.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });
    }
}
