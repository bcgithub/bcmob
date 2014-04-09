package com.bergcomputers.bcibmob.test;

import android.test.ActivityInstrumentationTestCase2;

import com.bergcomputers.bcibmob.AccountsListActivity;

public class AccountListActivityTest extends ActivityInstrumentationTestCase2<AccountsListActivity> {

    public AccountListActivityTest() {
    	super(Class<AccountsListActivity.class>);
    }

    public void testActivity() {
    	AccountsListActivity activity = getActivity();
        assertNotNull(activity);
    }
}

