package com.bergcomputers.bcibmob.common.activity;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.bergcomputers.bcibmob.R;
import com.bergcomputers.bcibmob.common.Util;

@SuppressLint("NewApi")
public abstract class BaseActivity extends Activity{

	protected static final int DIALOG_ID_FATAL_ERROR = 90;
	protected static final int DIALOG_ID_GENERIC_ERROR = 89;
	public static final int DIALOG_ID_DATA_ERROR = 82;
	protected static final int DIALOG_ID_NETWORK_ERROR = 81;
	protected static final int DIALOG_ID_NETWORK_NOTAVAILABLE = 80;
	protected static final int DIALOG_ID_SERVER_ERROR = 70;

	public static final int DIALOG_ID_EMAIL_INVALID = 71;
	public static final int DIALOG_ID_DATA_SAVED = 72;

	protected static final int DIALOG_ID_PLEASE_WAIT = 60;

	protected static final int DIALOG_ID_CHECK_NEW_VERSION = 61;
	protected static final int DIALOG_ID_SYNC_DEVICE_DATA = 66;
	protected static final int DIALOG_ID_LOADING_MEETINGS = 67;


	protected int serverErrorCode;
    protected String syncErrorDomain;
    protected List<String> imageNames;
    protected ProgressDialog loadingImagesDialog;
    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     * init the basic layout
     * all subclasses that will overwrite should  will first call super.oncreate
     */
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT >= 9) {
        	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        	StrictMode.setThreadPolicy(policy);
        }

    	baseInit();
    	init();
    }

    protected void baseInit() {
    }

	protected DisplayMetrics getDisplayMetrics() {
		Context context = getApplicationContext();
		WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		return metrics;
	}
    /**
     * the implementation should handle every operation regarding the layout
     */
    protected abstract void init();



	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i("onDestroy", "onDestroy "+this);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@SuppressWarnings("rawtypes")
	protected abstract Class getMainActivityClass();

	/**
	 * Handles dialogs shared by more activities.
	 * @see android.app.Activity#onCreateDialog(int)
	 * @param pId id of the dialog
	 */
	@Override
    protected Dialog onCreateDialog(int pId) {
	    Dialog dialog;
	    AlertDialog.Builder dialogBuilder;
	    switch (pId) {
	    case DIALOG_ID_NETWORK_NOTAVAILABLE:
	    	//dialog = buildMessageDialog(getString(R.string.netNotAvailable));
	        break;
	  /*  case DIALOG_ID_NETWORK_ERROR:
	    	dialog = buildMessageDialog(getString(R.string.netError));
	        break;
	    case DIALOG_ID_DATA_ERROR:
	    	dialog = buildMessageDialog(getString(R.string.dataError));
	        break;
	    case DIALOG_ID_GENERIC_ERROR:
	    	dialog = buildMessageDialog(getString(R.string.genericError));
	        break;
	    case DIALOG_ID_PLEASE_WAIT:
	    	dialog = ProgressDialog.show(this, null, getString(R.string.pleaseWait), true);
	        break;
	    case DIALOG_ID_FATAL_ERROR:
	    	// create a dialog that will start the main activity
			dialogBuilder = new AlertDialog.Builder(this);
	    	dialogBuilder.setMessage(R.string.genericError)
	    	       .setCancelable(false)
	    	       .setPositiveButton(getString(R.string.buttonOk), new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	                dialog.cancel();
	    	                Intent intent = new Intent(BaseActivity.this, getMainActivityClass());
	    	                startActivity(intent);
	    	           }
	    	       });
	    	dialog = dialogBuilder.create();
	        break;
	    case DIALOG_ID_SERVER_ERROR:
	    	dialog = buildMessageDialog(getServerErrorMessage(serverErrorCode), DIALOG_ID_SERVER_ERROR);
	        break;
	    case DIALOG_ID_CHECK_NEW_VERSION:
	    	dialog = ProgressDialog.show(this, null, getString(R.string.checkNewVersion), true);
	        break;
		case DIALOG_ID_LOADING_MEETINGS:
			dialog = ProgressDialog.show(this, null, getString(R.string.loadingMeetings), true);
			break;
		case DIALOG_ID_EMAIL_INVALID:
			dialog = buildMessageDialog(getString(R.string.form_error_emailNotValid));
			break;
		case DIALOG_ID_DATA_SAVED:
			dialog = buildMessageDialog(getString(R.string.data_saved));
			break;*/
	    default:
	        dialog = null;
	    }
	    return null; //dialog;
    }

	protected Dialog buildMessageDialog(String pMessage) {
		return buildMessageDialog(pMessage, -1);
	}

	protected Dialog buildMessageDialog(String pMessage, final int pId) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
    	/*dialogBuilder.setMessage(pMessage)
    	       .setCancelable(false)
    	       .setPositiveButton(getString(R.string.buttonOk), new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	        	   if (pId > 0) {
    	        		   removeDialog(pId);
    	        	   } else {
    	        		   dialog.cancel();
    	        	   }
    	           }
    	       });*/
    	return dialogBuilder.create();
	}



	protected Dialog buildYnNDialog(String text, DialogInterface.OnClickListener yesListener,
				DialogInterface.OnClickListener noListener){
    	return buildYnNDialog(text, getString(R.string.buttonYes), getString(R.string.buttonNo),
    				yesListener, noListener);
	}

	protected Dialog buildYnNDialog(String text, String yesLabel, String noLabel, DialogInterface.OnClickListener yesListener,
			DialogInterface.OnClickListener noListener){

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setMessage(text)
		       .setCancelable(false)
		       .setPositiveButton(yesLabel, yesListener)
		       .setNegativeButton(noLabel, noListener);
		return dialogBuilder.create();
	}

	protected void showServerErrorDialog(int pErrorCode) {
		serverErrorCode = pErrorCode;
		showDialog(DIALOG_ID_SERVER_ERROR);
	}

	protected void showServerErrorDialog(int pErrorCode, String pDomain) {
		serverErrorCode = pErrorCode;
		syncErrorDomain = pDomain;
		showDialog(DIALOG_ID_SERVER_ERROR);
	}


	/**
	 * Check if there is a connection available.
	 * @return boolean value
	 */
	protected boolean isNetworkConnectionAvailable() {
	    return ContextUtil.isNetworkConnectionAvailable(this);
	}

	protected JSONObject prepareJSONParameters(Map<String, String> pParams) {
		JSONObject jsonParams = prepareDefaultJSONParameters();
		try {
			for (Entry<String, String> entry : pParams.entrySet()) {
				jsonParams.put(entry.getKey(), entry.getValue());
			}
		} catch (JSONException e) {
		}
		return jsonParams;
	}

	public JSONObject prepareJSONParameters(String pKey, String pValue) {
		JSONObject jsonParams = prepareDefaultJSONParameters();
		try {
			jsonParams.put(pKey, pValue);
		} catch (JSONException e) {
		}
		return jsonParams;
	}

	protected String prepareGetParameters(Map<String, String> pParams) {
		String getParams = ContextUtil.prepareDefaultGetParameters(this);
		StringBuilder builder = new StringBuilder(getParams);
			for (Entry<String, String> entry : pParams.entrySet()) {
				builder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
			}
		return builder.toString();
	}

	public JSONObject addJSONObjectToParams(JSONObject params, String newParamsKey, JSONObject newParams) {
		try {
			params.put(newParamsKey, newParams);
		} catch (JSONException e) {
		}
		return params;
	}

	public JSONObject addJSONArrayToParams(JSONObject params, String arrayKey, JSONArray newParams) {
		try {
			params.put(arrayKey, newParams);
		} catch (JSONException e) {
		}
		return params;
	}

	protected JSONObject prepareDefaultJSONParameters() {
		return ContextUtil.prepareDefaultJSONParameters(this);
	}

	/*protected String getServerErrorMessage(final int errorCode) {
		switch (errorCode) {
        case Util.SERVER_ERROR_ALL_FIELDS_MANDATORY:
        	return getString(R.string.server_error_all_fields_mandatory);
        case Util.SERVER_ERROR_DATABASE_SAVE_FAILED:
        	return getString(R.string.server_error_database_save_failed);
        case Util.SERVER_ERROR_EMAIL_NOT_VALID:
        	return getString(R.string.server_error_email_not_valid);
        case Util.SERVER_ERROR_DEVICE_ID_NOT_VALID:
        	return getString(R.string.server_error_device_id_not_valid);
        case Util.SERVER_ERROR_DEVICE_ID_NOT_FOUND:
        	return getString(R.string.server_error_device_id_not_found);
        case Util.SERVER_ERROR_CODE_NOT_VALID:
        	return getString(R.string.server_error_invalid_confirmation_code);
        case Util.SERVER_ERROR_INVALID_MEETING_CODE:
        	return getString(R.string.server_error_invalid_meeting_code);
        case Util.SERVER_ERROR_SUBSCRIPTION_LIMIT_REACHED:
        	return getString(R.string.server_error_subscription_limit);
        case Util.SERVER_ERROR_EMAIL_ALREADY_REGISTERED:
        	return getString(R.string.server_error_email_registered);
        case Util.SERVER_ERROR_AGENDA_ITEM_NOT_VALID:
        	return getString(R.string.server_error_agenda_item_not_valid);
        case Util.SERVER_ERROR_NO_RATING_PROVIDED:
        	return getString(R.string.server_error_no_rating);
        case Util.SERVER_ERROR_ITEM_ALREADY_RATED:
        	return getString(R.string.server_error_item_rated);
        case Util.SERVER_ERROR_FILE_NOT_FOUND:
        	return getString(R.string.server_error_file_not_found);
        case Util.SERVER_ERROR_NO_ACCESS_ON_THIS_DEVICE:
        	return getString(R.string.server_error_no_access_on_device);
        case Util.SERVER_ERROR_APP_VERSION_NOT_SUPPORTED:
        	return getString(R.string.server_error_app_ver_not_supported);
        case Util.SERVER_ERROR_MATERIAL_IDS_INVALID:
        	return getString(R.string.server_error_materials_ids_invalid);
        case Util.SERVER_ERROR_RATING_AGENDA_ITEM_DOESNT_EXIST:
        	return getString(R.string.server_error_agenda_item_not_valid);
        case Util.SERVER_ERROR_GET_DOC_MATERIAL_WAS_REMOVED:
        	return getString(R.string.server_error_get_doc_material_was_removed);
        case Util.SERVER_ERROR_GET_MEETING_MEETING_NOT_FOUND:
        	return getString(R.string.server_error_get_meeting_meeting_not_found);
        case Util.SERVER_ERROR_MEETING_VISIBILITY_LEVEL_CHANGED:
        	if (syncErrorDomain != null) {
        		return getString(R.string.server_error_meeting_security) + " " + getString(R.string.server_error_meeting_security_domain, syncErrorDomain);
        	} else {
        		return getString(R.string.server_error_meeting_security) + " " + getString(R.string.server_error_meeting_security_email);
        	}
        default:
        	return getString(R.string.server_error);
        }
	}*/

}
