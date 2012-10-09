/*
 ******************************************************************************
 * Parts of this code sample are licensed under Apache License, Version 2.0   *
 * Copyright (c) 2009, Android Open Handset Alliance. All rights reserved.    *
 *																			  *																			*
 * Except as noted, this code sample is offered under a modified BSD license. *
 * Copyright (C) 2010, Motorola Mobility, Inc. All rights reserved.           *
 * 																			  *
 * For more details, see MOTODEV_Studio_for_Android_LicenseNotices.pdf        * 
 * in your installation folder.                                               *
 ******************************************************************************
 */
package ua.com.springbyexample.dao.provider;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class EmployeeContentProvider extends ContentProvider {

	private EmployeeOpenHelper dbHelper;
	private static HashMap<String, String> EMPLOYEE_PROJECTION_MAP;
	private static final String TABLE_NAME = "employee";
	static final String AUTHORITY = "ua.com.springbyexample.dao.provider.employeecontentprovider";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_NAME);
	public static final Uri ID_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/id");
	public static final Uri FIRST_NAME_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/first_name");
	public static final Uri SECOND_NAME_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/second_name");
	public static final Uri PROJECT_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/project");
	public static final Uri SYNC_STATUS_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/sync_status");

	public static final String DEFAULT_SORT_ORDER = "_id ASC";

	private static final UriMatcher URL_MATCHER;

	private static final int EMPLOYEE = 1;
	private static final int EMPLOYEE_ID = 3;
	private static final int EMPLOYEE_FIRST_NAME = 4;
	private static final int EMPLOYEE_SECOND_NAME = 5;
	private static final int EMPLOYEE_PROJECT = 6;
	private static final int EMPLOYEE_SYNC_STATUS = 7;

	// Content values keys (using column names)
	public static final String _ID = "_id";
	public static final String ID = "id";
	public static final String FIRST_NAME = "first_name";
	public static final String SECOND_NAME = "second_name";
	public static final String PROJECT = "project";
	public static final String SYNC_STATUS = "sync_status";

	public boolean onCreate() {
		dbHelper = new EmployeeOpenHelper(getContext(), true);
		return (dbHelper == null) ? false : true;
	}

	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sort) {
		SQLiteDatabase mDB = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (URL_MATCHER.match(url)) {
		case EMPLOYEE:
			qb.setTables(TABLE_NAME);
			qb.setProjectionMap(EMPLOYEE_PROJECTION_MAP);
			break;
		case EMPLOYEE_ID:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("id='" + url.getPathSegments().get(2) + "'");
			break;
		case EMPLOYEE_FIRST_NAME:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("first_name='" + url.getPathSegments().get(2) + "'");
			break;
		case EMPLOYEE_SECOND_NAME:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("second_name='" + url.getPathSegments().get(2) + "'");
			break;
		case EMPLOYEE_PROJECT:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("project='" + url.getPathSegments().get(2) + "'");
			break;
		case EMPLOYEE_SYNC_STATUS:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("sync_status='" + url.getPathSegments().get(2) + "'");
			break;

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
		String orderBy = "";
		if (TextUtils.isEmpty(sort)) {
			orderBy = DEFAULT_SORT_ORDER;
		} else {
			orderBy = sort;
		}
		Cursor c = qb.query(mDB, projection, selection, selectionArgs, null,
				null, orderBy);
		c.setNotificationUri(getContext().getContentResolver(), url);
		return c;
	}

	public String getType(Uri url) {
		switch (URL_MATCHER.match(url)) {
		case EMPLOYEE:
			return "vnd.android.cursor.dir/vnd.ua.com.springbyexample.dao.gen.employee";
		case EMPLOYEE_ID:
			return "vnd.android.cursor.item/vnd.ua.com.springbyexample.dao.gen.employee";
		case EMPLOYEE_FIRST_NAME:
			return "vnd.android.cursor.item/vnd.ua.com.springbyexample.dao.gen.employee";
		case EMPLOYEE_SECOND_NAME:
			return "vnd.android.cursor.item/vnd.ua.com.springbyexample.dao.gen.employee";
		case EMPLOYEE_PROJECT:
			return "vnd.android.cursor.item/vnd.ua.com.springbyexample.dao.gen.employee";
		case EMPLOYEE_SYNC_STATUS:
			return "vnd.android.cursor.item/vnd.ua.com.springbyexample.dao.gen.employee";

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
	}

	public Uri insert(Uri url, ContentValues initialValues) {
		checkUriForInsert(url);

		SQLiteDatabase mDB = dbHelper.getWritableDatabase();
		long rowID;
		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}

		rowID = mDB.insert("employee", "employee", values);
		if (rowID > 0) {
			Uri uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(uri, null);
			return uri;
		}
		throw new SQLException("Failed to insert row into " + url);
	}

	public int delete(Uri url, String where, String[] whereArgs) {
		SQLiteDatabase mDB = dbHelper.getWritableDatabase();
		int count;
		String segment = "";
		switch (URL_MATCHER.match(url)) {
		case EMPLOYEE:
			count = mDB.delete(TABLE_NAME, where, whereArgs);
			break;
		case EMPLOYEE_ID:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case EMPLOYEE_FIRST_NAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"first_name="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case EMPLOYEE_SECOND_NAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"second_name="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case EMPLOYEE_PROJECT:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"project="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case EMPLOYEE_SYNC_STATUS:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"sync_status="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
		getContext().getContentResolver().notifyChange(url, null);
		return count;
	}

	public int update(Uri url, ContentValues values, String where,
			String[] whereArgs) {
		SQLiteDatabase mDB = dbHelper.getWritableDatabase();
		int count;
		String segment = "";
		switch (URL_MATCHER.match(url)) {
		case EMPLOYEE:
			count = mDB.update(TABLE_NAME, values, where, whereArgs);
			break;
		case EMPLOYEE_ID:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case EMPLOYEE_FIRST_NAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"first_name="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case EMPLOYEE_SECOND_NAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"second_name="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case EMPLOYEE_PROJECT:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"project="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case EMPLOYEE_SYNC_STATUS:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"sync_status="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
		getContext().getContentResolver().notifyChange(url, null);
		return count;
	}

	// NON GENERATED CODE: be sure to migrate if regenerate

	/**
	 * Override as {@link ContentProvider#bulkInsert(Uri, ContentValues[])}
	 * provides no real optimization for bulk operation. Also - for some reason
	 * it doesn't send notifications to {@link ContentObserver}
	 */
	@Override
	public int bulkInsert(Uri uri, ContentValues[] valuesArray) {
		checkUriForInsert(uri);

		SQLiteDatabase database = dbHelper.getWritableDatabase();
		// ALL or nothing approach
		database.beginTransaction();
		try {
			for (ContentValues values : valuesArray) {
				long newID = database.insertOrThrow(TABLE_NAME, null, values);
				if (newID <= 0) {
					throw new SQLException("Failed to insert row into " + uri);
				}
			}
			database.setTransactionSuccessful();
			// we should notify on basic URI, as nobody listens yet for unknown
			// id...
			getContext().getContentResolver().notifyChange(uri, null);

		} finally {
			database.endTransaction();
			// Looks like this call is causing exception down the call stack...
			// TODO: verify
			// database.close();
		}
		return valuesArray.length;
	}

	private void checkUriForInsert(Uri url) {
		if (URL_MATCHER.match(url) != EMPLOYEE) {
			throw new IllegalArgumentException("Unknown URL " + url);
		}
	}

	static {
		URL_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase(), EMPLOYEE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/id" + "/*",
				EMPLOYEE_ID);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/first_name"
				+ "/*", EMPLOYEE_FIRST_NAME);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/second_name"
				+ "/*", EMPLOYEE_SECOND_NAME);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/project"
				+ "/*", EMPLOYEE_PROJECT);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/sync_status"
				+ "/*", EMPLOYEE_SYNC_STATUS);

		EMPLOYEE_PROJECTION_MAP = new HashMap<String, String>();
		EMPLOYEE_PROJECTION_MAP.put(_ID, "_id");
		EMPLOYEE_PROJECTION_MAP.put(ID, "id");
		EMPLOYEE_PROJECTION_MAP.put(FIRST_NAME, "first_name");
		EMPLOYEE_PROJECTION_MAP.put(SECOND_NAME, "second_name");
		EMPLOYEE_PROJECTION_MAP.put(PROJECT, "project");
		EMPLOYEE_PROJECTION_MAP.put(SYNC_STATUS, "sync_status");

	}
}
