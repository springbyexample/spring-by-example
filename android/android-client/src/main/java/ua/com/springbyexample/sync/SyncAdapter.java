package ua.com.springbyexample.sync;

import android.accounts.Account;
import android.content.*;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import org.springframework.web.client.RestClientException;
import ua.com.springbyexample.dao.DBConsts;
import ua.com.springbyexample.dao.EmployeeDAO;
import ua.com.springbyexample.dao.model.Employee;
import ua.com.springbyexample.dao.provider.EmployeeContentProvider;
import ua.com.springbyexample.net.RestProcessor;
import ua.com.springbyexample.util.Tags;

import java.util.List;

import static ua.com.springbyexample.dao.provider.EmployeeContentProvider.CONTENT_URI_EMPLOYEE;

/**
 * Our Sync core implementation
 * Created with IntelliJ IDEA.
 * User: akaverin
 * Date: 10/5/13
 * Time: 7:13 PM
 */
class SyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String TAG = Tags.getTag(SyncAdapter.class);
    private final RestProcessor processor;
    private final ContentResolver contentResolver;

    public SyncAdapter(Context context, boolean autoInitialize) {
        this(context, autoInitialize, false);
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        processor = new RestProcessor(context);
        contentResolver = context.getContentResolver();
        Log.i(TAG, "SyncAdapter created!");
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider,
                              SyncResult syncResult) {

        Log.i(TAG, "onPerformSync, " + authority + ", " + extras);

        try {
            sendDeletedEmployees();
            sendModifiedEmployees();
            sendCreatedEmployees();

            if (!extras.getBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD)) {
                refreshAllFromServer();
            }

            Log.i(TAG, "SpringByExample: Sync finished successfully!");

        } catch (RestClientException e) {
            Log.w(TAG, "Exception during sending request", e);
        }
    }

    /**
     * Usually we should update and keep data in cache, as we might have Foreign keys, etc.
     * Also, we should define some range for data sync, like past months/weeks, etc.
     * But for simplicity we just reload data
     */
    private void refreshAllFromServer() {
        List<Employee> employees = processor.fetchAll();

        // clean up local cache before full reload from server
        contentResolver.delete(EmployeeContentProvider.CONTENT_URI_EMPLOYEE, null,
                null);

        EmployeeDAO.save(contentResolver, employees);
    }

    private void sendModifiedEmployees() {
        List<Employee> updatedEmployees = EmployeeDAO.load(buildSyncQuery(DBConsts.SYNC_STATUS.UPDATE
                .name()));
        if (updatedEmployees.isEmpty()) {
            return;
        }
        processor.post(updatedEmployees);

        changeStatusToNoop(DBConsts.SYNC_STATUS.UPDATE);
    }

    private void sendDeletedEmployees() {
        List<Employee> removedEmployees = EmployeeDAO
                .load(buildSyncQuery(DBConsts.SYNC_STATUS.REMOVE.name()));
        if (removedEmployees.isEmpty()) {
            return;
        }
        for (Employee employee : removedEmployees) {
            processor.delete(employee);
            contentResolver.delete(CONTENT_URI_EMPLOYEE, DBConsts.Columns.ID + "=?",
                    new String[]{employee.getId().toString()});
        }
    }

    private void sendCreatedEmployees() {
        List<Employee> createdEmployees = EmployeeDAO
                .load(buildSyncQuery(DBConsts.SYNC_STATUS.CREATE.name()));
        if (createdEmployees.isEmpty()) {
            return;
        }
        processor.post(createdEmployees);

        changeStatusToNoop(DBConsts.SYNC_STATUS.CREATE);
    }

    private void changeStatusToNoop(DBConsts.SYNC_STATUS oldStatus) {
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(DBConsts.Columns.SYNC_STATUS, DBConsts.SYNC_STATUS.NOOP.name());

        // change entities status
        contentResolver.update(CONTENT_URI_EMPLOYEE, contentValues, DBConsts.Columns.SYNC_STATUS + "=?",
                new String[]{oldStatus.name()});
    }

    private Cursor buildSyncQuery(String status) {
        return contentResolver.query(EmployeeContentProvider.CONTENT_URI_EMPLOYEE, null,
                DBConsts.Columns.SYNC_STATUS + "= ?", new String[]{status}, null);
    }
}
