package ua.com.springbyexample.net;

import java.util.List;

import org.springframework.web.client.RestClientException;

import de.akquinet.android.androlog.Log;

import ua.com.springbyexample.dao.EmployeeDAO;
import ua.com.springbyexample.dao.model.Employee;

import android.app.IntentService;
import android.content.Intent;

public class SyncService extends IntentService {

	public static final String EXTRA_FULL_SYNC = "EXTRA_FULL_SYNC";

	public SyncService() {
		super(SyncService.class.getSimpleName());
		Log.d("Service created");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d("onHandleIntent");

		RestProcessor processor = new RestProcessor(getApplicationContext());

		try {
			// TODO: support 2 type of operations: FETCH only and FULL SYNC
			// if (intent.getExtras() != null &&
			// intent.getExtras().getBoolean(EXTRA_FULL_SYNC, false)) {
			List<Employee> employees = processor.fetchAll();
			// TODO: add code to prevent duplications..., maybe just drop all
			// local data
			EmployeeDAO.save(getApplicationContext(), employees);
			// }

		} catch (RestClientException e) {
			Log.e("Exception during sending request" + e.toString());
		}

	}

	@Override
	public void onDestroy() {
		Log.d("onDestroy");
	}

}
