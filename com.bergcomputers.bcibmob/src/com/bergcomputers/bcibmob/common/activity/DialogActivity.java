package com.bergcomputers.bcibmob.common.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.bergcomputers.bcibmob.R;

/**
 * Activity used only to display dialogs.
 * The service can not create dialogs, so we need this to display messages.
 * @author Adrian Macavei
 *
 */
public class DialogActivity extends Activity {

	private static final int DIALOG_ID_ALERT = 1;

	public static final String KEY_TITLE = "key_title";
	public static final String KEY_MESSAGE = "key_message";

	private String title;
	private String message;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        title = intent.getExtras().getString(KEY_TITLE);
        message = intent.getExtras().getString(KEY_MESSAGE);
        showDialog(DIALOG_ID_ALERT);
    }

	@Override
	protected Dialog onCreateDialog(int pId) {
		Dialog dialog = null;
		AlertDialog.Builder dialogBuilder;
		/*switch (pId) {
		case DIALOG_ID_ALERT:
			dialogBuilder = new AlertDialog.Builder(this);
	    	dialogBuilder.setMessage(message)
	    			.setTitle(title)
	    			.setCancelable(false)
	    			.setPositiveButton(getString(R.string.buttonOk), new DialogInterface.OnClickListener() {
	    				public void onClick(DialogInterface dialog, int id) {
	    					dialog.cancel();
	    					finish();
	    				}
	    			});
	    	dialog = dialogBuilder.create();
			break;
		}*/

		return dialog;
	}

}