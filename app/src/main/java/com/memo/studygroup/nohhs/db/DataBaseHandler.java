package com.memo.studygroup.nohhs.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import com.memo.studygroup.nohhs.adapter.item.MemoVO;

/**
 * Created by nohhs on 2015-07-06.
 */
public class DataBaseHandler extends SQLiteOpenHelper
	implements CRUDOperations {

	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "memoDb";

	// Table name
	private static final String TABLE_NAME = "memoTable";

	// Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_MEMO = "memo";
	private static final String KEY_DATE = "date";

	private static final String[] COLUMNS = { KEY_ID, KEY_MEMO, KEY_DATE };

	public DataBaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	public static DataBaseHandler getInstance(Context context) {
		return new DataBaseHandler(context.getApplicationContext());
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//DB를 새로 만든다.
		String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
			+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ KEY_MEMO + " TEXT,"
			+ KEY_DATE + " TEXT" + ");";
		db.execSQL(CREATE_TABLE);

		initValue(db);
	}

	private void initValue(SQLiteDatabase db) {
		List<MemoVO> list = new ArrayList<>();
		list.add(new MemoVO("test4", "20150619"));
		list.add(new MemoVO("test3", "20150619"));
		list.add(new MemoVO("test2", "20150619"));
		list.add(new MemoVO("test1", null));

		db.beginTransaction();
		for (MemoVO item : list) {
			writeItem(db, item);
		}
		db.setTransactionSuccessful();
		db.endTransaction();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// DB를 지우고 다시 쓴다.
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		this.onCreate(db);
	}

	//CRUDOperations Override Method

	@Override
	public long insert(MemoVO memoVO) {
		SQLiteDatabase db = this.getWritableDatabase();
		long result = writeItem(db, memoVO);
		db.close();
		return result;
	}

	private long writeItem(SQLiteDatabase db, MemoVO memoVO) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_MEMO, memoVO.getMemo());
		contentValues.put(KEY_DATE, memoVO.getRegDate());
		return db.insert(TABLE_NAME, null, contentValues);
	}

	@Override
	public MemoVO read(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_NAME, COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, KEY_ID, null);

		if (cursor != null) {
			cursor.moveToFirst();
		}
		return getItem(cursor);
	}

	@Override
	public List<MemoVO> readAll() {

		List<MemoVO> memoVOList = new ArrayList<>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, KEY_ID, null);

		if (cursor.moveToFirst()) {
			do {
				memoVOList.add(getItem(cursor));
			} while (cursor.moveToNext());
		}

		return memoVOList;
	}

	private MemoVO getItem(Cursor cursor) {
		MemoVO item = new MemoVO();
		item.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
		item.setMemo(cursor.getString(cursor.getColumnIndex(KEY_MEMO)));
		item.setRegDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
		return item;
	}

	@Override
	public int update(MemoVO memoVO) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_MEMO, memoVO.getMemo());
		values.put(KEY_DATE, memoVO.getRegDate());
		int i = db.update(TABLE_NAME,
						  values,
						  KEY_ID + " = ?",
						  new String[] { String.valueOf(memoVO.getId()) });

		db.close();
		return i;
	}

	@Override
	public void delete(MemoVO memoVO) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(memoVO.getId()) });
		db.close();
	}

}