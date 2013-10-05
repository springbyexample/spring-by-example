package ua.com.springbyexample.net;

import android.content.Context;
import android.net.Uri;
import android.preference.PreferenceManager;
import de.akquinet.android.androlog.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import ua.com.springbyexample.R;
import ua.com.springbyexample.dao.model.Employee;

import java.util.ArrayList;
import java.util.List;

public final class RestProcessor {

    private final Context context;
    private final RestTemplate restTemplate;

    public RestProcessor(Context context) {
        this.context = context;
        restTemplate = new RestTemplate(true);
    }

    // TODO: add RestTemplate and specific URL processing

    public void post(List<Employee> employees) {
        Employee[] employeeArray = employees.toArray(new Employee[]{});

        HttpHeaders headers = new HttpHeaders();

        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptableMediaTypes);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Employee[]> requestEntity = new HttpEntity<Employee[]>(
                employeeArray, headers);

        restTemplate.exchange(getBulkServerUrl(), HttpMethod.POST, requestEntity, null);
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
