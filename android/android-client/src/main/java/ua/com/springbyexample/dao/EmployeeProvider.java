package ua.com.springbyexample.dao;

import android.content.ContentProvider;
import android.content.ContentResolver;
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

	private static final String AUTHORITY = "ua.com.springbyexample.employeeprovider";
	private static final String BASE_PATH = "employees";
	private static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/vnd.ua.com.springbyexample.todos";
	private static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/vnd.ua.com.springbyexample.todo";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH);

	static final int EMPLOYEES = 1;
	static final int EMPLOYEE_ID = 2;

	private static final UriMatcher uriMatcher;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, BASE_PATH, EMPLOYEES);
		uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", EMPLOYEE_ID);
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
			return CONTENT_TYPE;

		case EMPLOYEE_ID:
			return CONTENT_ITEM_TYPE;
		}
		throw new IllegalArgumentException("Unsupported URI: " + uri);
	}

	/**
	 * @see android.content.ContentProvider#insert(Uri,ContentValues)
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		checkUri(uri);

		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		long rowId = database.insert(DBConsts.TABLE_NAME, "", values);
		database.close();
		if (rowId <= 0) {
			throw new SQLException("Failed to insert row into " + uri);
		}
		Uri newItemUri = ContentUris.withAppendedId(uri, rowId);
		// we should notify on basic URI, as nobody listens yet for unknown
		// id...
		getContext().getContentResolver().notifyChange(uri, null);
		return newItemUri;
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] valuesArray) {
		checkUri(uri);

		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		// ALL or nothing approach
		database.beginTransaction();
		try {
			for (ContentValues values : valuesArray) {
				long newID = database.insertOrThrow(DBConsts.TABLE_NAME, null,
						values);
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

	/**
	 * @see android.content.ContentProvider#query(Uri,String[],String,String[],String)
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(DBConsts.TABLE_NAME);

		switch (uriMatcher.match(uri)) {
		case EMPLOYEES:
			break;

		case EMPLOYEE_ID:
			queryBuilder.appendWhere(DBConsts._ID + "="
					+ uri.getLastPathSegment());
			break;

		default:
			throw new IllegalAccessError("Unknown URI: " + uri);
		}

		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		Cursor cursor = queryBuilder.query(database, projection, selection,
				selectionArgs, null, null, sortOrder);

		// Make sure that potential listeners are getting notified
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

			// SIMPLE TEST
			ContentValues values = new ContentValues();
			values.put(DBConsts.FIELD_FNAME, "Vasya");
			values.put(DBConsts.FIELD_SNAME, "Pupkin");
			values.put(DBConsts.FIELD_PROJECT, "Spring");
			values.put(DBConsts.FIELD_SYNC_STATUS,
					DBConsts.SYNC_STATUS.CREATE.ordinal());

			db.insert(DBConsts.TABLE_NAME, null, values);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DBConsts.TABLE_NAME);
			onCreate(db);
		}

	}

	private void checkUri(Uri uri) {
		if (uriMatcher.match(uri) != EMPLOYEES) {
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

}
