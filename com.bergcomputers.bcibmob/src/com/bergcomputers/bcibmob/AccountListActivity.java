package com.bergcomputers.bcibmob;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

/**
 * An activity representing a list of Accounts. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link AccountDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link AccountListFragment} and the item details (if present) is a
 * {@link AccountDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link AccountListFragment.Callbacks} interface to listen for item
 * selections.
 */
public class AccountListActivity extends FragmentActivity implements
		AccountListFragment.Callbacks, BeneficiaryListFragment.Callbacks,
		TransactionListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_list);

		if (findViewById(R.id.account_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((AccountListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.account_list))
					.setActivateOnItemClick(true);
		}

		AccountListFragment fragment = new AccountListFragment();
		getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.fragments, fragment).commit();
		
		final Button accounts = (Button) findViewById(R.id.accountsButton1);
		accounts.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Bundle arguments = new Bundle();
				// arguments.putString(AccountDetailFragment.ARG_ITEM_ID, id);
				AccountListFragment fragment = new AccountListFragment();
				fragment.setArguments(arguments);
				getSupportFragmentManager()
						.beginTransaction()
//						.remove(getSupportFragmentManager().findFragmentById(
//								R.id.account_list))
						.replace(R.id.fragments, fragment).commit();
			}
		});

		final Button trans = (Button) findViewById(R.id.transactionsButton2);
		trans.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Bundle arguments = new Bundle();
				// arguments.putString(AccountDetailFragment.ARG_ITEM_ID, id);
				TransactionListFragment fragment = new TransactionListFragment();
				fragment.setArguments(arguments);
				getSupportFragmentManager()
						.beginTransaction()
						//.remove(getSupportFragmentManager().findFragmentById(
						//		R.id.account_list))
						.replace(R.id.fragments, fragment).commit();
			}
		});

		final Button benef = (Button) findViewById(R.id.beneficiariesButton3);
		benef.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Bundle arguments = new Bundle();
				// arguments.putString(AccountDetailFragment.ARG_ITEM_ID, id);
				BeneficiaryListFragment fragment = new BeneficiaryListFragment();
				fragment.setArguments(arguments);
				getSupportFragmentManager()
						.beginTransaction()
						//.remove(getSupportFragmentManager().findFragmentById(
						//		R.id.account_list))
						.replace(R.id.fragments, fragment).commit();
			}
		});

		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link AccountListFragment.Callbacks} indicating
	 * that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(AccountDetailFragment.ARG_ITEM_ID, id);
			AccountDetailFragment fragment = new AccountDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.account_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, AccountDetailActivity.class);
			detailIntent.putExtra(AccountDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}

}
