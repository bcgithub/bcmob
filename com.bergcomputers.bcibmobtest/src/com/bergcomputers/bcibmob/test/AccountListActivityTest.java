package com.bergcomputers.bcibmob.test;

import android.test.ActivityInstrumentationTestCase2;

import com.bergcomputers.bcibmob.AccountListActivity;

public class AccountListActivityTest extends ActivityInstrumentationTestCase2<AccountListActivity> {

    public AccountListActivityTest() {
    	super(AccountListActivity.class);
    }

    public void testActivity() {
    	AccountListActivity activity = getActivity();
        assertNotNull(activity);
    }
}

