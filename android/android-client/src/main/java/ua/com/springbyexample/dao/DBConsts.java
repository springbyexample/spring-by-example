package ua.com.springbyexample.dao;

import android.provider.BaseColumns;

public interface DBConsts extends BaseColumns {

	String FIELD_ID = "id";
	String FIELD_FNAME = "fist_name";
	String FIELD_SNAME = "second_name";
	String FIELD_PROJECT = "project";
	
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "employee.db";
	public static final String TABLE_NAME = "employee";

	public static final String DATABASE_CREATE = "create table " + TABLE_NAME
			+ " (" + _ID + " integer primary key autoincrement, " + FIELD_ID
			+ " integer, " + FIELD_FNAME + " text not null, " + FIELD_SNAME
			+ " text not null, " + FIELD_PROJECT + " text not null" + ")";

}
