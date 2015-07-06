package com.memo.studygroup.nohhs.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.memo.studygroup.nohhs.common.Const;

/**
 * Created by nohhs on 2015-07-06.
 */
public class BaseActivity extends AppCompatActivity {

	private final int IGNORE_REQUEST_CODE = -1;

	protected void startActivity(Class<?> target) {
		startActivity(target, null);
	}

	protected void startActivity(Class<?> target, String key, Parcelable item) {
		Bundle bundle = new Bundle();
		bundle.putParcelable(key, item);
		startActivity(target, bundle);
	}

	protected void startActivity(Class<?> target, Bundle bundle) {
		startActivityForResult(target, IGNORE_REQUEST_CODE, bundle);
	}

	protected void startActivityForResult(Class<?> target, int requestCode) {
		startActivityForResult(target, requestCode, null);
	}

	protected void startActivityForResult(Class<?> target, int requestCode, String key, Parcelable item) {
		Bundle bundle = new Bundle();
		bundle.putParcelable(key, item);
		startActivityForResult(target, requestCode, bundle);
	}

	protected void startActivityForResult(Class<?> target, int requestCode, Bundle bundle) {
		if (target == null) {
			Log.d(Const.LOG_TAG, "Target is empty");
			return;
		}

		Intent intent = new Intent(this, target);
		intent.putExtras(bundle);
		startActivityForResult(intent, requestCode);
	}

	protected void showToast(int resId) {
		showToast(resId, Toast.LENGTH_SHORT);
	}

	protected void showToast(CharSequence text) {
		showToast(text, Toast.LENGTH_SHORT);
	}

	protected void showToast(int resId, int duration) {
		showToast(getString(resId), Toast.LENGTH_SHORT);
	}

	protected void showToast(CharSequence text, int duration) {
		if (TextUtils.isEmpty(text)) {
			Log.d(Const.LOG_TAG, "Text is empty");
			return;
		}
		Toast.makeText(this, text, duration).show();
	}


}
