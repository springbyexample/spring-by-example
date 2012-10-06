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

package ua.com.springbyexample.activity;

import ua.com.springbyexample.R;
import android.app.Activity;
import android.os.Bundle;

/**
 * Application default {@link Activity}
 * 
 * @author akaverin
 * 
 */
public class MainActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		// TODO: fix or leave APP only title, as it has different text which
		// noticeably changed during app initialization
		actionBar.setTitle(R.string.actionBarTitle);
		actionBar.setDisplayHomeAsUpEnabled(false);
	}

}