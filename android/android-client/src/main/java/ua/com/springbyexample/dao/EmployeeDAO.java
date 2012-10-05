package ua.com.springbyexample.dao;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;

import ua.com.springbyexample.dao.model.Employee;

public class EmployeeDAO {

	public static void save(Context context, List<Employee> employees) {

		ContentValues[] bulkValues = new ContentValues[employees.size()];
		for (int i = 0; i < employees.size(); ++i) {
			Employee employee = employees.get(i);
			bulkValues[i] = employeToValues(employee);
		}

		context.getContentResolver().bulkInsert(EmployeeProvider.CONTENT_URI,
				bulkValues);
	}

	private static ContentValues employeToValues(Employee employee) {
		ContentValues values = new ContentValues();
		values.put(DBConsts.FIELD_ID, employee.getId());
		values.put(DBConsts.FIELD_FNAME, employee.getFirstName());
		values.put(DBConsts.FIELD_SNAME, employee.getLastName());
		values.put(DBConsts.FIELD_PROJECT, employee.getProject());
		return values;
	}

}
