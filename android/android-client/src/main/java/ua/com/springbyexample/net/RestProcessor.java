package ua.com.springbyexample.net;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import ua.com.springbyexample.R;
import ua.com.springbyexample.dao.model.Employee;
import android.content.Context;
import android.net.Uri;
import android.preference.PreferenceManager;
import de.akquinet.android.androlog.Log;

public final class RestProcessor {

	private final Context context;

	private final RestTemplate restTemplate;

	public RestProcessor(Context context) {
		this.context = context;
		restTemplate = new RestTemplate(true);
	}

	// TODO: add RestTemplate and specific URL processing

	public void post(List<Employee> employees) {
		// non-optimal workaround until bulk post if fixed...
		for (Employee employee : employees) {
			String response = restTemplate.postForObject(getServerUrl(),
					employee, String.class);
		}
		// Employee[] employeeArray = employee.toArray(new Employee[] {});

		// HttpHeaders headers = new HttpHeaders();
		//
		// List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		// acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		// headers.setAccept(acceptableMediaTypes);
		// headers.setContentType(MediaType.APPLICATION_JSON);
		//
		// HttpEntity<Employee[]> requestEntity = new HttpEntity<Employee[]>(
		// employeeArray, headers);
		//
		// ResponseEntity<String> response = restTemplate.exchange(
		// getBulkServerUrl(), HttpMethod.POST, requestEntity, null);
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

		RestTemplate restTemplate = new RestTemplate(true);
		Uri baseUri = Uri.parse(getServerUrl());

		for (Employee employee : employees) {
			Uri deleteItemUri = Uri.withAppendedPath(baseUri, employee.getId()
					.toString());
			restTemplate.delete(deleteItemUri.toString());
		}
	}

	public void update(List<Employee> employees) {
		// TODO: in our case URL is the same... for BULK

		// non-optimal workaround until bulk post if fixed...
		for (Employee employee : employees) {
			restTemplate.put(
					getServerUrl().concat(employee.getId().toString()),
					employee);
		}
	}

	private String getServerUrl() {
		String ip = PreferenceManager.getDefaultSharedPreferences(context)
				.getString(context.getString(R.string.key_server_ip),
						"127.0.0.1");
		String url = "http://" + ip + ":8080" + RestConsts.EMPLOYEE_ROOT_URL;
		Log.i("Server URL: " + url);
		return url;
	}

	private String getBulkServerUrl() {
		return getServerUrl() + "/bulk";
	}

}
