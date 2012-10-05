package ua.com.springbyexample.net;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import ua.com.springbyexample.R;
import ua.com.springbyexample.dao.model.Employee;
import android.content.Context;
import android.preference.PreferenceManager;
import de.akquinet.android.androlog.Log;

public class RestProcessor {

	private Context context;

	public RestProcessor(Context context) {
		this.context = context;
	}

	// TODO: add RestTemplate and specific URL processing

	public void post(List<Employee> employee) {

	}

	public List<Employee> fetchAll() {
		RestTemplate restTemplate = new RestTemplate(true);

		Employee[] employees = restTemplate.getForObject(getServerUrl(),
				Employee[].class);
		ArrayList<Employee> list = new ArrayList<Employee>(employees.length);
		for (Employee employee : employees) {
			list.add(employee);
		}
		return list;
	}

	public void delete(List<Employee> employees) {

	}

	public void update(List<Employee> employees) {

	}

	private String getServerUrl() {
		String ip = PreferenceManager.getDefaultSharedPreferences(context)
				.getString(context.getString(R.string.key_server_ip),
						"127.0.0.1");
		String url = "http://" + ip + ":8080" + RestConsts.EMPLOYEE_ROOT_URL;
		Log.i("Server URL: " + url);
		return url;
	}

}
