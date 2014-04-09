/**
 *
 */
package com.bergcomputers.bcibmob;

import com.bergcomputers.bcibmob.common.activity.BaseActivity;

/**
 * @author bogdanp
 *
 */
public abstract class BasePhoneActivity extends BaseActivity {

	protected int currentAction;

	/**
	 *
	 */
	public BasePhoneActivity() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zwoor.android.meetings.common.activity.BaseActivity#getMainActivityClass
	 * ()
	 */
	@SuppressWarnings("rawtypes")
	protected Class getMainActivityClass() {
		return AccountListActivity.class;
	}

	public void downloadFinished() {
	}

	protected void postDelete() {
	}
}
