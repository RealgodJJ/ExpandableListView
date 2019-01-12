package reagodjj.example.com.expandablelistview.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import reagodjj.example.com.expandablelistview.bean.Chapter;
import reagodjj.example.com.expandablelistview.bean.ChapterItem;
import reagodjj.example.com.expandablelistview.db.ChapterDbHelper;

public class ChapterDao {
    public List<Chapter> loadFromDb(Context context) {
        List<Chapter> chapterList = new ArrayList<>();
        ChapterDbHelper chapterDbHelper = ChapterDbHelper.getsInstance(context);
        SQLiteDatabase sqLiteDatabase = chapterDbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + Chapter.TABLE_NAME, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(Chapter.COL_ID));
            String name = cursor.getString(cursor.getColumnIndex(Chapter.COL_NAME));
            Chapter chapter = new Chapter(id, name);
            chapterList.add(chapter);
        }
        cursor.close();

        for (Chapter chapter : chapterList) {
            int pid = chapter.getId();
            cursor = sqLiteDatabase.rawQuery("select * from " + ChapterItem.TABLE_NAME +
                    " where " + ChapterItem.COL_PID + " = ? ", new String[]{String.valueOf(pid)});
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(ChapterItem.COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(ChapterItem.COL_NAME));
                ChapterItem chapterItem = new ChapterItem(id, name);
                chapterItem.setPid(pid);
                chapter.addChild(chapterItem);
            }
            cursor.close();
        }

        return chapterList;
    }

    public void insert2Db(Context context, List<Chapter> chapterList) {
        if (context == null) {
            throw new IllegalArgumentException("context can not be null.");
        }

        if (chapterList == null || chapterList.isEmpty()) {
            return;
        }

        ChapterDbHelper chapterDbHelper = ChapterDbHelper.getsInstance(context);
        SQLiteDatabase sqLiteDatabase = chapterDbHelper.getWritableDatabase();

        sqLiteDatabase.beginTransaction();

        ContentValues contentValues;
        for (Chapter chapter : chapterList) {
            contentValues = new ContentValues();
            contentValues.put(Chapter.COL_ID, chapter.getId());
            contentValues.put(Chapter.COL_NAME, chapter.getName());

            sqLiteDatabase.insertWithOnConflict(Chapter.TABLE_NAME, null, contentValues,
                    SQLiteDatabase.CONFLICT_REPLACE);

            List<ChapterItem> chapterItemList = chapter.getChapterItems();
            for (ChapterItem chapterItem : chapterItemList) {
                contentValues = new ContentValues();
                contentValues.put(ChapterItem.COL_ID, chapterItem.getId());
                contentValues.put(ChapterItem.COL_PID, chapter.getId());
                contentValues.put(ChapterItem.COL_NAME, chapterItem.getName());

                sqLiteDatabase.insertWithOnConflict(ChapterItem.TABLE_NAME, null, contentValues,
                        SQLiteDatabase.CONFLICT_REPLACE);
            }
        }

        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }
}
