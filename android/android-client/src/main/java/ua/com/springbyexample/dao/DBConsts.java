package ua.com.springbyexample.dao;

import android.provider.BaseColumns;

public interface DBConsts extends BaseColumns {

	enum SYNC_STATUS {
		NOOP, CREATE, UPDATE, REMOVE
	}

}
