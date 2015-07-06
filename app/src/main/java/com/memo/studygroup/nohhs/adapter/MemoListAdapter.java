package com.memo.studygroup.nohhs.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.memo.studygroup.nohhs.adapter.item.MemoVO;
import com.memo.studygroup.nohhs.R;

/**
 * Created by nohhs on 2015-07-06.
 */
public class MemoListAdapter extends BaseAdapter {

	private final LayoutInflater inflater;
	private List<MemoVO> itemList;

	public MemoListAdapter(Context context, List<MemoVO> memoVOList) {
		this.itemList = memoVOList;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return itemList.size() > 0 ? itemList.size() : 0;
	}

	@Override
	public MemoVO getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_memo_list, parent, false);
		}

		TextView textId = ViewHolder.get(convertView, R.id.item_id_textView);
		TextView textMemo = ViewHolder.get(convertView, R.id.item_memo_textView);
		TextView textRegDate = ViewHolder.get(convertView, R.id.item_regdate_textView);

		MemoVO item = itemList.get(position);
		textId.setText(String.valueOf(item.getId()));
		textMemo.setText(item.getMemo());
		textRegDate.setText(item.getRegDate());

		return convertView;
	}

	public void addItem(MemoVO item) {
		if (itemList == null) {
			itemList = new ArrayList<>();
		}
		itemList.add(item);
	}

	public void updateItem(int position, MemoVO item) {
		if (itemList == null
			|| itemList.size() <= position) {
			return;
		}
		itemList.set(position, item);
	}

	public void removeItem(int position) {
		if (itemList == null
			|| itemList.size() <= position) {
			return;
		}
		itemList.remove(position);
	}

	private static class ViewHolder {
		@SuppressWarnings("unchecked")
		public static <T extends View> T get(View view, int id) {
			SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
			if (viewHolder == null) {
				viewHolder = new SparseArray<>();
				view.setTag(viewHolder);
			}
			View childView = viewHolder.get(id);
			if (childView == null) {
				childView = view.findViewById(id);
				viewHolder.put(id, childView);
			}
			return (T) childView;
		}
	}
}
