package reagodjj.example.com.expandablelistview.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import reagodjj.example.com.expandablelistview.bean.Chapter;
import reagodjj.example.com.expandablelistview.bean.ChapterItem;

public class ChapterDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "db_chapter.db";
    private static final int VERSION = 1;
    private static ChapterDbHelper sInstance;

    private ChapterDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static synchronized ChapterDbHelper getsInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ChapterDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + Chapter.TABLE_NAME + "(" + Chapter.COL_ID +
                " integer primary key autoincrement, " + Chapter.COL_NAME + " varchar(20))");

        db.execSQL("create table if not exists " + ChapterItem.TABLE_NAME + "(" + ChapterItem.COL_ID +
                " integer primary key autoincrement, " + ChapterItem.COL_PID + " integer, " +
                ChapterItem.COL_NAME + " varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
