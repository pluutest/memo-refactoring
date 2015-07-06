package com.memo.studygroup.nohhs;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.memo.studygroup.nohhs.adapter.item.MemoVO;
import com.memo.studygroup.nohhs.base.BaseActivity;
import com.memo.studygroup.nohhs.common.Const;
import com.memo.studygroup.nohhs.common.DateUtil;
import com.memo.studygroup.nohhs.common.OPTION_CODE;
import com.memo.studygroup.nohhs.db.DataBaseHandler;

public class MemoActivity extends BaseActivity
	implements View.OnClickListener {

	private OPTION_CODE option;
	private MemoVO currentItem;
	private int pos;

	private EditText et;
	private Button btnWrite, btnDelete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
		initValue();
	}

	private void init() {
		btnWrite = (Button) findViewById(R.id.write);
		btnWrite.setOnClickListener(this);
		btnDelete = (Button) findViewById(R.id.delete);
		btnDelete.setOnClickListener(this);
		et = (EditText) findViewById(R.id.edit);
	}

	private void initValue() {Intent intent = getIntent();
		if (intent.hasExtra(Const.KEY_OPTION)) {
			option = (OPTION_CODE) intent.getSerializableExtra(Const.KEY_OPTION);
		} else {
			option = OPTION_CODE.ADD;
		}

		if (option == OPTION_CODE.ADD) {
			btnDelete.setVisibility(View.GONE);
		}

		if (intent.hasExtra(Const.KEY_MEMO)) {
			currentItem = intent.getParcelableExtra(Const.KEY_MEMO);
			pos = intent.getIntExtra(Const.KEY_POS, 0);

			String viewMemo = currentItem.getMemo();
			et.setText(viewMemo);
			et.setSelection(viewMemo.length());
			showToast(viewMemo);
		} else {
			currentItem = new MemoVO();
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.write) {
			String str = et.getText().toString();

			currentItem.setMemo(str);

			if (option == OPTION_CODE.ADD) {
				MemoVO item = new MemoVO(str, DateUtil.getToday());
				long dbId = DataBaseHandler.getInstance(this).insert(item);
				if (dbId != -1) {
					showToast("Insert success");
					item.setId((int)dbId);
					currentItem = item;
				} else{
					showToast("Insert Fail");
					return;
				}
			} else {
				long dbId = DataBaseHandler.getInstance(this).update(currentItem);
				showToast("Update " + (dbId == -1 ? "Fail" : "Success"));
			}
			setResult(option == OPTION_CODE.ADD ? option : OPTION_CODE.UPDATE, currentItem);
			finish();
		} else if (id == R.id.delete) {
			DataBaseHandler.getInstance(this).delete(currentItem);
			setResult(OPTION_CODE.REMOVE, currentItem);
			finish();
		}
	}

	private void setResult(OPTION_CODE code, MemoVO item) {
		Intent data = new Intent();
		data.putExtra(Const.KEY_OPTION, code);
		data.putExtra(Const.KEY_MEMO, item);
		data.putExtra(Const.KEY_POS, pos);
		setResult(RESULT_OK, data);
	}
}
