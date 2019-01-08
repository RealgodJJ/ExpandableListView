package reagodjj.example.com.expandablelistview.biz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import reagodjj.example.com.expandablelistview.bean.Chapter;
import reagodjj.example.com.expandablelistview.bean.ChapterItem;
import reagodjj.example.com.expandablelistview.utils.HttpUtils;

public class ChapterBiz {

    private static final String DATA = "data";
    private static final String CHILDREN = "children";
    private static final String ID = "id";
    private static final String NAME = "name";

    public void loadDatas(final Context context, final CallBack callBack, boolean useCache) {

        @SuppressLint("StaticFieldLeak")
        AsyncTask<Boolean, Void, List<Chapter>> asyncTask = new AsyncTask<Boolean, Void, List<Chapter>>() {
            private Exception exception;

            @Override
            protected List<Chapter> doInBackground(Boolean... booleans) {
                final List<Chapter> chapters = new ArrayList<>();

                try {
                    boolean isUseCache = booleans[0];

                    if (isUseCache) {
                        //TODO:load datas from database
                    }

                    if (chapters.isEmpty()) {
                        //TODO:load from net
                        List<Chapter> chapterListFromNet = loadFromNet(context);
                        chapters.addAll(chapterListFromNet);
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                    exception = e;
                }
                return chapters;
            }

            @Override
            protected void onPostExecute(List<Chapter> chapters) {
                if (exception != null) {
                    callBack.loadFailed(exception);
                } else {
                    callBack.loadSuccess(chapters);
                }

            }
        };

        asyncTask.execute(useCache);
    }

    public interface CallBack {
        void loadSuccess(List<Chapter> chapterList);

        void loadFailed(Exception ex);
    }

    private List<Chapter> loadFromNet(Context context) {
        String url = "http://www.wanandroid.com/tools/mockapi/2/mooc-expandablelistview";
        List<Chapter> chapters = new ArrayList<>();

        //1.发总请求数据并获取
        String content = HttpUtils.doGet(url);

        //2.将String类型的数据转换成List<Chapter>
        if (content != null)
            chapters = parseContent(content);

        return chapters;
    }

    private List<Chapter> parseContent(String content) {
        List<Chapter> chapters = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(content);
            int errorCode = jsonObject.optInt("errorCode");
            if (errorCode == 0) {
                JSONArray parentJsonArray = jsonObject.optJSONArray(DATA);
                for (int i = 0; i < parentJsonArray.length(); i++) {
                    JSONObject tempParentJSONObj = parentJsonArray.optJSONObject(i);
                    int id = tempParentJSONObj.optInt(ID);
                    String name = tempParentJSONObj.optString(NAME);

                    Chapter chapter = new Chapter(id, name);

                    JSONArray childJsonArray = tempParentJSONObj.optJSONArray(CHILDREN);
                    for (int j = 0; j < childJsonArray.length(); j++) {
                        JSONObject tempChildJSONObj = childJsonArray.optJSONObject(j);
                        int cid = tempChildJSONObj.optInt(ID);
                        String cname = tempChildJSONObj.optString(NAME);
                        ChapterItem chapterItem = new ChapterItem(cid, cname);

                        chapter.addChild(chapterItem);
                    }
                    chapters.add(chapter);
                }
            }
            return chapters;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
