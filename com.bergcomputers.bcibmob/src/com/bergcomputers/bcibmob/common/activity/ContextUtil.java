package com.bergcomputers.bcibmob.common.activity;

import java.util.List;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import com.bergcomputers.bcibmob.common.Util;
import com.bergcomputers.bcibmob.common.data.DataModelUtil;

public class ContextUtil {

	public static final String SHARED_PREFERENCES_NAME = "swoor_meetings_preferences";
	public static final String PREFS_MY_PROFILE = "my_profile_prefs";
	protected static final String PREF_NAME_DEVICE_ID = "bcibmob_device_id";
	protected static final String PREF_NAME_GA_SAMPLERATE = "ga_sample_rate";
	public static final String PREF_NAME_LAST_UPDATE = "pref_last_update";

	private static final String TAG = "ContextUtil";


	public static JSONObject prepareDefaultJSONParameters(Context context) {
//		final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

		JSONObject jsonParams = new JSONObject();
		try {
			String deviceId = getDeviceId(context);
			jsonParams.put(Util.PARAM_DEVICE_GUID, deviceId);
			jsonParams.put(Util.PARAM_DEVICE_OS, Util.getAndroidVersionName());
			jsonParams.put(Util.PARAM_DEVICE_LANG, context.getResources().getConfiguration().locale.getLanguage());
			jsonParams.put(Util.PARAM_DEVICE_MODEL, Util.PARAM_DEVICE_MODEL_TABLET);
			jsonParams.put(DataModelUtil.JUNK_DATA, Util.generateRandomKey());
			try {
				jsonParams.put(Util.PARAM_DEVICE_APP_VERSION,
						context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JSONException e) {
		}

		return jsonParams;
	}

	/**
	 * Prepare the default parameters for the http get call
	 * @param context
	 * @return
	 */
	public static String prepareDefaultGetParameters(Context context) {
		StringBuilder builder = new StringBuilder();

		String deviceId = getDeviceId(context);
		builder.append(Util.PARAM_DEVICE_GUID).append("=").append( deviceId).append("&").
		        append(Util.PARAM_DEVICE_OS).append("=").append(Util.getAndroidVersionName()).append("&").
		        append(Util.PARAM_DEVICE_LANG).append("=").append(context.getResources().getConfiguration().locale.getLanguage()).append("&").
		        append(Util.PARAM_DEVICE_MODEL).append("=").append(Util.PARAM_DEVICE_MODEL_PHONE).append("&").
		        append(DataModelUtil.JUNK_DATA).append("=").append(Util.generateRandomKey());

		try {
			String version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
			builder.append("&").append(Util.PARAM_DEVICE_APP_VERSION).append("=").append(version);

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return builder.toString();
	}



	public static String getDeviceId(Context context) {
		String uuid = null;
		if (uuid == null) {
			final SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
			final String id = prefs.getString(PREF_NAME_DEVICE_ID, null);

			if (id != null) {
				// Use the ids previously computed and stored in the prefs file
				uuid = id;
			} else {
				final String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);

				// Use the Android ID unless it's broken, in which case fallback
				// on deviceId,
				// unless it's not available, then fallback on a random number
				// which we store
				// to a prefs file
				if (androidId != null && !"9774d56d682e549c".equals(androidId)) {
					uuid = androidId;
				} else {
					final String deviceId = ((TelephonyManager) context.getSystemService(
					                Context.TELEPHONY_SERVICE)).getDeviceId();
					uuid = deviceId != null ? deviceId : UUID.randomUUID().toString();
				}
				// Write the value out to the prefs file
				prefs.edit().putString(PREF_NAME_DEVICE_ID, uuid.toString()).commit();

			}
		}
		return uuid;
	}

	/**
	 * Check if there is a connection available.
	 * @return boolean value
	 */
	public static boolean isNetworkConnectionAvailable(Context context) {
	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnected()) {
	        return true;
	    }
	    return false;
	}


	public static long getAlertLastUpdate(Context context, long pMeetingId){
		final SharedPreferences prefs = context.getSharedPreferences(ContextUtil.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		final long lastUpdate = prefs.getLong(PREF_NAME_LAST_UPDATE + pMeetingId, -1);
		return lastUpdate;
	}

	public static void setAlertLastUpdate(Context context, long milis, long pMeetingId){
		final SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		final Editor edit = prefs.edit();
		if (edit != null){
			edit.putLong(PREF_NAME_LAST_UPDATE + pMeetingId, milis);
			edit.commit();
		}
	}

	public static void removeAlertLastUpdate(Context context, long pMeetingId){
		final SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		final Editor edit = prefs.edit();
		if (edit != null){
			edit.remove(PREF_NAME_LAST_UPDATE + pMeetingId);
			edit.commit();
		}
	}

	public static int getApiLevel() {
		return new Integer(Build.VERSION.SDK).intValue();
	}

	private static String getDefaultCalendarPackage(){
		String calendarPackage = "com.android.calendar";
		return calendarPackage;
	}

	private static boolean isCallable(Intent intent, Context context){
		List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent,
	            PackageManager.MATCH_DEFAULT_ONLY);
	    return list.size() > 0;
	}
}
