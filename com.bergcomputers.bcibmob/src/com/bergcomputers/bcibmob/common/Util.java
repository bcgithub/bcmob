package com.bergcomputers.bcibmob.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import android.os.Build;
import android.os.Environment;

public class Util {

	// base urls
	public static final String BASE_URL = "http://192.168.2.48:8080/bcibws/rest/";

	// to be appended to the url for different operation
	public static final String URL_GET_ACCOUNTS = "accounts";


	public static final char URL_SEPARATOR = '/';
	public static final String PIC_HEADER = "s3_";

	// error codes from server
	public static final int SERVER_ERROR_ALL_FIELDS_MANDATORY = 1000;
	public static final int SERVER_ERROR_DATABASE_SAVE_FAILED = 1007;
	public static final int SERVER_ERROR_EMAIL_NOT_VALID = 1010;
	public static final int SERVER_ERROR_DEVICE_ID_NOT_VALID = 1011;
	public static final int SERVER_ERROR_DEVICE_ID_NOT_FOUND = 1012;
	public static final int SERVER_ERROR_CODE_NOT_VALID = 1013;
	public static final int SERVER_ERROR_INVALID_MEETING_CODE = 1014;
	public static final int SERVER_ERROR_SUBSCRIPTION_LIMIT_REACHED = 1015;
	public static final int SERVER_ERROR_EMAIL_ALREADY_REGISTERED = 1016;
	public static final int SERVER_ERROR_AGENDA_ITEM_NOT_VALID = 1017;
	public static final int SERVER_ERROR_NO_RATING_PROVIDED = 1018;
	public static final int SERVER_ERROR_ITEM_ALREADY_RATED = 1019;
	public static final int SERVER_ERROR_FILE_NOT_FOUND = 1020;
	public static final int SERVER_ERROR_NO_ACCESS_ON_THIS_DEVICE = 1021;
	public static final int SERVER_ERROR_MATERIAL_IDS_INVALID = 1022;
	public static final int SERVER_ERROR_RATING_AGENDA_ITEM_DOESNT_EXIST = 1023;
	public static final int SERVER_ERROR_GET_DOC_MATERIAL_WAS_REMOVED = 1024;
	public static final int SERVER_ERROR_GET_MEETING_MEETING_NOT_FOUND = 1025;
	public static final int SERVER_ERROR_MEETING_VISIBILITY_LEVEL_CHANGED = 1026;

	public static final int SERVER_ERROR_APP_VERSION_NOT_SUPPORTED = 2000;

	// define min res, in pixels, that we will consider for a device, to be a
	// tablet
	public static final int TABLET_MIN_WIDTH = 480;
	public static final int TABLET_MIN_HEIGHT = 800;
	// define min diagonal, in inches, that we will consider for a device, to be
	// a tablet
	public static final int TABLET_MIN_DIAGONAL = 5;

	// specific parameters
	public static final String PARAM_KEYWORD = "keyword";
	public static final String PARAM_GCM_TOKEN = "push_token";
	public static final String PARAM_IS_ANDROID = "is_android";

	// generic parameters
	public static final String PARAM_DATAEN = "dataen";
	public static final String PARAM_DEVICE_OS = "device_os";
	public static final String PARAM_DEVICE_GUID = "device_guid";
	public static final String PARAM_DEVICE_LANG = "device_lang";
	public static final String PARAM_DEVICE_APP_VERSION = "device_appver";
	public static final String PARAM_MODEL = "model";
	public static final String PARAM_MODEL_VALUE = "android";
	public static final String PARAM_DEVICE_MODEL = "device_model";
	public static final String PARAM_DEVICE_MODEL_PHONE = "PHONE";
	public static final String PARAM_DEVICE_MODEL_TABLET = "TABLET";
	public static final String PARAM_MID = "mId";

	public static final String PARAM_OTHER = "other";

	public static final String EMAIL_FORMAT = "^[A-Z0-9a-z\\._%\\+\\-]+@[A-Za-z0-9\\.\\-]+\\.[A-Za-z]{2,4}$";

