package ua.com.springbyexample.dao.provider;

import android.net.Uri;
import novoda.lib.sqliteprovider.provider.SQLiteContentProviderImpl;
import ua.com.springbyexample.dao.DBConsts;

/**
 * Created with IntelliJ IDEA.
 * User: akaverin
 * Date: 10/5/13
 * Time: 4:33 PM
 */
public class EmployeeContentProvider extends SQLiteContentProviderImpl {

    public static final String AUTHORITY = "ua.com.springbyexample.dao.provider.employeecontentprovider";
    public static final Uri CONTENT_URI_EMPLOYEE = Uri.withAppendedPath(Uri.parse("content://" + AUTHORITY),
            DBConsts.Tables.EMPLOYEE);

    @Override
    public void notifyUriChange(Uri uri) {
        if (uri.getBooleanQueryParameter(DBConsts.Params.NO_SYNC, false)) {
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            getContext().getContentResolver().notifyChange(uri, null, true);
        }
    }
}
