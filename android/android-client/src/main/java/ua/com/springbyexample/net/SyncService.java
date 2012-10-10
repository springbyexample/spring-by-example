package ua.com.springbyexample.net;

import java.util.List;

import org.springframework.web.client.RestClientException;

import ua.com.springbyexample.dao.DBConsts;
import ua.com.springbyexample.dao.EmployeeDAO;
import ua.com.springbyexample.dao.model.Employee;
import ua.com.springbyexample.dao.provider.EmployeeContentProvider;
import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import de.akquinet.android.androlog.Log;

public class SyncService extends IntentService {

	public static final String EXTRA_FULL_SYNC = "EXTRA_FULL_SYNC";

	private Handler uiThreadHandler;

	private RestProcessor processor;

	public SyncService() {
		super(SyncService.class.getSimpleName());
		Log.d("Service created");

		processor = new RestProcessor(getApplicationContext());

		// TODO: replace with notifications like Twitter...
		uiThreadHandler = new Handler(new Handler.Callback() {

			public boolean handleMessage(Message msg) {
				Toast.makeText(getApplicationContext(), (String) msg.obj,
						Toast.LENGTH_LONG).show();
				return true;
			}
		});
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d("onHandleIntent");

		try {
			sendDeletedEmployees();
			sendModifiedEmployees();
			sendCreatedEmployees();

			// TODO: consider FULL and UPLOAD only sync
			refreshLocalCache();

		} catch (RestClientException e) {
			Log.e("Exception during sending request" + e.toString());
			Message.obtain(uiThreadHandler, 0,
					"SpringByExample: Sync was interrupted!").sendToTarget();
		}
		Message.obtain(uiThreadHandler, 0,
				"SpringByExample: Sync finished successfully!").sendToTarget();
	}

	@Override
	public void onDestroy() {
		Log.d("onDestroy");
	}

	private void refreshLocalCache() {
		List<Employee> employees = processor.fetchAll();

		// clean up local cache before full reload from server
		getContentResolver().delete(EmployeeContentProvider.CONTENT_URI, null,
				null);

		EmployeeDAO.save(getApplicationContext(), employees);
	}

	private void sendModifiedEmployees() {
		int status = DBConsts.SYNC_STATUS.UPDATE.ordinal();
		List<Employee> updatedEmployees = EmployeeDAO
				.load(buildSyncQuery(status));
		if (updatedEmployees.isEmpty()) {
			return;
		}
		processor.post(updatedEmployees);

	}

	private void sendDeletedEmployees() {
		int status = DBConsts.SYNC_STATUS.REMOVE.ordinal();
		List<Employee> removedEmployees = EmployeeDAO
				.load(buildSyncQuery(status));
		if (removedEmployees.isEmpty()) {
			return;
		}
		processor.delete(removedEmployees);
	}

	private void sendCreatedEmployees() {
		int status = DBConsts.SYNC_STATUS.CREATE.ordinal();
		List<Employee> createdEmployees = EmployeeDAO
				.load(buildSyncQuery(status));
		if (createdEmployees.isEmpty()) {
			return;
		}
		processor.post(createdEmployees);
	}

	private Cursor buildSyncQuery(int status) {
		// TODO: consider storing in DB status string instead of number...
		Cursor cursor = getContentResolver().query(
				EmployeeContentProvider.CONTENT_URI, null,
				EmployeeContentProvider.SYNC_STATUS + "= ?",
				new String[] { Integer.toString(status) }, null);
		return cursor;
	}

}
