package com.memo.studygroup.nohhs;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.List;

import com.memo.studygroup.nohhs.adapter.MemoListAdapter;
import com.memo.studygroup.nohhs.adapter.item.MemoVO;
import com.memo.studygroup.nohhs.base.BaseActivity;
import com.memo.studygroup.nohhs.common.Const;
import com.memo.studygroup.nohhs.common.OPTION_CODE;
import com.memo.studygroup.nohhs.db.DataBaseHandler;

public class MemoListActivity extends BaseActivity
	implements OnItemClickListener {

	private MemoListAdapter adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_memo_list);
		initView();
	}

	private void initView() {
		ListView listview = (ListView) findViewById(R.id.list);
		List<MemoVO> list = DataBaseHandler.getInstance(this).readAll();
		adapter = new MemoListAdapter(this, list);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_add) {
			startMemoWrite(-1, null);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}

		MemoVO item = data.getParcelableExtra(Const.KEY_MEMO);
		if (item == null) {
			return;
		}
		OPTION_CODE code = (OPTION_CODE) data.getSerializableExtra(Const.KEY_OPTION);
		int position = data.getIntExtra(Const.KEY_POS, 0);

		switch (code) {
			case ADD:
				adapter.addItem(item);
				adapter.notifyDataSetChanged();
				break;
			case UPDATE:
				adapter.updateItem(position, item);
				adapter.notifyDataSetChanged();
				break;
			case REMOVE:
				adapter.removeItem(position);
				adapter.notifyDataSetChanged();
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> view, View v, int position, long id) {
		startMemoWrite(position, adapter.getItem(position));
	}

	private void startMemoWrite(int position, MemoVO item) {
		Bundle bundle = new Bundle();
		if (item != null) {
			bundle.putParcelable(Const.KEY_MEMO, item);
			bundle.putInt(Const.KEY_POS, position);
			bundle.putSerializable(Const.KEY_OPTION, OPTION_CODE.UPDATE);
		} else {
			bundle.putSerializable(Const.KEY_OPTION, OPTION_CODE.ADD);
		}
		startActivityForResult(MemoActivity.class, 1, bundle);
	}

}
