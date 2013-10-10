package ua.com.springbyexample.net;

import android.content.Context;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import ua.com.springbyexample.R;
import ua.com.springbyexample.dao.model.Employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class RestProcessor {

    private static final String DEFAULT_SERVER_IP = "127.0.0.1";
    private final Context context;
    private final RestTemplate restTemplate;

    public RestProcessor(Context context) {
        this.context = context;
        restTemplate = new RestTemplate(true);
        //to solve issue: http://stackoverflow.com/questions/13182519/spring-rest-template-usage-causes-eofexception
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    public void post(List<Employee> employees) {

        HttpHeaders headers = new HttpHeaders();

        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptableMediaTypes);
        headers.setContentType(MediaType.APPLICATION_JSON);

        //TODO: do we need an array here?
        Employee[] employeeArray = employees.toArray(new Employee[employees.size()]);
        HttpEntity<Employee[]> requestEntity = new HttpEntity<Employee[]>(
                employeeArray, headers);

        restTemplate.exchange(getBulkServerUrl(), HttpMethod.POST, requestEntity, null);
    }

    public List<Employee> fetchAll() {
        Employee[] employees = restTemplate.getForObject(getServerUrl(),
                Employee[].class);
        ArrayList<Employee> list = new ArrayList<Employee>(employees.length);
        Collections.addAll(list, employees);
        return list;
    }

    public void delete(Employee employee) {
        Uri baseUri = Uri.parse(getServerUrl());
        Uri deleteItemUri = Uri.withAppendedPath(baseUri, employee.getId().toString());
        restTemplate.delete(deleteItemUri.toString());
    }

    private String getServerUrl() {
        String ip = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.key_server_ip), DEFAULT_SERVER_IP);
        if (TextUtils.isEmpty(ip)) {
            ip = DEFAULT_SERVER_IP;
        }
        return "http://" + ip + ":8080" + RestConsts.EMPLOYEE_ROOT_URL;
    }

    private String getBulkServerUrl() {
        return getServerUrl() + "/bulk";
    }

}
