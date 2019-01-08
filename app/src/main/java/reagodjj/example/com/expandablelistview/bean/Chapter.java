package reagodjj.example.com.expandablelistview.bean;

import java.util.ArrayList;
import java.util.List;

public class Chapter {
    private int id;
    private String name;

    public static final String TABLE_NAME = "tb_chapter";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";

    private List<ChapterItem> chapterItems = new ArrayList<>();

    public Chapter() {
    }

    public Chapter(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChapterItem> getChapterItems() {
        return chapterItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addChild(ChapterItem childChapterItem) {
        chapterItems.add(childChapterItem);
        childChapterItem.setPid(getId());
    }

    void addChild(int childId, String childName) {
        ChapterItem chapterItem = new ChapterItem(childId, childName);
        chapterItem.setPid(getId());
        chapterItems.add(chapterItem);
    }
}
