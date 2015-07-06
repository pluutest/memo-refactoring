package com.memo.studygroup.nohhs.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by nohhs on 2015-07-06.
 */
public class DateUtil {
	private static final String DATE_FORMAT = "yyyy.MM.dd.ss";
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.KOREA);

	public static String getToday() {
		Date currentTime = new Date();
		return dateFormat.format(currentTime);
	}

}
