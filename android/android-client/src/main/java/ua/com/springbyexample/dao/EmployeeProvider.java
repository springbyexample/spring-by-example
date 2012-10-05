package ua.com.springbyexample.dao;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class EmployeeProvider extends ContentProvider {

	private static final String TAG = EmployeeProvider.class.getSimpleName();

	static final String PROVIDER_NAME = "ua.com.springbyexample.employeeprovider";

	public static final Uri CONTENT_URI = Uri.parse("content://"
			+ PROVIDER_NAME);

	static final int EMPLOYEES = 1;
	static final int EMPLOYEE_ID = 2;

	private static final UriMatcher uriMatcher;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, "empliyees", EMPLOYEES);
		uriMatcher.addURI(PROVIDER_NAME, "employee/#", EMPLOYEE_ID);
	}

	DatabaseHelper databaseHelper;

	/**
	 * @see android.content.ContentProvider#onCreate()
	 */
	@Override
	public boolean onCreate() {
		databaseHelper = new DatabaseHelper(getContext());
		return true;
	}

	/**
	 * @see android.content.ContentProvider#delete(Uri,String,String[])
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Put your code here
		return 0;
	}

	/**
	 * @see android.content.ContentProvider#getType(Uri)
	 */
	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case EMPLOYEES:
			return "vnd.android.cursor.dir/vnd." + PROVIDER_NAME;

		case EMPLOYEE_ID:
			return "vnd.android.cursor.item/vnd." + PROVIDER_NAME;
		}
		throw new IllegalArgumentException("Unsupported URI: " + uri);
	}

	/**
	 * @see android.content.ContentProvider#insert(Uri,ContentValues)
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		long rowId = database.insert(DBConsts.TABLE_NAME, "", values);
		database.close();
		if (rowId <= 0) {
			throw new SQLException("Failed to insert row into " + uri);
		}
		Uri newItemUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
		getContext().getContentResolver().notifyChange(newItemUri, null);
		return newItemUri;
	}

	/**
	 * @see android.content.ContentProvider#query(Uri,String[],String,String[],String)
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(DBConsts.TABLE_NAME);

		if (uriMatcher.match(uri) == EMPLOYEE_ID) {
			queryBuilder.appendWhere(DBConsts._ID + "="
					+ uri.getPathSegments().get(1));
		}
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		Cursor cursor = queryBuilder.query(database, projection, selection,
				selectionArgs, null, null, sortOrder);

		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	/**
	 * @see android.content.ContentProvider#update(Uri,ContentValues,String,String[])
	 */
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Put your code here
		return 0;
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DBConsts.TABLE_NAME, null, DBConsts.DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.i(TAG, "create SQL: " + DBConsts.DATABASE_CREATE);
			db.execSQL(DBConsts.DATABASE_CREATE);

			ContentValues values = new ContentValues();
			values.put(DBConsts.FIELD_FNAME, "Vasya");
			values.put(DBConsts.FIELD_SNAME, "Pupkin");
			values.put(DBConsts.FIELD_PROJECT, "Spring");
			db.insert(DBConsts.TABLE_NAME, null, values);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DBConsts.TABLE_NAME);
			onCreate(db);
		}

	}

}
