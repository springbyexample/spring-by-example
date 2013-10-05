package ua.com.springbyexample.dao;

import android.provider.BaseColumns;

public interface DBConsts extends BaseColumns {

    enum SYNC_STATUS {
        NOOP, CREATE, UPDATE, REMOVE
    }

    interface Tables {
        String EMPLOYEE = "employee";
    }

    interface Columns {
        public static final String _ID = "_id";
        public static final String ID = "id";
        public static final String FIRST_NAME = "first_name";
        public static final String SECOND_NAME = "second_name";
        public static final String PROJECT = "project";
        public static final String SYNC_STATUS = "sync_status";
    }

}
