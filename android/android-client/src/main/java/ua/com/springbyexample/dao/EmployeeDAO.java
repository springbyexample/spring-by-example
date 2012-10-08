package ua.com.springbyexample.dao;

import java.util.List;

import ua.com.springbyexample.dao.model.Employee;
import ua.com.springbyexample.dao.provider.EmployeeContentProvider;
import android.content.ContentValues;
import android.content.Context;

/**
 * DAO layer to provider {@link Employee} POJO to {@link EmployeeProvider}
 * bridge. Useful when we work with POJOs obtained from REST API layer
 * 
 * @author akaverin
 * 
 */
public class EmployeeDAO {

	public static void save(Context context, List<Employee> employees) {

		ContentValues[] bulkValues = new ContentValues[employees.size()];
		for (int i = 0; i < employees.size(); ++i) {
			Employee employee = employees.get(i);
			bulkValues[i] = employeToValues(employee);
		}

		context.getContentResolver().bulkInsert(
				EmployeeContentProvider.CONTENT_URI, bulkValues);
	}

	private static ContentValues employeToValues(Employee employee) {
		ContentValues values = new ContentValues();
		values.put(EmployeeContentProvider.ID, employee.getId());
		values.put(EmployeeContentProvider.FIRST_NAME, employee.getFirstName());
		values.put(EmployeeContentProvider.SECOND_NAME, employee.getLastName());
		values.put(EmployeeContentProvider.PROJECT, employee.getProject());
		return values;
	}

}