	public static final DateFormat DATE_FORMAT;
	public static final DateFormat DATE_FORMAT_FULL;
	public static final DateFormat DATE_FORMAT_USER;
	public static final DateFormat TIME_FORMAT_USER;
	public static final int MILLISECONDS_IN_DAY = 1000 * 60 * 60 * 24;
	static {
		DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
		DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
		DATE_FORMAT_FULL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DATE_FORMAT_FULL.setTimeZone(TimeZone.getTimeZone("UTC"));
		DATE_FORMAT_USER = new SimpleDateFormat("MMM d, yyyy");
		DATE_FORMAT_USER.setTimeZone(TimeZone.getTimeZone("UTC"));
		TIME_FORMAT_USER = new SimpleDateFormat("hh:mm a");
		TIME_FORMAT_USER.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	public static final String SD_CARD_MATERIALS_DIR = "zwoor_materials";

	public static final String TEMP_IMG_DIR = "tmp_meeting_images";
	public static final String TEMP_IMG_SEARCH_DIR = "tmp_meeting_search_images";

	private Util() {
	}

	public static String inputStreamToString(InputStream is) throws IOException {
		String line = "";
		StringBuilder total = new StringBuilder();
		// Wrap a BufferedReader around the InputStream
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		// Read response until the end
		while ((line = rd.readLine()) != null) {
			total.append(line);
		}
		// Return full string
		return total.toString();
	}

	public static String generateRandomKey() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}

	public static String getMinSecFormat(long millis) {
		long minutes = (millis / 1000) / 60;
		long seconds = (millis / 1000) % 60;

		StringBuffer timeFormatted = new StringBuffer();
		if (minutes < 10) {
			timeFormatted.append("0");
			timeFormatted.append(minutes);
		} else {
			timeFormatted.append(minutes);
		}

		timeFormatted.append(":");
		if (seconds < 10) {
			timeFormatted.append("0");
			timeFormatted.append(seconds);
		} else {
			timeFormatted.append(seconds);
		}

		return timeFormatted.toString();
	}

	/**
	 * @param value
	 *            int value
	 * @return false for 0, true for anything else
	 */
	public static boolean getBoolean(Integer value) {
		if (value == null) {
			return false;
		}
		return value != 0;
	}

	/**
	 * @param question
	 * @return 1 for true, 0 for false
	 */
	public static int getInt(boolean condition) {
		if (condition) {
			return 1;
		}
		return 0;
	}

	public static boolean canWriteSDCard() {
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			return true;
		}
		return false;
	}

	public static File getMaterialsDir(String pMeetingCode) {
		File filesDir = Environment.getExternalStorageDirectory();
		// create a File object for the parent directory
		File materials = new File(filesDir, SD_CARD_MATERIALS_DIR
				+ File.separator + pMeetingCode);
		// have the object build the directory structure, if needed.
		materials.mkdirs();
		return materials;
	}

	public static String getAndroidVersionName() {
		switch (Build.VERSION.SDK_INT) {
		case 18:
			return "Android 4.3";
		case 17:
			return "Android 4.2.2";
		case 16:
			return "Android 4.1.2";
		case 15:
			return "Android 4.0.3";
		case 14:
			return "Android 4.0";
		case 13:
			return "Android 3.2";
		case 12:
			return "Android 3.1";
		case 11:
			return "Android 3.0";
		case 10:
			return "Android 2.3.3";
		case 9:
			return "Android 2.3";
		case 8:
			return "Android 2.2";
		case 7:
			return "Android 2.1";
		default:
			return "Android 0.0";
		}
	}

	public static boolean validateEmail(String pEmail) {
		if (pEmail == null) {
			return false;
		} else {
			String email = pEmail.trim();
			return email.length() > 1 && email.matches(EMAIL_FORMAT);
		}
	}

	public static final boolean isEmpty(String pString) {
		return pString == null || pString.trim().length() < 1;
	}

	public static Date getDate(long pMillis) {
		Calendar cal = DATE_FORMAT_FULL.getCalendar();
		cal.setTimeInMillis(pMillis);
		return cal.getTime();
	}

	public static String getSizeForUser(double pSize) {
		StringBuffer sb = new StringBuffer();
		DecimalFormat df = new DecimalFormat("#.##");
		double sizeInB = (long) pSize;
		if (sizeInB < 512) {
			sb.append(sizeInB);
		} else if (sizeInB < 1024 * 512) {
			sb.append(df.format(sizeInB / 1024));
			sb.append("K");
		} else {
			sb.append(df.format(sizeInB / 1024 / 1024));
			sb.append("M");
		}
		sb.append("B");
		return sb.toString();
	}
}
