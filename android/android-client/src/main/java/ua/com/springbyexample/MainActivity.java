/*
 ******************************************************************************
 * Parts of this code sample are licensed under Apache License, Version 2.0   *
 * Copyright (c) 2009, Android Open Handset Alliance. All rights reserved.    *
 *																			  *																			*
 * Except as noted, this code sample is offered under a modified BSD license. *
 * Copyright (C) 2011, Motorola Mobility, Inc. All rights reserved.           *
 * 																			  *
 * For more details, see MOTODEV_Studio_for_Android_LicenseNotices.pdf        * 
 * in your installation folder.                                               *
 ******************************************************************************
 */

package ua.com.springbyexample;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import ua.com.springbyexample.R;

public class MainActivity extends Activity {

	// reference to manipulate the action bar
	ActionBar actionBar = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		// get reference to the action bar
		actionBar = getActionBar();

		// set title and subtitle of the action bar
		actionBar.setTitle(getString(R.string.actionBarTitle));
		// actionBar.setSubtitle(getString(R.string.actionBarSubtitle));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.abmenu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			// when app icon in action bar is clicked, show home/main activity
			Intent intent = new Intent(this,
					ua.com.springbyexample.MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;

		case R.id.menuItemAdd:
			Toast.makeText(this, getString(R.string.menuItemAdd),
					Toast.LENGTH_SHORT).show();
			return true;

		case R.id.menuItemRefresh:
			Toast.makeText(this, getString(R.string.menuItemRefresh),
					Toast.LENGTH_SHORT).show();
			return true;

		case R.id.menuItemSettings:
			Toast.makeText(this, getString(R.string.menuItemSettings),
					Toast.LENGTH_SHORT).show();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

}