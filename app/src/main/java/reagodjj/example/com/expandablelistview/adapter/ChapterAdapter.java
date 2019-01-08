package reagodjj.example.com.expandablelistview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import reagodjj.example.com.expandablelistview.R;
import reagodjj.example.com.expandablelistview.bean.Chapter;

public class ChapterAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Chapter> chapters;

    public ChapterAdapter(Context context, List<Chapter> chapters) {
        this.context = context;
        this.chapters = chapters;
    }

    @Override
    public int getGroupCount() {
        return chapters != null ? chapters.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return chapters.get(groupPosition).getChapterItems().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return chapters.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return chapters.get(groupPosition).getChapterItems().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentViewHolder parentViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.parent_chapter_item, parent, false);
            parentViewHolder = new ParentViewHolder(convertView);
            convertView.setTag(parentViewHolder);
        } else {
            parentViewHolder = (ParentViewHolder) convertView.getTag();
        }

        parentViewHolder.tvParentItem.setText(chapters.get(groupPosition).getName());
        parentViewHolder.ivGroup.setImageResource(R.drawable.indicator_group);
        parentViewHolder.ivGroup.setSelected(isExpanded);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.child_chapter_item, parent, false);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        childViewHolder.tvChildItem.setText(chapters.get(groupPosition).getChapterItems().get(childPosition).getName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public static class ParentViewHolder {
        TextView tvParentItem;
        ImageView ivGroup;

        ParentViewHolder(View view) {
            tvParentItem = view.findViewById(R.id.tv_parent_item);
            ivGroup = view.findViewById(R.id.iv_group);
        }
    }

    public static class ChildViewHolder {
        TextView tvChildItem;

        ChildViewHolder(View view) {
            tvChildItem = view.findViewById(R.id.tv_child_item);
        }
    }
}
