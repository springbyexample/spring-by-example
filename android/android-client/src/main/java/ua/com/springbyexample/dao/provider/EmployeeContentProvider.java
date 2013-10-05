package ua.com.springbyexample.dao.provider;

import android.net.Uri;
import novoda.lib.sqliteprovider.provider.SQLiteContentProviderImpl;
import ua.com.springbyexample.dao.DBConsts;

/**
 * Created with IntelliJ IDEA.
 * User: akaverin
 * Date: 10/5/13
 * Time: 4:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmployeeContentProvider extends SQLiteContentProviderImpl {

    public static final String AUTHORITY = "ua.com.springbyexample.dao.provider.employeecontentprovider";
    static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final Uri CONTENT_URI_EMPLOYEE = Uri.withAppendedPath(CONTENT_URI, DBConsts.Tables.EMPLOYEE);

}
